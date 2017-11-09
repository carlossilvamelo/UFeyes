package ufeyes.com.ufeyes.serviceLayer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.FragmentEstatisticas;
import ufeyes.com.ufeyes.dataLayer.QueryRequest;
import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Localization;
import ufeyes.com.ufeyes.domain.Thug;
import ufeyes.com.ufeyes.domain.User;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.serviceLayer.Listeners.IQueryRequestListener;
import ufeyes.com.ufeyes.serviceLayer.Listeners.IRequestOcorrenceListener;
import ufeyes.com.ufeyes.utils.Atributo;
import ufeyes.com.ufeyes.utils.ContextElement;
import ufeyes.com.ufeyes.utils.ParseContextElement;
import ufeyes.com.ufeyes.utils.ParseQueryRequestJson;

/**
 * Created by carlo on 07/11/2017.
 */

public class QueryRequestServiceCarBreakIn implements IQueryRequestListener {

    private QueryRequest queryRequestCarBreakIn = new QueryRequest("CarBreakIn");
    private QueryRequest queryRequestUser = new QueryRequest("UserCarBreakIn");
    private QueryRequest queryRequestLocalization = new QueryRequest("LocalizationCarBreakIn");
    IRequestOcorrenceListener iRequestOcorrenceListener;
    private static ArrayList<ContextElement> listCarBreakIn =null, listUser=null, listLocalization=null;
    private User userForRequest = null;

    private static int barrierCarBreakIn;



    public void getAllCarBreakIn() {
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestCarBreakIn.execute(ParseQueryRequestJson.jsonAllCarBreakIn());
    }



    public void getVandalismByUser(User user) {
        this.userForRequest = user;
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestCarBreakIn.execute(ParseQueryRequestJson.jsonAllCarBreakIn());
    }


    //listeners
    @Override
    public void resultListenerVandalism(String json) {


    }

    @Override
    public void resultListenerAssalt(String json) {

    }

    @Override
    public void resultListenerCarBreakIn(String json) {
        Log.i("jsonCarBreakIn",json);
        Log.i("resultListenerService","CarBreakIn");
        listCarBreakIn = ParseContextElement.getContextResponse(json);
        barrierCarBreakIn++;
        if(this.userForRequest == null)
            sendVandalism();
        else
            sendVandalismByUser(userForRequest);
    }

    @Override
    public void resultListenerUser(String json) {
        Log.i("jsonCarBreakIn","USER"+json);
        listUser = ParseContextElement.getContextResponse(json);
        barrierCarBreakIn++;
        Log.i("resultListenerService","User");
    }

    @Override
    public void resultListenerLocalization(String json) {
        Log.i("jsonCarBreakIn","LOCAL"+json);
        listLocalization = ParseContextElement.getContextResponse(json);
        barrierCarBreakIn++;
        Log.i("resultListenerService","Localization");
    }

    @Override
    public void resultListenerLocaThug(String json) {

    }

