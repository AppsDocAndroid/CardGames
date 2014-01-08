package android.otasyn.cardgames.communication.asynctask.games.freestyle;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.GameAction;
import android.otasyn.cardgames.communication.dto.parse.JsonResponseParserUtility;
import android.otasyn.cardgames.communication.http.WebManager;
import org.apache.http.message.BasicNameValuePair;

public class FreestyleTurnTask extends AsyncTask<String,String,GameAction> {

    public AsyncTask<String, String, GameAction> execute(final String gameId, final String jsonGameState) {
        return super.execute(gameId, jsonGameState);
    }

    @Override
    protected GameAction doInBackground(final String... params) {
        return turn(params[0], params[1]);
    }

    private GameAction turn(final String gameId, final String jsonGameState) {
        String responseString = WebManager.post("http://cg.otasyn.net/game/freestyle/action/turn/json",
                                                new BasicNameValuePair("gameId", gameId),
                                                new BasicNameValuePair("gameState", jsonGameState));
        return responseString != null ? JsonResponseParserUtility.parseGameAction(responseString) : null;
    }

}