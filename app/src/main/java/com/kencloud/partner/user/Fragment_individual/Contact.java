package com.kencloud.partner.user.Fragment_individual;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.kencloud.partner.user.NewAddressActivity;
import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_adapter.EmailAdapter;
import com.kencloud.partner.user.app_adapter.Form2Adapter;
import com.kencloud.partner.user.app_adapter.PhoneAdapter;
import com.kencloud.partner.user.app_model.Address_Info_Individual;
import com.kencloud.partner.user.app_model.Phone;
import com.kencloud.partner.user.network_util.VolleySingleton;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Contact extends Fragment {
    Context context;
    ProgressDialog pDialog;
    Form2Adapter adapter;
    PhoneAdapter phoneAdapter;
    EmailAdapter emailAdapter;
    private List<Address_Info_Individual> form2List;
    private List<Phone> phoneList;
    private  RecyclerView recyclerView,phoneRecycle;
    private View rootView;
    private TextView addnewAddress,  addnewPhone, viewMore;
    private AppCompatEditText  phone;
    MaterialBetterSpinner  materialPhoneSpinner ;
    private Calendar myCalendar = Calendar.getInstance();
public  Button save_p, cancel_p;
ViewPager viewPager;
    private AppCompatButton btnNext, btnSave;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_contact, container, false);
viewPager=(ViewPager)getActivity().findViewById(R.id.viewpager);
        form2List = new ArrayList<>();
        adapter = new Form2Adapter(getContext(), form2List, recyclerView);
        // Initializing a String Array
//       final String[] emailArray = new String[]{
//                "Personal Id", "Official Id"
//
//        };
final String[] phoneArray= new String[]{
        "Mobile" , "Landline", "Office", "Others"

};
        btnSave=(AppCompatButton)rootView.findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG ).show();
            }
        });
        btnNext=(AppCompatButton)rootView.findViewById(R.id.next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(2);

            }
        });



        addnewAddress = (TextView) rootView.findViewById(R.id.add_new_address);
        addnewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), NewAddressActivity.class));
            }
        });

        addnewPhone=(TextView) rootView.findViewById(R.id.add_phone);
        addnewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    dialog = new Dialog(getActivity());
                }
                dialog.setTitle("Add Phone Number");
                dialog.setContentView(R.layout.dialog_phone);
                phone = (AppCompatEditText) dialog.findViewById(R.id.phone);
                materialPhoneSpinner=(MaterialBetterSpinner) dialog.findViewById(R.id.phone_type);
                ArrayAdapter<String> spinnerPhoneAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, phoneArray
                );
                spinnerPhoneAdapter.setDropDownViewResource(R.layout.spinner_item);
                materialPhoneSpinner.setAdapter(spinnerPhoneAdapter);
                save_p=(Button)dialog.findViewById(R.id.save);
                save_p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String no=phone.getText().toString();
                        String type=materialPhoneSpinner.getText().toString();
                        Phone phone=new Phone(no, type);
                        phoneList.add(phone);
                        dialog.dismiss();
                        phoneAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();

                    }
                });

                cancel_p=(Button)dialog.findViewById(R.id.cancel);
                cancel_p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });

        viewMore = (TextView) rootView.findViewById(R.id.viewMore);
        viewMore.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Contact.loadMoreListView1().execute();



            }
        });


//       Phone RecyclerView

       phoneRecycle =(RecyclerView) rootView.findViewById(R.id.phone_list);
        phoneRecycle.setHasFixedSize(true);
        phoneList = new ArrayList<>();
        phoneAdapter = new PhoneAdapter(getContext(), phoneList);
        LinearLayoutManager phoneLayout = new LinearLayoutManager(getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CompanyActivity.this);
        phoneRecycle.setLayoutManager(phoneLayout);
        phoneLayout.setOrientation(LinearLayoutManager.VERTICAL);
        phoneLayout.scrollToPosition(0);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        phoneRecycle.setItemAnimator(new DefaultItemAnimator());
        phoneRecycle.setAdapter(phoneAdapter);



        addressDetails();
        return rootView;
    }




    private void addressDetails() {


        String url = "http://kenmasterapi.azurewebsites.net/api/CompanyAddressInformation";
//        String url = "http://kencloudcustomer-staging.azurewebsites.net/CheckSubscription.svc/rest/GetAllCity";
        com.android.volley.toolbox.JsonArrayRequest req = new com.android.volley.toolbox.JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Result", response.toString());


                        for (int i = 0; i < 2; i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Address_Info_Individual address = new Address_Info_Individual();
                                //enquiryModel.setName(obj.getString("EnquiryId"));

                                address.setAddressType(obj.getString("Apl_AddressType"));
                                address.setAddress1(obj.getString("Apl_Address_Street1"));
                                address.setAddress2(obj.getString("Apl_Address_Street2"));
                                address.setLandmark(obj.getString("Apl_LandMark"));
                                address.setCity(obj.getString("Apl_Address_City"));
                                address.setState(obj.getString("Apl_Address_State"));
                                address.setCountry(obj.getString("Apl_Address_Country"));
                                address.setPin(obj.getString("Apl_Address_ZIP"));

                                form2List.add(address);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mLayoutManager.scrollToPosition(0);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
//                        layoutSwipe.setRefreshing(false);
                        //getRepresentatives
                        // getRepresentatives(getIntent().getStringExtra("cat_id"), "");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Result Error", "Error: " + error.getMessage());

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(req);


    }

    private class loadMoreListView1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
            pDialog = new ProgressDialog(
                    getContext());
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(Void... unused) {
            getActivity().runOnUiThread(new Runnable() {


                public void run() {
                    // increment current page
                    // Next page request

//                    addressList.remove(addressList.size() - 2);
//                    adapter.notifyItemRemoved(addressList.size());
                    String url = "http://kenmasterapi.azurewebsites.net/api/CompanyAddressInformation";
//        String url = "http://kencloudcustomer-staging.azurewebsites.net/CheckSubscription.svc/rest/GetAllCity";
                    com.android.volley.toolbox.JsonArrayRequest req = new com.android.volley.toolbox.JsonArrayRequest(url,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("Result", response.toString());


                                    int index = form2List.size();
                                    int end = index + 2;
                                    for (int i = index; i < end; i++) {
                                        try {

                                            JSONObject obj = response.getJSONObject(i);
                                            Address_Info_Individual address = new Address_Info_Individual();
                                            //enquiryModel.setName(obj.getString("EnquiryId"));

                                            address.setAddressType(obj.getString("Apl_AddressType"));
                                            address.setAddress1(obj.getString("Apl_Address_Street1"));
                                            address.setAddress2(obj.getString("Apl_Address_Street2"));
                                            address.setLandmark(obj.getString("Apl_LandMark"));
                                            address.setCity(obj.getString("Apl_Address_City"));
                                            address.setState(obj.getString("Apl_Address_State"));
                                            address.setCountry(obj.getString("Apl_Address_Country"));
                                            address.setPin(obj.getString("Apl_Address_ZIP"));

                                            form2List.add(address);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                                    recyclerView.setHasFixedSize(true);
                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    mLayoutManager.scrollToPosition(0);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(adapter);
//                        layoutSwipe.setRefreshing(false);
                                    //getRepresentatives
                                    // getRepresentatives(getIntent().getStringExtra("cat_id"), "");
                                    pDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Result Error", "Error: " + error.getMessage());

                        }
                    });


                    req.setRetryPolicy(new DefaultRetryPolicy(
                            90000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(req);

                }
            });

            return (null);
        }

    }
    }



