package ufeyes.com.ufeyes.domain;

/**
 * Created by carlo on 14/10/2017.
 */

public class Ocorrencia {

    private String id;
    private Localization localizacao;
    private User usuario;

    public Ocorrencia(User usuario, Localization localizacao) {
        this.usuario = usuario;
        this.localizacao = localizacao;

    }

    public Ocorrencia() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Localization getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localization localizacao) {
        this.localizacao = localizacao;
        String s = "{\"contextElement\": {\"type\": \"Assalt\",\"isPattern\": \"false\",\"id\": \"never1\"}}";

        String ss = "{\n    \"contextResponses\": [\n\n{\n            \"contextElement\": {\n                \"type\": \"Assalt\",\n                \"isPattern\": \"false\",\n                \"id\": \"never1\",\n                \"attributes\": [\n                    {\n                        \"name\": \"idLocalization\",\n                        \"type\": \"string\",\n                        \"value\": \"17\"\n                    },\n                    {\n                        \"name\": \"idUser\",\n                        \"type\": \"string\",\n                        \"value\": \"17\"\n                    }\n                ]\n            },\n            \"statusCode\": {\n                \"code\": \"200\",\n                \"reasonPhrase\": \"OK\"\n            }\n        },\n        {\n            \"contextElement\": {\n                \"type\": \"Assalt\",\n                \"isPattern\": \"false\",\n                \"id\": \"never2\",\n                \"attributes\": [\n                    {\n                        \"name\": \"idLocalization\",\n                        \"type\": \"string\",\n                        \"value\": \"17\"\n                    },\n                    {\n                        \"name\": \"idUser\",\n                        \"type\": \"string\",\n                        \"value\": \"17\"\n                    }\n                ]\n            },\n            \"statusCode\": {\n                \"code\": \"200\",\n                \"reasonPhrase\": \"OK\"\n            }\n        }\n        \n           ]\n}";
    }




}
