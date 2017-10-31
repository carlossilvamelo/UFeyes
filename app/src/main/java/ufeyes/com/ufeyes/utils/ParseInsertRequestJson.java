package ufeyes.com.ufeyes.utils;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Vandalism;

/**
 * Created by carlo on 30/10/2017.
 */

public class ParseInsertRequestJson {


    public static String jsonAssalt(Assalt assalt){

        String json = "{\"contextElements\": [{\"type\" : \"Assalt\",\"isPattern\": \"false\",\"id\": \""+assalt.getId()+"\",\"attributes\":[{\"name\":\"idUser\",\"type\":\"string\",\"value\":\""+assalt.getUsuario().getIdUser()+"\"},{\"name\":\"idLocalization\",\"type\":\"string\",\"value\":\""+assalt.getLocalizacao().getIdLocalizacao()+"\"}]}],\"updateAction\": \"APPEND\"}";



        return json;
    }

    public static String jsonCarBreakIn(CarBreakIn carBreakIn){

        String json = "{\"contextElements\": [{\"type\" : \"CarBreakIn\",\"isPattern\": \"false\",\"id\": \""+carBreakIn.getId()+"\",\"attributes\":[{\"name\":\"idUser\",\"type\":\"string\",\"value\":\""+carBreakIn.getUsuario().getIdUser()+"\"},{\"name\":\"idLocalization\",\"type\":\"string\",\"value\":\""+carBreakIn.getLocalizacao().getIdLocalizacao()+"\"}]}],\"updateAction\": \"APPEND\"}";



        return json;
    }

    public static String jsonVandalism(Vandalism vandalism){

        String json = "{\"contextElements\": [{\"type\" : \"Vandalism\",\"isPattern\": \"false\",\"id\": \""+vandalism.getId()+"\",\"attributes\":[{\"name\":\"idUser\",\"type\":\"string\",\"value\":\""+vandalism.getUsuario().getIdUser()+"\"},{\"name\":\"idLocalization\",\"type\":\"string\",\"value\":\""+vandalism.getLocalizacao().getIdLocalizacao()+"\"}]}],\"updateAction\": \"APPEND\"}";



        return json;
    }

}
