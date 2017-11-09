package ufeyes.com.ufeyes.dataLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import ufeyes.com.ufeyes.domain.UserA;
import ufeyes.com.ufeyes.utils.DataGateway;

/**
 * Created by gustavo on 08/11/2017.
 */

public class UserDAO {

    private final String TABLE_USER = "User";
    private DataGateway dg;

    public UserDAO(Context context){

        dg = DataGateway.getInstance(context);
    }

    /**
     * cadastra um novo usuario no bd
     * @param id
     * @param name
     * @param sex
     * @return
     */
    public boolean salvar(String id, String name, String sex){
        ContentValues cv = new ContentValues();

            cv.put("id", "0001");
            cv.put("name", name);
            cv.put("sex", sex);


        return dg.getDatabase().insert(TABLE_USER, null, cv) > 0;

    }

    /**
     * busca um usuario pelo id
     * @param id
     * @return
     */
    public UserA findUserById(String id){

        UserA user =  null;
        Log.d("Id busca", id);
        String[] columns = {"id","name","sex"};

        Cursor cursor = dg.getDatabase().query("User", columns, "id=?", new String[] { id }, null, null, null);

        if(cursor.moveToFirst()) {

             String id1 = cursor.getString(cursor.getColumnIndex("id"));
             String name = cursor.getString(cursor.getColumnIndex("name"));
             String sex = cursor.getString(cursor.getColumnIndex("sex"));

            user = new UserA(id1,name,sex);
         } else
         {
             Log.d("Error", "Invalid User id");

         }
        return user;
    }
}
