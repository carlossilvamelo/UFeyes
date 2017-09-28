package ufeyes.com.ufeyes;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by carlo on 19/09/2017.
 */

public class RequestHTTP extends AsyncTask<String, Integer, String>{




    public HttpURLConnection conectarServidor(){
        URL url = null;
        HttpURLConnection httpUrlConnection = null;
        try {
            url = new URL("http://google.com/");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            return httpUrlConnection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String request(){
        HttpURLConnection cliente = conectarServidor();
        String retorno = null;
        try {
            cliente.setRequestMethod("POST");
            cliente.setRequestProperty("Key","Value");
            cliente.setDoOutput(true);
            OutputStream outputPost = new BufferedOutputStream(cliente.getOutputStream());
            retorno = outputPost.toString();
            outputPost.flush();
            outputPost.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }


    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPreExecute(){
        //Codigo
    }

    @Override
    protected void onPostExecute(String numero){
        //Codigo
    }
    protected void onProgressUpdate(String params){
        //Codigo
    }
}
