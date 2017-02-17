package com.kencloud.partner.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.kencloud.partner.user.helpers.Constant_Client;
import com.kencloud.partner.user.helpers.HttpService;
import com.kencloud.partner.user.helpers.Utils;
import com.kencloud.partner.user.helpers.WorkItemAdapter;
import com.kencloud.partner.user.icon_util.IcoMoonIcons;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.AuthenticationSettings;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.aad.adal.UserIdentifier;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout about_us_layout, faq_layout, form_layout, feedback_layout, fees_layout, holiday_list_layout;
    AppCompatTextView ui_notification_num;
    private int notification_count = 0;
    private final static String TAG = "HomeActivity";

    private AuthenticationContext mAuthContext;
    private static AuthenticationResult sResult;

    /**
     * Adapter to sync the items list with the view
     */
    private WorkItemAdapter mAdapter = null;

    /**
     * Show this dialog when activity first launches to check if user has login
     * or not.
     */
    private ProgressDialog mLoginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.swash_logo);


        CookieSyncManager.createInstance(getApplicationContext());
//        Toast.makeText(getApplicationContext(), TAG + "LifeCycle: OnCreate", Toast.LENGTH_SHORT)
//                .show();

        // Clear previous sessions
        clearSessionCookie();
        try {
            // Provide key info for Encryption
            if (Build.VERSION.SDK_INT < 18) {
                Utils.setupKeyForSample();
            }
            final TextView name = (TextView)findViewById(R.id.userLoggedIn);


            mLoginProgressDialog = new ProgressDialog(this);
            mLoginProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mLoginProgressDialog.setMessage("Login in progress...");
            mLoginProgressDialog.show();
            // Ask for token and provide callback
            try {
                mAuthContext = new AuthenticationContext(HomeActivity.this, Constant_Client.AUTHORITY_URL,
                        false);
                String policy = getIntent().getStringExtra("thePolicy");

                if(Constant_Client.CORRELATION_ID != null &&
                        Constant_Client.CORRELATION_ID.trim().length() !=0){
                    mAuthContext.setRequestCorrelationId(UUID.fromString(Constant_Client.CORRELATION_ID));
                }

                AuthenticationSettings.INSTANCE.setSkipBroker(true);

                mAuthContext.acquireToken(HomeActivity.this, Constant_Client.SCOPES, Constant_Client.ADDITIONAL_SCOPES, policy, Constant_Client.CLIENT_ID,
                        Constant_Client.REDIRECT_URL, getUserInfo(), PromptBehavior.Always,
                        "nux=1&" + Constant_Client.EXTRA_QP,
                        new AuthenticationCallback<AuthenticationResult>() {

                            @Override
                            public void onError(Exception exc) {
                                if (mLoginProgressDialog.isShowing()) {
                                    mLoginProgressDialog.dismiss();
                                }
                                    finish();
//                                startActivity(new Intent(HomeActivity.this,AzureLoginActivity.class));
                                SimpleAlertDialog.showAlertDialog(HomeActivity.this,
                                        "Failed to get token", exc.getMessage());
                            }

                            @Override
                            public void onSuccess(AuthenticationResult result) {
                                if (mLoginProgressDialog.isShowing()) {
                                    mLoginProgressDialog.dismiss();
                                }

                                if (result != null && !result.getToken().isEmpty()) {
                                    setLocalToken(result);
                                    updateLoggedInUser();
                                    getTasks();

                                    HomeActivity.sResult = result;
                                    Toast.makeText(getApplicationContext(), "Token is returned", Toast.LENGTH_SHORT)
                                            .show();

                                    if (sResult.getUserInfo() != null) {
                                        name.setText(result.getUserInfo().getDisplayableId());
                                        Toast.makeText(getApplicationContext(),
                                                "User:" + sResult.getUserInfo().getDisplayableId(), Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } else {
                                    //TODO: popup error alert
                                }
                            }
                        });
            } catch (Exception e) {
                SimpleAlertDialog.showAlertDialog(HomeActivity.this, "Exception caught", e.getMessage());
            }
            Toast.makeText(HomeActivity.this, TAG + "done", Toast.LENGTH_SHORT).show();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }}

    private void clearSessionCookie() {

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        CookieSyncManager.getInstance().sync();
    }

    private void updateLoggedInUser() {
        TextView textView = (TextView) findViewById(R.id.userLoggedIn);
        textView.setText("N/A");
        if (sResult != null) {
            if (sResult.getToken() != null)
                textView.setText(sResult.getUserInfo().getDisplayableId());
            else {
                textView.setText("User with No ID Token");
            }
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar)  ;
//        getSupportActionBar().setTitle("KenPartner");
//        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        about_us_layout = (LinearLayout) findViewById(R.id.aboutUs);
        faq_layout = (LinearLayout) findViewById(R.id.FAQ);
        feedback_layout = (LinearLayout) findViewById(R.id.feedback);
        form_layout = (LinearLayout) findViewById(R.id.partnership_form);
        faq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FAQs.class));
            }
        });
        about_us_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AboutUs.class));
            }
        });
        form_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new Dialog(HomeActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    dialog = new Dialog(HomeActivity.this);
                }
                dialog.setContentView(R.layout.mainscreen_dialog);
                final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioCatagory);


                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        // find which radio button is selects
                        if (checkedId == R.id.radioIndividual) {

                        } else {

                        }

                        Button button = (Button) dialog.findViewById(R.id.btnSubmit);
                        final RadioButton individual = (RadioButton) dialog.findViewById(R.id.radioIndividual);
                        final RadioButton company = (RadioButton) dialog.findViewById(R.id.radioCompany);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override

                            public void onClick(View v) {

                                int selectedId = radioGroup.getCheckedRadioButtonId();


                                // find which radioButton is checked by id

                                if (selectedId == individual.getId()) {
                                    startActivity(new Intent(HomeActivity.this, IndividualActivity.class));
                                } else if (selectedId == company.getId()) {
                                    startActivity(new Intent(HomeActivity.this, CompanyActivity.class));

                                }
//                                dialog.dismiss();

                            }
                        });
