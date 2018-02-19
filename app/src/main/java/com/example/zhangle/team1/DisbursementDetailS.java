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

public class DisbursementDetailS extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail_s);

        //get the disbursement id from previous fragment
        Intent i = getIntent();
        final String did = i.getStringExtra("DisID");
        int sum;

        //retrieve the disbursement and populate the title for the detail page
        new AsyncTask<String, Void, DisbursementSItem>() {
            @Override
            protected DisbursementSItem doInBackground(String... params) {
                DisbursementSItem s = DisbursementSItem.getDisbursementById(params[0]);
                return s;
            }

            //@Override
            protected void onPostExecute(DisbursementSItem result) {
                //print result data
                TextView text1 = findViewById(R.id.disbursementidtext);
                text1.setText(result.get("DisbursementID").toString());
                TextView text2 = findViewById(R.id.disbursementstatus);
                text2.setText(result.get("Status"));
                TextView text3 = findViewById(R.id.disbursementcollecttext);
                text3.setText(result.get("CollectionPoint"));
                TextView text4 = findViewById(R.id.disbursementreptext);
                if (result.get("Deprep").toString().equals("0")) {
                    text4.setText("");
                } else {
                    text4.setText(result.get("Deprep").toString());
                }
                TextView text5 = findViewById(R.id.disbursementdepartmenttext);
                text5.setText(result.get("DepartmentName"));
            }
        }.execute(did);


        //retrieve the disbursement detail items with same disbursement id
        //and calculate the sum of item quantity
        new AsyncTask<String, Void, List<DisbursementDetailItemS>>() {
            @Override
            protected List<DisbursementDetailItemS> doInBackground(String... params) {

                return DisbursementDetailItemS.getDisbursementDetailListByDisId(params[0]);
            }

            @Override
            protected void onPostExecute(List<DisbursementDetailItemS> result) {
                //print result data

                ListView ddlistview = findViewById(R.id.disbursementlistview);
                ddlistview.setAdapter(new SimpleAdapter(DisbursementDetailS.this, result,
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

    }

    public void displaysum(int sum) {
        TextView text6 = findViewById(R.id.totalqtytext);

        //DisbursementDetailItemS testo = new DisbursementDetailItemS();
        text6.setText(String.valueOf(sum));
       // List<DisbursementDetailItemS> ltext = testo.ldist;
    }



}


