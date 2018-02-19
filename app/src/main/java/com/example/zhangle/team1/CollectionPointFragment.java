package com.example.zhangle.team1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import custfonts.TextView_Lato;


public class CollectionPointFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public CollectionPointFragment() {
        // Required empty public constructor
    }

    //Fragment initialise
    public static CollectionPointFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        CollectionPointFragment fragment = new CollectionPointFragment();
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
        final View view = inflater.inflate(R.layout.fragment_collection_point, container, false);
        String sc = String.valueOf(CurrentUser.current);

        //get collectionpoint information background and show it foreground
        new AsyncTask<String, Void, EmployeeItem>() {
            @Override
            protected EmployeeItem doInBackground(String... params) {
                return EmployeeItem.getCollectionById(params[0]);
            }
            @Override
            protected void onPostExecute(EmployeeItem result) {
                TextView_Lato tv1 = (TextView_Lato) view.findViewById(R.id.departmentidcode);
                tv1.setText(result.get("DepartmentName"));
                TextView_Lato tv2 = (TextView_Lato) view.findViewById(R.id.headname2);
                tv2.setText(result.get("HeadName"));
                TextView_Lato tv3 = (TextView_Lato) view.findViewById(R.id.collectionpointname);
                tv3.setText(result.get("CollectionPoint"));
            }
        }.execute(sc);
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
