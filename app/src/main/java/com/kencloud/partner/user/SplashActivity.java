package com.kencloud.partner.user;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.kencloud.partner.user.GPS.LocationProvider;
import com.kencloud.partner.user.helpers.Constants;


import java.util.Timer;

public class SplashActivity extends Activity {

    int count = 0;
    Timer timer;

    private static int SPLASH_TIME_OUT = 6500;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    LocationManager manager;
    public static LocationProvider mLocationProvider;


    String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_CHECKIN_PROPERTIES, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


       /* //check Permission
        if (ContextCompat.checkSelfPermission(LandingActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LandingActivity.this,
                    permissions,
                    0);
        }*/

        // Tracking any event
        //AnalyticsApplication.getInstance().trackEvent("Splash Screen","Create","Page Shown");

        // Tracking Screen
        //AnalyticsApplication.getInstance().trackScreenView("Splash Screen");


        initFields();
        getIdAppsFlier();

        // Initialize AppsFlier Lib
        AppsFlyerLib.getInstance().startTracking(this.getApplication(),"tZAZfbkbXhmKhtgsYv5usn");

        // Track Data : Add to activities where tracking needed
        AppsFlyerLib.getInstance().sendDeepLinkData(this);

        //trigger 'loadIMEI'
        //loadIMEI();

        permissionPhoneState();

        /*SplashTimertask timertask = new SplashTimertask();
        timer = new Timer();
        timer.schedule(timertask, 2 * 1000);*/
    }

    private void permissionPhoneState() {
        if (checkPermission(Manifest.permission.READ_PHONE_STATE, getApplicationContext(), SplashActivity.this)) {
            // Set Values IMEI & SECURE_ID
            String strImei = getImei();
            String strSecureId = getSecureId();
            AppsFlyerLib.getInstance().setImeiData(strImei);
            AppsFlyerLib.getInstance().setAndroidIdData(strSecureId);

            permissionLocation();
        }
        else {
            requestPermission(Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_ASK_PERMISSIONS, getApplicationContext(), SplashActivity.this);
        }
    }

    private void permissionLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getApplicationContext(), SplashActivity.this)) {
            CheckLocationEnable();
            mLocationProvider=new LocationProvider(this, null);
            mLocationProvider.connect();
        }
        else {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_REQUEST_CODE_LOCATION, getApplicationContext(), SplashActivity.this);
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
                //Intent in = new Intent(SplashActivity.this, SliderActivity.class);
                Intent in = new Intent(SplashActivity.this, MainActivity.class);
                in.putExtra("thePolicy", Constants.EMAIL_SIGNIN_POLICY);
                startActivity(in);
                finish();
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

            }
        }, timeOut);
    }

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
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    // Set Values IMEI & SECURE_ID
                    /*String strImei = getImei();
                    String strSecureId = getSecureId();
                    AppsFlyerLib.getInstance().setImeiData(strImei);
                    AppsFlyerLib.getInstance().setAndroidIdData(strSecureId);*/

                    permissionPhoneState();


                } else {
                    checkPermission();
                    // Permission Denied
                    permissionLocation();
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
        boolean hasPermission = (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hasLocationPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
                if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) { requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
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

    /**
     * Called when the 'loadIMEI' function is triggered.
     */
    /*public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }*/



    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    /*private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(LoadingActivity.this)
                    .setTitle("Permission Request")
                    .setMessage(getString(R.string.permission_read_phone_state_rationale))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(LoadingActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .setIcon(R.drawable.onlinlinew_warning_sign)
                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }*/



}
