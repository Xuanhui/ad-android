package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 26/1/2018.
 */

public class DisbursementSItem extends HashMap<String,String> {
    //final static String baseURL = "http://13.229.50.128:8080";
    public static int clerkcurrent=0;
    //final static String baseURL = "http://172.17.255.37/LUSSIS/Service.svc";
    final static String baseURL = JSONParser.getJSONURL();

    //Construtor
    public DisbursementSItem(int DisbursementID, String DepName, String Collection, int Representative, String Status,int Clerk, String DisbursementDate) {
        put("DisbursementID", String.valueOf(DisbursementID));
        put("DepartmentName", String.valueOf(DepName));
        put("CollectionPoint", Collection);
        put("Deprep", String.valueOf(Representative));
        put("Status", Status);
        put("Clerk", String.valueOf(Clerk));
        put("DisbursementDate",DisbursementDate);
    }
    public DisbursementSItem(int DisbursementID, String DepName, String Collection, int Representative, String Status, String DisbursementDate) {
        put("DisbursementID", String.valueOf(DisbursementID));
        put("DepartmentName", String.valueOf(DepName));
        put("CollectionPoint", Collection);
        put("Deprep", String.valueOf(Representative));
        put("Status", Status);
        put("DisbursementDate", DisbursementDate);

    }

    public DisbursementSItem(int DisbursementID, String DepName, String Collection, int Representative, String Status) {
        put("DisbursementID", String.valueOf(DisbursementID));
        put("DepartmentName", String.valueOf(DepName));
        put("CollectionPoint", Collection);
        put("Deprep", String.valueOf(Representative));
        put("Status", Status);


    }
    public DisbursementSItem(){}

    //End of Constructor


    //Confirm Disbursement by Representative
    public static void updateDisbursementItemS(DisbursementSItem di){
        JSONObject jDisbursement = new JSONObject();
        try{;
            jDisbursement.put("DisbursementID",(di.get("DisbursementID")));
            jDisbursement.put("Status",(di.get("Status")));
        }catch(Exception e){}
        String result = JSONParser.postStream(baseURL+"/UpdateDisbursement",jDisbursement.toString());
    }

    public  static void updateRepDisbursementItemS(DisbursementSItem di){
        JSONObject jDisbursement = new JSONObject();
        Date d = new Date();
        Long str = d.getTime();
        String temp = str.toString();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        String DisbursementDate = sdf.format(str).toString();
        String jsonDate = "/Date("+temp+"+0800)/";

        try{
            jDisbursement.put("DisbursementID",(Integer.parseInt(di.get("DisbursementID"))));
            jDisbursement.put("Deprep",CurrentUser.current);
            jDisbursement.put("Status","Confirmed");
            jDisbursement.put("DisbursementDate",jsonDate);
            //jDisbursement.put("Deprep",di.get("Deprep"));

        }catch(Exception e){}
        String result = JSONParser.postStream(baseURL+"/UpdateDisbursement",jDisbursement.toString());

    }
    //End of function

    //List all Disbursement

    public static List<DisbursementSItem> listAll(String eid) {
        List<DisbursementSItem> list = new ArrayList<DisbursementSItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Disbursement");
        try {
            for(int i=0;i<a.length();i++) {
                JSONObject b = a.getJSONObject(i);
                String aqqq = b.get("Deprep").toString();
                if (aqqq.equals("null"))
                    list.add(new DisbursementSItem(
                            b.getInt("DisbursementID"),
                            b.getString("DepartmentName"),
                            b.getString("CollectionPoint"),
                            0,
                            b.getString("Status"),
                            ""
                    ));
                else
                    list.add(new DisbursementSItem(
                            b.getInt("DisbursementID"),
                            b.getString("DepartmentName"),
                            b.getString("CollectionPoint"),
                            b.getInt("Deprep"),
                            b.getString("Status"),
                            b.getString("DisbursementDate")
                    ));
            }
        } catch (Exception e) {
            Log.e("DisbItem.listAll", "JSONArray error");
        }
        return list;
    }

    //Get specific Disbursement

