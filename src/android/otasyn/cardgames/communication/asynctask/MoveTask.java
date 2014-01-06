package android.otasyn.cardgames.communication.asynctask;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.GameAction;
import android.otasyn.cardgames.communication.dto.parse.JsonResponseParserUtility;
import android.otasyn.cardgames.communication.http.WebManager;
import org.apache.http.message.BasicNameValuePair;

public class MoveTask extends AsyncTask<String,String,GameAction> {

    public AsyncTask<String, String, GameAction> execute(final String gameId, final String jsonGameState) {
        return super.execute(gameId, jsonGameState);
    }

    @Override
    protected GameAction doInBackground(final String... params) {
        return move(params[0], params[1]);
    }

    private GameAction move(final String gameId, final String jsonGameState) {
        String responseString = WebManager.post("http://cg.otasyn.net/game/action/move/json",
                                                new BasicNameValuePair("gameId", gameId),
                                                new BasicNameValuePair("gameState", jsonGameState));
        return responseString != null ? JsonResponseParserUtility.parseGameAction(responseString) : null;
    }

}