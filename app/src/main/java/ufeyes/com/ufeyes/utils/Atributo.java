package ufeyes.com.ufeyes.utils;

/**
 * Created by carlo on 30/10/2017.
 */

public class Atributo {
    private String name;
    private String type;
    private String value;

    public String getId() {
        return name;
    }
    public void setName(String id) {
        this.name = id;
    }
    public String getName() {
        return this.name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
