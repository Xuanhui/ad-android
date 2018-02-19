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

public class DisbursementDetailItemS extends HashMap<String,String> {
    static public List<DisbursementDetailItemS> ldist;
    //final static String baseURL = "http://172.17.255.37/LUSSIS/Service.svc";
    final static String baseURL = JSONParser.getJSONURL();
    public DisbursementDetailItemS(int DisbursementDetailID, int DisbursementID, String ItemCode, String Description, int ItemQty, int Amount,int Clerk) {
        put("DisbursementDetailID", String.valueOf(DisbursementDetailID));
        put("DisbursementID", String.valueOf(DisbursementID));
        put("Itemcode", ItemCode);
        put("Description", Description);
        put("ItemQty", String.valueOf(ItemQty));
        put("Amount", String.valueOf(Amount));
        put("ClerkID",String.valueOf(Clerk));
    }
    public DisbursementDetailItemS(){}



    //update disbursement detail item qty
    //pass jsonobject to wcf

    public static void updateDisbursementDetailItem(DisbursementDetailItemS ret){
        JSONObject jDisbursementDetail =new JSONObject();

        try{jDisbursementDetail.put("DisbursementDetailID",(ret.get("DisbursementDetailID")));
            jDisbursementDetail.put("DisbursementID",(ret.get("DisbursementID")));
            jDisbursementDetail.put("Itemcode",ret.get("Itemcode"));
            jDisbursementDetail.put("Description",ret.get("Description"));
            jDisbursementDetail.put("ItemQty",(ret.get("ItemQty")));
            jDisbursementDetail.put("Amount",ret.get("Amount"));
            jDisbursementDetail.put("ClerkID",CurrentUser.current);
        }catch(Exception e){}

        String result = JSONParser.postStream(baseURL+"/UpdateDisbursementDetail",jDisbursementDetail.toString());

        //DisbursementSItem.updateDisbursementItemS(DisbursementSItem.getDisbursementById(ret.get("DisbursementID").toString()));


    }


    //get the Disbursement Detail list with same Disbursement ID

    public static List<DisbursementDetailItemS> getDisbursementDetailListByDisId(String id){
        List<DisbursementDetailItemS>list=new ArrayList<DisbursementDetailItemS>();
        ldist = new ArrayList<DisbursementDetailItemS>();
        try{
            JSONArray a= JSONParser.getJSONArrayFromUrl(baseURL+"/DisbursementDetail/"+id);
            for(int i=0;i<a.length();i++){
                JSONObject b = a.getJSONObject(i);
                list.add(new DisbursementDetailItemS(
                        b.getInt("DisbursementDetailID"),
                        b.getInt("DisbursementID"),
                        b.getString("Itemcode"),
                        b.getString("Description"),
                        b.getInt("ItemQty"),
                        b.getInt("Amount"),
                        0
                ));

            }
        } catch (Exception e) {
            Log.e("getRetrievalItemList()", "JSONArray error");
        }
        return list;

    }

//    public  static int getDisbursementTotalQty(String id){
//        List<DisbursementDetailItemS> list = new ArrayList<DisbursementDetailItemS>();
//        int sum = 0;
//        try{
//
//            JSONArray arr = JSONParser.getJSONArrayFromUrl(baseURL+"/DisbursementDetail"+id);
//            for (int i=0; i<arr.length();i++) {
//                JSONObject b = arr.getJSONObject(i);
//
//                sum = sum+b.getInt("ItemQty");
//            }
//
//        }catch (Exception e){
//            Log.e("getTotalQty()","TotalQTy() JSONArray error");
//        }
//
//
//        return  sum;
//    }

}
