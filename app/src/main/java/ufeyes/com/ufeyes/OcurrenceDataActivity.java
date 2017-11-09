package ufeyes.com.ufeyes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.domain.Vandalism;

public class OcurrenceDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Ocorrencia ocurrence = new Ocorrencia();
        if(intent != null){
            ocurrence = (Ocorrencia) intent.getSerializableExtra("occurrence");
        }

        setContentView(R.layout.activity_ocurrence_data);
        ImageView ocurrImg = (ImageView) findViewById(R.id.ocurrImage);
        TextView ocurrType = (TextView) findViewById(R.id.ocurrType);
        TextView ocurrDescript = (TextView) findViewById(R.id.ocurrDescript);
        Button ocurrMapButton = (Button) findViewById(R.id.mapRedirectButton);

        ocurrMapButton.setEnabled(false);
        if (ocurrence instanceof Assalt) {
            ocurrImg.setBackgroundColor(Color.parseColor("#f7f5c7"));
            ocurrType.setText("Assalto");
            ocurrImg.setImageResource(R.drawable.assalto);
            ocurrDescript.setText("Aconteceu um assalto no circular às 13:25, tinham 2 caras armados.");
        } else if (ocurrence instanceof CarBreakIn) {
            ocurrImg.setBackgroundColor(Color.parseColor("#fcd5c5"));
            ocurrType.setText("Arrombamento de carro");
            ocurrImg.setImageResource(R.drawable.arrombamento_carro);
            ocurrDescript.setText("Vi arrombarem um carro no estacionamento do CB, era um palio verde.");
        } else if (ocurrence instanceof Vandalism) {
            ocurrImg.setBackgroundColor(Color.parseColor("#c7f6f7"));
            ocurrType.setText("Vandalismo");
            ocurrImg.setImageResource(R.drawable.vandalismo);
            ocurrDescript.setText("Tavam pixando ali na lateral da biblioteca.");
        }


    }
}
