package com.example.zhangle.team1;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangle on 31/1/18.
 */

public class TempUpdateDisbursementSitem extends HashMap<String,String> {
    final static String baseURL = "http://172.17.251.82/LUSSIS/Service.svc";
    public TempUpdateDisbursementSitem(int DisbursementDetailID, int DisbursementID, String ItemCode, String Description, int ItemQty, int Amount) {
        put("DisbursementDetailID", String.valueOf(DisbursementDetailID));
        put("DisbursementID", String.valueOf(DisbursementID));
        put("Itemcode", ItemCode);
        put("ItemQty", String.valueOf(Amount));
    }
}
