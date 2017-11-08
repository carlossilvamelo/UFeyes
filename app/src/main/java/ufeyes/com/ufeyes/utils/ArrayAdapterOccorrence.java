package ufeyes.com.ufeyes.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ufeyes.com.ufeyes.R;

import java.util.List;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.domain.Vandalism;

/**
 * Created by carlo on 08/11/2017.
 */

public class ArrayAdapterOccorrence extends BaseAdapter {

    private final List<Ocorrencia> occorrenceList;
    private final Activity act;

    public ArrayAdapterOccorrence(List<Ocorrencia> occorrenceList, Activity act) {
        this.occorrenceList = occorrenceList;
        this.act = act;
    }

    @Override
    public int getCount() {
        return occorrenceList.size();
    }

    @Override
    public Object getItem(int position) {
        return occorrenceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.layout_listview_ocorrencias, parent, false);

        Ocorrencia occorrence = occorrenceList.get(position);

        TextView titulo = (TextView) view.findViewById(R.id.lvTitulo);
        TextView descricao = (TextView) view.findViewById(R.id.lvDescricao);
        ImageView imagem = (ImageView) view.findViewById(R.id.lvImagem);


        descricao.setText(occorrence.getLocalizacao().getTimeStamp().toString());



        if (occorrence instanceof Assalt) {
            view.setBackgroundColor(Color.parseColor("#f7f5c7"));
            titulo.setText("Assalto");
            imagem.setImageResource(R.drawable.assalto);
        } else if (occorrence instanceof CarBreakIn) {
            view.setBackgroundColor(Color.parseColor("#fcd5c5"));
            titulo.setText("Arrombamento de carro");
            imagem.setImageResource(R.drawable.arrombamento_carro);
        } else if (occorrence instanceof Vandalism) {
            view.setBackgroundColor(Color.parseColor("#c7f6f7"));
            titulo.setText("Vandalismo");
            imagem.setImageResource(R.drawable.vandalismo);
        }

        return view;
    }
}
