package com.kencloud.partner.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;


//
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.swashapps.app_util.CustomPreference;
//import com.swashapps.app_util.GCMClientManager;
//import com.swashapps.network_utils.ServiceCalls;

public class SplashActivity_old extends AppCompatActivity {
    String regId;
//    private GCMClientManager pushClientManager;
    Context context;

//    Project ID: kenschool-140807
//    Project number: 466610842678

//    String PROJECT_NUMBER = "466610842678";
    private static int SPLASH_TIME_OUT = 3000;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;
    private static final int REQUEST_PERMISSION_EXTERNAL_STORAGE_RESULT = 22;
    private static final int REQUEST_PERMISSION_READ_CONTACTS = 33;
    LocationManager manager;
    public static LocationProvider mLocationProvider;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        permissionPhoneState();

    }
//        if (TextUtils.isEmpty(CustomPreference.with(SplashActivity_old.this).getString("GCM_ID", ""))) {
//            pushClientManager = new GCMClientManager(SplashActivity_old.this, PROJECT_NUMBER);
//            pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
//                @Override
//                public void onSuccess(String registrationId, boolean isNewRegistration) {
//                    regId = registrationId;
//                    CustomPreference.with(SplashActivity_old.this).save("GCM_ID", regId);
//                    Log.d("RegisterActivity", "Success2: " + CustomPreference.with(SplashActivity_old.this).getString("GCM_ID", ""));
//                    //check gcm is generated
//                    if (TextUtils.isEmpty(CustomPreference.with(SplashActivity_old.this).getString("GCM_ID", ""))) {
//
//                    } else {
//                        //check gcm is send to server
//                        if (TextUtils.isEmpty(CustomPreference.with(SplashActivity_old.this).getString("REGD_GCM_ID", ""))) {
//                            ServiceCalls.postDeviceDetails(SplashActivity_old.this);
//                        }
//
//                    }
//
//                }
//
//                @Override
//                public void onFailure(String ex) {
//                    super.onFailure(ex);
//                }
//
//            });
//        } else {
//            if (TextUtils.isEmpty(CustomPreference.with(SplashActivity_old.this).getString("REGD_GCM_ID", ""))) {
//                ServiceCalls.postDeviceDetails(SplashActivity_old.this);
//            }
//        }

    private void permissionPhoneState() {
        if (checkPermission(android.Manifest.permission.READ_PHONE_STATE, getApplicationContext(), SplashActivity_old.this)) {
            // Set Values IMEI & SECURE_ID
            String strImei = getImei();
            String strSecureId = getSecureId();
            AppsFlyerLib.getInstance().setImeiData(strImei);
            AppsFlyerLib.getInstance().setAndroidIdData(strSecureId);

            permissionLocation();
        }
        else {
            requestPermission(android.Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_ASK_PERMISSIONS, getApplicationContext(), SplashActivity_old.this);
        }
    }

    private void permissionLocation() {
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, getApplicationContext(), SplashActivity_old.this)) {
            CheckLocationEnable();
            permissionCamera();
            // Shu
//            mLocationProvider=new LocationProvider(this, null);
//            mLocationProvider.connect();
        }
        else {
            requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_REQUEST_CODE_LOCATION, getApplicationContext(), SplashActivity_old.this);
        }
    }

    private void permissionCamera() {
        if (checkPermission(android.Manifest.permission.CAMERA, getApplicationContext(), SplashActivity_old.this)) {

            permissionContacts();
            } else {
                requestPermission(android.Manifest.permission.CAMERA, REQUEST_PERMISSION_EXTERNAL_STORAGE_RESULT, getApplicationContext(), SplashActivity_old.this);
            }
        }
    //shukra
    private void permissionContacts() {
        if (checkPermission(android.Manifest.permission.READ_CONTACTS, getApplicationContext(), SplashActivity_old.this)) {

        }
        else {
            requestPermission(android.Manifest.permission.READ_CONTACTS, REQUEST_PERMISSION_READ_CONTACTS, getApplicationContext(), SplashActivity_old.this);
        }
    }


    private void getIdAppsFlier() {

    }

    private void initFields() {
        // Initialisation here
    }

    public void splashHandlar(int timeOut){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent in = new Intent(SplashActivity_old.this, SliderActivity.class);
                Intent in = new Intent(SplashActivity_old.this, WelcomeActivity.class);
                startActivity(in);
                finish();
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

            }
        }, timeOut);
    }

//    private void splashHandlar (int timeOut){
//        // Initialisation here
//    }
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                startActivity(new Intent(SplashActivity_old.this, WelcomeActivity.class));
//                finish();
//
//            }
//        }, SPLASH_TIME_OUT);
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //new FetchTask().execute();
                    //checkPermission();
                    CheckLocationEnable();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();
                    splashHandlar(SPLASH_TIME_OUT);
                }
                //splashHandlar(SPLASH_TIME_OUT);
                break;
            case REQUEST_PERMISSION_READ_CONTACTS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    // Set Values IMEI & SECURE_ID
                    /*String strImei = getImei();
                    String strSecureId = getSecureId();



                    AppsFlyerLib.getInstance().setImeiData(strImei);
                    AppsFlyerLib.getInstance().setAndroidIdData(strSecureId);*/

                    permissionContacts();


                } else {
                    checkPermission();
                    // Permission Denied

                }
                splashHandlar(SPLASH_TIME_OUT);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public static boolean checkPermission(String strPermission,Context _c,Activity _a){
        int result = ContextCompat.checkSelfPermission(_c, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    public void requestPermission(String strPermission, int perCode, Context _c, Activity _a){
        if (ActivityCompat.shouldShowRequestPermissionRationale(_a, strPermission)){
            ActivityCompat.requestPermissions(_a,new String[]{strPermission},perCode);
            //Toast.makeText(VisitorHomePageActivity.this,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(_a,new String[]{strPermission},perCode);
        }
    }
    private boolean checkPermission() {
        boolean flag = true;
        //String[] permissions = {"android.permission.READ_PHONE_STATE"};
        boolean hasPermission = (ContextCompat.checkSelfPermission(SplashActivity_old.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hasLocationPermission = checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE);
                if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) { requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
                    flag = false;
                    return false;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }


    // CAMERA

    public void requestPermissionn(String strPermissionn, int perCode, Context _c, Activity _a){
        if (ActivityCompat.shouldShowRequestPermissionRationale(_a, strPermissionn)){
            ActivityCompat.requestPermissions(_a,new String[]{strPermissionn},perCode);
            //Toast.makeText(VisitorHomePageActivity.this,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(_a,new String[]{strPermissionn},perCode);
        }
    }
    private boolean checkPermissionn() {
        boolean flag = true;
        //String[] permissions = {"android.permission.CAMERA"};
        boolean hasPermission = (ContextCompat.checkSelfPermission(SplashActivity_old.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hasLocationPermission = checkSelfPermission(android.Manifest.permission.CAMERA);
                if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) { requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
                    flag = false;
                    return false;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity_old.this);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                splashHandlar(6000);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                splashHandlar(SPLASH_TIME_OUT);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void CheckLocationEnable()
    {
        if (Build.VERSION.SDK_INT >= 17)
        {
            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                showSettingsAlert();

            }
            else {
                splashHandlar(SPLASH_TIME_OUT);
            }
        }
        else
        {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            sendBroadcast(intent);
            splashHandlar(SPLASH_TIME_OUT);
        }
    }

    public String getImei(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = telephonyManager.getDeviceId();
        return device_id;
    }
    public String getSecureId(){
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String secure_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return secure_id;
    }
}


