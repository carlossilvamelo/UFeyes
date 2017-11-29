package ufeyes.com.ufeyes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ufeyes.com.ufeyes.serviceLayer.AlertService;
import ufeyes.com.ufeyes.serviceLayer.MapOccurrencesService;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    private OnFragmentInteractionListener mListener;
    private GoogleMap map;

    private LatLng ufrn = new LatLng(-5.8382418, -35.2096425);


    private ArrayList<MarkerOptions> listOccurrence;


    //recebe o resultado do processamento do service
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Bundle bundle = intent.getParcelableExtra("bundle");
            System.out.println("Serviço Ocorrencias");

            if (intent != null) {

                ArrayList<MarkerOptions> occurrences = intent.getParcelableArrayListExtra("occurrences");
                System.out.println("Num occorrencias: " + occurrences.size());
                listOccurrence = occurrences;

                for (MarkerOptions mo : occurrences) {
                    map.addMarker(mo);
                    map.moveCamera(CameraUpdateFactory.newLatLng(mo.getPosition()));
                }
            }
        }
    };

    private BroadcastReceiver receiverAlert = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Bundle bundle = intent.getParcelableExtra("bundle");
            System.out.println("Serviço alert");
            if (intent != null) {
                String alert = intent.getStringExtra("alert");

                Toast.makeText(context, alert,Toast.LENGTH_LONG).show();

            }
        }
    };

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // use this to start and trigger a service
        Intent occurrenceService = new Intent(getActivity(), MapOccurrencesService.class);
        // potentially add data to the intent
        getActivity().startService(occurrenceService);

        Intent alertService = new Intent(getActivity(), AlertService.class);
        // potentially add data to the intent
        getActivity().startService(alertService);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_map, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        //System.out.println("Ocorrencia: "+occurrence.getTitle());
        map.setMinZoomPreference(17);
        LatLngBounds UFRN = new LatLngBounds(
                new LatLng(-5.8400137,-35.2125469), new LatLng(-5.8347,-35.1964387));
        // Constrain the camera target to the Adelaide bounds.
        //map.setLatLngBoundsForCameraTarget(UFRN);
        map.moveCamera(CameraUpdateFactory.newLatLng(ufrn));

    }

    @Override
    public  void onResume() {
        super.onResume();

        //registra quem estara pronto pra receber o evento
        getActivity().registerReceiver(receiver, new IntentFilter("ufeyes.com.ufeyes"));
        getActivity().registerReceiver(receiverAlert, new IntentFilter("ufeyes.com;ufeyes"));

    }
    @Override
    public void onPause() {
        super.onPause();
        //remove do reggitro
        getActivity().unregisterReceiver(receiver);

        getActivity().unregisterReceiver(receiverAlert);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
