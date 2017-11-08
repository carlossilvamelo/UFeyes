package ufeyes.com.ufeyes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.domain.Vandalism;

public class EditarOcorrenciaActivity extends AppCompatActivity {
private Button btAddThug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ocorrencia);
        getSupportActionBar().setTitle("Edição de ocorrência");


        btAddThug = (Button) findViewById(R.id.btAddThug);


       Ocorrencia occorrence = (Ocorrencia) getIntent().getSerializableExtra("occorrence");
        if(occorrence instanceof Assalt){
            Assalt assalt = (Assalt) occorrence;
        }
        if(occorrence instanceof CarBreakIn){
            btAddThug.setVisibility(View.INVISIBLE);
            CarBreakIn carBreakIn = (CarBreakIn) occorrence;
        }
        if(occorrence instanceof Vandalism){
            Vandalism vandalism = (Vandalism) occorrence;
        }

    }
}
