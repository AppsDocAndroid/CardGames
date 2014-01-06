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
import android.otasyn.cardgames.activity.widget.GameRow;
import android.otasyn.cardgames.communication.asynctask.AcceptGameTask;
import android.otasyn.cardgames.communication.asynctask.CurrentUserTask;
import android.otasyn.cardgames.communication.asynctask.StartGameTask;
import android.otasyn.cardgames.communication.dto.Game;
import android.otasyn.cardgames.communication.dto.GamePlayer;
import android.otasyn.cardgames.communication.dto.SimpleUser;
import android.otasyn.cardgames.communication.utility.AccountUtility;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private LinearLayout gamesListLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        gamesListLayout = (LinearLayout) findViewById(R.id.gamesListLayout);

        currentUser = AccountUtility.currentUser();
        gamesList();
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