package ufeyes.com.ufeyes.serviceLayer;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by carlo on 24/10/2017.
 */

public class NotificationService extends IntentService{
    private ObservableRequest observableRequest;
    public NotificationService() {
        super("threadNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        observableRequest = (ObservableRequest) intent.getSerializableExtra("observableRequest");
        observableRequest.setJson("service");
        Log.i("observer", "service intent");

    }


}
