package com.kencloud.partner.user.Fragment_Company;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_util.ValidationUtil;
import com.kencloud.partner.user.network_util.JsonArrayRequest;
import com.kencloud.partner.user.network_util.VolleySingleton;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceJsonTable;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

//import com.microsoft.sqlserver.jdbc.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.RadioGroup;
import android.widget.Toast;
import com.microsoft.windowsazure.mobileservices.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirstFragment extends Fragment {
    private View rootView;
    private AppCompatButton btnNext, btnSave;
    private AppCompatEditText name, emailid, editDob, phone, cellular, incpdate;
    RadioGroup maritalStatus;
    private Calendar myCalendar = Calendar.getInstance();
    private MobileServiceClient mClient;
    private MobileServiceJsonTable mJsonToDoTable;
    private MobileServiceTable<FirstFragment> mTable;
    ViewPager viewPager;
    Context context;

    MaterialBetterSpinner nationality, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_first, container, false);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

//        final String[] gender_array = new String[]{
//                "Male", "Female"
//
//        };
//        final String[] nation_array = new String[]{
//                "Indian", "NRI"
//
//        };

//        maritalStatus=(RadioGroup) rootView.findViewById(R.id.marital_status);
//        nationality = (MaterialBetterSpinner) rootView.findViewById(R.id.nationality);
//        gender = (MaterialBetterSpinner) rootView.findViewById(R.id.gender);
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, nation_array
//        );
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        nationality.setAdapter(spinnerArrayAdapter);
//        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, gender_array
//        );
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        gender.setAdapter(spinnerArrayAdapter1);

        name = (AppCompatEditText) rootView.findViewById(R.id.name);
        name = (AppCompatEditText) rootView.findViewById(R.id.name);
        ValidationUtil.removeWhiteSpaceFromFront(name);
        name.setFilters(new InputFilter[]{ValidationUtil.filter});
        name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        name.setLongClickable(false);


        editDob = (AppCompatEditText) rootView.findViewById(R.id.dob);
        editDob.setKeyListener(null);
        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnSave = (AppCompatButton) rootView.findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ValidationUtil.isValidName(name.getText().toString()))
                    Snackbar.make(rootView, "Invalid name", Snackbar.LENGTH_LONG).show();
                else if (editDob.getText().toString().equals(""))

                    Snackbar.make(rootView, "Please enter date of birth", Snackbar.LENGTH_LONG).show();
                else if (gender.getText().toString().equals(""))
                    Snackbar.make(rootView, "Please specify gender", Snackbar.LENGTH_LONG).show();
                else {
                  personalDetails();
                    Toast.makeText(getActivity().getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnNext = (AppCompatButton) rootView.findViewById(R.id.next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(1);

            }
        });

        return rootView;


    }


