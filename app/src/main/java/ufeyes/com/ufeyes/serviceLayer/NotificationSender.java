package ufeyes.com.ufeyes.serviceLayer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by carlo on 06/12/2017.
 */

public class NotificationSender extends AsyncTask<String,Void,String> {

    StringBuffer data = new StringBuffer();
    BufferedReader bufferReader = null;
    BufferedWriter bufferedWriter = null;
    String host ="10.7.40.90:1026";
    String request = "";
    String type;





    @Override
    protected String doInBackground(String... params) {

        String json = params[0];

        try {
            final URL url = new URL("https://fcm.googleapis.com/fcm/send");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key=AAAA2ONznE0:APA91bF5aYWIq93jW7MraQlTPoXUJrjA6tpqIywGnO2c2lpNacQ796uxs7sjNEeQSRqcYg3LxWq9g6DEWSs0PICsKgP1I2jc9UlsS4VePwcmvGB9x44J3S_ex5tEfrfcJYJqdTKG-7TQ");

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

    protected void onPostExecute(String s) {
        Log.i("response", s);


    }
}
