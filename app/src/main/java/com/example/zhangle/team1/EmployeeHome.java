package com.example.zhangle.team1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class EmployeeHome extends FragmentActivity{
//botton toolbar
    private TabLayout mTabLayout;
    private int[] mTabsIcons = {
            R.drawable.pendreq,
            R.drawable.compdreq,
            R.drawable.catalogue,
            R.drawable.profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        //
        String cuser = getIntent().getExtras().getString("cuser");
        TextView twlcom = (TextView) findViewById(R.id.welcom);
        twlcom.setText(cuser);

        FloatingActionButton b = findViewById(R.id.fab);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(EmployeeHome.this, MainActivity.class);
                EmployeeHome.this.startActivity(activityChangeIntent);
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
        //add button logic
        //FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        //btn.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
        //        CustomerItem c = new CustomerItem("555","233","111","4");
                //CustomerItem.addCustomer(c);
        //        CustomerItem.updateCustomer(c);
        //    }
        //});
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 4;

        private final String[] mTabsTitle = {"Home", "Add User", "Notification","More"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(EmployeeHome.this).inflate(R.layout.custom_tab, null);
//            TextView title = (TextView) view.findViewById(R.id.title);
//            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    ReqPendingFragment rd = new ReqPendingFragment();
                    return rd.newInstance(1);
                case 1:
                    RequisitionFragment rf = new RequisitionFragment();
                    return rf.newInstance(2);
                case 2:
                    CatalogueFragment cf = new CatalogueFragment();
                    return cf.newInstance(3);
                case 3:
                    ProfileFragment pf = new ProfileFragment();
                    return pf.newInstance(4);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }
}

