/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.otasyn.cardgames.R;
import android.otasyn.cardgames.activity.game.BlackjackGameActivity;
import android.otasyn.cardgames.activity.game.CardGameActivity;
import android.otasyn.cardgames.activity.game.FivesGameActivity;
import android.otasyn.cardgames.activity.game.FreestyleGameActivity;
import android.otasyn.cardgames.activity.widget.GameRow;
import android.otasyn.cardgames.communication.asynctask.AcceptGameTask;
import android.otasyn.cardgames.communication.asynctask.CurrentUserTask;
import android.otasyn.cardgames.communication.asynctask.StartGameTask;
import android.otasyn.cardgames.communication.dto.Friend;
import android.otasyn.cardgames.communication.dto.Game;
import android.otasyn.cardgames.communication.dto.GamePlayer;
import android.otasyn.cardgames.communication.dto.SimpleUser;
import android.otasyn.cardgames.communication.enumeration.GameType;
import android.otasyn.cardgames.communication.utility.AccountUtility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GamesListActivity extends Activity {

    public static void launch(final Context context) {
        SimpleUser user = null;
        try {
            user = new CurrentUserTask().execute().get();
        } catch (Exception e) { }

        if (user != null) {
            context.startActivity(new Intent(context, GamesListActivity.class));
        } else {
            LoginActivity.setNextDestination(GamesListActivity.class);
            Intent loginIntent = new Intent(context, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(loginIntent);
        }
    }

    private final List<Game> games = new ArrayList<Game>();
    private SimpleUser currentUser;
    private List<Friend> friends;

    private LinearLayout gamesListLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        gamesListLayout = (LinearLayout) findViewById(R.id.gamesListLayout);

        currentUser = AccountUtility.currentUser();
        friends = AccountUtility.retrieveFriendsList();
        gamesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_create_game){
            Log.d("CardGames", "Create Game selected.");
            GamesListActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Map<Friend, CheckBox> friendsCheckboxes = new HashMap<Friend, CheckBox>(friends.size());
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GamesListActivity.this);
                    LayoutInflater inflater = GamesListActivity.this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.popup_create_game, null);
                    final AlertDialog alertDialog = alertBuilder
                            .setView(dialogView)
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, final int which) {
                                    Spinner gameTypeSpinner = (Spinner) dialogView.findViewById(R.id.gameTypeSpinner);
                                    String gameTypeName = String.valueOf(gameTypeSpinner.getSelectedItem());
                                    GameType gameType = GameType.findGameType(gameTypeName);

                                    Set<Friend> selectedFriends = new HashSet<Friend>();
                                    for (Map.Entry<Friend, CheckBox> entry : friendsCheckboxes.entrySet()) {
                                        if (entry.getValue() != null && entry.getValue().isChecked()) {
                                            selectedFriends.add(entry.getKey());
                                        }
                                    }

                                    boolean success = AccountUtility.createGame(gameType, selectedFriends);
                                    if (success) {
                                        gamesList();
                                    }

                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, final int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    alertDialog.show();

                    Spinner gameTypeSpinner = (Spinner) alertDialog.findViewById(R.id.gameTypeSpinner);
                    ArrayAdapter<String> gameTypeAdapter = new ArrayAdapter<String>(GamesListActivity.this,
                            android.R.layout.simple_spinner_item);
                    for (GameType gameType : GameType.values()) {
                        if (gameType != GameType.UNKNOWN) {
                            gameTypeAdapter.add(gameType.getName());
                        }
                    }
                    gameTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gameTypeSpinner.setAdapter(gameTypeAdapter);

                    if (friends != null) {
                        LinearLayout gameFriendsLayout = (LinearLayout) alertDialog.findViewById(R.id.gameFriendsLayout);
                        gameFriendsLayout.removeAllViews();
                        for (Friend friend : friends) {
                            CheckBox friendCheckbox = new CheckBox(GamesListActivity.this);
                            friendCheckbox.setText(friend.getFirstname() + " " + friend.getLastname());
                            gameFriendsLayout.addView(friendCheckbox);
                            friendsCheckboxes.put(friend, friendCheckbox);
                        }
                    }
                }
            });
            return true;
        }
        return false;
    }

    private void gamesList() {
        games.clear();
        games.addAll(AccountUtility.retrieveGamesList());

        if (!games.isEmpty()) {
            gamesListLayout.removeAllViews();
            for (Game game : games) {
                GameRow gameLayout = new GameRow(this, game, currentUser);
                gameLayout.setOnClickListener(new GameClickListener());
                gamesListLayout.addView(gameLayout);
            }
        }
    }

    private void playGame(final Game game) {
        Intent gameIntent = null;
        switch (game.getGameType()) {
            case BLACKJACK:
                gameIntent = new Intent(GamesListActivity.this, BlackjackGameActivity.class);
                break;
            case FIVES:
                gameIntent = new Intent(GamesListActivity.this, FivesGameActivity.class);
                break;
            case FREESTYLE:
                gameIntent = new Intent(GamesListActivity.this, FreestyleGameActivity.class);
                break;
            default:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GamesListActivity.this);
                alertBuilder
                        .setTitle("Game Type Not Supported")
                        .setMessage("This game type is not supported on this device.")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
        }
        if (gameIntent != null) {
            gameIntent.putExtra(CardGameActivity.GAME_INFO, game);
            gameIntent.putExtra(CardGameActivity.CURRENT_USER, currentUser);
            GamesListActivity.this.startActivity(gameIntent);
        }
    }

    private void askToAccept(final Game game, final GameRow gameRow, final SimpleUser currentUser) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GamesListActivity.this);
        alertBuilder
                .setTitle("Pending")
                .setMessage("Would you like to accept this game?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        accept(game, gameRow, currentUser);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void accept(final Game game, final GameRow gameRow, final SimpleUser currentUser) {
        Game newGame;
        try {
            newGame = new AcceptGameTask().execute(game).get();
        } catch (Exception e) {
            newGame = null;
        }
        if (newGame != null) {
            gameRow.setGame(newGame);
            selectGame(game, gameRow, currentUser);
        }
    }

    private void askToStart(final Game game, final GameRow gameRow, final SimpleUser currentUser) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GamesListActivity.this);
        alertBuilder
                .setTitle("Not Yet Started")
                .setMessage("Would you like to start this game?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        start(game, gameRow, currentUser);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void start(final Game game, final GameRow gameRow, final SimpleUser currentUser) {
        Game newGame;
        try {
            newGame = new StartGameTask().execute(game).get();
        } catch (Exception e) {
            newGame = null;
        }
        if (newGame != null) {
            gameRow.setGame(newGame);
            selectGame(newGame, gameRow, currentUser);
        }
    }

    private void alertPending() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GamesListActivity.this);
        alertBuilder
                .setTitle("Pending")
                .setMessage("Not all players have accepted the game.")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void selectGame(final Game game, final GameRow gameRow, final SimpleUser currentUser) {
        if (game != null) {
            if (Boolean.TRUE.equals(game.getStarted())) {
                playGame(game);
            } else {
                Iterator<GamePlayer> iter;
                GamePlayer currentPlayer = null;
                int numPending = 0;
                for (iter = game.getPlayers().iterator(); iter.hasNext();) {
                    GamePlayer gamePlayer = iter.next();
                    if (currentUser.equals(gamePlayer)) {
                        currentPlayer = gamePlayer;
                    }
                    if (!Boolean.TRUE.equals(gamePlayer.getAccepted())) {
                        numPending++;
                    }
                }
                if (currentPlayer != null) {
                    if (!Boolean.TRUE.equals(currentPlayer.getAccepted())) {
                        askToAccept(game, gameRow, currentUser);
                    } else if (numPending == 0) {
                        askToStart(game, gameRow, currentUser);
                    } else {
                        alertPending();
                    }
                }
            }
        }
    }

    private class GameClickListener extends GameRow.OnClickListener {
        @Override
        public void onClick(final Game game, final GameRow gameRow, final SimpleUser currentUser) {
            selectGame(game, gameRow, currentUser);
        }
    }

}