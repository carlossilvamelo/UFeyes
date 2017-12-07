package ufeyes.com.ufeyes.utils;

/**
 * Created by carlo on 06/12/2017.
 */

public class ParseFCM {

    public static String msgTopic(String json){
        return "{\"to\" : \"/topics/ocorrencia\",\"data\" : {\"message\" : \""+json+"\"}}";
    }
}
