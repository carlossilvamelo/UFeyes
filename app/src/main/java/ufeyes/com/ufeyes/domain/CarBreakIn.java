package ufeyes.com.ufeyes.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by carlo on 14/10/2017.
 */

public class CarBreakIn extends Ocorrencia implements Serializable {

    public CarBreakIn(User usuario, Localization localizacao) {
        super(usuario, localizacao);
    }

    public CarBreakIn() {
    }
}
