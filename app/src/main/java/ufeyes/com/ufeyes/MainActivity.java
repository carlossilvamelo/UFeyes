package ufeyes.com.ufeyes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import ufeyes.com.ufeyes.serviceLayer.ListenerService;
import ufeyes.com.ufeyes.serviceLayer.NotificationListener;
import ufeyes.com.ufeyes.serviceLayer.NotificationService;
import ufeyes.com.ufeyes.serviceLayer.ObservableRequest;
import ufeyes.com.ufeyes.serviceLayer.SubscribeApp;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

public class MainActivity extends AppCompatActivity
        implements Observer, NavigationView.OnNavigationItemSelectedListener
        , FragmentEstatisticas.OnFragmentInteractionListener
        , PrincipalFragment.OnFragmentInteractionListener
        ,MapaOcorrenciasFragment.OnFragmentInteractionListener{


    private Observable observableRequest;
    private String json;


    private Menu menu;
    private NavigationView navigationView = null;
    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        PrincipalFragment fragmentInicial = new PrincipalFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.corrent_layout,fragmentInicial).commit();

        ObservableRequest observableRequest = new ObservableRequest();
        construtorObservable(observableRequest);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//listener de notificações
        NotificationListener notificationListener = new NotificationListener(observableRequest);
        notificationListener.start();


     //   Toast.makeText(this, "asdasd", Toast.LENGTH_SHORT).show();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //verificando é a primeira vez que o aplicativo é aberto
        //para sobrescrever nas entidades do oreon
        SubscribeApp subscribeApp = new SubscribeApp();
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
        this.menu = menu;
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
            PrincipalFragment fragPrincipal = new PrincipalFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.corrent_layout,fragPrincipal).commit();
        } else if (id == R.id.nav_gallery) {
            FragmentEstatisticas fragmentEstatisticas = new FragmentEstatisticas();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.corrent_layout,fragmentEstatisticas).commit();
        }else if (id == R.id.mapa_ocorrencias) {

            MapaOcorrenciasFragment fragMapa = new MapaOcorrenciasFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.corrent_layout, fragMapa).commit();


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public Menu getMenu(){
        return this.menu;
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
     * */
    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof ObservableRequest) {
            ObservableRequest observableRequest = (ObservableRequest) observable;
            json = observableRequest.getEdicao();
          //  toolbar.setTitle(json);
           // getSupportActionBar().setTitle("asd");
            Log.i("update","metodo update chamado na main");
          //  Toast.makeText(getApplicationContext(),"recebeu",Toast.LENGTH_LONG).show();
            runOnUiThread(new Runnable()
            {
                public void run()
                {

                    Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
                }
            });
           //System.out.println("chegou");
        }
    }




}
