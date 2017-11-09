package ufeyes.com.ufeyes;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.domain.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotification extends Fragment {

    private int[] IMAGES = {R.drawable.assalto, R.drawable.arrombamento_carro, R.drawable.vandalismo};
    private ListView listNotif;
    private ArrayList<Ocorrencia> listOcurr;
    private ArrayAdapter<Ocorrencia> adaptOcurr;
    private View parentView;
    private int max_item_count = 20;

    public FragmentNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_principal, container, false);
        listNotif = (ListView) parentView.findViewById(R.id.lst_items);
        listOcurr = new ArrayList<Ocorrencia>();
        updateListOcurr();

        adaptOcurr = new ArrayAdapter<Ocorrencia>(parentView.getContext(),
                android.R.layout.simple_selectable_list_item, android.R.id.text1, listOcurr);

        listNotif.setAdapter(adaptOcurr);

        listNotif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return parentView;
    }

    private void updateListOcurr(){
        for(int i = 0;i < 5; ++i){
            Ocorrencia tempOcurr = new Ocorrencia();
            tempOcurr.setId(""+i);
            User usu = new User();
            usu.setIdUser("usu"+i);
            usu.setCondition(i);
            tempOcurr.setUsuario(usu);
            listOcurr.add(tempOcurr);
        }
    }

    

}