    //enviadores
    public void sendVandalism() {
        Log.i("barrierCarBreakIn","nao concluiu barreira "+ barrierCarBreakIn);
        while (barrierCarBreakIn != 3) {
        }
        barrierCarBreakIn=0;
        Log.i("barrierCarBreakIn","concluiu barreira");
        List<CarBreakIn> carBreakInList = new ArrayList<CarBreakIn>();
        CarBreakIn carBreakIn = null;
        User user = null;
        Localization localization = null;
        for (ContextElement ce : listCarBreakIn) {
            carBreakIn = new CarBreakIn();
            carBreakIn.setId(ce.getId());
            List<Atributo> atributoList = ce.getAttributes();
            for (Atributo a : atributoList) {
                if (a.getName().equals("idUser")) {
                    if(listUser != null){
                        for (ContextElement u : listUser) {
                            if (u.getId().equals(a.getValue())) {
                                user = new User();
                                user.setIdUser(u.getId());
                                List<Atributo> attUserList = u.getAttributes();
                                for (Atributo attUser : attUserList) {
                                    if (attUser.getName().equals("condition")) {
                                        user.setCondition(Integer.parseInt(attUser.getValue()));
                                    }
                                }//for (Atributo attUser : attUserList)

                            }
                        }//(ContextElement u : listUser)
                    }
                    carBreakIn.setUsuario(user);
                }//if(a.getName().equals("idUser"))
                if (a.getName().equals("idLocalization")) {
                    if(listLocalization != null){
                        for (ContextElement l : listLocalization) {
                            if (l.getId().equals(a.getValue())) {
                                localization = new Localization();
                                localization.setIdLocalizacao(l.getId());
                                List<Atributo> attLocalizationList = l.getAttributes();
                                for (Atributo attLocalization : attLocalizationList) {
                                    if (attLocalization.getName().equals("long")) {
                                        localization.setLongitude(Double.parseDouble(attLocalization.getValue()));
                                    }
                                    if (attLocalization.getName().equals("lat")) {
                                        localization.setLatitude(Double.parseDouble(attLocalization.getValue()));
                                    }
                                    if (attLocalization.getName().equals("timestamp")) {
                                        localization.setTimeStamp(attLocalization.getValue());
                                    }
                                }//for (Atributo attUser : attUserList)
                                carBreakIn.setLocalizacao(localization);
                            }//if(l.getId().equals(a.getValue()))
                        }//for (ContextElement l : listLocalization)
                    }
                }
            }//for (Atributo a : atributoList)






            carBreakInList.add(carBreakIn);

        }//for (ContextElement ce : listAssalt)


        iRequestOcorrenceListener = new FragmentEstatisticas();
        iRequestOcorrenceListener.resultListenerCarBreakIn(carBreakInList);

    }//sendAssalt()

    public void sendVandalismByUser(User user) {
        while (barrierCarBreakIn != 4) {
        }

        List<CarBreakIn> carBreakInList = new ArrayList<CarBreakIn>();
        CarBreakIn car = null;
        user = null;
        Localization localization = null;
        for (ContextElement ce : listCarBreakIn) {
            car = new CarBreakIn();
            car.setId(ce.getId());
            List<Atributo> atributoList = ce.getAttributes();
            for (Atributo a : atributoList) {
                if (a.getName().equals("idUser")) {
                    for (ContextElement u : listUser) {
                        if (u.getId().equals(a.getValue())) {
                            user = new User();
                            user.setIdUser(u.getId());
                            List<Atributo> attUserList = u.getAttributes();
                            for (Atributo attUser : attUserList) {
                                if (attUser.getName().equals("condition")) {
                                    user.setCondition(Integer.parseInt(attUser.getValue()));
                                }
                            }//for (Atributo attUser : attUserList)

                        }
                    }//(ContextElement u : listUser)
                    car.setUsuario(user);
                }//if(a.getName().equals("idUser"))
                if (a.getName().equals("idLocalization")) {
                    for (ContextElement l : listLocalization) {
                        if (l.getId().equals(a.getValue())) {
                            localization = new Localization();
                            localization.setIdLocalizacao(l.getId());
                            List<Atributo> attLocalizationList = l.getAttributes();
                            for (Atributo attLocalization : attLocalizationList) {
                                if (attLocalization.getName().equals("long")) {
                                    localization.setLongitude(Double.parseDouble(attLocalization.getValue()));
                                }
                                if (attLocalization.getName().equals("lat")) {
                                    localization.setLatitude(Double.parseDouble(attLocalization.getValue()));
                                }
                                if (attLocalization.getName().equals("timestamp")) {
                                    localization.setTimeStamp(attLocalization.getValue());
                                }
                            }//for (Atributo attUser : attUserList)
                            car.setLocalizacao(localization);
                        }//if(l.getId().equals(a.getValue()))
                    }//for (ContextElement l : listLocalization)
                }
            }//for (Atributo a : atributoList)


            //buscando os meliantes vinculados a ocorrencia




            carBreakInList.add(car);

        }//for (ContextElement ce : listAssalt)


        iRequestOcorrenceListener = new FragmentEstatisticas();
        iRequestOcorrenceListener.resultListenerCarBreakIn(carBreakInList);

    }//sendAssalt()


}
