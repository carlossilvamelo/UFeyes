package ufeyes.com.ufeyes.domain;

import java.util.List;

/**
 * Created by carlo on 14/10/2017.
 */

public class ArrombamentoVeiculo extends Ocorrencia{

    public ArrombamentoVeiculo(Usuario usuario, Localizacao localizacao, List<Meliante> meliante) {
        super(usuario, localizacao, meliante);
    }

    public ArrombamentoVeiculo() {
    }
}
