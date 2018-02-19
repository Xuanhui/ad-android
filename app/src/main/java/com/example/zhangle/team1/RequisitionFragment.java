package com.example.zhangle.team1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;


public class RequisitionFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public RequisitionFragment() {
        // Required empty public constructor
    }

    public static RequisitionFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        RequisitionFragment fragment = new RequisitionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_requisition, container, false);
        //TextView textView = (TextView) view;
        //Data Logic
        new AsyncTask<String, Void, List<RequisitionItem>>() {
            @Override
            protected List<RequisitionItem> doInBackground(String... params) {
                return RequisitionItem.listAll("");

            }
            @Override
            protected void onPostExecute(List<RequisitionItem> result) {
                ListView lv = (ListView) view.findViewById(R.id.listview1);
                lv.setAdapter(new SimpleAdapter(getContext(),result,
                        R.layout.layoutrequsitionitem,new String[]{"RequisitionID","Date","Status"},
                        new int[]{ R.id.reqid,R.id.reqfragdate,R.id.reqfragstatus }));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RequisitionItem s = (RequisitionItem) parent.getAdapter().getItem(position);
                        //Toast.makeText(getApplicationContext(), s.get("Id") + " selected",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),RequisitionDetailActivity2.class);
                        intent.putExtra("rid", String.valueOf(s.get("RequisitionID")));
                        startActivity(intent);
                    }
                });
            }
        }.execute("");

//        textView.setText("Fragment #" + mPageNo);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
