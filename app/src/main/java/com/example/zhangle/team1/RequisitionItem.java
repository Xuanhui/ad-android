package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.text.DateFormat;
/**
 * Created by zhangle on 23/1/18.
 */

public class RequisitionItem extends HashMap<String,String> {
    //final static String baseURL = "http://172.17.251.82/LUSSIS/Service.svc";
    final static String baseURL = JSONParser.getJSONURL();

//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//    String formattedDate;
//    Date newDateString;
//    String textdate;
        public RequisitionItem(int RequisitionID, int EmployeeID, int AuthorizedBy, String sdate, String AuthorizedDate,
                           String Status, String Comment) {
        put("RequisitionID", String.valueOf(RequisitionID));
        put("EmployeeID", String.valueOf(EmployeeID));
        put("AuthorizedBy", String.valueOf(AuthorizedBy));
//        sdate.replace("/(date","").replace(")/","");
//        int ia = Integer.valueOf(sdate);
//        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeInMillis(ia * 1000L);
//        formattedDate = sdf.format(cal.getTime());
        put("Date", sdate);
        put("AuthorizedDate", AuthorizedDate);
        put("Status", Status);
        put("Comment", Comment);
    }
    public RequisitionItem(){}

    public RequisitionItem(int RequisitionID,String EmployeeID,int AuthorizedBy,String date,String AuthorizedDate,
                           String Status,String Comment) {
        put("RequisitionID", String.valueOf(RequisitionID));
        put("EmployeeID", EmployeeID);
        put("AuthorizedBy", String.valueOf(AuthorizedBy));
        //put("Date", date);
        //put("AuthorizedDate", AuthorizedDate);
        put("Status", Status);
        put("Comment", Comment);
    }

    public RequisitionItem(int RequisitionID, int AuthorizedBy, String AuthorizedDate ,String date,
                           String Status) {
        put("RequisitionID", String.valueOf(RequisitionID));
        put("AuthorizedBy", String.valueOf((AuthorizedBy)));
        put("AuthorizedDate", AuthorizedDate);
        put("Date", date);
        put("Status", Status);

    }

    public static List<RequisitionItem> listAll(String url) {
        List<RequisitionItem> list = new ArrayList<RequisitionItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Requisition");
        String s1 = "";int s2 = 0;String s3="";
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                s1 = "";s2 = 0;s3="";
                if (b.getString("Status").equals("Pending")){}
                else {
                    if (b.get("AuthorizedBy").toString().equals("null")) {
                    } else {
                        s2 = b.getInt("AuthorizedBy");
                    }
                    if (b.get("AuthorizedBy").toString().equals("null")) {
                    } else {
                        s1 = b.getString("AuthorizedDate");
                    }
                    if (b.get("Comment").toString().equals("null")) {
                    } else {
                        s3 = b.getString("Comment");
                    }
                    list.add(new RequisitionItem(
                            b.getInt("RequisitionID"),
                            b.getInt("EmployeeID"),
                            s2,
                            JSONParser.transDate(b.getString("Date")),
                            s1,
                            b.getString("Status"),
                            s3));
                }
            }
            return list;
        } catch (Exception e) {
            Log.e("ReqItem.listAll()", "JSONArray error");
            return list;
        }

    }

    public static RequisitionItem GetItemByID(String id){
        JSONObject c = JSONParser.getJSONFromUrl(baseURL+"/Requisition/"+id);
        try {
            JSONObject ce = JSONParser.getJSONFromUrl(baseURL+"/Employee/"+c.getInt("EmployeeID"));
            String authorizeddate = "";int authorizedby = 0;String comment="";
            if(c.get("AuthorizedBy").toString().equals("null")) {
                //authorizedby = 0;
            }
            else{authorizedby = c.getInt("AuthorizedBy");}
            if (c.get("AuthorizedBy").toString().equals("null")){
               // authorizeddate = "";
            }
            else{authorizeddate = c.getString("AuthorizedDate");}
            if (c.get("Comment").toString().equals("null")){
                //comment = "";
            }
            else{comment = c.getString("Comment");}

            String testdate = c.getString("Date");

            return new RequisitionItem(
                    c.getInt("RequisitionID"),
                    ce.getString("EmployeeName"),
                    authorizedby,
                    JSONParser.transDate(c.getString("Date")),
                    authorizeddate,
                    c.getString("Status"),
                    comment
            );
        }catch (Exception e) {
            Log.e("Req.GetItemByID(Str id)", "JSONArray error");
        }
        return null;
    }

    public static RequisitionItem getReqForTitleById(String id){
        JSONObject c = JSONParser.getJSONFromUrl(baseURL+"/Requisition/"+id);
        RequisitionItem req =null;
        try{
            req = new RequisitionItem(
                    c.getInt("RequisitionID"),
                    c.getInt("AuthorizedBy"),
                    c.getString("AuthorizedDate"),
                    JSONParser.transDate(c.getString("Date")),
                    c.getString("Status"));
        }catch (Exception e){}


        return req;

    }

    public static RequisitionItem getPendingReqForTitleById(String id){
        JSONObject c = JSONParser.getJSONFromUrl(baseURL+"/Requisition/"+id);
        RequisitionItem req =null;
        try{
            req = new RequisitionItem(
                    c.getInt("RequisitionID"),
                   0,
                    "",
                    JSONParser.transDate(c.getString("Date")),
                    c.getString("Status"));
        }catch (Exception e){}


        return req;

    }

    public static List<RequisitionItem> listPending(String url) {
        List<RequisitionItem> plist = new ArrayList<RequisitionItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Requisition");
        String s1 = "";int s2 = 0;String s3="";
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                if (b.getString("Status").equals("Pending")) {
                    s1 = "";
                    s2 = 0;
                    s3 = "";
                    if (b.get("AuthorizedBy").toString().equals("null")) {
                    } else {
                        s2 = b.getInt("AuthorizedBy");
                    }
                    if (b.get("AuthorizedBy").toString().equals("null")) {
                    } else {
                        s1 = b.getString("AuthorizedDate");
                    }
                    if (b.get("Comment").toString().equals("null")) {
                    } else {
                        s3 = b.getString("Comment");
                    }
                    plist.add(new RequisitionItem(
                            b.getInt("RequisitionID"),
                            b.getInt("EmployeeID"),
                            s2,
                            JSONParser.transDate(b.getString("Date")),
                            s1,
                            b.getString("Status"),
                            s3));
                }
            }
        } catch (Exception e) {
            Log.e("ReqItem.listPending", "JSONArray error");
        }
        return plist;
    }

    public static void updateRequisition(RequisitionItem req){
        JSONObject jreq = new JSONObject();
        try{
            jreq.put("RequisitionID", req.get("RequisitionID"));
            jreq.put("EmployeeID", req.get("EmployeeID"));
            jreq.put("AuthorizedBy", req.get("AuthorizedBy"));
            jreq.put("Status", req.get("Status"));
            jreq.put("Comment", req.get("Comment"));
        }catch(Exception e){}
        String result = JSONParser.postStream(baseURL+"/UpdateRequisition", jreq.toString());

    }
}
