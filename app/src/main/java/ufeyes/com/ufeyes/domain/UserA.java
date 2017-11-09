package ufeyes.com.ufeyes.domain;

import java.io.Serializable;

/**
 * Created by gustavo on 08/11/2017.
 */

public class UserA implements Serializable{

    private String id;
    private String nome;
    private String sexo;

    public UserA(String id, String nome, String sexo) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object o){
        return this.id == ((UserA)o).id;
    }

}
