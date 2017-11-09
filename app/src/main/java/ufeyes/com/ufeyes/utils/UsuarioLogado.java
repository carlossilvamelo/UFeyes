package ufeyes.com.ufeyes.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ufeyes.com.ufeyes.dataLayer.DataHelper;
import ufeyes.com.ufeyes.dataLayer.UserDAO;
import ufeyes.com.ufeyes.domain.UserA;

/**
 * Classe que mantêm o usuario que está logado na aplicação
 * Created by gustavo on 08/11/2017.
 */

public class UsuarioLogado {

    private static UsuarioLogado usuarioLogado;
    private UserA user;

    private UsuarioLogado(String id, Context ctx){

        UserDAO userDAO = new UserDAO(ctx);
        user = userDAO.findUserById(id);
    }

    /**
     * garantindo que so exista apenas um usuario logado
     * @param ctx contexto do componente
     * @return
     */
    public static UsuarioLogado getInstance(String id, Context ctx){
        if(usuarioLogado == null)
            usuarioLogado = new UsuarioLogado(id, ctx);
        return usuarioLogado;
    }

    public UserA getUser(){
        return this.user;
    }

}
