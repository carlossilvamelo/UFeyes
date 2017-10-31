package ufeyes.com.ufeyes.serviceLayer;

import ufeyes.com.ufeyes.dataLayer.InsertRequest;
import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.utils.ParseInsertRequestJson;

/**
 * Created by carlo on 30/10/2017.
 */

public class InsertRequestService {
    InsertRequest insertRequest = new InsertRequest();


    public void insertAssaltEntity(Assalt assalt){

        insertRequest.execute(ParseInsertRequestJson.jsonAssalt(assalt));
    }

    public void insertCarBreakInEntity(CarBreakIn carBreakIn){

        insertRequest.execute(ParseInsertRequestJson.jsonCarBreakIn(carBreakIn));
    }

    public void insertVandalismEntity(Vandalism vandalism){

        insertRequest.execute(ParseInsertRequestJson.jsonVandalism(vandalism));
    }

}
