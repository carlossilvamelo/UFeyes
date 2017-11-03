package ufeyes.com.ufeyes.utils;

import java.sql.Timestamp;

/**
 * Created by carlo on 03/11/2017.
 */

public class TimestampManager {

    public static String getTimeStamp(){
        return new Timestamp(System.currentTimeMillis()).toString();
    }
}
