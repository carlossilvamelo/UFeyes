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

public class QueryRequestServiceVandalism implements IQueryRequestListener {

    private QueryRequest queryRequestVandalism = new QueryRequest("Vandalism");
    private QueryRequest queryRequestThug = new QueryRequest("ThugVandalism");
    private QueryRequest queryRequestUser = new QueryRequest("UserVandalism");
    private QueryRequest queryRequestLocalization = new QueryRequest("LocalizationVandalism");
    private static IRequestOcorrenceListener iRequestOcorrenceListener;
    private static ArrayList<ContextElement> listVandalism =null,listThug=null, listUser=null, listLocalization=null;
    private User userForRequest = null;

    private static int barrierVandalism;
    public  QueryRequestServiceVandalism(IRequestOcorrenceListener iRequestOcorrenceListener){
        this.iRequestOcorrenceListener = iRequestOcorrenceListener;
    }

    public  QueryRequestServiceVandalism( ){

    }


    public void getAllVandalism() {
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestThug.execute(ParseQueryRequestJson.jsonAllThug());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestVandalism.execute(ParseQueryRequestJson.jsonAllVandalism());
    }



    public void getVandalismByUser(User user) {
        this.userForRequest = user;
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestThug.execute(ParseQueryRequestJson.jsonAllThug());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestVandalism.execute(ParseQueryRequestJson.jsonAllVandalism());
    }


    //listeners
    @Override
    public void resultListenerVandalism(String json) {
        Log.i("jsonVandalism",json.length()+"");
        Log.i("resultListenerService","Vandalism");
        listVandalism = ParseContextElement.getContextResponse(json);
        barrierVandalism++;
        if(this.userForRequest == null)
            sendVandalism();
        else
            sendVandalismByUser(userForRequest);

    }

    @Override
    public void resultListenerAssalt(String json) {

    }

    @Override
    public void resultListenerCarBreakIn(String json) {

    }

    @Override
    public void resultListenerUser(String json) {
        Log.i("jsonVandalism","USER"+json);
        listUser = ParseContextElement.getContextResponse(json);
        barrierVandalism++;
        Log.i("resultListenerService","User");
    }

    @Override
    public void resultListenerLocalization(String json) {
        Log.i("jsonVandalism","Local"+json);
        listLocalization = ParseContextElement.getContextResponse(json);
        barrierVandalism++;
        Log.i("resultListenerService","Localization");
    }

    @Override
    public void resultListenerLocaThug(String json) {
        listThug = ParseContextElement.getContextResponse(json);
        barrierVandalism++;
        Log.i("resultListenerService","Thug");
    }

    //enviadores
    public void sendVandalism() {
        Log.i("barrierVandalism","nao concluiu barreira vandalism"+ barrierVandalism);
        while (barrierVandalism != 4) {
        }

        barrierVandalism=0;
        Log.i("barrierVandalism","concluiu barreira vandalism" );
        List<Vandalism> vandalismList = new ArrayList<Vandalism>();
        Vandalism vandalism = null;
        User user = null;
        Localization localization = null;
        Log.i("sendVandalism",listVandalism.size()+ "size" );
        for (ContextElement ce : listVandalism) {
            vandalism = new Vandalism();
            vandalism.setId(ce.getId());


            ArrayList<Thug> thugs = new ArrayList<Thug>();
            Thug thug = null;
            if(listThug != null){
                for (ContextElement t : listThug) {
                    thug = new Thug();
                    thug.setId(t.getId());
                    List<Atributo> attList = t.getAttributes();
                    if(attList != null){
                        for (Atributo attThug : attList) {
                            if(attThug.getName().equals("idOccorrence") && attThug.getValue().equals(vandalism.getId())){
                                thug.setIdOccorrence(vandalism.getId());
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
            vandalism.setThugList(thugs);





            List<Atributo> atributoList = ce.getAttributes();
            Log.i("sendVandalism",atributoList.size()+ "atributoList" );
            for (Atributo a : atributoList) {
                if (a.getName().equals("idUser")) {

                    if(listUser != null){
                        Log.i("sendVandalism","entrou na lista de user" );
                        for (ContextElement u : listUser) {

                            if (u.getId().equals(a.getValue())) {
                                Log.i("sendVandalism",a.getValue()+ " user encontrado" );
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
                    vandalism.setUsuario(user);
                }//if(a.getName().equals("idUser"))
                if (a.getName().equals("idLocalization")) {
                    if(listLocalization != null){
                        Log.i("sendVandalism","entrou na lista de locais" );
                        for (ContextElement l : listLocalization) {
                            if (l.getId().equals(a.getValue())) {
                                Log.i("sendVandalism",a.getValue()+ " local encontrado" );
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
                                vandalism.setLocalizacao(localization);
                            }//if(l.getId().equals(a.getValue()))
                        }//for (ContextElement l : listLocalization)
                    }
                }
            }//for (Atributo a : atributoList)



            vandalismList.add(vandalism);

        }//for (ContextElement ce : listAssalt)


      //  iRequestOcorrenceListener = new FragmentEstatisticas();
        iRequestOcorrenceListener.resultListenerVandalism(vandalismList);

    }//sendAssalt()

    public void sendVandalismByUser(User user) {
        while (barrierVandalism != 4) {
        }

        List<Assalt> assaltList = new ArrayList<Assalt>();
        Assalt assalt = null;
        user = null;
        Localization localization = null;
        for (ContextElement ce : listVandalism) {
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



}
