package com.example.zhangle.team1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class DisbursementDetailActivity extends Activity {

    TextView formid;
    TextView formpoint;
    TextView formstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail);

        //get the data of disbursement from previous fragment
        //populate title by it
        Intent i = getIntent();
        String did = i.getStringExtra("did");
        String dpoint = i.getStringExtra("dpoint");
        String dstatus = i.getStringExtra("dstatus");

        formid = (TextView) findViewById(R.id.disID);
        formid.setText(did);
        formpoint = (TextView) findViewById(R.id.disCollect);
        formpoint.setText(dpoint);
        formstatus = (TextView) findViewById(R.id.disStatus);
        formstatus.setText(dstatus);

        //show the disbursement detail list
        new AsyncTask<String, Void, List<DisbursementDetailItem>>() {

            @Override
            protected List<DisbursementDetailItem> doInBackground(String... params) {
                return DisbursementDetailItem.listAll(params[0]);

            }
            @Override
            protected void onPostExecute(List<DisbursementDetailItem> result) {
                ListView lv = (ListView) findViewById(  R.id.listview2);
                lv.setAdapter(new SimpleAdapter(DisbursementDetailActivity.this,result,
                        R.layout.layoutdisbursementdetailitem,new String[]{"ItemCode","ItemQty"},
                        new int[]{ R.id.text21, R.id.disquantity }));
            }
        }.execute(did);
    }
}
