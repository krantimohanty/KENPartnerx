package com.kencloud.partner.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import com.joanzapata.iconify.IconDrawable;
import com.kencloud.partner.user.GPS.CustomPreference;
import com.kencloud.partner.user.app_model.User;
import com.kencloud.partner.user.app_util.GooglePlusLoginUtils;
import com.kencloud.partner.user.icon_util.IcoMoonIcons;
import com.kencloud.partner.user.network_util.VolleySingleton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static android.R.attr.type;
import static com.kencloud.partner.user.R.id.url;

/**
 * Created by suchismita.p on 10/20/2016.
 */

public class LoginActivityKen extends BaseActivity implements GooglePlusLoginUtils.GPlusLoginStatus {

    private GooglePlusLoginUtils gLogin;
    private ContentLoadingProgressBar progressBar;
    JSONObject jresponse;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private User user;
    private ConnectionResult mConnectionResult;
    private SignInButton signinButton;
    private TextInputLayout inputLayoutEmail, inputLayoutPass;
    private AppCompatEditText  password;
    private AutoCompleteTextView email_id;
    public String pageFrom = "";
    public String type = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); //Facebook Login
        callbackManager = CallbackManager.Factory.create();
//        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();
        setContentView(R.layout.login);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        email_id = (AutoCompleteTextView) findViewById(R.id.email_id);
        email_id.setHintTextColor(Color.WHITE);
        email_id.setTextColor(Color.BLUE);

        password = (AppCompatEditText) findViewById(R.id.password);
        password.setHintTextColor(Color.WHITE);
        password.setTextColor(Color.BLUE);
//        IconDrawable email = new IconDrawable(LoginActivityKen.this, IcoMoonIcons.ic_email).colorRes(R.color.white).sizeDp(16);
//        email_id.setCompoundDrawablesWithIntrinsicBounds(email,null,null,null);
//         IconDrawable pass = new IconDrawable(LoginActivityKen.this, IcoMoonIcons.ic_password).colorRes(R.color.white).sizeDp(18);
//        password.setCompoundDrawablesWithIntrinsicBounds(pass, null, null, null);
        Button button = (Button) findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityKen.this, MainActivity.class));
            }
        });
//

//        TextView signup = (TextView) findViewById(R.id.signUpTextView);
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivityKen.this, RegistrationActivity.class));
//            }
//        });

    //FB Login
       loginButton = (LoginButton) findViewById(R.id.fb_login);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i("rtag", "loginResult: " + loginResult);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                    System.out.println("ERROR");
                                } else {
                                    System.out.println("Success");
                                    try {
                                        user = new User();
                                        String jsonresult = String.valueOf(object);
                                        System.out.println("JSON Result" + jsonresult);
                                        //Toast.makeText(LoginActivityOldie.this, jsonresult + "", Toast.LENGTH_LONG).show();
                                        user.email = object.getString("email").toString();
                                        user.facebookID = object.getString("id").toString();
                                        user.name = object.getString("name").toString();
                                        user.fname = object.getString("first_name").toString();
                                        user.lname = object.getString("last_name").toString();
                                        user.gender = object.getString("gender").toString();
                                        user.userPhoto = "https://graph.facebook.com/" + user.facebookID + "/picture?type=large";

                                        //  AppSharedPreference.setCurrentUser(user, LoginActivityOldie.this);
                                        //String urlProfilePic = "https://graph.facebook.com/" + user.facebookID + "/picture?type=large";
                                        // String device_id="";
                                        fbLogin(user);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //Toast.makeText(LoginActivityOldie.this, "welcome " + user.name + "\n" + user.email + "\n" + user.facebookID + "\n" + user.gender, Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
                Log.i("rtag", "cancel:");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("rtag", "exception:" + exception);
            }
        });

        gLogin = new GooglePlusLoginUtils(this, R.id.btn_sign_in);
        gLogin.setLoginStatus(this);
    }

    protected void onStart() {
        super.onStart();
        gLogin.connect();
    }

    protected void onStop() {
        super.onStop();
        gLogin.disconnect();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        gLogin.onActivityResult(requestCode, resultCode, data);
    }


        //login using fb
    public void fbLogin(final User user) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url="http://kenmasterapi.azurewebsites.net/api/ApplicationPersonalInformation";
        JSONObject params = new JSONObject();

        try {
            params.put("method", "FB_login");
            params.put("Fbid", user.facebookID);
            params.put("name", user.name);
            params.put("email", user.email);
            params.put("photopath", user.userPhoto);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("rtag", params.toString());
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("App", response.toString());
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("FB_LoginResult");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                CustomPreference.with(LoginActivityKen.this).save("user_id", jsonArray.getJSONObject(i).getString("user_id"));
                               /* startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                finish();*/

                                if (pageFrom.equalsIgnoreCase("BaseActivity")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("latest")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class).putExtra("pageFrom", "latest"));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("Popular")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class).putExtra("pageFrom", "Popular"));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("Sectoral")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class)
                                            .putExtra("pageFrom", "Sectoral")
                                            .putExtra("sector_id", CustomPreference.with(LoginActivityKen.this).getString("sector_id", "")));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("EventzDetailActivity")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class)
                                            .putExtra("flag", "news")
                                            .putExtra("type", type)
                                            .putExtra("post_id", CustomPreference.with(LoginActivityKen.this).getString("post_id", "")));
                                    finish();
                                } else {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class));
                                    finish();
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
                        .setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    //login using Google
    public void googleLogin(final User user) {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url="http://kenmasterapi.azurewebsites.net/api/ApplicationPersonalInformation";
        JSONObject params = new JSONObject();

        try {
            params.put("method", "G_Login");
            params.put("gid", user.gPlusId);
            params.put("name", user.name);
            params.put("email", user.email);
            params.put("photopath", user.userPhoto);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("rtag", params.toString());
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("App", response.toString());
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("G_LoginResult");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                CustomPreference.with(LoginActivityKen.this).save("user_id", jsonArray.getJSONObject(i).getString("user_id"));
                               /* startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                finish();*/

                                if (pageFrom.equalsIgnoreCase("BaseActivity")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("latest")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class).putExtra("pageFrom", "latest"));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("Popular")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class).putExtra("pageFrom", "Popular"));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("Sectoral")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class)
                                            .putExtra("pageFrom", "Sectoral")
                                            .putExtra("sector_id", CustomPreference.with(LoginActivityKen.this).getString("sector_id", "")));
                                    finish();
                                } else if (pageFrom.equalsIgnoreCase("EventzDetailActivity")) {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class)
                                            .putExtra("flag", "news")
                                            .putExtra("type", type)
                                            .putExtra("post_id", CustomPreference.with(LoginActivityKen.this).getString("post_id", "")));
                                    finish();
                                } else {
                                    startActivity(new Intent(LoginActivityKen.this, MainActivity.class));
                                    finish();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
                        .setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    @Override
    public void OnSuccessGPlusLogin(Bundle profile) {
        user = new User();
        user.name = profile.getString(GooglePlusLoginUtils.NAME);
        user.email = profile.getString(GooglePlusLoginUtils.EMAIL);
        user.userPhoto = profile.getString(GooglePlusLoginUtils.PHOTO);
        user.gPlusId = profile.getString(GooglePlusLoginUtils.GID);
        googleLogin(user);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
