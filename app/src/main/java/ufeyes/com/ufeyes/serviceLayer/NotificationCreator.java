package ufeyes.com.ufeyes.serviceLayer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import ufeyes.com.ufeyes.MainActivity;
import ufeyes.com.ufeyes.R;

/**
 * Created by carlo on 29/10/2017.
 */

public class NotificationCreator {
    private NotificationCompat.Builder builder;

    public NotificationCreator(Context context){
        this.builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo);


    }

    public void sendNotification(Context context, Intent destination, String title, String content, int mId){
        this.builder.setContentTitle(title);
        this.builder.setContentText(content);
        this.builder.setAutoCancel(true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(destination);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        this.builder.setContentIntent(resultPendingIntent);
        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        myNotificationManager.notify(mId, this.builder.build());
    }
}
