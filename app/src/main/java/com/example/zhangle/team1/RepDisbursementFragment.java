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
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepDisbursementFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public RepDisbursementFragment() {

       }

    public static RepDisbursementFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        RepDisbursementFragment fragment = new RepDisbursementFragment();
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


        //Data Logic
        new AsyncTask<String, Void, List<DisbursementSItem>>() {
            @Override
            protected List<DisbursementSItem> doInBackground(String... params) {
                return DisbursementSItem.listPendingList("");

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
                        Intent intent = new Intent(getActivity(),RepDisbursementActivity.class);
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












//    public RepDisbursementFragment() {
//        // Required empty public constructor
//    }
//
//    public static final String ARG_PAGE = "ARG_PAGE";
//    private int mPageNo;
//
//
//    public static RepDisbursementFragment newInstance(int pageNo) {
//
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, pageNo);
//        RepDisbursementFragment fragment = new RepDisbursementFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mPageNo = getArguments().getInt(ARG_PAGE);
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.activity_rep_disbursement, container, false);
//
//        Intent i = this.getActivity().getIntent();
//        final String did = i.getStringExtra("DisID");
//        int sum;
//        final TextView text6 = view.findViewById(R.id.totalqtytext);
//        new AsyncTask<String, Void, DisbursementSItem>() {
//            @Override
//            protected DisbursementSItem doInBackground(String... params) {
//                DisbursementSItem s = DisbursementSItem.getDisbursementById(params[0]);
//                return s;
//            }
//
//            //@Override
//            protected void onPostExecute(DisbursementSItem result) {
//                //print result data
//                TextView text1 = view.findViewById(R.id.disbursementidtext41);
//                text1.setText(result.get("DisbursementID").toString());
//                TextView text2 = view.findViewById(R.id.disbursementstatus41);
//                text2.setText(result.get("Status"));
//                TextView text3 = view.findViewById(R.id.disbursementcollecttext41);
//                text3.setText(result.get("CollectionPoint"));
//                TextView text4 = view.findViewById(R.id.disbursementreptext41);
//                if (result.get("Deprep").toString().equals("0")) {
//                    text4.setText("");
//                } else {
//                    text4.setText(result.get("Deprep").toString());
//                }
//                TextView text5 = view.findViewById(R.id.disbursementdepartmenttext41);
//                text5.setText(result.get("DepartmentName"));
//            }
//        }.execute(did);
//
//
//        new AsyncTask<String, Void, List<DisbursementDetailItemS>>() {
//            @Override
//            protected List<DisbursementDetailItemS> doInBackground(String... params) {
//
//                return DisbursementDetailItemS.getDisbursementDetailListByDisId(params[0]);
//            }
//
//            @Override
//            protected void onPostExecute(List<DisbursementDetailItemS> result) {
//                //print result data
//
//                ListView ddlistview = view.findViewById(R.id.disbursementlistview41);
//                ddlistview.setAdapter(new SimpleAdapter(getActivity(), result,
//                        R.layout.layout_disbursementdetail_item,
//                        new String[]{"Description", "ItemQty"},
//                        new int[]{R.id.itemdesctext, R.id.qtytext}));
//
//                int sum = 0;
//                for (DisbursementDetailItemS dditem : result) {
//                    //loop
//                    {
//                        sum = sum + Integer.parseInt(dditem.get("ItemQty"));
//                    }
//
//                }
//                //displaysum(sum);
//                text6.setText(String.valueOf(sum));
//            }
//        }.execute(did);
//        return view;
//    }
//
//    public void displaysum(int sum) {
//
//        //DisbursementDetailItemS testo = new DisbursementDetailItemS();
//        //text6.setText(String.valueOf(sum));
//        // List<DisbursementDetailItemS> ltext = testo.ldist;
//    }
}