// Server Connection

    public void personalDetails() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....Please wait....");
        progressDialog.show();

        final JSONObject params = new JSONObject();
        try {
            //postWillGo
            params.put("User_RegistrationId","1");
//            params.put("ApplicationId", 16);
            params.put("Applicant_FirstName", name.getText().toString().trim());
            params.put("Applicant_Dateofbirth", editDob.getText().toString().trim());
            params.put("Applicant_Nationality", nationality.getText().toString().trim());
            params.put("Applicant_Gender", gender.getText().toString().trim());
            params.put("Applicant_MaritalStatus", maritalStatus.getCheckedRadioButtonId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url="http://kenmasterapi.azurewebsites.net/api/CompanyPersonalInformation";

        JsonArrayRequest request=new JsonArrayRequest(url,params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result======", response.toString());
                System.out.println("ResultOut" + params.toString());

                Toast.makeText(getActivity().getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG)
                        .show();
                progressDialog.hide();
//                Intent intent=new Intent(CreateEnquiryActivity.this,EnquiryActivity.class);
//                startActivity(intent);

                //dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


               progressDialog.hide();
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);


    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editDob.setText(sdf.format(myCalendar.getTime()));
    }

    //        MobileServiceTable<FirstModel> mToDoTable = mClient.getTable("ToDoItemBackup", FirstModel.class );
//        MobileServiceTable<FirstModel> mToDoTable1 = mClient.getTable(FirstModel.class);
//        try {
//
//
//            mClient = new MobileServiceClient(
//                    "https://kenpartnermobileapp.azurewebsites.net", getContext()
//            );
//        } catch (MalformedURLException e) {
//            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
//        }

//        FirstModel item = new FirstModel();
//        item.name = "Suchismita";
//        mClient.getTable(FirstModel.class).insert(item, new TableOperationCallback<item>() {
//            public void onCompleted(FirstModel entity, Exception exception, ServiceFilterResponse response) {
//                if (exception == null) {
//                    // Insert succeeded
//                   Toast.makeText(getActivity().getApplicationContext(), "Inserted Successfully" ,Toast.LENGTH_LONG).show();
//                } else {
//                    // Insert failed
//                    Toast.makeText(getActivity().getApplicationContext(), "Insertion Failed" ,Toast.LENGTH_LONG).show();
//                }
//            }
//        });



//    public void showAllUntyped() {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    final JsonElement result = mJsonToDoTable.execute().get();
//                    final JsonArray results = result.getAsJsonArray();
//                    getActivity().runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            for (JsonElement item : results) {
//                                String ID = item.getAsJsonObject().getAsJsonPrimitive("id").getAsString();
//                                String mText = item.getAsJsonObject().getAsJsonPrimitive("text").getAsString();
////                                Boolean mComplete = item.getAsJsonObject().getAsJsonPrimitive("complete").getAsBoolean();
//                                FirstModel mToDoItem = new FirstModel();
////                                mToDoItem.setName(ID);
//                                mToDoItem.setName(mText);
////                                mToDoItem.setComplete(mComplete);
////                                mAdapter.add(mToDoItem);
//                            }
//                        }
//                    });
//                } catch (Exception exception) {
//                    createAndShowDialog(exception, "Error");
//                }
//                return null;
//            }
//        }.execute();
//    }

    //    private void createAndShowDialogFromTask(final Exception exception, String title) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                createAndShowDialog(exception, "Error");
//            }
//        });
//    }
//    private void createAndShowDialog(Exception exception, String title) {
//        Throwable ex = exception;
//        if (exception.getCause() != null) {
//            ex = exception.getCause();
//        }
//        createAndShowDialog(ex.getMessage(), title);
//    }
//
//    private void createAndShowDialog(final String message, final String title) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        builder.setMessage(message);
//        builder.setTitle(title);
//        builder.create().show();
//    }

    //        Connecting to database

//    public void ConnectToDatabase(){
//        try {
//            // SET CONNECTIONSTRING
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
//            String username = "swashDB_Admin";
//            String password = "Swash@2016";
////            Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.188.4.83:1433/DATABASE;user=" + username + ";password=" + password);
//            Connection DbConn = DriverManager.getConnection
//                    ("jdbc:sqlserver://swash.database.windows.net:1433;database=SwashMaster;user=swashDB_Admin@swash;password={Swash@2016};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
//            Log.w("Connection","open");
//            Statement stmt = DbConn.createStatement();
//            ResultSet reset = stmt.executeQuery(" select * from KP_Application ");
//           name.setText(reset.getString(1));
//
//            DbConn.close();
//
//        } catch (Exception e)
//        {
//            Log.w("Error connection","" + e.getMessage());
//        }
//    }


//    public class FirstAdapter extends ArrayAdapter<FirstModel> {
//
//        /**
//         * Adapter context
//         */
//        Context mContext;
//
//        /**
//         * Adapter View layout
//         */
//        int mLayoutResourceId;
//
//        public FirstAdapter(Context context, int layoutResourceId) {
//            super(context, layoutResourceId);
//
//            mContext = context;
//            mLayoutResourceId = layoutResourceId;
//        }
//
//        /**
//         * Returns the view for a specific item on the list
//         */
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View row = convertView;
//
//            final FirstModel currentItem = getItem(position);
//
//            if (row == null) {
//                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//                row = inflater.inflate(mLayoutResourceId, parent, false);
//            }
//
//            row.setTag(currentItem);
////final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkToDoItem);
////        checkBox.setText(currentItem.getText());
////        checkBox.setChecked(false);
////        checkBox.setEnabled(true);
//
//
//            final TextView name = (TextView) row.findViewById(R.id.name);
//            final TextView dob = (TextView) row.findViewById(R.id.dob);
//            final MaterialBetterSpinner nationality = (MaterialBetterSpinner) row.findViewById(R.id.nationality);
//            final MaterialBetterSpinner gender = (MaterialBetterSpinner) row.findViewById(R.id.gender);
//            final RadioGroup status = (RadioGroup) row.findViewById(R.id.marital_status);
//            final Button save = (Button) row.findViewById(R.id.save);
//            save.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//
//                    if (!ValidationUtil.isValidName(name.getText().toString()))
//                        Snackbar.make(rootView, "Invalid name", Snackbar.LENGTH_LONG).show();
//                    else if (editDob.getText().toString().equals(""))
//
//                        Snackbar.make(rootView, "Please enter date of birth", Snackbar.LENGTH_LONG).show();
//                    else if (gender.getText().toString().equals(""))
//                        Snackbar.make(rootView, "Please specify gender", Snackbar.LENGTH_LONG).show();
//                    else {
//                        showAllUntyped();
//                        Toast.makeText(getActivity().getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
//
//
//                    }
//
//                }
//
//
//            });
//            return row;
//        }
//    public void checkItem(final FirstModel item) {
//        if (mClient == null) {
//            return;
//        }

        // Set the item as completed and update it in the table
//        item.setComplete(true);

        //        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//
////                    checkItemInTable(item);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            FirstModel model= new FirstModel();
//                            model.name="TestAndroid";
//
//                            FirstModel entity = mToDoTable.insert(item).get();
////                            if (item.isComplete()) {
////                                mAdapter.remove(item);
////                            }
//                        }
//                    });
//                } catch (final Exception e) {
//                    createAndShowDialogFromTask(e, "Error");
//                }
//
//                return null;
//            }
//        };
//
//        runAsyncTask(task);
//
//    }
//        private void createAndShowDialogFromTask(final Exception exception, String title) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    createAndShowDialog(exception, "Error");
//                }
//            });
//        }


//     OFFLINE SYNC


//    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceException, ExecutionException, InterruptedException {
//
//        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//
//                    MobileServiceSyncContext syncContext = mClient.getSyncContext();
//
//                    if (syncContext.isInitialized())
//                        return null;
//
////                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);
//
//                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
//                    tableDefinition.put("id", ColumnDataType.String);
//                    tableDefinition.put("text", ColumnDataType.String);
//                    tableDefinition.put("complete", ColumnDataType.Boolean);
//
//                    localStore.defineTable("Ken_MobileApp", tableDefinition);
//
//                    SimpleSyncHandler handler = new SimpleSyncHandler();
//
//                    syncContext.initialize(localStore, handler).get();
//
//                } catch (final Exception e) {
//                    createAndShowDialogFromTask(e, "Error");
//                }
//
//                return null;
//            }
//        };
//
//        return runAsyncTask(task);
//    }


//        private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            } else {
//                return task.execute();
//            }
//        }
//    }
}

