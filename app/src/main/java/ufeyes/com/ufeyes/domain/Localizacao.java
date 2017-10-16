package ufeyes.com.ufeyes.domain;

/**
 * Created by carlo on 14/10/2017.
 */

public class Localizacao {

    private String idLocalizacao;
    private String latitude;
    private String longitude;

    public Localizacao(String idLocalizacao, String latitude, String longitude) {
        this.idLocalizacao = idLocalizacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Localizacao() {
    }

    public String getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setIdLocalizacao(String idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
