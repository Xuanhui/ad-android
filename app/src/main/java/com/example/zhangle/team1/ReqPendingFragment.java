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

import java.util.List;


public class ReqPendingFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public ReqPendingFragment() {
        // Required empty public constructor
    }

    public static ReqPendingFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        ReqPendingFragment fragment = new ReqPendingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);

        final View view = inflater.inflate(R.layout.fragment_req_pending, container, false);

        new AsyncTask<String, Void, List<RequisitionItem>>() {
            @Override
            protected List<RequisitionItem> doInBackground(String... params) {
                return RequisitionItem.listPending("");

            }
            @Override
            protected void onPostExecute(List<RequisitionItem> result) {
                ListView lv = (ListView) view.findViewById(R.id.listviewreqpend);
                lv.setAdapter(new SimpleAdapter(getContext(),result,
                        R.layout.layoutrequsitionitem,new String[]{"RequisitionID","Date","Status"},
                        new int[]{ R.id.reqid,R.id.reqfragdate,R.id.reqfragstatus }));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RequisitionItem s = (RequisitionItem) parent.getAdapter().getItem(position);
                        //Toast.makeText(getApplicationContext(), s.get("Id") + " selected",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),RequisitionDetailActivity.class);
                        intent.putExtra("rid", String.valueOf(s.get("RequisitionID")));
                        startActivity(intent);
                    }
                });
            }
        }.execute("");
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
