package com.example.zhangle.team1;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yeolu on 24/1/2018.
 */

public class ReqDetailItem extends HashMap<String,String> {
    final static String baseURL = JSONParser.getJSONURL();
    //final static String baseURL = "http://172.17.251.82/LUSSIS/Service.svc";

    public ReqDetailItem(int RequisitionDetailID, int RequisitionID,String Description, String ItemCode, int ItemQty, int OutstandingQty,
                         int DisbursedQty,int DisbursementID) {
        put("RequisitionDetailID", String.valueOf(RequisitionDetailID));
        put("RequisitionID", String.valueOf(RequisitionID));
        put("Description", Description);
        put("ItemCode", ItemCode);
        put("ItemQty", String.valueOf(ItemQty));
        put("OutstandingQty", String.valueOf(OutstandingQty));
        put("DisbursedQty", String.valueOf(DisbursedQty));
        put("DisbursementID",String.valueOf(DisbursementID));
    }

    public ReqDetailItem(int RequisitionDetailID, int RequisitionID,String Description, String ItemCode, int ItemQty, int OutstandingQty,
                         int DisbursedQty) {
        put("RequisitionDetailID", String.valueOf(RequisitionDetailID));
        put("RequisitionID", String.valueOf(RequisitionID));
        put("Description", Description);
        put("ItemCode", ItemCode);
        put("ItemQty", String.valueOf(ItemQty));
        put("OutstandingQty", String.valueOf(OutstandingQty));
        put("DisbursedQty", String.valueOf(DisbursedQty));
    }

    public static List<ReqDetailItem> listAll(String id) {
        List<ReqDetailItem> list = new ArrayList<ReqDetailItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/RequisitionDetail/"+id);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                String disid = b.get("DisbursementID").toString();
                if(disid.equals("null"))
                list.add(new ReqDetailItem(
                        b.getInt("RequisitionDetailID"),
                        b.getInt("RequisitionID"),
                        b.getString("Description"),
                        b.getString("ItemCode"),
                        b.getInt("ItemQty"),
                        b.getInt("OutstandingQty"),
                        b.getInt("DisbursedQty"),
                        0
                ));
                else
                    list.add(new ReqDetailItem(
                            b.getInt("RequisitionDetailID"),
                            b.getInt("RequisitionID"),
                            b.getString("Description"),
                            b.getString("ItemCode"),
                            b.getInt("ItemQty"),
                            b.getInt("OutstandingQty"),
                            b.getInt("DisbursedQty"),
                            b.getInt("DisbursementID")
                    ));
            }
        } catch (Exception e) {
            Log.e("ReqDetailItem.listAll()", "JSONArray error");
        }
        return list;
    }

    public static ReqDetailItem getReqDetailsByID(String id) {
        JSONObject f = JSONParser.getJSONFromUrl(baseURL+"/RequisitionDetail/"+id);

        try {
            JSONObject b = JSONParser.getJSONFromUrl(baseURL+"/Catalogue/"+f.getString("ItemCode"));
            return new ReqDetailItem(
                    b.getInt("RequisitionDetailID"),
                    b.getInt("RequisitionID"),
                    b.getString("Description"),
                    b.getString("ItemCode"),
                    b.getInt("ItemQty"),
                    b.getInt("OutstandingQty"),
                    b.getInt("DisbursedQty")
                );
        } catch (Exception e) {
            Log.e("ReqDItem.getByID(Strid)", "JSONArray error");
        }
        return null;
    }
}
