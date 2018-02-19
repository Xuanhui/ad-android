package com.example.zhangle.team1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class RequisitionDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_pending211);
        Intent i = getIntent();
        String rid = i.getStringExtra("rid");
        new AsyncTask<String, Void, RequisitionItem>() {
            @Override
            protected RequisitionItem doInBackground(String... params) {
                return RequisitionItem.getPendingReqForTitleById(params[0]);
            }
            @Override
            protected void onPostExecute(RequisitionItem result) {
                //print result data
                TextView text1 = findViewById(R.id.repreqid);
                text1.setText(result.get("RequisitionID").toString());
                TextView text2 = findViewById(R.id.repreqstatus);
                text2.setText(result.get("Status"));
                TextView text3=findViewById(R.id.repreqdate);
                text3.setText(result.get("Date").toString());
                //TextView text4=findViewById(R.id.approvedbyname);
                //text4.setText(result.get("AuthorizedBy").toString());
//                if (result.get("AuthorizedBy").toString().equals("0")) { }
//                else{
//                    //showEmployeeName(result.get("AuthorizedBy").toString());
//                    }
//                TextView text5=findViewById(R.id.approvedatedetail);
//                text5.setText(result.get("AuthorizedDate").toString());
            }
        }.execute(rid);

        new AsyncTask<String, Void, List<ReqDetailItem>>() {
            @Override
            protected List<ReqDetailItem> doInBackground(String... params) {
                return ReqDetailItem.listAll(params[0]);

            }
            @Override
            protected void onPostExecute(List<ReqDetailItem> result) {
                ListView lv = (ListView) findViewById(R.id.replistview);
                lv.setAdapter(new SimpleAdapter(RequisitionDetailActivity.this,result,
                        R.layout.layoutrequisitiondetailitem,new String[]{"ItemCode","ItemQty"},
                        new int[]{ R.id.text21,R.id.retquantity }));
            }
        }.execute(rid);
    }
}
