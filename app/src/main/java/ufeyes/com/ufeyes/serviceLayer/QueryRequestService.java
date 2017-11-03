package ufeyes.com.ufeyes.serviceLayer;

import ufeyes.com.ufeyes.dataLayer.QueryRequest;
import ufeyes.com.ufeyes.utils.ParseQueryRequestJson;

/**
 * Created by carlo on 30/10/2017.
 */

public class QueryRequestService {
    QueryRequest queryRequestVandalism = new QueryRequest("Vandalism");
    QueryRequest queryRequestCarBreakIn = new QueryRequest("CarBreakIn");
    QueryRequest queryRequestAssalt = new QueryRequest("Assalt");


    public void getAllAssalt(){
        queryRequestAssalt.execute(ParseQueryRequestJson.jsonAllAssalt());
    }
    public void getAllCarBreakIn(){
        queryRequestCarBreakIn.execute(ParseQueryRequestJson.jsonAllCarBreakIn());
         }
    public void getAllVandalism(){
        queryRequestVandalism.execute(ParseQueryRequestJson.jsonAllVandalism());
    }

    public void getAssaltByUserId(String userId){}
    public void getCarBreakInByUserId(String userId){}
    public void getVandalismByUserId(String userId){}

}
