package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhangle on 23/1/18.
 */

public class RequisitionDetailItem extends HashMap<String,String> {
    //final static String baseURL = "http://13.229.50.128:8080";
    final static String baseURL = JSONParser.getJSONURL();
    public RequisitionDetailItem(int RequisitionDetailID,int RequisitionID,String ItemCode,int ItemQty,
                                 int OutstandingQty, int DisbursedQty) {
        put("RequisitionDetailID", String.valueOf(RequisitionDetailID));
        put("RequisitionID", String.valueOf(RequisitionID));
        put("ItemCode", ItemCode);
        put("ItemQty", String.valueOf(ItemQty));
        put("OutstandingQty", String.valueOf(OutstandingQty));
        put("DisbursedQty", String.valueOf(DisbursedQty));
    }

    public static List<RequisitionDetailItem> listAll(String rid) {
        List<RequisitionDetailItem> list = new ArrayList<RequisitionDetailItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/RequisitionDetail/"+rid);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new RequisitionDetailItem(
                        b.getInt("RequisitionDetailID"),
                        b.getInt("RequisitionID"),
                        b.getString("Description"),
                        b.getInt("ItemQty"),
                        b.getInt("OutstandingQty"),
                        b.getInt("DisbursedQty")
                ));
            }
        } catch (Exception e) {
            Log.e("RequisitionDetailItem.listAll()", "JSONArray error");
        }
        return list;
    }
}
