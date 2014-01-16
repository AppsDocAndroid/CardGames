/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.communication.asynctask;

import android.os.AsyncTask;
import android.otasyn.cardgames.communication.dto.Friend;
import android.otasyn.cardgames.communication.enumeration.GameType;
import android.otasyn.cardgames.communication.http.WebManager;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.Set;

public class CreateGameTask extends AsyncTask<Object,String,Void> {

    public AsyncTask<Object,String,Void> execute(final GameType gameType, final Set<Friend> players) {
        return super.execute(gameType, players);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void doInBackground(final Object... params) {
        return accept((GameType) params[0], (Set<Friend>) params[1]);
    }

    private Void accept(final GameType gameType, final Set<Friend> players) {
        NameValuePair[] params = new NameValuePair[1 + players.size()];
        params[0] = new BasicNameValuePair("gameTypeId", String.valueOf(gameType.getId()));
        int n = 1;
        for (Friend friend : players) {
            params[n++] = new BasicNameValuePair("playerIds", String.valueOf(friend.getId()));
        }
        String responseString = WebManager.post("http://cg.otasyn.net/games/create/json", params);
        //return responseString != null ? JsonResponseParserUtility.parseGame(responseString) : null;
        return null;
    }

}
