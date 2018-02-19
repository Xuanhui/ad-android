package com.example.zhangle.team1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class RepRequisitionPendingActivity extends Activity {

    TextView formid;
    TextView formdate;
    TextView formstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_requisition_pending211);
        Intent i = getIntent();
        final String pid = i.getStringExtra("pid");
        final String pdate = i.getStringExtra("pdate");
        final String pstatus = i.getStringExtra("pstatus");
        final String pemp = i.getStringExtra("pemp");
        final Date date = new Date();

        ;


        formid = (TextView) findViewById(R.id.repreqid);
        formid.setText(pid);
        formdate = (TextView) findViewById(R.id.repreqdate);
        formdate.setText(pdate);
        formstatus = (TextView) findViewById(R.id.repreqstatus);
        formstatus.setText(pstatus);

        new AsyncTask<String, Void, List<RequisitionDetailItem>>() {

            @Override
            protected List<RequisitionDetailItem> doInBackground(String... params) {
                return RequisitionDetailItem.listAll(params[0]);

            }
            @Override
            protected void onPostExecute(List<RequisitionDetailItem> result) {
                ListView lv = (ListView) findViewById(  R.id.replistview);
                lv.setAdapter(new SimpleAdapter(RepRequisitionPendingActivity.this,result,
                        R.layout.layoutrequisitiondetailitem,new String[]{"ItemCode","ItemQty"},
                        new int[]{ R.id.text21,R.id.retquantity }));
            }
        }.execute(pid);


    }
}
