package com.example.zhangle.team1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangle on 23/1/18.
 */

public class EmployeeItem extends HashMap<String,String> {
    //final static String baseURL = "http://13.229.50.128:8080";
    //final static String baseURL = "http://172.17.255.37/LUSSIS/Service.svc";
    final static String baseURL = JSONParser.getJSONURL();

    public EmployeeItem(int EmployeeID,String EmployeeName,String Password,int RoleID,String DepartmentID,
                           String Authorization,String StartDate,String EndDate,String EmailAdd) {
        put("EmployeeID", String.valueOf(EmployeeID));
        put("EmployeeName", EmployeeName);
        put("Password", Password);
        put("RoleID", String.valueOf(RoleID));
        put("DepartmentID", DepartmentID);
        put("Authorization", Authorization);
        put("StartDate", StartDate);
        put("EndDate", EndDate);
        put("EmailAdd", EmailAdd);
    }
    public EmployeeItem(int EmployeeID,String EmployeeName,String Password,int RoleID,String DepartmentID) {
        put("EmployeeID", String.valueOf(EmployeeID));
        put("EmployeeName", EmployeeName);
        put("Password", Password);
        put("RoleID", String.valueOf(RoleID));
        put("DepartmentID", DepartmentID);
    }
    public EmployeeItem(int EmployeeID,String EmployeeName,String Password,int RoleID,String DepartmentID,
                        String DepartmentName,String HeadName,String CollectionPoint) {
        put("EmployeeID", String.valueOf(EmployeeID));
        put("EmployeeName", EmployeeName);
        put("Password", Password);
        put("RoleID", String.valueOf(RoleID));
        put("DepartmentID", DepartmentID);
        put("DepartmentName", DepartmentName);
        put("HeadName", HeadName);
        put("CollectionPoint", CollectionPoint);
    }
    public static List<EmployeeItem> listAll(String url) {
        List<EmployeeItem> list = new ArrayList<EmployeeItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/Employee/");
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new EmployeeItem(
                        b.getInt("EmployeeID"),
                        b.getString("EmployeeName"),
                        b.getString("Password"),
                        b.getInt("RoleID"),
                        b.getString("DepartmentID"),
                        b.getString("Authorization"),
                        b.getString("StartDate"),
                        b.getString("EndDate"),
                        b.getString("EmailAdd")
                ));
            }
        } catch (Exception e) {
            Log.e("EmployeeItem.listAll()", "JSONArray error");
        }
        return list;
    }

    public static EmployeeItem getId(String bid) {
        JSONObject b = JSONParser.getJSONFromUrl(baseURL + "/Employee/" + bid);
        try {
            EmployeeItem b1 =  new EmployeeItem(
                    b.getInt("EmployeeID"),
                    b.getString("EmployeeName"),
                    b.getString("Password"),
                    b.getInt("RoleID"),
                    b.getString("DepartmentID"),
                    b.getString("Authorization"),
                    b.getString("StartDate"),
                    b.getString("EndDate"),
                    b.getString("EmailAdd")
            );
            return b1;
        } catch (Exception e) {
            Log.e("EmployeeItem.getId()", "JSONArray error");
            return(null);
        }

    }

    public static EmployeeItem getCollectionById(String bid) {
        JSONObject b = JSONParser.getJSONFromUrl(baseURL + "/Employee/" + bid);
        try {
            JSONObject c = JSONParser.getJSONFromUrl(baseURL + "/Department/" +  b.getString("DepartmentID"));
            EmployeeItem b1 =  new EmployeeItem(
                    b.getInt("EmployeeID"),
                    b.getString("EmployeeName"),
                    b.getString("Password"),
                    b.getInt("RoleID"),
                    b.getString("DepartmentID"),
                    c.getString("DepartmentName"),
                    c.getString("HeadName"),
                    c.getString("CollectionPoint")
            );
            return b1;
        } catch (Exception e) {
            Log.e("EmployeeItem.getCollectionById()", "JSONArray error");
            return(null);
        }

    }
}
