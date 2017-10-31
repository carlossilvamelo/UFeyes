package ufeyes.com.ufeyes.serviceLayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

/**
 * Created by carlo on 30/10/2017.
 */

public class LocationService {
    private Activity activity;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Double latitude = null;
    private Double longitude = null;
    Location location;
    public LocationService(Activity activity) {
        this.activity = activity;
    }

    public void pegarOrientacao() {

        // tvLatitude = (TextView) getActivity().findViewById(R.id.tvLatitude);
        //  tvLongitude = (TextView) getActivity().findViewById(R.id.tvLongitude);



        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //permição em tempode xecução
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);

        } else {
            android.location.LocationManager locationManager = (android.location.LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);


        }
        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            // tvLatitude.setText("Latitude: " + latitude);
            // tvLongitude.setText("Longitude: " + longitude);
        }


    }
}
