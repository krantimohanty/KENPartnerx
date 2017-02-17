package com.kencloud.partner.user.Fragment_individual;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_util.ValidationUtil;
import com.kencloud.partner.user.icon_util.IcoMoonIcons;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Personal extends Fragment {
    private String imgString = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private View rootView;
    private ImageView profile_Pic;
    RadioGroup maritalStatus;
    MaterialBetterSpinner nationality, gender ;
    private AppCompatButton btnNext, btnSave;
    private AppCompatEditText nameApp,nameGuadian,nameMother,   emailid, dob  ;
    private Calendar myCalendar = Calendar.getInstance();
ViewPager viewPager;
    private  Bitmap bm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Iconify.with(new FontAwesomeModule());
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_personal, container, false);
        profile_Pic = (ImageView) rootView.findViewById(R.id.profilePic);
//        profile_Pic.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_user)
//                .colorRes(R.color.grey)
//                .sizePx(70));
        profile_Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

viewPager=(ViewPager)getActivity().findViewById(R.id.viewpager);

//        final String[] gender_array = new String[]{
//                "Male", "Female"
//
//        };
//        final String[] nation_array = new String[]{
//                "Indian", "NRI"
//
//        };
//nationality=(MaterialBetterSpinner)rootView.findViewById(R.id.nationality);
//        gender=(MaterialBetterSpinner)rootView.findViewById(R.id.gender);
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, nation_array
//        );
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        nationality.setAdapter(spinnerArrayAdapter);
//        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, gender_array
//        );
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        gender.setAdapter(spinnerArrayAdapter1);
        btnSave=(AppCompatButton)rootView.findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetailsIndividual();
