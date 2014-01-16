package android.otasyn.cardgames.communication.asynctask.games.blackjack;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.GameAction;
import android.otasyn.cardgames.communication.dto.parse.JsonResponseParserUtility;
import android.otasyn.cardgames.communication.http.WebManager;
import org.apache.http.message.BasicNameValuePair;

public class BlackjackBetTask extends AsyncTask<String,String,GameAction> {

    public AsyncTask<String, String, GameAction> execute(final String gameId, final String bet) {
        return super.execute(gameId, bet);
    }

    @Override
    protected GameAction doInBackground(final String... params) {
        return bet(params[0], params[1]);
    }

    private GameAction bet(final String gameId, final String bet) {
        String responseString = WebManager.post("http://cg.otasyn.net/game/blackjack/action/bet/json",
                new BasicNameValuePair("gameId", gameId),
                new BasicNameValuePair("bet", bet));
        return responseString != null ? JsonResponseParserUtility.parseGameAction(responseString) : null;
    }

}