package ufeyes.com.ufeyes.dataLayer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import ufeyes.com.ufeyes.serviceLayer.Listeners.IQueryRequestListener;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestService;

/**
 * Created by carlo on 17/10/2017.
 */

public class QueryRequest extends AsyncTask<String,Void,String> {

    StringBuffer data = new StringBuffer();
    BufferedReader bufferReader = null;
    BufferedWriter bufferedWriter = null;
    String host ="10.7.40.82:1026";
    String request = "";
    String type;
    public QueryRequest(String type){
        this.type = type;
    }

    private IQueryRequestListener iQueryRequestListener = null;




    @Override
    protected String doInBackground(String... params) {

        String json = params[0];

        try {
            final URL url = new URL("http://"+host+"/v1/queryContext");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            final OutputStream outputStream = connection.getOutputStream();
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            // writer.write("{\"entities:\"[{\"type\":\"Room\",\"isPattern\":\"false\",\"id\":\"Room1\"}]}");
            //writer.write("{\"entities\":[{\"type\":\"Room\",\"isPattern\":\"false\",\"id\":\"Room1\"}]}");
            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();
            bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String linha = null;
            while (null != (linha = bufferReader.readLine())) {
                data.append(linha);
            }
            connection.disconnect();

        } catch (Exception e) {
            Log.e("Your tag", "Error", e);
        }




        return data.toString();

    }

    @Override
    protected void onPostExecute(String s){
        Log.i("DataLayer","QueryRequest response");
        if(type.equals("Vandalism")){
            iQueryRequestListener = new QueryRequestService();
            iQueryRequestListener.resultListenerVandalism(s);
        }
        if(type.equals("CarBreakIn")){
            iQueryRequestListener = new QueryRequestService();
            iQueryRequestListener.resultListenerCarBreakIn(s);
        }
        if(type.equals("Assalt")){
            iQueryRequestListener = new QueryRequestService();
            iQueryRequestListener.resultListenerAssalt(s);
        }
        if(type.equals("User")){
            iQueryRequestListener = new QueryRequestService();
            iQueryRequestListener.resultListenerUser(s);
        }
        if(type.equals("Localization")){
            iQueryRequestListener = new QueryRequestService();
            iQueryRequestListener.resultListenerLocalization(s);
        }
        if(type.equals("Thug")){
            iQueryRequestListener = new QueryRequestService();
            iQueryRequestListener.resultListenerLocaThug(s);
        }


    }


}
