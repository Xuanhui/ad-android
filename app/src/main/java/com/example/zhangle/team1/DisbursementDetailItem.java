package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Reuben on 24/1/2018.
 */

public class DisbursementDetailItem extends HashMap<String,String> {
    //final static String baseURL = "http://13.229.50.128:8080";
    final static String baseURL = JSONParser.getJSONURL();
    public DisbursementDetailItem(int DisbursementDetailID, int DisbursementID, String ItemCode, int ItemQty, int Amount) {
        put("DisbursementDetailID", String.valueOf(DisbursementDetailID));
        put("DisbursementID", String.valueOf(DisbursementID));
        put("ItemCode", ItemCode);
        put("ItemQty", String.valueOf(ItemQty));
        put("Amount", String.valueOf(Amount));
    }

    public DisbursementDetailItem(int DisbursementDetailID, int DisbursementID, String ItemCode, String Description, int ItemQty, int Amount) {
        put("DisbursementDetailID", String.valueOf(DisbursementDetailID));
        put("DisbursementID", String.valueOf(DisbursementID));
        put("Itemcode", ItemCode);
        put("Description", Description);
        put("ItemQty", String.valueOf(ItemQty));
        put("Amount", String.valueOf(Amount));
    }
    public DisbursementDetailItem(){}

    public static List<DisbursementDetailItem> listAll(String id) {
        List<DisbursementDetailItem> list = new ArrayList<DisbursementDetailItem>();
        try {
            JSONArray a= JSONParser.getJSONArrayFromUrl(baseURL+"/DisbursementDetail/"+id);
            for(int i=0;i<a.length();i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new DisbursementDetailItem(
                        b.getInt("DisbursementDetailID"),
                        b.getInt("DisbursementID"),
                        b.getString("Itemcode"),
                        b.getString("Description"),
                        b.getInt("ItemQty"),
                        b.getInt("Amount")
                ));
            }
        } catch (Exception e) {
            Log.e("DisDetailItem.listAll", "JSONArray error");
        }
        return list;
    }
}

