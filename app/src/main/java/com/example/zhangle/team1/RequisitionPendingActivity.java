package com.example.zhangle.team1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class RequisitionPendingActivity extends Activity {

    TextView formid;
    TextView formdate;
    TextView formstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_requisition_pending);
        Intent i = getIntent();
        final String pid = i.getStringExtra("pid");
        final String pdate = i.getStringExtra("pdate");
        final String pstatus = i.getStringExtra("pstatus");
        final String pemp = i.getStringExtra("pemp");
        final Date date = new Date();

        ;


        formid = (TextView) findViewById(R.id.reqid);
        formid.setText(pid);
        formdate = (TextView) findViewById(R.id.reqdate);
        formdate.setText(pdate);
        formstatus = (TextView) findViewById(R.id.reqstatus);
        formstatus.setText(pstatus);

        new AsyncTask<String, Void, List<RequisitionDetailItem>>() {

            @Override
            protected List<RequisitionDetailItem> doInBackground(String... params) {
                return RequisitionDetailItem.listAll(params[0]);

            }
            @Override
            protected void onPostExecute(List<RequisitionDetailItem> result) {
                ListView lv = (ListView) findViewById(  R.id.listview2);
                lv.setAdapter(new SimpleAdapter(RequisitionPendingActivity.this,result,
                        R.layout.layoutrequisitiondetailitem,new String[]{"ItemCode","ItemQty"},
                        new int[]{ R.id.text21,R.id.retquantity }));
            }
        }.execute(pid);

        Button b = (Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequisitionItem r = new RequisitionItem();
                EditText comments = (EditText) findViewById(R.id.editTextComments);
                r.put("RequisitionID", pid);
                r.put("EmployeeID", pemp);
                r.put("AuthorizedBy", String.valueOf(CurrentUser.current));
                r.put("Date", pdate);
                r.put("AuthorizedDate", date.toString());
                r.put("Status", "Approved");
                r.put("Comment", comments.getText().toString());
                new AsyncTask<RequisitionItem, Void, Void>() {
                    @Override
                    protected Void doInBackground(RequisitionItem... params) {
                        RequisitionItem.updateRequisition(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(r);
            }
        });

        Button b2 = (Button) findViewById(R.id.button4);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequisitionItem r2 = new RequisitionItem();
                EditText comments = (EditText) findViewById(R.id.editTextComments);
                r2.put("RequisitionID", pid);
                r2.put("EmployeeID", pemp);
                r2.put("AuthorizedBy", String.valueOf(CurrentUser.current));
                r2.put("Date", pdate);
                r2.put("AuthorizedDate", date.toString());
                r2.put("Status", "Rejected");
                r2.put("Comment", comments.getText().toString());

                new AsyncTask<RequisitionItem, Void, Void>() {
                    @Override
                    protected Void doInBackground(RequisitionItem... params) {
                        RequisitionItem.updateRequisition(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(r2);
            }
        });
    }
}
