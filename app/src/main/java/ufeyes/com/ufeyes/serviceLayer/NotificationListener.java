package ufeyes.com.ufeyes.serviceLayer;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import ufeyes.com.ufeyes.MainActivity;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * Created by carlo on 24/10/2017.
 */

public class NotificationListener extends Thread{
    private ObservableRequest observableRequest;



    public NotificationListener(ObservableRequest observableRequest) {
        this.observableRequest = observableRequest;
    }

    private static InetAddress ia;
    private static String ip;

    @Override
    public void run(){


        Log.i("NotificationListener","thread run");
        // Toast.makeText(context.getApplicationContext(), "asdasd", Toast.LENGTH_SHORT).show();

        // pi android 177.20.157.36
        // System.out.println("Servidor Iniciado!!");
        retrieveIP();
        ServerSocket serverSocket = null;
        boolean listening = true;
        try {
            serverSocket = new ServerSocket(1026);
            Log.i("NotificationListener","thread loop");
            while (listening) {
                Socket socket = serverSocket.accept();

                // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner s  = new Scanner(new InputStreamReader(socket.getInputStream()));
                String leitura = "";
                String aux;
                while(true){
                    aux = s.nextLine();

                    if(aux.equals(""))
                        break;
                }
                while(s.hasNextLine()){
                    leitura += s.nextLine();
                }


                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                //System.out.println(leitura );
                out.write("A String enviada foi: \n"+leitura );

                JSONObject jo;
                JSONArray ja;

                try {
                    jo = new JSONObject(leitura);
                    ja = jo.getJSONArray("contextResponses");
                    //System.out.println(ja.getJSONObject(0));//imprime id
                    // Log.i("json",ja.getJSONObject(0).toString());
                    // observableRequest.setJson(ja.getJSONObject(0).toString())
                    Log.i("RequestResponse",ja.getJSONObject(0).toString());
                    updateObserver(ja.getJSONObject(0).toString());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } //objetos é o meu o array json


                out.close();
                s.close();
                socket.close();
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Log.i("NotificationListener","thread loop finish");
    }


    private void retrieveIP(){
        Enumeration<NetworkInterface> nis = null;

        try {
            nis = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (nis.hasMoreElements()) {
            try {
                nis = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ias.nextElement();
                    if (ia.getHostAddress().contains("10."))
                        Log.d("IP", ia.getHostAddress()+"==========================");
                        //System.out.println(ia.getHostAddress());
                }
            }

        }


    }


    public void updateObserver(String json){
       // Looper.prepare();

        observableRequest.setJson(json);
        //Looper.loop();
    }


}
