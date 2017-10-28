package ufeyes.com.ufeyes.serviceLayer;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by carlo on 24/10/2017.
 */

public class ObservableRequest extends Observable implements Serializable{
    private String json;

    public void setJson(String novaEdicao) {
        this.json = novaEdicao;

        setChanged();
        notifyObservers();
    }

    public String getEdicao() {
        return this.json;
    }
}
