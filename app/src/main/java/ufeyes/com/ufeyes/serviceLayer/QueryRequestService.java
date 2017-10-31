package ufeyes.com.ufeyes.serviceLayer;

import ufeyes.com.ufeyes.dataLayer.QueryRequest;
import ufeyes.com.ufeyes.utils.ParseQueryRequestJson;

/**
 * Created by carlo on 30/10/2017.
 */

public class QueryRequestService {
    QueryRequest queryRequest = new QueryRequest("");


    public void getAllAssalt(){
        queryRequest.execute(ParseQueryRequestJson.jsonAllAssalt());
    }
    public void getAllCarBreakIn(){
        queryRequest.execute(ParseQueryRequestJson.jsonAllCarBreakIn());
         }
    public void getAllVandalism(){
        queryRequest.execute(ParseQueryRequestJson.jsonAllVandalism());
    }

    public void getAssaltByUserId(String userId){}
    public void getCarBreakInByUserId(String userId){}
    public void getVandalismByUserId(String userId){}

}
