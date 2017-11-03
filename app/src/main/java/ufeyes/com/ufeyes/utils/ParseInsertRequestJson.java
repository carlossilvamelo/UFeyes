package ufeyes.com.ufeyes.utils;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Localization;
import ufeyes.com.ufeyes.domain.Thug;
import ufeyes.com.ufeyes.domain.User;
import ufeyes.com.ufeyes.domain.Vandalism;

/**
 * Created by carlo on 30/10/2017.
 */

public class ParseInsertRequestJson {


    public static String jsonAssalt(Assalt assalt) {

        String json = "{\"contextElements\": [{\"type\" : \"Assalt\",\"isPattern\": \"false\",\"id\": \"" + assalt.getId() + "\",\"attributes\":[{\"name\":\"idUser\",\"type\":\"string\",\"value\":\"" + assalt.getUsuario().getIdUser() + "\"},{\"name\":\"idLocalization\",\"type\":\"string\",\"value\":\"" + assalt.getLocalizacao().getIdLocalizacao() + "\"}]}],\"updateAction\": \"APPEND\"}";
        return json;
    }

    public static String jsonCarBreakIn(CarBreakIn carBreakIn) {

        String json = "{\"contextElements\": [{\"type\" : \"CarBreakIn\",\"isPattern\": \"false\",\"id\": \"" + carBreakIn.getId() + "\",\"attributes\":[{\"name\":\"idUser\",\"type\":\"string\",\"value\":\"" + carBreakIn.getUsuario().getIdUser() + "\"},{\"name\":\"idLocalization\",\"type\":\"string\",\"value\":\"" + carBreakIn.getLocalizacao().getIdLocalizacao() + "\"}]}],\"updateAction\": \"APPEND\"}";
        return json;
    }

    public static String jsonVandalism(Vandalism vandalism) {

        String json = "{\"contextElements\": [{\"type\" : \"Vandalism\",\"isPattern\": \"false\",\"id\": \"" + vandalism.getId() + "\",\"attributes\":[{\"name\":\"idUser\",\"type\":\"string\",\"value\":\"" + vandalism.getUsuario().getIdUser() + "\"},{\"name\":\"idLocalization\",\"type\":\"string\",\"value\":\"" + vandalism.getLocalizacao().getIdLocalizacao() + "\"}]}],\"updateAction\": \"APPEND\"}";
        return json;
    }

    public static String jsonUser(User user) {

        String json = "{\"contextElements\": [{\"type\" : \"User\",\"isPattern\": \"false\",\"id\": \"" + user.getIdUser() + "\",\"attributes\":[{\"name\":\"condition\",\"type\":\"integer\",\"value\":\"" + user.getCondition() + "\"}]}],\"updateAction\": \"APPEND\"}";
        return json;
    }

    public static String jsonLocalization(Localization localization) {

        String json = "{\"contextElements\": [{\"type\" : \"Localization\",\"isPattern\": \"false\",\"id\": \"" + localization.getIdLocalizacao() + "\",\"attributes\":[{\"name\":\"long\",\"type\":\"double\",\"value\":\"" + localization.getLongitude() + "\"},{\"name\":\"lat\",\"type\":\"double\",\"value\":\"" + localization.getLatitude() + "\"},{\"name\":\"timestamp\",\"type\":\"string\",\"value\":\"" + localization.getTimeStamp() + "\"}]}],\"updateAction\": \"APPEND\"}";
        return json;
    }

    public static String jsonThug(Thug thug) {
        String json = "{\"contextElements\": [{\"type\" : \"Thug\",\"isPattern\": \"false\",\"id\": \"" + thug.getId() + "\",\"attributes\":[{\"name\":\"idOccorrence\",\"type\":\"integer\",\"value\":\"" + thug.getIdOccorrence() + "\"},{\"name\":\"skinColor\",\"type\":\"integer\",\"value\":\"" + thug.getSkinColor() + "\"},{\"name\":\"weaponType\",\"type\":\"integer\",\"value\":\"" + thug.getWeaponType() + "\"},{\"name\":\"clothingColor\",\"type\":\"integer\",\"value\":\"" + thug.getClothingColor() + "\"},{\"name\":\"veicle\",\"type\":\"integer\",\"value\":\"" + thug.getVehicle() + "\"},{\"name\":\"stature\",\"type\":\"integer\",\"value\":\"" + thug.getStature() + "\"}]}],\"updateAction\": \"APPEND\"}";
        return json;
    }

}
