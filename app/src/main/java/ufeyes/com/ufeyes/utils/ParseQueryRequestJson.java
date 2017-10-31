package ufeyes.com.ufeyes.utils;

/**
 * Created by carlo on 30/10/2017.
 */

public class ParseQueryRequestJson {

    public static String jsonAllAssalt(){
        String json = "{\"entities\": [{\"type\": \"Assalt\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"idUser\",\"idLocalization\"]}";
        return json;
    }

    public static String jsonAllCarBreakIn(){
        String json = "{\"entities\": [{\"type\": \"CarBreakIn\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"idUser\",\"idLocalization\"]}";
        return json;
    }

    public static String jsonAllVandalism(){
        String json = "{\"entities\": [{\"type\": \"Vandalism\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"idUser\",\"idLocalization\"]}";
        return json;
    }
}
