package com.kencloud.partner.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Grievances extends AppCompatActivity {
    MaterialBetterSpinner materialSpinner ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievances);
        String[] spinnerArray = new String[]{
                "Application form related", "Payment Related" , "Product Related"

        };
        materialSpinner = (MaterialBetterSpinner)findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerPhoneAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray
        );
        spinnerPhoneAdapter.setDropDownViewResource(R.layout.spinner_item);
        materialSpinner.setAdapter(spinnerPhoneAdapter);
    }
}
