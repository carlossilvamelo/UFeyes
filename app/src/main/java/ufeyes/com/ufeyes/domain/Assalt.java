package ufeyes.com.ufeyes.domain;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by carlo on 14/10/2017.
 */

public class Assalt extends Ocorrencia {

    private List<Thug> thugList;


    public Assalt(User usuario, Localization localizacao) {
        super(usuario, localizacao);
    }

    public Assalt() {
    }

    public String parseJson(){
        Gson json = new Gson();
        return null;
    }

}
