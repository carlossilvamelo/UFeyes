package ufeyes.com.ufeyes.serviceLayer;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.dataLayer.OccurrenceDAO;
import ufeyes.com.ufeyes.domain.Occurrence;

/**
 * Created by gustavo on 25/11/2017.
 */

public class AlertService extends IntentService {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters


    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0; // 1 minute

    private ArrayList<MarkerOptions> occurrences = new ArrayList<MarkerOptions>();

    private LocationManager locationManager;
    private LocationListener locationListener;


    public AlertService() {
        super("AlertService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        System.out.println("Chamada ao alert service!");
        Log.d("AlertService", "Iniciado...");


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                System.out.println("Mudança na localização");
                int lat = (int) (location.getLatitude());
                int lng = (int) (location.getLongitude());


                ArrayList<Double> dif = new ArrayList<Double>();
                for(MarkerOptions mo: occurrences){
                    float[] val = new float[1];
                    Location.distanceBetween(location.getLatitude(),location.getLongitude(), mo.getPosition().longitude, mo.getPosition().longitude, val);
                    dif.add((double) val[1]);
                    System.out.println("Distancia ente os pontos: " +val[1]);
                }

                Intent intent = new Intent("ufeyes.com.ufeyes");
                //bundle.putParcelableArrayListE("occurrences", occurrences);
                intent.putExtra("alert", "Nova localização: "+lat + ", "+ lng);

                sendBroadcast(intent);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        };
        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                MIN_TIME_BW_UPDATES,
                locationListener);

    }


}
