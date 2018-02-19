package com.example.zhangle.team1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import custfonts.MyEditText;

public class RetrievalDetailItemActivity extends AppCompatActivity {
    final static int []view = {R.id.textView2, R.id.textView6, R.id.textView10, R.id.textView8, R.id.textView13, R.id.currentqty};
    final static String []key = {"RetrievalDetailID","Itemcode","Description","QuantityNeeded","Status", "CurrentQuantity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_detail_item);

        final  String item = getIntent().getExtras().getString("ID");

        new AsyncTask<String, Void, RetrievalItemDetail>(){
            @Override
            protected RetrievalItemDetail doInBackground(String... params) {
                return  RetrievalItemDetail.getCertainRetrievalItem(params[0]);
            }
            @Override
            protected void onPostExecute(RetrievalItemDetail result) {
             for(int i=0;i<view.length;i++){
                TextView t=(TextView)findViewById(view[i]);
                String s = result.get(key[i]);
                t.setText(result.get(key[i]).toString());
             }
                MyEditText t=(MyEditText)findViewById(R.id.myEditText);
                t.setText(result.get("ActualQty"));
            }
        }.execute(item);
        Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {RetrievalItemDetail ret=new RetrievalItemDetail();
            for(int i=0;i<view.length;i++){
                TextView t=(TextView)findViewById(view[i]);
                ret.put(key[i],t.getText().toString());}
                MyEditText t1=(MyEditText)findViewById(R.id.myEditText);
                ret.put("ActualQty",t1.getText().toString());
                ret.put("ClerkID",String.valueOf(CurrentUser.current));
                new AsyncTask<RetrievalItemDetail, Void, Void>() {
                    @Override
                    protected Void doInBackground(RetrievalItemDetail... params) {
                        RetrievalItemDetail.updateRetrievalItemDetail(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(ret);


            }
        });

    }
}
