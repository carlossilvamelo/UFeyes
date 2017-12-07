package ufeyes.com.ufeyes.dataLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.domain.Occurrence;
import ufeyes.com.ufeyes.domain.User;
import ufeyes.com.ufeyes.utils.DataGateway;

/**
 * Created by gustavo on 08/11/2017.
 */

public class OccurrenceDAO {

    private final String TABLE_OCCURRENCE = "Occurrence";
    private DataGateway dg;

    public OccurrenceDAO(Context context){
        dg = DataGateway.getInstance(context);
    }

    /**
     * Cadastrar uma nova ocorrencia no bd
     * @param id
     * @param lat
     * @param lng
     * @param type
     * @param idUser
     * @param time
     * @return
     */
    public boolean salvar(String id, double lat, double lng, String type, String idUser, String time){
        ContentValues cv = new ContentValues();

            cv.put("id", id);
            cv.put("lat", lat);
            cv.put("lng", lng);
            cv.put("type", type);
            cv.put("idUser", idUser);
            cv.put("date", time);

        return dg.getDatabase().insert(TABLE_OCCURRENCE, null, cv) > 0;

    }

    /**
     * lista todas as ocorrencias
     * @return
     */
    public List<Occurrence> listAll(){
        List<Occurrence> occurrences = new ArrayList<>();
        Cursor cursor = dg.getDatabase().rawQuery("SELECT * FROM Occurrence", null);
        while(cursor.moveToNext()){

            String id = cursor.getString(cursor.getColumnIndex("id"));
            Double lat = cursor.getDouble(cursor.getColumnIndex("lat"));
            Double lng = cursor.getDouble(cursor.getColumnIndex("lng"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String idUser = cursor.getString(cursor.getColumnIndex("idUser"));
            String date = cursor.getString(cursor.getColumnIndex("date"));


            occurrences.add(new Occurrence(id, lat, lng, type, idUser, date));
        }
        cursor.close();
        return occurrences;
    }

    public void removeAll(){
        dg.getDatabase().delete(TABLE_OCCURRENCE, null,null );
    }
}
