package ufeyes.com.ufeyes.serviceLayer;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.R;
import ufeyes.com.ufeyes.dataLayer.OccurrenceDAO;
import ufeyes.com.ufeyes.domain.Occurrence;

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

        OccurrenceDAO Odao = new OccurrenceDAO(getApplicationContext());
        List<Occurrence> list = Odao.listAll();

        for(Occurrence o: list){

            switch (o.getType()){

                case "Arrombamento":

                    occurrences.add(new MarkerOptions().position(new LatLng(o.getLat(),o.getLng()))
                            .title(o.getType())
                            .snippet(o.getDate())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_arrombamento)));
                    break;

                    case "Assalto":

                    occurrences.add(new MarkerOptions().position(new LatLng(o.getLat(),o.getLng()))
                            .title(o.getType())
                            .snippet(o.getDate())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_assalto)));
                    break;

                default:

                    occurrences.add(new MarkerOptions().position(new LatLng(o.getLat(),o.getLng()))
                            .title(o.getType())
                            .snippet(o.getDate())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mp_vandalismo)));
                    break;

            }



        }

        publishResults();

    }



    private void publishResults() {
        Intent intent = new Intent("ufeyes.com.ufeyes");
  //bundle.putParcelableArrayListE("occurrences", occurrences);
        intent.putParcelableArrayListExtra("occurrences", occurrences);

        sendBroadcast(intent);
    }


}
