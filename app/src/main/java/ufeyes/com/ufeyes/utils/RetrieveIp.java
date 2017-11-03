package ufeyes.com.ufeyes.utils;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by carlo on 30/10/2017.
 */

public class RetrieveIp {
    private static String range = "10.";


    public static String retrieveIP(){
        String ip =null;
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
                    if (ia.getHostAddress().contains(range))
                        ip = ia.getHostAddress();

                    //System.out.println(ia.getHostAddress());
                }
            }

        }

        return ip;
    }
}
