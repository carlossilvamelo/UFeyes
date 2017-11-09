package ufeyes.com.ufeyes.serviceLayer.Listeners;

/**
 * Created by carlo on 03/11/2017.
 */

public interface IQueryRequestListener {

    public void resultListenerVandalism(String json);
    public void resultListenerAssalt(String json);
    public void resultListenerCarBreakIn(String json);
    public void resultListenerUser(String json);
    public void resultListenerLocalization(String json);
    public void resultListenerLocaThug(String json);

}
