package ufeyes.com.ufeyes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.serviceLayer.Listeners.IRequestOcorrenceListener;
import ufeyes.com.ufeyes.serviceLayer.MapOccurrencesService;
import ufeyes.com.ufeyes.utils.ContextElement;
import ufeyes.com.ufeyes.utils.ParseContextElement;

public class MapOccurrencesActivity extends FragmentActivity implements OnMapReadyCallback, IRequestOcorrenceListener {

    private GoogleMap mMap;
    private LatLng ufrn = new LatLng(-5.8382418,-35.2096425);
    private ArrayList<MarkerOptions> listOccurrence;


    //recebe o resultado do processamento do service
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Bundle bundle = intent.getParcelableExtra("bundle");
    System.out.println("Serviço Ocorrencias");
            if (intent != null) {
                ArrayList<MarkerOptions> occurrences =  intent.getParcelableArrayListExtra("occurrences");
                System.out.println("Num occorrencias: " + occurrences.size());
                listOccurrence = occurrences;
                for(MarkerOptions mo: occurrences){
                    mMap.addMarker(mo);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_occurrence2);


        // use this to start and trigger a service
        Intent intent= new Intent(this, MapOccurrencesService.class);
        // potentially add data to the intent
        startService(intent);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //registra quem estara pronto pra receber o evento
        registerReceiver(receiver, new IntentFilter("ufeyes.com.ufeyes"));

    }
    @Override
    protected void onPause() {
        super.onPause();
        //remove do reggitro
        unregisterReceiver(receiver);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //System.out.println("Ocorrencia: "+occurrence.getTitle());
        mMap.setMinZoomPreference(17);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ufrn));
    }

    @Override
    public void resultListenerVandalism(List<Vandalism> vandalism) {

    }

    @Override
    public void resultListenerAssalt(List<Assalt> assalt) {

    }

    @Override
    public void resultListenerCarBreakIn(List<CarBreakIn> carBreakIn) {

    }
}