package ufeyes.com.ufeyes.serviceLayer;

import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ufeyes.com.ufeyes.MainActivity;
import ufeyes.com.ufeyes.R;
import ufeyes.com.ufeyes.serviceLayer.Listeners.INotificationListener;

/**
 * Created by carlo on 06/12/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    NotificationCreator notificationCreator = null;
    public MyFirebaseMessagingService() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //  Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d("onMessageReceived", "onMessageReceived");
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //    Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d("msg", remoteMessage.getData()+"");
            NotificationCreator notecreate = new NotificationCreator(getApplicationContext());
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            notecreate.sendNotification(getApplicationContext(), resultIntent, "Nova ocorrência", "Nova ocorrência na UFRN",
                    001);


            Intent intentBC = new Intent("ufeyes.com.ufeyes.notif");
            intentBC.putExtra("change_bell", true);
            sendBroadcast(intentBC);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
