package ufeyes.com.ufeyes.serviceLayer;

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

public class QueryRequestService implements IQueryRequestListener {
    private QueryRequest queryRequestVandalism = new QueryRequest("Vandalism");
    private QueryRequest queryRequestCarBreakIn = new QueryRequest("CarBreakIn");
    private QueryRequest queryRequestAssalt = new QueryRequest("Assalt");
    private QueryRequest queryRequestThug = new QueryRequest("Thug");
    private QueryRequest queryRequestUser = new QueryRequest("User");
    private QueryRequest queryRequestLocalization = new QueryRequest("Localization");
    IRequestOcorrenceListener iRequestOcorrenceListener;
    private ArrayList<ContextElement> listVandalism, listAssalt, listCarBreakIn, listUser, listThug, listLocalization;
    private User userForRequest = null;

    int barrier = 0;

    public void getAllAssalt() {
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestThug.execute(ParseQueryRequestJson.jsonAllThug());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestAssalt.execute(ParseQueryRequestJson.jsonAllAssalt());
    }

    public void getAllCarBreakIn() {
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestCarBreakIn.execute(ParseQueryRequestJson.jsonAllCarBreakIn());
    }

    public void getAllVandalism() {
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestThug.execute(ParseQueryRequestJson.jsonAllThug());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestVandalism.execute(ParseQueryRequestJson.jsonAllVandalism());
    }

    public void getAssaltByUser(User user) {
        this.userForRequest = user;
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestThug.execute(ParseQueryRequestJson.jsonAllThug());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestAssalt.execute(ParseQueryRequestJson.jsonAllAssalt());
    }

    public void getCarBreakInByUser(User user) {
        this.userForRequest = user;
        queryRequestUser.execute(ParseQueryRequestJson.jsonAllUser());
        queryRequestLocalization.execute(ParseQueryRequestJson.jsonAllLocalization());
        queryRequestCarBreakIn.execute(ParseQueryRequestJson.jsonAllCarBreakIn());
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
        listVandalism = ParseContextElement.getContextResponse(json);
        barrier++;
        if(this.userForRequest == null)
            sendVandalism();
        else
            sendVandalismByUser(userForRequest);

    }

    @Override
    public void resultListenerAssalt(String json) {
        listAssalt = ParseContextElement.getContextResponse(json);
        barrier++;
        if(this.userForRequest == null)
            sendAssalt();
        else
            sendAssaltByUser(userForRequest);
    }

    @Override
    public void resultListenerCarBreakIn(String json) {
        listCarBreakIn = ParseContextElement.getContextResponse(json);
        barrier++;
        if(this.userForRequest == null)
            sendCarBreakIn();
        else
            sendCarBreakInByUser(userForRequest);
    }

    @Override
    public void resultListenerUser(String json) {
        listUser = ParseContextElement.getContextResponse(json);
        barrier++;

    }

    @Override
    public void resultListenerLocalization(String json) {
        listLocalization = ParseContextElement.getContextResponse(json);
        barrier++;
    }

    @Override
    public void resultListenerLocaThug(String json) {
        listThug = ParseContextElement.getContextResponse(json);
        barrier++;
    }

    //enviadores
    public void sendAssalt() {
        while (barrier != 4) {
        }

        List<Assalt> assaltList = new ArrayList<Assalt>();
        Assalt assalt = null;
        User user = null;
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
            }//for (ContextElement t : listThug)


            assalt.setThugList(thugs);
            assaltList.add(assalt);

        }//for (ContextElement ce : listAssalt)


        iRequestOcorrenceListener = new FragmentEstatisticas();
        iRequestOcorrenceListener.resultListenerAssalt(assaltList);

    }//sendAssalt()

    public void sendCarBreakIn(){}//sendCarBreakIn()
    public void sendVandalism(){}//sendVandalism()

    public void sendAssaltByUser(User user) {
        while (barrier != 4) {
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
