package ufeyes.com.ufeyes.utils;

import ufeyes.com.ufeyes.domain.User;

/**
 * Created by carlo on 30/10/2017.
 */

public class ParseQueryRequestJson {

    public static String jsonAllAssalt() {
        String json = "{\"entities\": [{\"type\": \"Assalt\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"idUser\",\"idLocalization\"]}";
        return json;
    }

    public static String jsonAllCarBreakIn() {
        String json = "{\"entities\": [{\"type\": \"CarBreakIn\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"idUser\",\"idLocalization\"]}";
        return json;
    }

    public static String jsonAllVandalism() {
        String json = "{\"entities\": [{\"type\": \"Vandalism\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"idUser\",\"idLocalization\"]}";
        return json;
    }

    public static String jsonAllLocalization() {
        String json = "{\"entities\": [{\"type\": \"Localization\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"long\",\"lat\",\"timestamp\"]}";
        return json;
    }

    public static String jsonAllUser() {
        String json = "{\"entities\": [{\"type\": \"User\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"condition\"]}";
        return json;
    }

    public static String jsonAllThug() {
        String json = "{\"entities\": [{\"type\": \"Thug\",\"isPattern\": \"true\",\"id\": \".*\"}],\"attributes\": [\"idOccorrence\",\"skinColor\",\"weaponType\",\"clothingColor\",\"veicle\",\"stature\"]}";
        return json;
    }




}
