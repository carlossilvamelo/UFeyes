package ufeyes.com.ufeyes.serviceLayer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by carlo on 24/10/2017.
 */

public class RequestObserver implements Observer {
    Observable observableRequest;

    String json;

    public RequestObserver(Observable revistaInformatica) {
        this.observableRequest = revistaInformatica;
        revistaInformatica.addObserver(this);
    }


    public void update(Observable observable, Object arg) {
        if (observable instanceof ObservableRequest) {
            ObservableRequest observableRequest = (ObservableRequest) observable;
            json = observableRequest.getEdicao();
            System.out.println(json);
        }

    }

}
