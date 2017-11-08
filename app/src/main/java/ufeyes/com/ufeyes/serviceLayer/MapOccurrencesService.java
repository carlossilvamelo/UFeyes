package ufeyes.com.ufeyes.serviceLayer;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ufeyes.com.ufeyes.R;

/**
 * Created by gustavo on 03/11/2017.
 */

public class MapOccurrencesService extends IntentService {


    private ArrayList<MarkerOptions> occurrences = new ArrayList<MarkerOptions>();

    public MapOccurrencesService() {
        super("OccurrencesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        System.out.println("Chamada ao service!");

        occurrences.add(new MarkerOptions().position(new LatLng(-5.8382418,-35.2096425))
                .title("Arrombamento")
                .snippet("20/10/2017:15:10:12")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_arrombamento)));
        occurrences.add(new MarkerOptions().position(new LatLng(-5.8322137,-35.2044957))
                .title("Assalto")
                .snippet("20/10/2017:15:10:12")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_assalto)));
        occurrences.add(new MarkerOptions().position(new LatLng(-5.833201,-35.2034012))
                .title("Vandalismo")
                .snippet("20/10/2017:15:10:12")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_vandalismo)));
        occurrences.add(new MarkerOptions().position(new LatLng(-5.8375343,-35.1986939))
                .title("Assalto")
                .snippet("20/10/2017:15:10:12")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_assalto)));


        publishResults();

    }



    private void publishResults() {
        Intent intent = new Intent("ufeyes.com.ufeyes");
  //bundle.putParcelableArrayListE("occurrences", occurrences);
        intent.putParcelableArrayListExtra("occurrences", occurrences);

        sendBroadcast(intent);
    }


}
