package com.kencloud.partner.user;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kencloud.partner.user.Fragment_individual.BankDetails;
import com.kencloud.partner.user.Fragment_individual.Contact;
import com.kencloud.partner.user.Fragment_individual.Personal;
import com.kencloud.partner.user.Fragment_individual.Profile;
import com.kencloud.partner.user.app_adapter.Form2Adapter;
import com.kencloud.partner.user.app_model.Address_Info_Individual;

import java.util.ArrayList;
import java.util.List;

public class IndividualActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Dialog dialog;
    private  ImageView profile_Pic;
    Form2Adapter adapter;
    private RecyclerView recyclerView;
    private List<Address_Info_Individual> form2List;
    private TextView addnew;
    private Spinner spinner_email, spinner_phone;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
//    private FloatingActionButton next;
    private String imgString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);
//        next = (FloatingActionButton) findViewById(R.id.next);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(getItem(+1), true);
//            }
//
//        });
//        InputMethodManager inputManager = (InputMethodManager)
//                getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Bottom Bar implementation
        int[] icons = {R.drawable.user,
                R.drawable.contact,
                R.drawable.profile,
                R.drawable.bankdetails,
//                R.drawable.operator
        };
        String[] title = {
                "Personal Info", "Contact", "Profile","Bank Details" ,
//                "Operation deatils"

        };
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Application Form");
        viewPager = (ViewPager) findViewById( R.id.viewpager_I);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < icons.length; i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
            tabLayout.getTabAt(i).setText(title[i]);

        }
        tabLayout.getTabAt(0).select();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#0d70e0"),PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    private void setupViewPager(ViewPager viewPager) {
        IndividualActivity.ViewPagerAdapter adapter = new IndividualActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.insertNewFragment(new Personal());
        adapter.insertNewFragment(new Contact());
        adapter.insertNewFragment(new Profile());
        adapter.insertNewFragment(new BankDetails());
//        adapter.insertNewFragment(new Operation());
        viewPager.setAdapter(adapter);
    }
//    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//        @Override
//        public void onPageSelected(int position) {
//
//            switch (position) {
//                cases 1:
//                    next.hide();
//                    break;
//                cases 2:
//                    next.hide();
//                    break;
//                cases 3:
//                    next.hide();
//                    break;
//                default:
//                    next.show();
//                    break;
//            }
//        }
//


//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//
//        }
//    };

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        //        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    }

