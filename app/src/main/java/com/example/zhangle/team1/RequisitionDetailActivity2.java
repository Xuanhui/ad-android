package com.example.zhangle.team1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class RequisitionDetailActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_detail);

        Intent i = getIntent();
        String rid = i.getStringExtra("rid");

        //populate top part of activity_requisition_detail.xml
        new AsyncTask<String, Void, RequisitionItem>() {
            @Override
            protected RequisitionItem doInBackground(String... params) {
                return RequisitionItem.getReqForTitleById(params[0]);
            }
            @Override
            protected void onPostExecute(RequisitionItem result) {
                //print result data
                TextView text1 = findViewById(R.id.reqiddetail);
                text1.setText(result.get("RequisitionID").toString());
                TextView text2 = findViewById(R.id.statusdetail);
                text2.setText(result.get("Status"));
                TextView text3=findViewById(R.id.datesubmitteddetail);
                text3.setText(result.get("Date").toString());
                TextView text4=findViewById(R.id.approvedbyname);
                text4.setText(result.get("AuthorizedBy").toString());
//                if (result.get("AuthorizedBy").toString().equals("0")) { }
//                else{showEmployeeName(result.get("AuthorizedBy").toString());}
                TextView text5=findViewById(R.id.approvedatedetail);
                text5.setText(JSONParser.transDate(result.get("AuthorizedDate").toString()));
            }
        }.execute(rid);

        //populate middle part listview of activity_requisition_detail.xml
        new AsyncTask<String, Void, List<ReqDetailItem>>() {
            @Override
            protected List<ReqDetailItem> doInBackground(String... params) {
                return ReqDetailItem.listAll(params[0]);
            }
            @Override
            protected void onPostExecute(List<ReqDetailItem> result) {
                ListView lv = (ListView) findViewById(  R.id.listview2);
                lv.setAdapter(new SimpleAdapter(RequisitionDetailActivity2.this,result,
                        R.layout.layoutrequisitiondetailitem,new String[]{"ItemCode","ItemQty"},
                        new int[]{ R.id.text21,R.id.retquantity }));
            }
            //add the parameters you need here .execute(add here)
        }.execute(rid);

/*        //populate bottom part of activity_requisition_detail.xml
        new AsyncTask<String, Void, ReqDetailItem>() {
            @Override
            protected ReqDetailItem doInBackground(String... params) {
                return ReqDetailItem.getReqDetailsByID(params[0]);
            }
            @Override
            protected void onPostExecute(ReqDetailItem result) {
                //print result data
                TextView text4 = findViewById(R.id.totalqty);

                text4.setText(result.get("ItemQty").toString());
            }
        }.execute(rid);*/
    }
    public void showEmployeeName(String s){

        new AsyncTask<String, Void, EmployeeItem>() {
            @Override
            protected EmployeeItem doInBackground(String... params) {
                //get data information from json url - look at the requisitionitem - see which function and which url to use
                return EmployeeItem.getId(params[0]);
            }
            @Override
            protected void onPostExecute(EmployeeItem result) {
                //display this on the listview of fragment_requisition.xml
                TextView text4=findViewById(R.id.approvedbyname);
                text4.setText(result.get("EmployeeName").toString());
            }
            //add the parameters you need here .execute(add here)
        }.execute(s);

    }
}
