package ufeyes.com.ufeyes.domain;

import java.util.List;

/**
 * Created by carlo on 14/10/2017.
 */

public class Vandalism extends Ocorrencia{

    private List<Thug> thugList;

    public List<Thug> getThugList() {
        return thugList;
    }

    public void setThugList(List<Thug> thugList) {
        this.thugList = thugList;
    }

    public Vandalism(User usuario, Localization localizacao) {
        super(usuario, localizacao);

    }

    public Vandalism() {
    }
}
