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


public class DisbursementRFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public DisbursementRFragment() {
        // Required empty public constructor
    }

    //Fragment initialise
    public static DisbursementRFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        DisbursementRFragment fragment = new DisbursementRFragment();
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
        final View view = inflater.inflate(R.layout.fragment_disbursement_confirmed, container, false);
        //TextView textView = (TextView) view;



        //Data Logic
        //list pending disbursement list on foreground
        //if click any item of the list will enter into detail page
        new AsyncTask<String, Void, List<DisbursementSItem>>() {
            @Override
            protected List<DisbursementSItem> doInBackground(String... params) {
                return DisbursementSItem.listPendingList("");

            }
            @Override
            protected void onPostExecute(final List<DisbursementSItem> result) {
                ListView lv = (ListView) view.findViewById(R.id.disbursementlistview21);
                lv.setAdapter(new SimpleAdapter(getContext(),result,
                        //"DepartmentCode","CollectionPoint"
                        R.layout.layoutdisbursementitem2s,new String[]{"DisbursementID","DepartmentName","CollectionPoint"},
                        new int[]{ R.id.disbursementidtext21, R.id.departmenttext21, R.id.collectionpointtext21}));
//                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DisbursementSItem s = (DisbursementSItem) parent.getAdapter().getItem(position);
                        //Toast.makeText(getApplicationContext(), s.get("Id") + " selected",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),DisbursementDetailS.class);
//                                                  String s1 = result.get(0).get("RetrivalID").toString();
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
