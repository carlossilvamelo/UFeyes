package ufeyes.com.ufeyes.serviceLayer.Listeners;

/**
 * Created by carlo on 31/10/2017.
 */

public interface IRequestOcorrenceListener {

    public void resultListenerVandalism(String result);
    public void resultListenerAssalt(String result);
    public void resultListenerCarBreakIn(String result);

}
