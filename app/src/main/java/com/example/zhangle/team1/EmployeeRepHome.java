package com.example.zhangle.team1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class EmployeeRepHome extends FragmentActivity  {
//bottom toolbar
    private TabLayout mTabLayout;
    private int[] mTabsIcons = {
            R.drawable.disbursement,
            R.drawable.comppdisb,
            R.drawable.bell,
            R.drawable.profile

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        FloatingActionButton b = findViewById(R.id.fab);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(EmployeeRepHome.this, MainActivity.class);
                EmployeeRepHome.this.startActivity(activityChangeIntent);
            }
        });

        String cuser = getIntent().getExtras().getString("cuser");
        TextView twlcom = (TextView) findViewById(R.id.welcom);
        twlcom.setText(cuser);

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
            View view = LayoutInflater.from(EmployeeRepHome.this).inflate(R.layout.custom_tab, null);
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
                    RepDisbursementFragment rdf = new RepDisbursementFragment();
                    return rdf.newInstance(1);
                case 1:
                    DisbursementConfirmedS dc = new DisbursementConfirmedS();
                    return dc.newInstance(2);
                case 2:
                    CollectionPointFragment cf = new CollectionPointFragment();
                    return cf.newInstance(3);
                case 3:
                    ProfileFragment prof = new ProfileFragment();
                    return prof.newInstance(4);
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

