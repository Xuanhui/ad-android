package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Reuben on 25/1/2018.
 */

public class DisbursementItem extends HashMap<String,String> {
    //final static String baseURL = "http://13.229.50.128:8080";
    //final static String baseURL = "http://172.17.255.37/LUSSIS/Service.svc";
    final static String baseURL = JSONParser.getJSONURL();

    public DisbursementItem(int DisbursementID, int DeptRep, String Clerk, String DisbursementDate, String Status,
                            int Amount, String DepartmentID) {
        put("DisbursementID", String.valueOf(DisbursementID));
        put("DeptRep", String.valueOf(DeptRep));
        put("Clerk", Clerk);
        put("Disbursement Date", DisbursementDate);
        put("Status", Status);
        put("Amount", String.valueOf(Amount));
        put("DepartmentID", DepartmentID);
    }

    public static List<DisbursementItem> listAll(String url) {
        List<DisbursementItem> list = new ArrayList<DisbursementItem>();
        //JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/listDisbursements");
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Disbursement");
        int s1 = 0;String sd = "";
        try {
            for(int i=0;i<a.length();i++) {
                JSONObject b = a.getJSONObject(i);
                String aqqq = b.get("Deprep").toString();
                if (aqqq.equals("null")){s1=0;}else{s1=b.getInt("Deprep"); }
                if (b.get("DisbursementDate").toString().equals("null")){}else{sd = b.getString("DisbursementDate");}
                JSONObject e = JSONParser.getJSONFromUrl(baseURL+"/Employee/"+b.get("Clerk"));
                    list.add(new DisbursementItem(
                            b.getInt("DisbursementID"),
                            s1,
                            e.getString("EmployeeName"),
                            sd,
                            b.getString("Status"),
                            b.getInt("Amount"),
                            b.getString("DepartmentID")
                    ));
            }
        } catch (Exception e) {
            Log.e("DisItem.listAll", "JSONArray error");
        }
        return list;
}




}
