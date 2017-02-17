package com.kencloud.partner.user.Fragment_Company;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.kencloud.partner.user.NewAddress_Comp;
import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_adapter.Form1Adapter;
import com.kencloud.partner.user.app_model.Address_Info_Comp;
import com.kencloud.partner.user.app_util.ValidationUtil;
import com.kencloud.partner.user.network_util.JsonArrayRequest;
import com.kencloud.partner.user.network_util.VolleySingleton;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SecondFragment extends Fragment {
    Form1Adapter adapter;
    private List<Address_Info_Comp> addressList;
    MaterialBetterSpinner companyType, contactDesignation;
    private RecyclerView recyclerView;
    private View rootView;
    private TextView addnew, viewMore;
    private AppCompatButton btnSave, btnNext;
    private AppCompatEditText compName, incpdate, emailid, phone,mobile, PAN_no, board_directores, website, employees_no, annual_turnover, TIN_no, tax_no, CIN_no, TAN_no,
    contactName, contactMobile, contactEmail;
    private Calendar myCalendar = Calendar.getInstance();
    ViewPager viewPager;
    Context context;
    private SwipeRefreshLayout layoutSwipe;
    ProgressDialog pDialog;
    int current_page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_second, container, false);
        addressList = new ArrayList<>();
        adapter = new Form1Adapter(getContext(), addressList, recyclerView);
        addressDetails();
        //        Spinner
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
//
//        ViewSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.ViewSwipe);
//        ViewSwipe.setColorSchemeResources(R.color.colorPrimaryDark,
//                R.color.colorPrimary,
//                R.color.colorAccent,
//                R.color.bg_button_login,
//                R.color.colorAccent);
//
//        layoutSwipe.setColorSchemeResources(R.color.colorPrimaryDark,
//                R.color.colorPrimary,
//                R.color.colorAccent,
//                R.color.bg_button_login,
//                R.color.colorAccent);
//        ViewSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                clearData();
//                //getRepresentatives
//
//
//            }
//        });
//        layoutSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.layoutSwipe);
//        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                addressDetails();
//            }
//        });

        companyType = (MaterialBetterSpinner) rootView.findViewById(R.id.company_type);

        // Initializing a String Array
        String[] company_type = new String[]{
                "Private Limited", "Public Limited", "Proprietorship", "Parternership"

        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, company_type
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        companyType.setAdapter(spinnerArrayAdapter);

contactDesignation= (MaterialBetterSpinner) rootView.findViewById(R.id.contact_designation);
        String[] contact_desg = new String[]{
                "Administrative", "Finance", "Accountant"

        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, contact_desg
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        contactDesignation.setAdapter(spinnerArrayAdapter);


        btnSave = (AppCompatButton) rootView.findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (compName.getText().toString().equals(""))
//                    Snackbar.make(rootView,"Please enter Company name", Snackbar.LENGTH_LONG).show();
                    compName.setError("Please Enter valid name");
                else if (!ValidationUtil.isValidEmail(emailid.getText().toString()))
                    emailid.setError("Please enter valid email Id");
                else if (!ValidationUtil.isValidMobile(phone.getText().toString()))
                    phone.setError("Please enter valid Mobile No");
                else if (incpdate.getText().toString().equals(""))
                    incpdate.setError("Please enter incorporated date");
                else if (website.getText().toString().equals(""))
                    website.setError("Please enter website");
//                else if (!ValidationUtil.isValidPanCard(PAN_no.getText().toString()))
//                    PAN_no.setError("Please enter valid PAN no");
                else
                    companyDetails();
                    Toast.makeText(getActivity().getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();

            }
        });
        btnNext = (AppCompatButton) rootView.findViewById(R.id.next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(2);

            }
        });
        compName = (AppCompatEditText) rootView.findViewById(R.id.company_name);
        mobile= (AppCompatEditText) rootView.findViewById(R.id.mobile);
        board_directores=(AppCompatEditText) rootView.findViewById(R.id.board_directors);
        employees_no=(AppCompatEditText) rootView.findViewById(R.id.employees_no);
        annual_turnover=(AppCompatEditText) rootView.findViewById(R.id.annual_turnover);
        TIN_no=(AppCompatEditText) rootView.findViewById(R.id.TIN);
        tax_no=(AppCompatEditText) rootView.findViewById(R.id.TAX);
        CIN_no=(AppCompatEditText) rootView.findViewById(R.id.CIN);
        TAN_no=(AppCompatEditText) rootView.findViewById(R.id.TAN);
        contactName=(AppCompatEditText) rootView.findViewById(R.id.contact_name);
        contactEmail=(AppCompatEditText) rootView.findViewById(R.id.contact_email);
        contactMobile=(AppCompatEditText) rootView.findViewById(R.id.contact_mobile);
        addnew = (TextView) rootView.findViewById(R.id.add_new_address);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewAddress_Comp.class));
            }
        });

        emailid = (AppCompatEditText) rootView.findViewById(R.id.email);
        ValidationUtil.removeWhiteSpaceFromFront(emailid);
        emailid.setFilters(new InputFilter[]{ValidationUtil.filter});
        emailid.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        emailid.setLongClickable(false);

        phone = (AppCompatEditText) rootView.findViewById(R.id.phone);
        ValidationUtil.removeWhiteSpaceFromFront(phone);
        phone.setFilters(new InputFilter[]{ValidationUtil.filter});
        phone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        phone.setLongClickable(false);



        //click listener to open calender

        incpdate = (AppCompatEditText) rootView.findViewById(R.id.incpdate);
        incpdate.setKeyListener(null);
        incpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        website = (AppCompatEditText) rootView.findViewById(R.id.website);
        PAN_no = (AppCompatEditText) rootView.findViewById(R.id.PAN);

