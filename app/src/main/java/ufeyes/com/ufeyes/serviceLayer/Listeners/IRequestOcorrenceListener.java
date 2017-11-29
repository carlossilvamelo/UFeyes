package ufeyes.com.ufeyes.serviceLayer.Listeners;

import java.util.List;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Vandalism;


/**
 * Created by carlo on 31/10/2017.
 */

public interface IRequestOcorrenceListener {

    public void resultListenerVandalism(List<Vandalism> vandalism);
    public void resultListenerAssalt(List<Assalt> assalt);
    public void resultListenerCarBreakIn(List<CarBreakIn> carBreakIn);

}
