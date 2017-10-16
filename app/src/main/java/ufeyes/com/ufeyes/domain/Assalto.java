package ufeyes.com.ufeyes.domain;

import java.util.List;

/**
 * Created by carlo on 14/10/2017.
 */

public class Assalto extends Ocorrencia {
    private Long numeroMeliantes;


    public Assalto(Usuario usuario, Localizacao localizacao, List<Meliante> meliante, Long numeroMeliantes) {
        super(usuario, localizacao, meliante);
        this.numeroMeliantes = numeroMeliantes;
    }

    public Assalto() {
    }

    public Long getNumeroMeliantes() {
        return numeroMeliantes;
    }

    public void setNumeroMeliantes(Long numeroMeliantes) {
        this.numeroMeliantes = numeroMeliantes;
    }
}
