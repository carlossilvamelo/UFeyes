package ufeyes.com.ufeyes.domain;

import java.util.List;

/**
 * Created by carlo on 07/11/2017.
 */

public class Assalt extends Ocorrencia{

   private List<Thug> thugList;

    public List<Thug> getThugList() {
        return thugList;
    }

    public void setThugList(List<Thug> thugList) {
        this.thugList = thugList;
    }
}
