package com.kencloud.partner.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class RegistrationActivity extends AppCompatActivity {
Button btnSave;
    Spinner spStates, spCities;
    String sstaes,scities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSave=(Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });

//        spStates = (Spinner) findViewById(R.id.spStates);
//        spCities = (Spinner) findViewById(R.id.spCitys);
//        spStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapter, View v,
//                                       int position, long id) {
//                // On selecting a spinner item
//                sstaes = adapter.getItemAtPosition(position).toString();
//                // Showing selected spinner item
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//        spCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapter, View v,
//                                       int position, long id) {
//                // On selecting a spinner item
//                scities = adapter.getItemAtPosition(position).toString();
//                // Showing selected spinner item
////                Toast.makeText(getApplicationContext(),
////                        "Selected Country : " + scountry, Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//            }
//        });
    }
}
