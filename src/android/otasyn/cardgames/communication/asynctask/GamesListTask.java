/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.communication.asynctask;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.Game;
import android.otasyn.cardgames.communication.dto.parse.JsonResponseParserUtility;
import android.otasyn.cardgames.communication.http.WebManager;

import java.util.List;

public class GamesListTask extends AsyncTask<String,String,List<Game>> {

    @Override
    protected List<Game> doInBackground(final String... params) {
        return gamesList();
    }

    private List<Game> gamesList() {
        String responseString = WebManager.get("http://cg.otasyn.net/games/list/json");
        return responseString != null ? JsonResponseParserUtility.parseGamesList(responseString) : null;
    }

}
