package com.example.zhangle.team1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

import custfonts.TextView_Lato;


public class ProfileFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        ProfileFragment fragment = new ProfileFragment();
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
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        String sc = String.valueOf(CurrentUser.current);
        new AsyncTask<String, Void, EmployeeItem>() {
            @Override
            protected EmployeeItem doInBackground(String... params) {
                return EmployeeItem.getId(params[0]);
            }
            @Override
            protected void onPostExecute(EmployeeItem result) {
                TextView_Lato tv1 = (TextView_Lato) view.findViewById(R.id.username1);
                tv1.setText(result.get("EmployeeName"));
                TextView_Lato tv2 = (TextView_Lato) view.findViewById(R.id.department);
                tv2.setText(result.get("DepartmentID"));
                TextView_Lato tv3 = (TextView_Lato) view.findViewById(R.id.emailaddress);
                tv3.setText(result.get("EmailAdd"));
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
