package com.example.zhangle.team1;

import android.content.Context;
import android.content.Intent;
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


public class DisbursementFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public DisbursementFragment() {
        // Required empty public constructor
    }

    //Fragment initialise
    public static DisbursementFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        DisbursementFragment fragment = new DisbursementFragment();
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
        final View view = inflater.inflate(R.layout.fragment_disbursement, container, false);
        //TextView textView = (TextView) view;
        //Data Logic
        //list all disbursement on the foreground
        new AsyncTask<String, Void, List<DisbursementItem>>() {
            @Override
            protected List<DisbursementItem> doInBackground(String... params) {
                return DisbursementItem.listAll("");
            }
            @Override
            protected void onPostExecute(List<DisbursementItem> result) {
                ListView lv = (ListView) view.findViewById(R.id.listviewDis);
                lv.setAdapter(new SimpleAdapter(getContext(),result,
                        R.layout.layoutdisbursementitem,new String[]{"DisbursementID","DeptRep","Status"},
                        new int[]{ R.id.pendingID, R.id.disrep, R.id.listdisStatus }));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DisbursementItem t = (DisbursementItem) parent.getAdapter().getItem(position);
                        //Toast.makeText(getApplicationContext(), s.get("Id") + " selected",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),DisbursementDetailActivity.class);
                        intent.putExtra("did", String.valueOf(t.get("DisbursementID")));
                        intent.putExtra("dpoint", String.valueOf(t.get("CollectionPoint")));
                        intent.putExtra("dstatus", String.valueOf(t.get("Status")));
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
