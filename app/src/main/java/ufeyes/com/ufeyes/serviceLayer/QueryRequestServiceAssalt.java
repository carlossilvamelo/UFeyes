package ufeyes.com.ufeyes.serviceLayer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.FragmentEstatisticas;
import ufeyes.com.ufeyes.dataLayer.QueryRequest;
import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.Localization;
import ufeyes.com.ufeyes.domain.Thug;
import ufeyes.com.ufeyes.domain.User;
import ufeyes.com.ufeyes.serviceLayer.Listeners.IQueryRequestListener;
import ufeyes.com.ufeyes.serviceLayer.Listeners.IRequestOcorrenceListener;
import ufeyes.com.ufeyes.utils.Atributo;
import ufeyes.com.ufeyes.utils.ContextElement;
import ufeyes.com.ufeyes.utils.ParseContextElement;
import ufeyes.com.ufeyes.utils.ParseQueryRequestJson;

/**
 * Created by carlo on 30/10/2017.
 */

public class QueryRequestServiceAssalt implements IQueryRequestListener {
    private QueryRequest queryRequestAssalt = new QueryRequest("Assalt");
    private QueryRequest queryRequestThug = new QueryRequest("ThugAssalt");
    private QueryRequest queryRequestUser = new QueryRequest("UserAssalt");
    private QueryRequest queryRequestLocalization = new QueryRequest("LocalizationAssalt");
    IRequestOcorrenceListener iRequestOcorrenceListener;
    private static ArrayList<ContextElement> listAssalt =null, listUser=null, listThug=null, listLocalization=null;
    private User userForRequest = null;

    private static int barrierAssalt;


    public void getAllAssalt() {
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestThug.execute(ParseQueryRequestJson.jsonAllThug());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestAssalt.execute(ParseQueryRequestJson.jsonAllAssalt());
    }



    public void getAssaltByUser(User user) {
        this.userForRequest = user;
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestThug.execute(ParseQueryRequestJson.jsonAllThug());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestAssalt.execute(ParseQueryRequestJson.jsonAllAssalt());
    }



    //listeners
    @Override
    public void resultListenerVandalism(String json) {


    }

    @Override
    public void resultListenerAssalt(String json) {

        listAssalt = ParseContextElement.getContextResponse(json);
        Log.i("jsonAssalt",json+"");
        barrierAssalt++;
        Log.i("resultListenerService","Assalt");
        if(this.userForRequest == null) {
            Log.i("S-resultListenerAssalt","resultListenerAssalt");
            sendAssalt();

        }else
            sendAssaltByUser(userForRequest);
    }

    @Override
    public void resultListenerCarBreakIn(String json) {

    }

    @Override
    public void resultListenerUser(String json) {
        listUser = ParseContextElement.getContextResponse(json);
        barrierAssalt++;


        Log.i("resultListenerService","User");
    }

    @Override
    public void resultListenerLocalization(String json) {

        listLocalization = ParseContextElement.getContextResponse(json);

        barrierAssalt++;
        Log.i("resultListenerService","Localization "+ listLocalization.size());
    }

    @Override
    public void resultListenerLocaThug(String json) {
        listThug = ParseContextElement.getContextResponse(json);
        barrierAssalt++;
        Log.i("resultListenerService","Thug");
    }

