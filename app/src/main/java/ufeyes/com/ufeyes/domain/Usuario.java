package ufeyes.com.ufeyes.domain;

/**
 * Created by carlo on 14/10/2017.
 */

public class Usuario {

    private int condicao;
    private String idUsuario;

    public Usuario(int condicao, String idUsuario) {
        this.condicao = condicao;
        this.idUsuario = idUsuario;
    }

    public Usuario() {
    }

    public int getCondicao() {
        return condicao;
    }

    public void setCondicao(int condicao) {
        this.condicao = condicao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
