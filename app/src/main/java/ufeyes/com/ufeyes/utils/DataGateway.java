package ufeyes.com.ufeyes.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ufeyes.com.ufeyes.dataLayer.DataHelper;

/**
 * portão para as conexões com as tabelas do banco
 * Created by gustavo on 08/11/2017.
 */

public class DataGateway {

    private static DataGateway gw;
    private SQLiteDatabase db;

    private DataGateway(Context ctx){
        DataHelper helper = new DataHelper(ctx);
        db = helper.getWritableDatabase();
    }

    /**
     * garantindo que so exista apenas um cliente do bd
     * @param ctx
     * @return
     */
    public static DataGateway getInstance(Context ctx){
        if(gw == null)
            gw = new DataGateway(ctx);
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }
}
