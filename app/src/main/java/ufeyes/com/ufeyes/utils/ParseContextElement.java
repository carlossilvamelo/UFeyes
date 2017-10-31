package ufeyes.com.ufeyes.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ufeyes.com.ufeyes.utils.Atributo;
import ufeyes.com.ufeyes.utils.ContextElement;

/**
 * Created by carlo on 31/10/2017.
 */

public class ParseContextElement {

    public static ArrayList<ContextElement> getContextResponse(String jsonContextResponses){
        ArrayList<ContextElement> listCe = new ArrayList<ContextElement>();
        JSONObject obj;
        try {
            obj = new JSONObject(jsonContextResponses);
            String json = obj.get("contextResponses").toString();
            JSONArray array = new JSONArray(json);

            String jsonCorrent;
            for(int i=0;i<array.length();i++){
                jsonCorrent = array.get(i).toString();
                listCe.add(getContextElement(jsonCorrent));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listCe;
    }


    public static ContextElement getContextElement(String jsonString){
        ContextElement ce = new ContextElement();
        try {

            JSONObject obj = new JSONObject(jsonString);
            String json = obj.get("contextElement").toString();
            obj = new JSONObject(json);
            ce.setId(obj.get("id").toString());
            ce.setType(obj.get("type").toString());
            String attributesJson = obj.get("attributes").toString();
            JSONArray array = new JSONArray(attributesJson);


            ArrayList<Atributo> attList = new ArrayList<Atributo>();
            for(int i=0;i<array.length();i++){
                Atributo att = new Atributo();
                JSONObject oj = (JSONObject) array.get(i);
                att.setName(oj.getString("name"));
                att.setType(oj.getString("type"));
                att.setValue(oj.getString("value"));
                attList.add(att);
            }
            ce.setAttributes(attList);


            //ContextElement contextElement = new ContextElement();
            //	contextElement =gson.fromJson(json, ContextElement.class);
            //	System.out.println(contextElement.getType());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ce;
    }
}
