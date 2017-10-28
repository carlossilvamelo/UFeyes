package ufeyes.com.ufeyes.serviceLayer;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ufeyes.com.ufeyes.MainActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ListenerService extends IntentService {

    private ObservableRequest observableRequest;
    public ListenerService() {
        super("ListenerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent myIntent = new Intent(".serviceLayer.MyReceiver");
        sendBroadcast(myIntent);
        Log.i("aa","aaaaaaaaaaaaaaaaaaaaa");


    }


}
