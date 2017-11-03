package ufeyes.com.ufeyes.serviceLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by carlo on 17/10/2017.
 */

public class SubscribeVerificationService {


    public static void verifySubscribe(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("subscribe", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("acess1")) {
            Log.i("subscrib", "Host já foi subescrito nas entidades de contexto");
        }else {
            Log.i("subscrib", "Host NÃO foi subescrito nas entidades de contexto");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("acess1", "ok");
            editor.apply();


            Log.i("subscrib", "Host subescrito nas entidades de contexto");
        }
    }
}
