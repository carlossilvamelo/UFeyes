package ufeyes.com.ufeyes;

import android.*;
import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import ufeyes.com.ufeyes.dataLayer.OccurrenceDAO;
import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Localization;
import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.domain.User;
import ufeyes.com.ufeyes.domain.UserA;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.enumerations.EnumUserCondition;
import ufeyes.com.ufeyes.serviceLayer.AlertService;
import ufeyes.com.ufeyes.serviceLayer.InsertRequestService;
import ufeyes.com.ufeyes.serviceLayer.LocationService;
import ufeyes.com.ufeyes.serviceLayer.MapOccurrencesService;
import ufeyes.com.ufeyes.utils.TimestampManager;
import ufeyes.com.ufeyes.utils.UsuarioLogado;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    private OnFragmentInteractionListener mListener;
    private GoogleMap map;

    private LatLng ufrn = new LatLng(-5.8382418, -35.2096425);
    private ArrayList<MarkerOptions> listOccurrence;

    private Location location;
    private LocationManager locationManager;


    private double latitude;
    private double longitude;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen=false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static AlertDialog alert;

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

        fabLayout1= (LinearLayout) view.findViewById(R.id.fabLayout1);
        fabLayout2= (LinearLayout) view.findViewById(R.id.fabLayout2);
        fabLayout3= (LinearLayout) view.findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2= (FloatingActionButton) view.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) view.findViewById(R.id.fab3);
        fabBGLayout = view.findViewById(R.id.fabBGLayout);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pegarOrientacao();

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout, null);

                Button btConfirm = (Button) view.findViewById(R.id.btConfirmar);
                btConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.assalto, 0, 0, 0);
                btConfirm.setText("Confirmar Assalto");
                view.findViewById(R.id.btCancelar).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alert.dismiss();
                    }

                });

                view.findViewById(R.id.btConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = "Assalto";
                        confirmarDenuncia(type);
                        alert.dismiss();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                alert = builder.create();
                alert.show();

            }

        });
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pegarOrientacao();

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout, null);

                Button btConfirm = (Button) view.findViewById(R.id.btConfirmar);
                btConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrombamento_carro, 0, 0, 0);
                btConfirm.setText("Confirmar Arrombamento");

                view.findViewById(R.id.btCancelar).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alert.dismiss();
                    }

                });

                view.findViewById(R.id.btConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = "Arrombamento";
                        confirmarDenuncia(type);
                        alert.dismiss();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                alert = builder.create();
                alert.show();

            }

        });
        fab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pegarOrientacao();

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout, null);

                Button btConfirm = (Button) view.findViewById(R.id.btConfirmar);
                btConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vandalismo, 0, 0, 0);
                btConfirm.setText("Confirmar Vandalismo");

                view.findViewById(R.id.btCancelar).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alert.dismiss();
                    }

                });

                view.findViewById(R.id.btConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = "Vandalismo";
                        confirmarDenuncia(type);
                        alert.dismiss();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                alert = builder.create();
                alert.show();
            }

        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });

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

    public void confirmarDenuncia(String type){
        //setando usuário
        User newUser = new User();
        newUser.setCondition(EnumUserCondition.WITNESS.ordinal());
        newUser.setIdUser("ID" + TimestampManager.getTimeStamp());

        //obtendo localização
        LocationService locationService = new LocationService(getActivity());
        locationService.pegarOrientacao();
        Localization newLocalization = new Localization();
        newLocalization.setLatitude(locationService.getLatitude());
        newLocalization.setLongitude(locationService.getLongitude());
        newLocalization.setIdLocalizacao("ID" + TimestampManager.getTimeStamp());

        InsertRequestService insertRequestService = new InsertRequestService();
        Ocorrencia ocorrencia = null;
        switch (type){
            case "Assalto":
                ocorrencia = new Assalt();
                ocorrencia.setId(TimestampManager.getTimeStamp());
                ocorrencia.setUsuario(newUser);
                ocorrencia.setLocalizacao(newLocalization);

                //enviando requisição
                insertRequestService.insertAssaltEntity((Assalt) ocorrencia);
                break;
            case "Arrombamento":
                //setando arrombamento
                ocorrencia = new CarBreakIn();
                ocorrencia.setId(new Timestamp(System.currentTimeMillis()).toString());
                ocorrencia.setUsuario(newUser);
                ocorrencia.setLocalizacao(newLocalization);

                //enviando requisição
                insertRequestService.insertCarBreakInEntity((CarBreakIn) ocorrencia);
                break;
            case "Vandalismo":
                //setando assalto
                ocorrencia = new Vandalism();
                ocorrencia.setId(new Timestamp(System.currentTimeMillis()).toString());
                ocorrencia.setUsuario(newUser);
                ocorrencia.setLocalizacao(newLocalization);

                //enviando requisição
                insertRequestService.insertVandalismEntity((Vandalism) ocorrencia);
                break;
            default:
                break;
        }

        if(ocorrencia != null){
            LatLng latlng = new LatLng(ocorrencia.getLocalizacao().getLatitude(),
                    ocorrencia.getLocalizacao().getLongitude());
            MarkerOptions marker = new MarkerOptions().position(latlng)
                    .title(type)
                    .snippet(new Date().toString())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_arrombamento));

            map.addMarker(marker);
            map.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        }


        UsuarioLogado user = UsuarioLogado.getInstance("2013021629", getContext());

        //UserA usuarioLogado = user.getUser();
        UserA usuarioLogado = new UserA("19281","Usuario Anonimo","Nao definido" );
        //cadastra uma nova ocorrencia no bd
        String time  = new Date().toString();
        Log.d("Data da localizacao ", time);
        Log.d("Usuario logado ", usuarioLogado.getNome());

        OccurrenceDAO Odao = new OccurrenceDAO(getContext());
        boolean sucessOccurrenc = Odao.salvar(time, latitude, longitude, type, usuarioLogado.getId(), time);
        if(sucessOccurrenc){
            System.out.println("Ocorrência inserida com sucesso: " + type);
        }

    }

    private void enviarOcorrencia(Ocorrencia oc){
        UsuarioLogado user = UsuarioLogado.getInstance("0001", getContext());

        UserA usuarioLogado = user.getUser();

        //cadastra uma nova ocorrencia no bd
        String time  = new Date().toString();
        Log.d("Data da localizacao ", time);
        Log.d("Usuario logado ", usuarioLogado.getNome());


        OccurrenceDAO Odao = new OccurrenceDAO(getContext());
        boolean sucessOccurrenc = Odao.salvar(time, latitude, longitude, "Vandalismo", usuarioLogado.getId(), time);
        if(sucessOccurrenc){
            System.out.println("Success occurrence vandalism");
        }
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                    fabBGLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void pegarOrientacao() {

        // tvLatitude = (TextView) getActivity().findViewById(R.id.tvLatitude);
        //  tvLongitude = (TextView) getActivity().findViewById(R.id.tvLongitude);


        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //permição em tempode xecução
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);

        } else {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            //location = locationManager
            //.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            location = locationManager
                    .getLastKnownLocation(
                            LocationManager.PASSIVE_PROVIDER
                    );

        }
        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("location:",latitude +", "+longitude);
            // tvLatitude.setText("Latitude: " + latitude);
            // tvLongitude.setText("Longitude: " + longitude);
        }


    }
}
