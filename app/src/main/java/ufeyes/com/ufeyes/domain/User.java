package ufeyes.com.ufeyes.domain;

/**
 * Created by carlo on 14/10/2017.
 */

public class User {

    private int condition;
    private String idUser;

    public User(int condicao, String idUsuario) {
        this.condition = condicao;
        this.idUser = idUsuario;
    }

    public User() {
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
