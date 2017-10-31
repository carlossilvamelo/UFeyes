package ufeyes.com.ufeyes.utils;

import java.util.List;

/**
 * Created by carlo on 30/10/2017.
 */

public class ContextElement {

    private String type;
    private String isPattern;
    private String id;
    private List<Atributo> attributes;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIsPattern() {
        return isPattern;
    }
    public void setIsPattern(String isPattern) {
        this.isPattern = isPattern;
    }
    public List<Atributo> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<Atributo> attributes) {
        this.attributes = attributes;
    }

}
