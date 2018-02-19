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


/**
 */
public class CatalogueFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public CatalogueFragment() {
        // Required empty public constructor
    }


    //Fragment initialise
    public static CatalogueFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        CatalogueFragment fragment = new CatalogueFragment();
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
        final View view = inflater.inflate(R.layout.fragment_catalogue, container, false);

        //List all the catalogue item in background and show it foreground

        new AsyncTask<String, Void, List<CatalogueItem>>() {
            @Override
            protected List<CatalogueItem> doInBackground(String... params) {
                return CatalogueItem.listAll("");

            }
            @Override
            protected void onPostExecute(List<CatalogueItem> result) {
                ListView lv = (ListView) view.findViewById(R.id.listviewcatalogue);
                lv.setAdapter(new SimpleAdapter(getContext(),result,
                        R.layout.layoutcatalogueitem,new String[]{"Description","ItemCode","ShelfLocation"},
                        new int[]{ R.id.reqid,R.id.reqfragdate,R.id.reqfragstatus }));
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
