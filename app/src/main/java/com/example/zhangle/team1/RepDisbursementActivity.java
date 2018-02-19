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

import java.util.List;

public class RepDisbursementActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_disbursement);

        Intent i = getIntent();
        final String did = i.getStringExtra("DisID");
        int sum;

        new AsyncTask<String, Void, DisbursementSItem>() {
            @Override
            protected DisbursementSItem doInBackground(String... params) {
                DisbursementSItem s = DisbursementSItem.getDisbursementById(params[0]);
                return s;
            }

            //@Override
            protected void onPostExecute(DisbursementSItem result) {
                //print result data
                TextView text1 = findViewById(R.id.disbursementidtext41);
                text1.setText(result.get("DisbursementID").toString());
                TextView text2 = findViewById(R.id.disbursementstatus41);
                text2.setText(result.get("Status"));
                TextView text3 = findViewById(R.id.disbursementcollecttext41);
                text3.setText(result.get("CollectionPoint"));
                TextView text4 = findViewById(R.id.disbursementreptext41);
                if (result.get("Deprep").toString().equals("0")) {
                    text4.setText("");
                } else {
                    text4.setText(result.get("Deprep").toString());
                }
                TextView text5 = findViewById(R.id.disbursementdepartmenttext41);
                text5.setText(result.get("DepartmentName"));
            }
        }.execute(did);


        new AsyncTask<String, Void, List<DisbursementDetailItemS>>() {
            @Override
            protected List<DisbursementDetailItemS> doInBackground(String... params) {

                return DisbursementDetailItemS.getDisbursementDetailListByDisId(params[0]);
            }

            @Override
            protected void onPostExecute(List<DisbursementDetailItemS> result) {
                //print result data

                ListView ddlistview = findViewById(R.id.disbursementlistview41);
                ddlistview.setAdapter(new SimpleAdapter(RepDisbursementActivity.this, result,
                        R.layout.layout_disbursementdetail_item,
                        new String[]{"Description", "ItemQty"},
                        new int[]{R.id.itemdesctext, R.id.qtytext}));

                int sum = 0;
                for (DisbursementDetailItemS dditem : result) {
                    //loop
                    {
                        sum = sum + Integer.parseInt(dditem.get("ItemQty"));
                    }

                }
                displaysum(sum);

            }
        }.execute(did);


        Button b = (Button) findViewById(R.id.confirmeditbtn);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                new AsyncTask<String, Void,Void>() {
                    @Override
                    protected  Void doInBackground(String... params) {
                        ListView lv = findViewById(R.id.disbursementlistview41);


                        DisbursementSItem di = DisbursementSItem.getDisbursementById(did);
                        DisbursementSItem.updateRepDisbursementItemS(di);
                        return null;

                    }
                    @Override
                    protected void onPostExecute(Void result) {

                        finish();

                        //DisbursementEditActS.this.notifyAll();

                    }
                }.execute(did);
            }
        });

    }

    public void displaysum(int sum) {
        TextView text6 = findViewById(R.id.totalqtytext41);
        text6.setText(String.valueOf(sum));

    }

}
