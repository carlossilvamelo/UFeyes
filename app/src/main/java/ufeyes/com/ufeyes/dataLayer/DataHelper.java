package ufeyes.com.ufeyes.dataLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gustavo on 08/11/2017.
 */

public class DataHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "UFeyes.db";
    private static final  int DATABASE_VERSION = 2;

    //############################# SCRIPTS #################################################
    /**
     * Query para criar a tabela de usuarios
     */
    private final String CREATE_TABLE_OCCURRENCE =
            "CREATE TABLE Occurrence (id TEXT PRIMARY KEY, lat DOUBLE NOT NULL, lng Double, type TEXT, idUser TEXT NOT NULL, date TEXT);";
    /**
     * Query para criar a tabela de ocorrencias
     */
    private final String CREATE_TABLE_USER =
            "CREATE TABLE User (id TEXT PRIMARY KEY, name TEXT NOT NULL, sex TEXT);";


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * chamado automaticamente na primeira vez que for realizada uma conexão com o banco de dados
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_OCCURRENCE);
    }

    /**
     *  método responsável por atualizar o banco de dados com alguma informação estrutural que tenha
     *  sido alterada. Ele sempre é chamado quando uma atualização é necessária, para não ter nenhum
     *  tipo de inconsistência de dados entre o banco existente no aparelho e o novo que a aplicação
     *  irá utilizar.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion < 2){
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Occurrence");
        }

        onCreate(db);
    }
}
