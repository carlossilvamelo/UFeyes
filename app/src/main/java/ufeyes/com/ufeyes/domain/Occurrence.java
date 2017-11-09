package ufeyes.com.ufeyes.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gustavo on 08/11/2017.
 */

public class Occurrence implements Serializable{

    private String id;
    private Double lat;
    private Double lng;
    private String type;
    private String idUser;
    private String date;

    public Occurrence(String id, Double lat, Double lng, String type, String idUser, String date) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.idUser = idUser;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Occurrence that = (Occurrence) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        if (lng != null ? !lng.equals(that.lng) : that.lng != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return idUser != null ? idUser.equals(that.idUser) : that.idUser == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (idUser != null ? idUser.hashCode() : 0);
        return result;
    }
}
