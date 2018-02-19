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

import custfonts.MyEditText;

public class DisbursementEditActS extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_edit_act_s);

        //as the same as disbursement detailS activity but have one button more
        //for clerk, used for update the disbursement detail item quantity
        Intent i = getIntent();
        final String did = i.getStringExtra("DisID");


        new AsyncTask<String, Void, DisbursementSItem>() {
            @Override
            protected DisbursementSItem doInBackground(String... params) {
                DisbursementSItem s = DisbursementSItem.getDisbursementById(params[0]);
                return s;
            }

            //@Override
            protected void onPostExecute(DisbursementSItem result) {
                //print result data
                TextView text1 = findViewById(R.id.disbursementidtext31);
                text1.setText(result.get("DisbursementID").toString());
                TextView text2 = findViewById(R.id.disbursementstatus31);
                text2.setText(result.get("Status"));
                TextView text3 = findViewById(R.id.disbursementcollecttext31);
                text3.setText(result.get("CollectionPoint"));
                TextView text4 = findViewById(R.id.disbursementreptext31);
                if (result.get("Deprep").toString().equals("0")) {
                    text4.setText("");
                } else {
                    text4.setText(result.get("Deprep").toString());
                }
                TextView text5 = findViewById(R.id.disbursementdepartmenttext31);
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

                ListView ddlistview = findViewById(R.id.disbursementlistview31);
                ddlistview.setAdapter(new SimpleAdapter(DisbursementEditActS.this, result,
                        R.layout.layout_editable_disdetailitem,
                        new String[]{"Description", "ItemQty"},
                        new int[]{R.id.itemdesctext31, R.id.qtytext31}));
            }
        }.execute(did);


        //click the edit button and invoke update function for each detail item
        Button b = (Button) findViewById(R.id.disbursementeditbtn);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                new AsyncTask<String, Void,Void>() {
                    @Override
                    protected  Void doInBackground(String... params) {
                        ListView lv = findViewById(R.id.disbursementlistview31);

                       List<DisbursementDetailItemS> dislist = DisbursementDetailItemS.getDisbursementDetailListByDisId(params[0]);

                        for(int i=0;i<dislist.size();i++){

                            View v = lv.getChildAt(i);
                            EditText et = (EditText) v.findViewById(R.id.qtytext31);
                            String qty = et.getText().toString();
                            String testqty = et.getText().toString();

                            DisbursementDetailItemS ddi = (DisbursementDetailItemS) dislist.get(i);
                            ddi.put("ItemQty",qty);
                            DisbursementDetailItemS.updateDisbursementDetailItem(ddi);

                        }
                        DisbursementSItem di = DisbursementSItem.getDisbursementById(did);
                        DisbursementSItem.updateDisbursementItemS(di);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(did);
            }
        });


    }

}
