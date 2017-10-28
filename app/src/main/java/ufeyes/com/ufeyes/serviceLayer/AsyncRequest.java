package ufeyes.com.ufeyes.serviceLayer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by carlo on 24/10/2017.
 */

public class AsyncRequest extends AsyncTask<Context,Void,Void> {


    @Override
    protected Void doInBackground(Context... params) {
        Toast.makeText(params[0],"chegou",Toast.LENGTH_LONG).show();
        return null;
    }
}
