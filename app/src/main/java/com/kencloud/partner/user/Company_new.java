package com.kencloud.partner.user;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.kencloud.partner.user.Fragment_Company.FirstFragment;
import com.kencloud.partner.user.Fragment_Company.SecondFragment;
import com.kencloud.partner.user.Fragment_Company.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class Company_new extends AppCompatActivity {
    private static final String TAG = Company_new.class.getSimpleName();
    private Fragment fragment;
    private FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;


    //Fragments
FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    MenuItem prevMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_new);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.user:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.company:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.bank_details:
                                viewPager.setCurrentItem(2);
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
        firstFragment=new FirstFragment();
        secondFragment=new SecondFragment();
        thirdFragment=new ThirdFragment();
        adapter.insertNewFragment(firstFragment);
        adapter.insertNewFragment(secondFragment);
        adapter.insertNewFragment(thirdFragment);
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