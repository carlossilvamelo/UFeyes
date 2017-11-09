package ufeyes.com.ufeyes.serviceLayer;

import ufeyes.com.ufeyes.dataLayer.InsertRequest;
import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Thug;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.utils.ParseInsertRequestJson;

/**
 * Created by carlo on 30/10/2017.
 */

public class InsertRequestService {
    InsertRequest insertRequestOccorrence = new InsertRequest();
    InsertRequest insertRequestUser = new InsertRequest();
    InsertRequest insertRequestLocalization = new InsertRequest();
    InsertRequest insertRequestThug;


    public void insertAssaltEntity(Assalt assalt){


        insertRequestUser.execute(ParseInsertRequestJson.jsonUser(assalt.getUsuario()));
        insertRequestLocalization.execute(ParseInsertRequestJson.jsonLocalization(assalt.getLocalizacao()));

        if(assalt.getThugList() != null){
            for (Thug thug: assalt.getThugList()) {
                insertRequestThug = new InsertRequest();
                insertRequestThug.execute(ParseInsertRequestJson.jsonThug(thug));
            }
        }

        insertRequestOccorrence.execute(ParseInsertRequestJson.jsonAssalt(assalt));
    }

    public void insertCarBreakInEntity(CarBreakIn carBreakIn){
        insertRequestUser.execute(ParseInsertRequestJson.jsonUser(carBreakIn.getUsuario()));
        insertRequestLocalization.execute(ParseInsertRequestJson.jsonLocalization(carBreakIn.getLocalizacao()));
        insertRequestOccorrence.execute(ParseInsertRequestJson.jsonCarBreakIn(carBreakIn));
    }

    public void insertVandalismEntity(Vandalism vandalism){
        insertRequestUser.execute(ParseInsertRequestJson.jsonUser(vandalism.getUsuario()));
        insertRequestLocalization.execute(ParseInsertRequestJson.jsonLocalization(vandalism.getLocalizacao()));
        if(vandalism.getThugList() != null){
            for (Thug thug: vandalism.getThugList()) {
                insertRequestThug = new InsertRequest();
                insertRequestThug.execute(ParseInsertRequestJson.jsonThug(thug));
            }
        }
        insertRequestOccorrence.execute(ParseInsertRequestJson.jsonVandalism(vandalism));
    }

}
