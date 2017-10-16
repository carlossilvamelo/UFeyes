package ufeyes.com.ufeyes.domain;

import java.util.List;

/**
 * Created by carlo on 14/10/2017.
 */

public class Vandalismo extends Ocorrencia{

    private Long numeroMeliantes;

    public Vandalismo(Usuario usuario, Localizacao localizacao, List<Meliante> meliante, Long numeroMeliantes) {
        super(usuario, localizacao, meliante);
        this.numeroMeliantes = numeroMeliantes;
    }

    public Vandalismo() {
    }
}
