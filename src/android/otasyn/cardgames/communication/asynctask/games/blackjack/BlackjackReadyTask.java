package android.otasyn.cardgames.communication.asynctask.games.blackjack;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.GameAction;
import android.otasyn.cardgames.communication.dto.parse.JsonResponseParserUtility;
import android.otasyn.cardgames.communication.http.WebManager;
import org.apache.http.message.BasicNameValuePair;

public class BlackjackReadyTask extends AsyncTask<String,String,GameAction> {

    public AsyncTask<String, String, GameAction> execute(final String gameId) {
        return super.execute(gameId);
    }

    @Override
    protected GameAction doInBackground(final String... params) {
        return ready(params[0]);
    }

    private GameAction ready(final String gameId) {
        String responseString = WebManager.post("http://cg.otasyn.net/game/blackjack/action/ready/json",
                new BasicNameValuePair("gameId", gameId));
        return responseString != null ? JsonResponseParserUtility.parseGameAction(responseString) : null;
    }

}