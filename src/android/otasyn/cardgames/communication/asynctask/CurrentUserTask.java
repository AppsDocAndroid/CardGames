/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.communication.asynctask;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.SimpleUser;
import android.otasyn.cardgames.communication.dto.parse.JsonResponseParserUtility;
import android.otasyn.cardgames.communication.http.WebManager;

public class CurrentUserTask extends AsyncTask<String,String,SimpleUser> {

    @Override
    protected SimpleUser doInBackground(final String... params) {
        return currentuser();
    }

    private SimpleUser currentuser() {
        String responseString = WebManager.get("http://cg.otasyn.net/users/currentuser/json");
        return responseString != null ? JsonResponseParserUtility.parseUser(responseString) : null;
    }

}