//                Toast.makeText(getActivity().getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG ).show();
            }
        });
        btnNext=(AppCompatButton)rootView.findViewById(R.id.next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewPager.setCurrentItem(getItem(+1), true);
               viewPager.setCurrentItem(1);

            }
        });



        maritalStatus=(RadioGroup) rootView.findViewById(R.id.marital_status);



        emailid= (AppCompatEditText) rootView.findViewById(R.id.email);


        nameApp = (AppCompatEditText) rootView.findViewById(R.id.name_applicant);
        ValidationUtil.removeWhiteSpaceFromFront(nameApp);
        nameApp.setFilters(new InputFilter[]{ValidationUtil.filter});
        nameApp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        nameApp.setLongClickable(false);


        nameGuadian = (AppCompatEditText) rootView.findViewById(R.id.name_guardian);
        ValidationUtil.removeWhiteSpaceFromFront(nameGuadian);
        nameGuadian.setFilters(new InputFilter[]{ValidationUtil.filter});
        nameGuadian.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        nameGuadian.setLongClickable(false);


        nameMother = (AppCompatEditText) rootView.findViewById(R.id.name_mother);
        ValidationUtil.removeWhiteSpaceFromFront(nameMother);
        nameMother.setFilters(new InputFilter[]{ValidationUtil.filter});
        nameMother.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        nameMother.setLongClickable(false);


        dob = (AppCompatEditText) rootView.findViewById(R.id.dob);
        dob.setKeyListener(null);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return  rootView;
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
        dob.setText(sdf.format(myCalendar.getTime()));
    }
    //getting profile pic

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        profile_Pic.setImageBitmap(thumbnail);

        imgString = getImageString(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = this.getActivity().managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

//        bm=((BitmapDrawable) profile_Pic.getDrawable()).getBitmap();;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        profile_Pic.setImageBitmap(bm);
        imgString = getImageString(bm);
    }
    //converting bitmap to base64 string
    private String getImageString(Bitmap bm) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String img = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return img;
    }


    public  void personalDetailsIndividual()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving your data.. Please wait..");
        progressDialog.show();


        String url="http://kenmasterapi.azurewebsites.net/api/ApplicationPersonalInformation";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        progressDialog.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity().getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG)
                       .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        progressDialog.dismiss();
                        //Showing toast
                        Toast.makeText(getActivity().getApplicationContext(), ""+volleyError, Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
//                String image = getImageString(bm);

                //Getting Image Name
//                String name = editTextName.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();



            String UserFullName=nameApp.getText().toString();
            String[] uarr=UserFullName.split(" ");

            String fname=uarr[0];
            String mname=uarr[1];
//            String lname=uarr[2];


            String GuadianFullName=nameGuadian.getText().toString();
            String[] garr=GuadianFullName.split(" ");

            String gfname=garr[0];
            String gmname=garr[1];
//            String glname=garr[2];

            String MotherFullName=nameMother.getText().toString();
            String[] marr=MotherFullName.split(" ");

            String mfname=marr[0];
            String mmname=marr[1];
//            String mlname=marr[2];
            //postWillGo
            params.put("User_RegistrationId","9");
//            params.put("ApplicationId", 16);
            params.put("Applicant_FirstName", fname);
//            params.put("Applicant_MiddleName",mname);
            params.put("Applicant_LastName", mname);
            params.put("Applicant_Guardian_FirstName", gfname);
//            params.put("Applicant_Guardian_MiddleName", gmname);
            params.put("Applicant_Guardian_LastName", gmname);
            params.put("Applicant_Mother_FirstName", mfname);
//            params.put("Applicant_Mother_MiddleName", mmname);
            params.put("Applicant_Mother_LastName", mmname);
            params.put("Applicant_Contact_Email_Id", emailid.getText().toString().trim());
            params.put("Applicant_Dateofbirth", dob.getText().toString().trim());
            params.put("Applicant_Nationality", nationality.getText().toString().trim());
            params.put("Applicant_Gender", gender.getText().toString().trim());
            int selected = maritalStatus.getCheckedRadioButtonId();
            RadioButton status = (RadioButton) rootView.findViewById(selected);
            String selectedValue = status.getText().toString();
            params.put("Applicant_MaritalStatus", selectedValue);
            params.put("Application_UploadFileLink", imgString);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

//        final JSONObject params = new JSONObject();
//        try {
////            String image = getImageString(bm);
//            String UserFullName=nameApp.getText().toString();
//            String[] uarr=UserFullName.split(" ");
//
//            String fname=uarr[0];
//            String mname=uarr[1];
////            String lname=uarr[2];
//
//
//            String GuadianFullName=nameGuadian.getText().toString();
//            String[] garr=GuadianFullName.split(" ");
//
//            String gfname=garr[0];
//            String gmname=garr[1];
////            String glname=garr[2];
//
//            String MotherFullName=nameMother.getText().toString();
//            String[] marr=MotherFullName.split(" ");
//
//            String mfname=marr[0];
//            String mmname=marr[1];
////            String mlname=marr[2];
//            //postWillGo
//            params.put("User_RegistrationId","9");
////            params.put("ApplicationId", 16);
//            params.put("Applicant_FirstName", fname);
////            params.put("Applicant_MiddleName",mname);
//            params.put("Applicant_LastName", mname);
//            params.put("Applicant_Guardian_FirstName", gfname);
////            params.put("Applicant_Guardian_MiddleName", gmname);
//            params.put("Applicant_Guardian_LastName", gmname);
//            params.put("Applicant_Mother_FirstName", mfname);
////            params.put("Applicant_Mother_MiddleName", mmname);
//            params.put("Applicant_Mother_LastName", mmname);
//            params.put("Applicant_Contact_Email_Id", emailid.getText().toString().trim());
//            params.put("Applicant_Dateofbirth", dob.getText().toString().trim());
//            params.put("Applicant_Nationality", nationality.getText().toString().trim());
//            params.put("Applicant_Gender", gender.getText().toString().trim());
//            int selected = maritalStatus.getCheckedRadioButtonId();
//            RadioButton status = (RadioButton) rootView.findViewById(selected);
//            String selectedValue = status.getText().toString();
//            params.put("Applicant_MaritalStatus", selectedValue);
//            params.put("Application_UploadFileLink", imgString);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String url="http://kenmasterapi.azurewebsites.net/api/ApplicationPersonalInformation";
//
//        JsonArrayRequest request=new JsonArrayRequest(url,params, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                Log.d("Result======", response.toString());
//                System.out.println("ResultOut" + params.toString());
//
//                Toast.makeText(getActivity().getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG)
//                        .show();
//                progressDialog.hide();
////                Intent intent=new Intent(CreateEnquiryActivity.this,EnquiryActivity.class);
////                startActivity(intent);
//
//                //dialog.dismiss();

//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//               progressDialog.dismiss();
//                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                90000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
//
//
//    }

}


