package ufeyes.com.ufeyes.serviceLayer;

import ufeyes.com.ufeyes.dataLayer.SubscribeAssaltEntity;
import ufeyes.com.ufeyes.dataLayer.SubscribeCarBreakInEntity;
import ufeyes.com.ufeyes.dataLayer.SubscribeVandalismEntity;
import ufeyes.com.ufeyes.utils.ParseSubscribeRequestJson;

/**
 * Created by carlo on 30/10/2017.
 */

public class SubscribeRequestService {
    SubscribeAssaltEntity subscribeAssaltEntity = new SubscribeAssaltEntity();
    SubscribeVandalismEntity subscribeVandalismEntity = new SubscribeVandalismEntity();
    SubscribeCarBreakInEntity subscribeCarBreakInEntity = new SubscribeCarBreakInEntity();
    private String host;

    public SubscribeRequestService(String host) {
        this.host = host+":8080";
    }


    public void setSubscribeAllEntities(){
        subscribeAssaltEntity.execute(ParseSubscribeRequestJson.jsonAssalt(host));
        subscribeVandalismEntity.execute(ParseSubscribeRequestJson.jsonCarBreakIn(host));
        subscribeCarBreakInEntity.execute(ParseSubscribeRequestJson.jsonVandalism(host));
    }

}
