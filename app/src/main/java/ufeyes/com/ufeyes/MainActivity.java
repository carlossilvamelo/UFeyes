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
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ufeyes.com.ufeyes.dataLayer.UserDAO;
import ufeyes.com.ufeyes.domain.UserA;
import ufeyes.com.ufeyes.serviceLayer.AlertService;
import ufeyes.com.ufeyes.serviceLayer.MapOccurrencesService;
import ufeyes.com.ufeyes.serviceLayer.NotificationCreator;
import ufeyes.com.ufeyes.serviceLayer.Listeners.NotificationListener;
import ufeyes.com.ufeyes.serviceLayer.ObservableRequest;
import ufeyes.com.ufeyes.utils.RetrieveIp;
import ufeyes.com.ufeyes.serviceLayer.SubscribeVerificationService;
import ufeyes.com.ufeyes.serviceLayer.SubscribeRequestService;
import ufeyes.com.ufeyes.utils.UsuarioLogado;

public class MainActivity extends AppCompatActivity
        implements Observer, NavigationView.OnNavigationItemSelectedListener
        , FragmentEstatisticas.OnFragmentInteractionListener
        , PrincipalFragment.OnFragmentInteractionListener
        , MinhasDenunciasFragment.OnFragmentInteractionListener
        , FragmentNotification.OnFragmentInteractionListener
        , OnMapReadyCallback {

    private GoogleMap map;
    private Observable observableRequest;
    private String json;

    private UserA user;
    private UserDAO userDAO;

    private NavigationView navigationView = null;
    private Toolbar toolbar = null;

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
                    map.addMarker(mo).showInfoWindow();
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

                    Toast.makeText(getParent(), alert,Toast.LENGTH_LONG).show();

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UserDAO Udao = new UserDAO(this.activity.getApplicationContext());
        //boolean sucessUser = Udao.salvar("2013021629", "Gustavo Henrique", "Masculino");
        //if(sucessUser){
        // System.out.println("Success user");
        //}

        //pegando o usuario no br e colocando como usuario logado
        UsuarioLogado user = UsuarioLogado.getInstance("2013021629", getApplicationContext());
        UserA usuarioLogado = user.getUser();

        // use this to start and trigger a service
        Intent occurrenceService = new Intent(this, MapOccurrencesService.class);
        // potentially add data to the intent
        startService(occurrenceService);

        Intent alertService = new Intent(this, AlertService.class);
        // potentially add data to the intent
        startService(alertService);

        //subscrevendo nas entidades de contexto

        SubscribeRequestService subscribeRequestService = new SubscribeRequestService(RetrieveIp.retrieveIP());
        subscribeRequestService.setSubscribeAllEntities();

        PrincipalFragment fragmentInicial = new PrincipalFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.corrent_layout, fragmentInicial).commit();

        ObservableRequest observableRequest = new ObservableRequest();
        construtorObservable(observableRequest);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //listener de notificações
        NotificationListener notificationListener = new NotificationListener(observableRequest);
        notificationListener.start();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        //colocando o nome do usuário na tela de menu
        if (usuarioLogado != null) {
            TextView username_tv = (TextView) v.findViewById(R.id.username);

            username_tv.setText(usuarioLogado.getNome());
        }

        //verificando é a primeira vez que o aplicativo é aberto
        //para sobrescrever nas entidades do oreon
        SubscribeVerificationService subscribeApp = new SubscribeVerificationService();
        subscribeApp.verifySubscribe(getApplicationContext());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_notification) {
            FragmentNotification fragNotif = new FragmentNotification();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.corrent_layout, fragNotif).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.denuncias) {
            // Handle the camera action
            PrincipalFragment fragPrincipal = new PrincipalFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.corrent_layout, fragPrincipal).commit();
        } else if (id == R.id.minhas_denuncias) {
            MinhasDenunciasFragment minhasDenunciasFragment = new MinhasDenunciasFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.corrent_layout, minhasDenunciasFragment).commit();
        } else if (id == R.id.estatisticas) {
            FragmentEstatisticas fragmentEstatisticas = new FragmentEstatisticas();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.corrent_layout, fragmentEstatisticas).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void construtorObservable(Observable obs) {
        this.observableRequest = obs;
        obs.addObserver(this);
    }


    /**
     * Metodo listener das requisições do oreo (notificações)
     */
    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof ObservableRequest) {
            ObservableRequest observableRequest = (ObservableRequest) observable;
            json = observableRequest.getEdicao();
            //  toolbar.setTitle(json);
            // getSupportActionBar().setTitle("asd");
            Log.i("update", "metodo update chamado na main");
            //  Toast.makeText(getApplicationContext(),"recebeu",Toast.LENGTH_LONG).show();
            runOnUiThread(new Runnable() {
                public void run() {
                    NotificationCreator notecreate = new NotificationCreator(getApplicationContext());
                    Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                    notecreate.sendNotification(getApplicationContext(), resultIntent, "Testando", "Texto da notificação",
                            001);
                    MenuItem notifMenu = toolbar.getMenu().findItem(R.id.action_notification);
                    notifMenu.setIcon(R.drawable.notification_received);
                    // Teste de atualização

                    Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
                }
            });
            //System.out.println("chegou");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    protected void onResume() {
        super.onResume();

        //registra quem estara pronto pra receber o evento
        registerReceiver(receiver, new IntentFilter("ufeyes.com.ufeyes"));
        registerReceiver(receiverAlert, new IntentFilter("ufeyes.com;ufeyes"));

    }
    @Override
    protected void onPause() {
        super.onPause();
        //remove do reggitro
        unregisterReceiver(receiver);
        unregisterReceiver(receiverAlert);
    }
}
