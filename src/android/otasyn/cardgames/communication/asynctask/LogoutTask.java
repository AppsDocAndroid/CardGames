/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.communication.asynctask;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.parse.JsonResponseParserUtility;
import android.otasyn.cardgames.communication.http.WebManager;

public class LogoutTask extends AsyncTask<String,String,Boolean> {

    @Override
    protected Boolean doInBackground(final String... params) {
        return logout();
    }

    private Boolean logout() {
        String responseString = WebManager.post("http://cg.otasyn.net/users/logout/process");
        return responseString != null && JsonResponseParserUtility.parseSuccess(responseString);
    }

}