    public static DisbursementSItem getDisbursementById(String id){
        //DisbursementItem item = new DisbursementItem(0,0,null,null);
        JSONObject object = JSONParser.getJSONFromUrl((baseURL+"/Disbursement/"+id));
        try{
            int i = object.getInt("DisbursementID");
                if (object.get("Deprep").toString().equals("null")& object.get("DisbursementDate").toString().equals("null") & object.get("Deprep").toString().equals("null")) {
                    DisbursementSItem s = new DisbursementSItem(object.getInt("DisbursementID"),
                        object.getString("DepartmentName"),
                        object.getString("CollectionPoint"),
                        0,
                        object.getString("Status"),
                        ""


                );

                return s;
            }
            else
            {
                DisbursementSItem s = new DisbursementSItem(object.getInt("DisbursementID"),
                        object.getString("DepartmentName"),
                        object.getString("CollectionPoint"),
                        object.getInt("Deprep"),
                        object.getString("Status")

                );

                return s;
            }
        }catch (Exception e){
            Log.e("DisbursementItem.get",
                    "error");
            return (null);
        }

    }


    //List all pending disbursement list

    public static List<DisbursementSItem> listPendingList(String url) {
        List<DisbursementSItem> list = new ArrayList<DisbursementSItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Disbursement");
        try {
            for(int i=0;i<a.length();i++) {
                JSONObject b = a.getJSONObject(i);
                String j = (String) b.get("Status");

                JSONObject currentemployee = JSONParser.getJSONFromUrl(baseURL+"/Employee/"+CurrentUser.current);
                if ( b.getString("DepartmentID").equals(currentemployee.getString("DepartmentID"))) {

                    if (b.getString("Status").equals("Pending")) {
                        list.add(new DisbursementSItem(
                                b.getInt("DisbursementID"),
                                b.getString("DepartmentName"),
                                b.getString("CollectionPoint"),
                                0,
                                b.getString("Status")
                        ));
                    }
                }


            }
        } catch (Exception e) {
            Log.e("DisbItem.listAll", "JSONArray error");
        }
        return list;
    }

    public static List<DisbursementSItem> listPendingListClerk(String url) {
        List<DisbursementSItem> list = new ArrayList<DisbursementSItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Disbursement");
        try {
            for(int i=0;i<a.length();i++) {
                JSONObject b = a.getJSONObject(i);
                String j = (String) b.get("Status");
                   if (b.getString("Status").equals("Pending")) {
                           list.add(new DisbursementSItem(
                                   b.getInt("DisbursementID"),
                                   b.getString("DepartmentName"),
                                   b.getString("CollectionPoint"),
                                   0,
                                   b.getString("Status")
                           ));

                    }

            }
        } catch (Exception e) {
            Log.e("DisbItem.listAll", "JSONArray error");
        }
        return list;
    }

    //List all confirmed Disbursement list

    public static List<DisbursementSItem> listConfirmedList(String url) {
        List<DisbursementSItem> list = new ArrayList<DisbursementSItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Disbursement");
        try {
            for(int i=0;i<a.length();i++) {
                JSONObject b = a.getJSONObject(i);
                String j = (String) b.get("Status");
                if (!b.getString("Status").equals( "Pending")){
                    JSONObject currentemployee = JSONParser.getJSONFromUrl(baseURL+"/Employee/"+CurrentUser.current);
                    if ( b.getString("DepartmentID").equals(currentemployee.getString("DepartmentID"))) {

                        list.add(new DisbursementSItem(
                                b.getInt("DisbursementID"),
                                b.getString("DepartmentName"),
                                b.getString("CollectionPoint"),
                                b.getInt("Deprep"),
                                b.getString("Status")

                        ));
                    }

                }
                else
                    continue;
            }
        } catch (Exception e) {
            Log.e("DisbItem.listAll", "JSONArray error");
        }
        return list;
    }

    public static List<DisbursementSItem> listConfirmedListClerk(String url) {
        List<DisbursementSItem> list = new ArrayList<DisbursementSItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Disbursement");
        try {
            for(int i=0;i<a.length();i++) {
                JSONObject b = a.getJSONObject(i);
                String j = (String) b.get("Status");
                if (!b.getString("Status").equals( "Pending")){

                    list.add(new DisbursementSItem(
                            b.getInt("DisbursementID"),
                            b.getString("DepartmentName"),
                            b.getString("CollectionPoint"),
                            b.getInt("Deprep"),
                            b.getString("Status")

                    ));

                }
                else
                    continue;
            }
        } catch (Exception e) {
            Log.e("DisbItem.listAll", "JSONArray error");
        }
        return list;
    }
}

