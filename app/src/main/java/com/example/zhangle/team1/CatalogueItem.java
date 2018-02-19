package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yeolu on 25/1/2018.
 */

    public class CatalogueItem extends HashMap<String,String> {
        //final static String baseURL = "http://13.229.50.128:8080";
        //final static String baseURL = "http://172.17.251.82/LUSSIS/Service.svc";
        final static String baseURL = JSONParser.getJSONURL();
        public CatalogueItem(String ItemCode, String Description, int CategoryID, int ReorderLevel, int ReorderMinQty,
                             String UnitofMeasure, String ShelfLocation, int AveragePrice, int CurrentStockQty) {
            put("ItemCode", ItemCode);
            put("Description", Description);
            put("CategoryID", String.valueOf(CategoryID));
            put("ReorderLevel", String.valueOf(ReorderLevel));
            put("ReorderMinQty", String.valueOf(ReorderMinQty));
            put("UnitOfMeasure", UnitofMeasure);
            put("ShelfLocation", ShelfLocation);
            put("AveragePrice", String.valueOf(AveragePrice));
            put("CurrentStockQty", String.valueOf(CurrentStockQty));
        }

        //retrieve catalogue list from WCF service
        public static List<CatalogueItem> listAll(String url) {
            List<CatalogueItem> list = new ArrayList<CatalogueItem>();
            JSONArray d = JSONParser.getJSONArrayFromUrl(baseURL + "/Catalogue");
            try {
                for (int i = 0; i < d.length(); i++) {
                    JSONObject f = d.getJSONObject(i);
                    list.add(new CatalogueItem(f.getString("ItemCode"),
                            f.getString("Description"),
                            f.getInt("CategoryID"),
                            f.getInt("ReorderLevel"),
                            f.getInt("ReorderMinQty"),
                            f.getString("UnitOfMeasure"),
                            f.getString("ShelfLocation"),
                            f.getInt("AveragePrice"),
                            f.getInt("CurrentStockQty")
                    ));
                }
                return list;
            } catch (Exception e) {
                Log.e("ReqDetailItem.listAll()", "JSONArray error");
                return list;
            }

        }

    //retrieve specific catalogue item from WCF service
        public static com.example.zhangle.team1.CatalogueItem getCatItemByID(String id) {
            JSONObject f = JSONParser.getJSONFromUrl(baseURL+"/Catalogue/"+id);

            try {
                return new CatalogueItem(
                        f.getString("ItemCode"),
                        f.getString("Description"),
                        f.getInt("CategoryID"),
                        f.getInt("ReorderLevel"),
                        f.getInt("ReorderMinQty"),
                        f.getString("UnitofMeasure"),
                        f.getString("ShelfLocation"),
                        f.getInt("AveragePrice"),
                        f.getInt("CurrentStockQty")
                );
            } catch (Exception e) {
                Log.e("CatalogueItem.getCatItemByID(String id)", "JSONArray error");
            }
            return null;
        }
    }


