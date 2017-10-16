package ufeyes.com.ufeyes.domain;

import java.util.List;

/**
 * Created by carlo on 14/10/2017.
 */

public class Ocorrencia {

    private Usuario usuario;
    private Localizacao localizacao;
    private List<Meliante> meliante;

    public Ocorrencia(Usuario usuario, Localizacao localizacao, List<Meliante> meliante) {
        this.usuario = usuario;
        this.localizacao = localizacao;
        this.meliante = meliante;
    }

    public Ocorrencia() {
    }
}
