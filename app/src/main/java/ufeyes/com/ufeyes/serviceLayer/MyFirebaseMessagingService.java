package ufeyes.com.ufeyes.serviceLayer;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("msg", remoteMessage.getNotification().getBody()+"");
            
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
