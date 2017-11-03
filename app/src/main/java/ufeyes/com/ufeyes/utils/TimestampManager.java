package ufeyes.com.ufeyes.utils;

import java.sql.Timestamp;

/**
 * Created by carlo on 03/11/2017.
 */

public class TimestampManager {

    public static String getTimeStamp(){
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static DateTime stringToTimestamp(String tmString){
        String[] aux = tmString.split(" ");
        String date = aux[0];
        String time = aux[1];

        String[] dateAttibuties = date.split("-");
        String[] timeAttibuties = time.split(":");

        DateTime dateTime = new DateTime();
        dateTime.setYear(Integer.parseInt(dateAttibuties[0]));
        dateTime.setMonth(Integer.parseInt(dateAttibuties[1]));
        dateTime.setDay(Integer.parseInt(dateAttibuties[2]));

        dateTime.setHour(Integer.parseInt(timeAttibuties[0]));
        dateTime.setMinutes(Integer.parseInt(timeAttibuties[1]));


        return dateTime;
    }
}
