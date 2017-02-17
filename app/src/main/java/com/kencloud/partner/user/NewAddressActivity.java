package com.kencloud.partner.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kencloud.partner.user.network_util.JsonArrayRequest;
import com.kencloud.partner.user.network_util.VolleySingleton;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewAddressActivity extends AppCompatActivity {
private AppCompatEditText address1,address2, city, state, country, zip, landmark;
    Button geolocation;
    private RadioButton isPrimary;
    private MaterialBetterSpinner addressType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        address1 = (AppCompatEditText) findViewById(R.id.address1);
        address2 = (AppCompatEditText) findViewById(R.id.address2);
        city = (AppCompatEditText) findViewById(R.id.city);
        state = (AppCompatEditText) findViewById(R.id.state);
        country = (AppCompatEditText) findViewById(R.id.country);
        zip = (AppCompatEditText) findViewById(R.id.zip);
        landmark=(AppCompatEditText) findViewById(R.id.landmark);
        isPrimary= (RadioButton)findViewById(R.id.address_primary);
        geolocation=(Button) findViewById(R.id.geolocation);
        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewAddressActivity.this, MyMapActivity.class));
            }
        });
        AppCompatButton button_save=(AppCompatButton)findViewById(R.id.btn_save);

//        Spinner
        addressType=(MaterialBetterSpinner)findViewById(R.id.address_type) ;
        // Initializing a String Array
        String[] type = new String[]{

                "Mailing Address",
                "Permanent Address",
                "Registered Address",
                "Correspondence Address",
                "Branch Address",

        };


        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,type
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        addressType.setAdapter(spinnerArrayAdapter);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addresslDetails();

            }
        });


    }


    public void addresslDetails() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting your data.. Please wait..");
        progressDialog.show();

        final JSONObject params = new JSONObject();
        try {
            //postWillGo

//            params.put("AddressType", addressType.getText().toString().trim());
            params.put("User_RegistrationId", "3");
            params.put("Apl_AddressType", addressType.getText().toString().trim());
            params.put("Apl_Address_Street1", address1.getText().toString().trim());
            params.put("Apl_Address_Street2", address2.getText().toString().trim());
            params.put("Apl_LandMark", landmark.getText().toString().trim());
            params.put("Apl_Address_City", city.getText().toString().trim());
            params.put("Apl_Address_State", state.getText().toString().trim());
            params.put("Apl_Address_Country", country.getText().toString().trim());
            params.put("Apl_Address_ZIP", zip.getText().toString().trim());
            params.put("Apl_Address_IsPrimary", isPrimary.getText().toString().trim());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        String url="http://kenmasterapi.azurewebsites.net/api/CompanyAddressInformation";

        JsonArrayRequest request=new JsonArrayRequest(url,params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result======", response.toString());
                System.out.println("ResultOut" + params.toString());

                Toast.makeText(NewAddressActivity.this,"Saved Successfully",Toast.LENGTH_LONG)
                        .show();
                progressDialog.hide();
                finish();
//                Intent intent=new Intent(CreateEnquiryActivity.this,EnquiryActivity.class);
//                startActivity(intent);

                //dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();

                Toast.makeText(NewAddressActivity.this.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(NewAddressActivity.this.getApplicationContext()).addToRequestQueue(request);

    }

}

