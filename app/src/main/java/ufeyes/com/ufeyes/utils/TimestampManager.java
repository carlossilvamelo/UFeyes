package ufeyes.com.ufeyes.utils;

import java.sql.Timestamp;
import java.util.List;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Vandalism;

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

    public static String calculatePeriodAssalt(List<Assalt> listAssalt) {
        int night = 0;
        int morning = 0;
        int afternoon = 0;
        int dawn = 0;
        String period = null;
        DateTime dateTime = null;
        for (Assalt assalt : listAssalt) {
            dateTime = TimestampManager.stringToTimestamp(assalt.getLocalizacao().getTimeStamp());
            if (dateTime.getHour() > 5 && dateTime.getHour() < 12) {
                morning++;
            }
            if (dateTime.getHour() > 12 && dateTime.getHour() < 18) {
                afternoon++;
            }
            if (dateTime.getHour() > 18 && dateTime.getHour() < 23) {
                morning++;
            }
            if (dateTime.getHour() > 0 && dateTime.getHour() < 5) {
                dawn++;
            }
        }

        if(dawn>=morning && dawn>=afternoon && dawn>=night)
            period = "Madrugada";

        if(morning>=dawn && morning>=afternoon && morning>=night)
            period = "Manhã";

        if(afternoon>=dawn && afternoon>=morning && afternoon>=night)
            period = "Tarde";

        if(night>=dawn && night>=morning && night>=afternoon)
            period = "Noite";


        return period;
    }

    public static String calculatePeriodVandalism(List<Vandalism> listVandalism) {
        int night = 0;
        int morning = 0;
        int afternoon = 0;
        int dawn = 0;
        String period = null;
        DateTime dateTime = null;
        for (Vandalism vandalism : listVandalism) {
            dateTime = TimestampManager.stringToTimestamp(vandalism.getLocalizacao().getTimeStamp());
            if (dateTime.getHour() > 5 && dateTime.getHour() < 12) {
                morning++;
            }
            if (dateTime.getHour() > 12 && dateTime.getHour() < 18) {
                afternoon++;
            }
            if (dateTime.getHour() > 18 && dateTime.getHour() < 23) {
                morning++;
            }
            if (dateTime.getHour() > 0 && dateTime.getHour() < 5) {
                dawn++;
            }
        }

        if(dawn>=morning && dawn>=afternoon && dawn>=night)
            period = "Madrugada";

        if(morning>=dawn && morning>=afternoon && morning>=night)
            period = "Manhã";

        if(afternoon>=dawn && afternoon>=morning && afternoon>=night)
            period = "Tarde";

        if(night>=dawn && night>=morning && night>=afternoon)
            period = "Noite";

       return period;
    }

    public static String calculatePeriodCarBreakIn(List<CarBreakIn> listCarBreakIn) {
        int night = 0;
        int morning = 0;
        int afternoon = 0;
        int dawn = 0;
        String period = null;
        DateTime dateTime = null;
        for (CarBreakIn carBreakIn : listCarBreakIn) {
            dateTime = TimestampManager.stringToTimestamp(carBreakIn.getLocalizacao().getTimeStamp());
            if (dateTime.getHour() > 5 && dateTime.getHour() < 12) {
                morning++;
            }
            if (dateTime.getHour() > 12 && dateTime.getHour() < 18) {
                afternoon++;
            }
            if (dateTime.getHour() > 18 && dateTime.getHour() < 23) {
                morning++;
            }
            if (dateTime.getHour() > 0 && dateTime.getHour() < 5) {
                dawn++;
            }
        }

        if(dawn>=morning && dawn>=afternoon && dawn>=night)
            period = "Madrugada";

        if(morning>=dawn && morning>=afternoon && morning>=night)
            period = "Manhã";

        if(afternoon>=dawn && afternoon>=morning && afternoon>=night)
            period = "Tarde";

        if(night>=dawn && night>=morning && night>=afternoon)
            period = "Noite";

        return period;
    }

}
