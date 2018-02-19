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


/**
 * A simple {@link Fragment} subclass.
 */
public class RepPedingRequisitionFragment extends Fragment {


    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public RepPedingRequisitionFragment() {
        // Required empty public constructor
    }

    public static PendingFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        PendingFragment fragment = new PendingFragment();
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
        final View view = inflater.inflate(R.layout.fragment_pending, container, false);
        //TextView textView = (TextView) view;
        //Data Logic
        new AsyncTask<String, Void, List<RequisitionItem>>() {
            @Override
            protected List<RequisitionItem> doInBackground(String... params) {
                return RequisitionItem.listPending("");
            }
            @Override
            protected void onPostExecute(List<RequisitionItem> result) {
                ListView lv = (ListView) view.findViewById(R.id.listviewpend);
                lv.setAdapter(new SimpleAdapter(getContext(),result,
                        R.layout.layoutpendingitem,new String[]{"RequisitionID","Date","EmployeeID","Status"},
                        new int[]{ R.id.pendingID, R.id.pendingEmp, R.id.pendpoint, R.id.pending }));
                lv.setOnItemClickListener(  new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RequisitionItem s = (RequisitionItem) parent.getAdapter().getItem(position);
                        //Toast.makeText(getApplicationContext(), s.get("Id") + " selected",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),RepRequisitionPendingActivity.class);
                        intent.putExtra("pid", String.valueOf(s.get("RequisitionID")));
                        intent.putExtra("pdate", String.valueOf(s.get("Date")));
                        intent.putExtra("pstatus", String.valueOf(s.get("Status")));
                        intent.putExtra("pemp", String.valueOf(s.get("EmployeeID")));
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