//View More

        viewMore = (TextView) rootView.findViewById(R.id.viewMore);
        viewMore.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new loadMoreListView().execute();


//        adapter.setOnLoadMoreListener(new Form1Adapter.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
////                Log.e("haint", "Load More");
////                addressList.add(null);
////                adapter.notifyItemInserted(addressList.size() - 1);
//
//                //Load more data for reyclerview
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("haint", "Load More 2");
//
//                        //Remove loading item
////                        addressList.remove(addressList.size() - 1);
////                        adapter.notifyItemRemoved(addressList.size());
//
//                        //Load data
//                        int index = addressList.size();
//                        int end = index + 4;
//                        for (int i = index; i < end; i++) {
//                            addressDetails();
////                            Address_Info_Comp user = new Address_Info_Comp();
////                            user.setName("Name " + i);
////                            user.setEmail("alibaba" + i + "@gmail.com");
////                            addressList.add(user);
//                        }
//                        adapter.notifyDataSetChanged();
//                        adapter.setLoaded();
//                    }
//                }, 5000);
//            }



                //
//        });
            }
        });
        return rootView;
    }

    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    private void updateLabel1() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        incpdate.setText(sdf.format(myCalendar.getTime()));
    }





    public void companyDetails() {

//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Saving your data.. Please wait..");
//        progressDialog.show();

        final JSONObject params = new JSONObject();
        try {
            //postWillGo
            params.put("User_RegistrationId","3");
            params.put("Company_Name", compName.getText().toString().trim());
            params.put("Company_Type", companyType.getText().toString().trim());
            params.put("Company_Phone_number", phone.getText().toString().trim());
            params.put("Company_Mobile_Number", mobile.getText().toString().trim());
            params.put("Company_Email_Id", emailid.getText().toString().trim());
            params.put("Company_IncorporatedOn", incpdate.getText().toString().trim());
            params.put("Company_NoOfBoardDirectors", board_directores.getText().toString().trim());
            params.put("Company_Website", website.getText().toString().trim());
            params.put("Company_NoOfEmployee", employees_no.getText().toString().trim());
            params.put("Company_PAN_Number", PAN_no.getText().toString().trim());
            params.put("Company_AnnualTurnOver", annual_turnover.getText().toString().trim());
            params.put("Company_TIN_Number", TIN_no.getText().toString().trim());
            params.put("Company_Service_Tax_Number", tax_no.getText().toString().trim());
            params.put("Company_CIN_Number", CIN_no.getText().toString().trim());
            params.put("Company_TAN_Number", TAN_no.getText().toString().trim());
            params.put("Appl_CmpDesignation", contactDesignation.getText().toString().trim());
            params.put("Appl_CmpContact_FirstName", contactName.getText().toString().trim());
            params.put("Appl_CmpContact_EmailID", contactEmail.getText().toString().trim());
            params.put("Appl_CmpContact_MobileNo", contactMobile.getText().toString().trim());




        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://kenmasterapi.azurewebsites.net/api/PartnerCompanyProfile";

        JsonArrayRequest request = new JsonArrayRequest(url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result======", response.toString());
                System.out.println("ResultOut" + params.toString());

                Toast.makeText(getActivity().getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG)
                        .show();
//                progressDialog.hide();
//                Intent intent=new Intent(CreateEnquiryActivity.this,EnquiryActivity.class);
//                startActivity(intent);

//                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.hide();
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

    }


//     Address Details


    private void addressDetails() {
//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Saving your data.. Please wait..");
//        progressDialog.show();
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
                                Address_Info_Comp address = new Address_Info_Comp();
                                //enquiryModel.setName(obj.getString("EnquiryId"));

                                address.setApl_AddressType(obj.getString("Apl_AddressType"));
                                address.setApl_Address_Street1(obj.getString("Apl_Address_Street1"));
                                address.setApl_Address_Street2(obj.getString("Apl_Address_Street2"));
                                address.setApl_Address_Landmark(obj.getString("Apl_LandMark"));
                                address.setApl_Address_City(obj.getString("Apl_Address_City"));
                                address.setApl_Address_State(obj.getString("Apl_Address_State"));
                                address.setApl_Address_Country(obj.getString("Apl_Address_Country"));
                                address.setApl_Address_ZIP(obj.getString("Apl_Address_ZIP"));

                                addressList.add(address);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
//                        progressDialog.dismiss();
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
//                progressDialog.dismiss();
                VolleyLog.d("Result Error", "Error: " + error.getMessage());

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(req);

    }

    private class loadMoreListView extends AsyncTask<Void, Void, Void> {

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


                                    int index = addressList.size();
                                    int end = index + 2;
                                    for (int i = index; i < end; i++) {
                                        try {

                                            JSONObject obj = response.getJSONObject(i);
                                            Address_Info_Comp address = new Address_Info_Comp();
                                            //enquiryModel.setName(obj.getString("EnquiryId"));

                                            address.setApl_AddressType(obj.getString("Apl_AddressType"));
                                            address.setApl_Address_Street1(obj.getString("Apl_Address_Street1"));
                                            address.setApl_Address_Street2(obj.getString("Apl_Address_Street2"));
                                            address.setApl_Address_Landmark(obj.getString("Apl_LandMark"));
                                            address.setApl_Address_City(obj.getString("Apl_Address_City"));
                                            address.setApl_Address_State(obj.getString("Apl_Address_State"));
                                            address.setApl_Address_Country(obj.getString("Apl_Address_Country"));
                                            address.setApl_Address_ZIP(obj.getString("Apl_Address_ZIP"));

                                            addressList.add(address);

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



