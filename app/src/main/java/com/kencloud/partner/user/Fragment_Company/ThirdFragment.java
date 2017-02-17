package com.kencloud.partner.user.Fragment_Company;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kencloud.partner.user.R;

public class ThirdFragment extends Fragment {
private View rootView;
    ViewPager viewPager;
   private AppCompatButton payNow;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_third, container, false);

        payNow=(AppCompatButton)rootView.findViewById(R.id.paynow);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG ).show();
            }
        });


        return rootView;
    }

}
