package ufeyes.com.ufeyes.domain;

import java.io.Serializable;

/**
 * Created by carlo on 14/10/2017.
 */

public class Ocorrencia implements Serializable{

    private String id;
    private Localization localizacao;
    private User usuario;

    public Ocorrencia(User usuario, Localization localizacao) {
        this.usuario = usuario;
        this.localizacao = localizacao;

    }

    public Ocorrencia() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Localization getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localization localizacao) {
        this.localizacao = localizacao;
    }




}
