package com.example.zhangle.team1;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 1/24/2018.
 */

public class RetrievalItem extends HashMap<String,String> {
    final static String baseURL = JSONParser.getJSONURL();

public RetrievalItem(int RetrievalID, String clerkname, String date, String Status) {
    put("RetrievalID", String.valueOf(RetrievalID));
    put("ClerkName", clerkname);
    put("Date", date);
    put("Status", Status);}
    public RetrievalItem(){

    }
    public static void updateStatus(String id){
        RetrievalItem ret=getRetrievalItem(id);
        JSONObject jret=new JSONObject();
        try{jret.put("Status","Retrieved");
            jret.put("Date",ret.get("Date"));
            jret.put("ClerkName",ret.get("ClerkName"));
            jret.put("RetrievalID",ret.get("RetrievalID"));
        }catch(Exception e){Log.e("updateStatus()", "JSONArray error");}
      String result = JSONParser.postStream(baseURL+"/UpdateRetrieval",jret.toString());
    }

    public static RetrievalItem getRetrievalItem(String id) {
        RetrievalItem ret = null;
        try {JSONObject c= JSONParser.getJSONFromUrl(baseURL+"/Retrieval/"+id);
            ret=new RetrievalItem(  c.getInt("RetrievalID"),
                   c.getString("ClerkName"),
                    c.getString("Date"),
                    c.getString("Status")
            );}catch(Exception e){ Log.e("getRetrievalItem()", "JSONArray error");}
         return ret;
    }
    public static List<RetrievalItem> listAll(String url) {
        List<RetrievalItem> list = new ArrayList<RetrievalItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/Retrieval");
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new RetrievalItem(
                        b.getInt("RetrievalID"),
                        b.getString("ClerkName"),
                        JSONParser.transDate(b.getString("Date")),
                        b.getString("Status")
                ));
            }
        } catch (Exception e) {
            Log.e("RetrievalItem.listAll()", "JSONArray error");
        }
        return list;
    }

//      JSONObject  a = JSONParser.getJSONFromUrl(baseURL+"/Retrieval");
//        try {
//            Iterator<?> keys = a.keys();
//            while (keys.hasNext()) {
//               String key = (String) keys.next();
//            JSONObject b = a.getJSONObject(key);
//            list.add(new RetrievalItem(
//                    b.getInt("RetrivalID"),
//                    b.getString("ClerkName"),
//                    b.getString("Date"),
//                 b.getString("Status")
//            ));
//        }
//} catch (JSONException e) {
//            e.printStackTrace();
//        } return list;
//    }
}