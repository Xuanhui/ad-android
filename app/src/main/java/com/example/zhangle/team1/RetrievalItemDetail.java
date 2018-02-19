package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by user on 1/25/2018.
 */

//To be or not to be


public class RetrievalItemDetail extends HashMap<String,String> {
    final static String baseURL = JSONParser.getJSONURL();
    public RetrievalItemDetail(int RetrievalDetailID, int RetrievalID, String Itemcode, String Description, int QtyNeeded, int ActualQty,int CurrentQuantity, String Status) {
        put("RetrievalDetailID", String.valueOf(RetrievalDetailID));
        put("RetrievalID", String.valueOf(RetrievalID));
        put("Itemcode", Itemcode);
        put("Description", Description);
        put("QuantityNeeded", String.valueOf(QtyNeeded));
        put("ActualQty", String.valueOf(ActualQty));
        put("CurrentQuantity", String.valueOf(CurrentQuantity));
        put("Status", Status);
    }

    public RetrievalItemDetail(int RetrievalDetailID, int RetrievalID, String Itemcode, String Description, int QtyNeeded, int ActualQty,int CurrentQuantity, String Status,int ClerkID) {
       put("RetrievalDetailID",String.valueOf(RetrievalDetailID));
        put("RetrievalID",String.valueOf(RetrievalID));
        put("Itemcode", Itemcode);
        put("Description",Description);
        put("QuantityNeeded", String.valueOf(QtyNeeded));
        put("ActualQty", String.valueOf(ActualQty));
        put("CurrentQuantity", String.valueOf(CurrentQuantity));
        put("Status", Status);
        put("ClerkID",String.valueOf(ClerkID));
    }
        public RetrievalItemDetail(){}

public static RetrievalItemDetail getCertainRetrievalItem(String id){

        try{
            JSONObject a= JSONParser.getJSONFromUrl(baseURL+"/RetrievalDetailItem/"+id);
            RetrievalItemDetail item ;
        item=new RetrievalItemDetail( a.getInt("RetrievalDetailID"),
        a.getInt("RetrievalID"),
        a.getString("Itemcode"),
        a.getString("Description"),
        a.getInt("QuantityNeeded"),
        a.getInt("ActualQty"),
        a.getInt("CurrentQuantity"),
        a.getString("Status"));
        return item;
        }catch (Exception e) {
            Log.e("getCertainRetrievalItem", "JSON error");
            return (null);
        }
}
public static void updateRetrievalItemDetail(RetrievalItemDetail ret){
    JSONObject jRetrievalItemDetail =new JSONObject();
    RetrievalItemDetail real=getCertainRetrievalItem(ret.get("RetrievalDetailID"));
    try{jRetrievalItemDetail.put("CurrentQuantity",(ret.get("CurrentQuantity")));
        jRetrievalItemDetail.put("RetrievalID",Integer.valueOf(real.get("RetrievalID")));
        jRetrievalItemDetail.put("RetrievalDetailID",Integer.valueOf(ret.get("RetrievalDetailID")));
        jRetrievalItemDetail.put("Itemcode",ret.get("Itemcode"));
        jRetrievalItemDetail.put("Description",ret.get("Description"));
        jRetrievalItemDetail.put("QuantityNeeded",Integer.valueOf(ret.get("QuantityNeeded")));
        jRetrievalItemDetail.put("ActualQty",Integer.valueOf(ret.get("ActualQty")));
        //jRetrievalItemDetail.put("CurrentQuantity",(ret.get("CurrentQuantity")));
        jRetrievalItemDetail.put("Status","Collected");
        jRetrievalItemDetail.put("ClerkID",Integer.valueOf(ret.get("ClerkID")));
    }catch(Exception e){}
    String result = JSONParser.postStream(baseURL+"/UpdateRetrievalDetail",jRetrievalItemDetail.toString());
}

    public static List<RetrievalItemDetail> getRetrievalItemlist(String id){
       List<RetrievalItemDetail>list=new ArrayList<RetrievalItemDetail>();
        try{
            JSONArray a= JSONParser.getJSONArrayFromUrl(baseURL+"/RetrievalDetail/"+id);
           for(int i=0;i<a.length();i++){
               JSONObject b = a.getJSONObject(i);

               list.add(new RetrievalItemDetail(
                       b.getInt("RetrievalDetailID"),
                        b.getInt("RetrievalID"),
                        b.getString("Itemcode"),
                        b.getString("Description"),
                        b.getInt("QuantityNeeded"),
                       b.getInt("ActualQty"),
                        b.getInt("CurrentQuantity"),
                        b.getString("Status")
               ));}


        } catch (Exception e) {
            Log.e("getRetrievalItemList()", "JSONArray error");
        }
        return list;

    }

}
