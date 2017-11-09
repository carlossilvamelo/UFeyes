package ufeyes.com.ufeyes.serviceLayer;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gustavo on 06/11/2017.
 */

public class DataApiService extends IntentService{

    // temporary string to show the parsed response
    private String jsonResponse;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView texto;

    public DataApiService() {
        super("DataApiService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        String urlJsonObj = "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info";
        OAuthTokenRequest.getInstance().resourceRequest(this, Request.Method.GET, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String nome = jsonObject.getString("nome");
                    String login = jsonObject.getString("login");

                    jsonResponse = "";
                    jsonResponse += "Name: " + nome + "\n\n";
                    jsonResponse += "Login: " + login + "\n\n";

                    texto.setText(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("SAIDA", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });


    }
}
