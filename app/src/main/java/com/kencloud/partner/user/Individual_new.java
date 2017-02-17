package com.kencloud.partner.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.kencloud.partner.user.Fragment_Company.FirstFragment;
import com.kencloud.partner.user.Fragment_Company.SecondFragment;
import com.kencloud.partner.user.Fragment_Company.ThirdFragment;
import com.kencloud.partner.user.Fragment_individual.BankDetails;
import com.kencloud.partner.user.Fragment_individual.Contact;
import com.kencloud.partner.user.Fragment_individual.Personal;
import com.kencloud.partner.user.Fragment_individual.Profile;

import java.util.ArrayList;
import java.util.List;

public class Individual_new extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;


    //Fragments
    Personal firstFragment;
    Contact secondFragment;
    Profile thirdFragment;
    BankDetails forthFragment;
    MenuItem prevMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_new);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.personal_info:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.contact:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.profile:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.bank_details:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /*  //Disable ViewPager Swipe

       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        */

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        firstFragment=new Personal();
        secondFragment=new Contact();
        thirdFragment=new Profile();
        forthFragment=new BankDetails();
        adapter.insertNewFragment(firstFragment);
        adapter.insertNewFragment(secondFragment);
        adapter.insertNewFragment(thirdFragment);
        adapter.insertNewFragment(forthFragment);
        viewPager.setAdapter(adapter);
    }

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

}