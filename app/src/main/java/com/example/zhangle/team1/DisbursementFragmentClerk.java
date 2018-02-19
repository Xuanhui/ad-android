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


public class DisbursementFragmentClerk extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public DisbursementFragmentClerk() {
        // Required empty public constructor
    }

    //Fragment initialise
    public static DisbursementFragmentClerk newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        DisbursementFragmentClerk fragment = new DisbursementFragmentClerk();
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
        final View view = inflater.inflate(R.layout.fragment_disbursement_s, container, false);
        final View view2 = inflater.inflate(R.layout.activity_employee_home, container,false);
        //TextView textView = (TextView) view;



        //Data Logic
        //show all the pending disbursement list
        //if click any item of disbursement list , will enter into the disbursement detail page
        new AsyncTask<String, Void, List<DisbursementSItem>>() {
            @Override
            protected List<DisbursementSItem> doInBackground(String... params) {
                return DisbursementSItem.listPendingListClerk("");

            }
            @Override
            protected void onPostExecute(final List<DisbursementSItem> result) {
                ListView lv = (ListView) view.findViewById(R.id.disbursementlistview);
                lv.setAdapter(new SimpleAdapter(getContext(),result,
                        //"DepartmentCode","CollectionPoint"
                        R.layout.layoutdisbursementitems,new String[]{"DisbursementID","DepartmentName","CollectionPoint"},
                        new int[]{ R.id.disbursementidtext, R.id.departmenttext, R.id.collectionpointtext}));
//                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DisbursementSItem s = (DisbursementSItem) parent.getAdapter().getItem(position);
                        //Toast.makeText(getApplicationContext(), s.get("Id") + " selected",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),DisbursementEditActS.class);
//                                                String s1 = result.get(0).get("RetrivalID").toString();
                        String testm = s.get("DisbursementID");
                        intent.putExtra("DisID", String.valueOf(s.get("DisbursementID")));
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
