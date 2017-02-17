package com.kencloud.partner.user;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;

public class FormMainActivity extends AppCompatActivity  {
    private SliderLayout mDemoSlider;
    private Button btnSubmit;
    private TextView individual, company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_main);
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Regular.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        toolbarTitle.setTypeface(font);
        individual=(TextView)findViewById(R.id.individual);
        company=(TextView)findViewById(R.id.company);
//        btnSubmit = (Button) findViewById (R.id.btnSubmit);
        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormMainActivity.this,IndividualActivity.class));
               }
        });
        company.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(FormMainActivity.this,CompanyActivity.class));
            }
        });

//        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioCatagory);
//
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                // find which radio button is selects
//                if (checkedId == R.id.radioIndividual) {
//
//                } else {
//
//                }
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//
//                    public void onClick(View v) {
//
//                        int selectedId = radioGroup.getCheckedRadioButtonId();
//
//
//                        // find which radioButton is checked by id
//
//                        if (selectedId == individual.getId()) {
//                            startActivity(new Intent(FormMainActivity.this, IndividualActivity.class));
//                        } else if (selectedId == company.getId()) {
//                            startActivity(new Intent(FormMainActivity.this, CompanyActivity.class));
//                        }
//
//
//                    }
//                });
//
//            }
//
//        });
  }
//
//
//


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == R.id.save) {
//
//            //do something
//            return true;
//        }
//        if (id == R.id.cancel) {
//
//            //do something
//            return true;
//        }
//       return true;
//    }

}