//                        dialog.dismiss();
                    }

                });
                dialog.show();
//               startActivity(new Intent(HomeActivity.this, FormMainActivity.class));
            }
        });

        feedback_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, Feedback.class));
            }
        });
    }

    private String getUniqueId() {
        if (sResult != null && sResult.getUserInfo() != null
                && sResult.getUserInfo().getUniqueId() != null) {
            return sResult.getUserInfo().getUniqueId();
        }

        return null;
    }

    private UserIdentifier getUserInfo() {

        final TextView names = (TextView)findViewById(R.id.userLoggedIn);
        String name = names.getText().toString();
        return new UserIdentifier(name, UserIdentifier.UserIdentifierType.OptionalDisplayableId);
    }
    private void getTasks() {
        if (sResult == null || sResult.getToken().isEmpty())
            return;

        List<String> items = new ArrayList<>();
        try {
            items = new HttpService().getAllItems(sResult.getToken());
        } catch (Exception e) {
            items = new ArrayList<>();
        }

        ListView listview = (ListView) findViewById(R.id.listViewToDo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, items);
        listview.setAdapter(adapter);
    }

    private URL getEndpointUrl() {
        URL endpoint = null;
        try {
            endpoint = new URL(Constant_Client.SERVICE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return endpoint;
    }

    private void initAppTables() {
        try {
            ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
            listViewToDo.setAdapter(mAdapter);

        } catch (Exception e) {
            createAndShowDialog(new Exception(
                    "There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
    }

    private void getToken(final AuthenticationCallback callback) {

        String policy = getIntent().getStringExtra("thePolicy");

        // one of the acquireToken overloads
        mAuthContext.acquireToken(HomeActivity.this, Constant_Client.SCOPES, Constant_Client.ADDITIONAL_SCOPES,
                policy, Constant_Client.CLIENT_ID, Constant_Client.REDIRECT_URL, getUserInfo(),
                PromptBehavior.Always, "nux=1&" + Constant_Client.EXTRA_QP, callback);
    }

    private AuthenticationResult getLocalToken() {
        return Constant_Client.CURRENT_RESULT;
    }

    private void setLocalToken(AuthenticationResult newToken) {


        Constant_Client.CURRENT_RESULT = newToken;
    }

    @Override
    public void onResume() {
        super.onResume(); // Always call the superclass method first

        updateLoggedInUser();
        // User can click logout, it will come back here
        // It should refresh list again
        getTasks();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAuthContext.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param exception The exception to show in the dialog
     * @param title     The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        createAndShowDialog(exception.toString(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message The dialog message
     * @param title   The dialog title
     */
    private void createAndShowDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option_menu, menu);
//        menu.findItem(R.id.action_notification).setIcon(
//                new IconDrawable(this, IcoMoonIcons.ic_notification)
//                        .colorRes(R.color.white)
//                        .sizeDp(20));
////        MenuItem item = menu.findItem(R.id.action_notification);
////        MenuItemCompat.setActionView(item, R.layout.menu_badge_view);
////        View view = MenuItemCompat.getActionView(item);
////        ui_notification_num = (AppCompatTextView) view.findViewById(R.id.notification_count);
////        ui_notification_num.setVisibility(View.GONE);
//        // new GetUpcomingWish().execute();
//        //calling wish inbox count service
////        getWishInboxCount();
////        int totalRecords = 0;
//
////        updateWishCount(notification_count);
////        new MyMenuItemStuffListener(view, "Notification") {
////            @Override
////            public void onClick(View v) {
////                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
//////                finish();
////            }
////        };
//
//        return true;
//    }
    public void updateWishCount(final int _count) {
        notification_count = _count;
        //final int[] totalRecords = {0};
        if (ui_notification_num == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (_count == 0)
                    ui_notification_num.setVisibility(View.VISIBLE);
                else {
                    ui_notification_num.setVisibility(View.VISIBLE);
                    ui_notification_num.setText(Integer.toString(_count));
                }
            }
        });
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        int id = item.getItemId();
//
//
//        if (id == R.id.action_notification) {
//            startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
//
//            return true;
//        }
//        if (id == R.id.cancel) {
//
//            //do something
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}




