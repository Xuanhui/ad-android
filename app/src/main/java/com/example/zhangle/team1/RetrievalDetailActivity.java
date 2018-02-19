package com.example.zhangle.team1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class RetrievalDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_detail);
        TextView t=findViewById(R.id.ge11);
     final  String item = getIntent().getExtras().getString("ID");
        t.setText(item);
            new AsyncTask<String, Void, List<RetrievalItemDetail>>(){
                @Override
                protected  List<RetrievalItemDetail> doInBackground(String... params) {
                    return  RetrievalItemDetail.getRetrievalItemlist(params[0]);
                }
                @Override
                protected void onPostExecute(List<RetrievalItemDetail> result) {

                    ListView lv = (ListView)findViewById(R.id.ge34);
                    lv.setAdapter(new SimpleAdapter(RetrievalDetailActivity.this,result,
                            R.layout.layout_retrievaldetail_item  ,new String[]{"Itemcode","Description","QuantityNeeded","ActualQty","Status"},
                            new int[]{ R.id.ge7, R.id.ge8, R.id.ge3, R.id.ge4, R.id.ge9 }));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            RetrievalItemDetail s = (RetrievalItemDetail) parent.getAdapter().getItem(position);
                            //Toast.makeText(getApplicationContext(), s.get("Id") + " selected",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RetrievalDetailActivity.this,RetrievalDetailItemActivity.class);
                            intent.putExtra("ID", String.valueOf(s.get("RetrievalDetailID")));
                            startActivity(intent);
                        }
                    });
                }
            }.execute(item);

        Button b = (Button) findViewById(R.id.ge33);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                         RetrievalItem.updateStatus(params[0]);
                         return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(item);
            }
        });
    }
}
