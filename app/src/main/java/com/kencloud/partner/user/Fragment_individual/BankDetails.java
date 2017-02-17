package com.kencloud.partner.user.Fragment_individual;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kencloud.partner.user.R;


public class BankDetails extends Fragment {
View rootview;
   private Button paynow;
    LinearLayout bankLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_bankdetails, container, false);
paynow=(Button)rootview.findViewById(R.id.paynow);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
            }
        });



//        RadioGroup if_partner = (RadioGroup) rootview.findViewById(R.id.if_partner);
////        RadioButton radioYes= (RadioButton) rootview.findViewById(R.id.radio_yes);
//        bankLayout =(LinearLayout)  rootview.findViewById(R.id.bank_layout);
//        if_partner.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId==R.id.radio_yes)
//                {
//                    bankLayout.setVisibility(View.VISIBLE);
//                }
//
//                else if(checkedId==R.id.radio_yes)
//                {
//                    bankLayout.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

        return rootview;
    }


}
