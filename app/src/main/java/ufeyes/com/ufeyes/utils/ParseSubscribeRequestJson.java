package ufeyes.com.ufeyes.utils;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Vandalism;

/**
 * Created by carlo on 30/10/2017.
 */

public class ParseSubscribeRequestJson {



    public static String jsonAssalt(String host){

        String json ="{\"entities\":[{\"type\" : \"Assalt\",\"isPattern\":\"false\",\"id\":\".*\"}],\"attributes\":[\"idUser\",\"idLocalization\"],\"reference\": \"http://"+host+"/accumulate\",\"duration\": \"P1M\"}";

        return json;
    }

    public static String jsonCarBreakIn(String host){

        String json = "{\"entities\":[{\"type\" : \"CarBreakIn\",\"isPattern\":\"false\",\"id\":\".*\"}],\"attributes\":[\"idUser\",\"idLocalization\",\"carColor\",\"carModel\"],\"reference\": \"http://"+host+"/accumulate\",\"duration\": \"P1M\"}";

        return json;
    }

    public static String jsonVandalism(String host){

        String json = "{\"entities\":[{\"type\" : \"Vandalism\",\"isPattern\":\"false\",\"id\":\".*\"}],\"attributes\":[\"idUser\",\"idLocalization\"],\"reference\": \"http://"+host+"/accumulate\",\"duration\": \"P1M\"}";

        return json;
    }
}