    //enviadores
    public void sendAssalt() {
        Log.i("barrierAssalt","nao concluiu barreira assalt"+ barrierAssalt);
        while (barrierAssalt != 4) {
        }
        barrierAssalt =0;
        Log.i("barrierAssalt","concluiu barreira assalt");


        List<Assalt> assaltList = new ArrayList<Assalt>();
        Assalt assalt = null;
        User user = null;
        Localization localization = null;
        for (ContextElement ce : listAssalt) {
            assalt = new Assalt();
            assalt.setId(ce.getId());


            ArrayList<Thug> thugs = new ArrayList<Thug>();
            Thug thug = null;
            if(listThug != null){
                for (ContextElement t : listThug) {
                    thug = new Thug();
                    thug.setId(t.getId());
                    List<Atributo> attList = t.getAttributes();
                    if(attList != null){
                        for (Atributo attThug : attList) {
                            if(attThug.getName().equals("idOccorrence") && attThug.getValue().equals(assalt.getId())){
                                thug.setIdOccorrence(assalt.getId());
                                if (attThug.getName().equals("clothingColor")) {
                                    thug.setClothingColor(Integer.parseInt(attThug.getValue()));
                                }
                                if (attThug.getName().equals("skinColor")) {
                                    thug.setSkinColor(Integer.parseInt(attThug.getValue()));

                                }
                                if (attThug.getName().equals("stature")) {
                                    thug.setStature(Integer.parseInt(attThug.getValue()));

                                }if (attThug.getName().equals("weaponType")) {
                                    thug.setWeaponType(Integer.parseInt(attThug.getValue()));
                                }

                            }

                        }//for (Atributo attUser : attUserList)

                    }
                    thugs.add(thug);
                }
            }
            assalt.setThugList(thugs);



            List<Atributo> atributoList = ce.getAttributes();
            for (Atributo a : atributoList) {
                if (a.getName().equals("idUser")) {
                    if(listUser != null){
                        for (ContextElement u : listUser) {
                            if (u.getId().equals(a.getValue())) {
                                Log.i("sendAssalt",u.getId()+ " encontro usuario" );
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
                    assalt.setUsuario(user);
                }//if(a.getName().equals("idUser"))
                if (a.getName().equals("idLocalization")) {
                    if(listLocalization != null){

                        for (ContextElement l : listLocalization) {
                            if (l.getId().equals(a.getValue())) {
                                Log.i("sendAssalt",l.getId()+ " encontro local" );
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
                                assalt.setLocalizacao(localization);
                            }//if(l.getId().equals(a.getValue()))
                        }//for (ContextElement l : listLocalization)
                    }
                }
            }//for (Atributo a : atributoList)


            //buscando os meliantes vinculados a ocorrencia





            assaltList.add(assalt);

        }//for (ContextElement ce : listAssalt)


        iRequestOcorrenceListener = new FragmentEstatisticas();
        iRequestOcorrenceListener.resultListenerAssalt(assaltList);

    }//sendAssalt()

    public void sendCarBreakIn(){}//sendCarBreakIn()
    public void sendVandalism(){}//sendVandalism()

    public void sendAssaltByUser(User user) {
        while (barrierAssalt != 4) {
        }

        List<Assalt> assaltList = new ArrayList<Assalt>();
        Assalt assalt = null;
        user = null;
        Localization localization = null;
        for (ContextElement ce : listAssalt) {
            assalt = new Assalt();
            assalt.setId(ce.getId());
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
                    assalt.setUsuario(user);
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
                            assalt.setLocalizacao(localization);
                        }//if(l.getId().equals(a.getValue()))
                    }//for (ContextElement l : listLocalization)
                }
            }//for (Atributo a : atributoList)


            //buscando os meliantes vinculados a ocorrencia

            ArrayList<Thug> thugs = new ArrayList<Thug>();
            Thug thug = null;
            if(listThug != null){
                for (ContextElement t : listThug) {
                    thug.setId(t.getId());
                    List<Atributo> attList = t.getAttributes();
                    for (Atributo attThug : attList) {
                        if (attThug.getName().equals(assalt.getId())) {

                            if (attThug.getName().equals("skinColor")) {
                                thug.setSkinColor(Integer.parseInt(attThug.getValue()));
                            }
                            if (attThug.getName().equals("weaponType")) {
                                thug.setWeaponType(Integer.parseInt(attThug.getValue()));
                            }
                            if (attThug.getName().equals("clothingColor")) {
                                thug.setClothingColor(Integer.parseInt(attThug.getValue()));
                            }
                            if (attThug.getName().equals("vehicle")) {
                                thug.setVehicle(Integer.parseInt(attThug.getValue()));
                            }
                            if (attThug.getName().equals("stature")) {
                                thug.setStature(Integer.parseInt(attThug.getValue()));
                            }

                        }
                        thugs.add(thug);
                    }
            }
            }//for (ContextElement t : listThug)


            assalt.setThugList(thugs);
            assaltList.add(assalt);

        }//for (ContextElement ce : listAssalt)


        iRequestOcorrenceListener = new FragmentEstatisticas();
        iRequestOcorrenceListener.resultListenerAssalt(assaltList);

    }//sendAssalt()

    public void sendCarBreakInByUser(User user){}//sendCarBreakIn()
    public void sendVandalismByUser(User user){}//sendVandalism()



}
