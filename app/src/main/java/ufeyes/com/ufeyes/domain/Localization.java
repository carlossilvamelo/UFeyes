package ufeyes.com.ufeyes.domain;

/**
 * Created by carlo on 14/10/2017.
 */

public class Localization {

    private String idLocalizacao;
    private Double latitude;
    private Double longitude;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Localization(String idLocalizacao, Double latitude, Double longitude) {
        this.idLocalizacao = idLocalizacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Localization() {
    }

    public String getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setIdLocalizacao(String idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
