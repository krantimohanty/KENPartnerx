package com.kencloud.partner.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.kencloud.partner.user.app_adapter.PartnerAdapter;
import com.kencloud.partner.user.app_model.HApp;
import com.kencloud.partner.user.helpers.Constants;
import com.kencloud.partner.user.helpers.Utils;
import com.kencloud.partner.user.helpers.WorkItemAdapter;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.AuthenticationSettings;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.aad.adal.UserIdentifier;

public class MainActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener  {
    ExpandableListView expListView;
    HashMap<String, List<String>> listDataChild;
//    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
private List<HApp> app;
    private PartnerAdapter adapter1;
    private RecyclerView mRecyclerView, mRecyclerView1;
private SliderLayout mDemoSlider;
    LinearLayout newsLayout, policyLayout, trainingLayout, productLayout, orderLayout;
    private final static String TAG = "MainActivity";
    private AuthenticationContext mAuthContext;
    private static AuthenticationResult sResult;
    private WorkItemAdapter mAdapter = null;
    private ProgressDialog mLoginProgressDialog;
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CookieSyncManager.createInstance(getApplicationContext());
////        Toast.makeText(getApplicationContext(), TAG + "LifeCycle: OnCreate", Toast.LENGTH_SHORT)
////                .show();
//
//        //
//        // Clear previous sessions
//        clearSessionCookie();
//        try {
//            // Provide key info for Encryption
//            if (Build.VERSION.SDK_INT < 18) {
//                Utils.setupKeyForSample();
//            }
//
//            final TextView name = (TextView)findViewById(R.id.userLoggedIn);
//
//
//            mLoginProgressDialog = new ProgressDialog(this);
//            mLoginProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            mLoginProgressDialog.setMessage("Login in progress...");
//            mLoginProgressDialog.show();
//            // Ask for token and provide callback
//            try {
//                mAuthContext = new AuthenticationContext(MainActivity.this, Constants.AUTHORITY_URL,
//                        false);
//                String policy = getIntent().getStringExtra("thePolicy");
//
//                if(Constants.CORRELATION_ID != null &&
//                        Constants.CORRELATION_ID.trim().length() !=0){
//                    mAuthContext.setRequestCorrelationId(UUID.fromString(Constants.CORRELATION_ID));
//                }
//
//                AuthenticationSettings.INSTANCE.setSkipBroker(true);
//
//                mAuthContext.acquireToken(MainActivity.this, Constants.SCOPES, Constants.ADDITIONAL_SCOPES, policy, Constants.CLIENT_ID,
//                        Constants.REDIRECT_URL, getUserInfo(), PromptBehavior.Always,
//                        "nux=1&" + Constants.EXTRA_QP,
//                        new AuthenticationCallback<AuthenticationResult>() {
//
//                            @Override
//                            public void onError(Exception exc) {
//                                if (mLoginProgressDialog.isShowing()) {
//                                    mLoginProgressDialog.dismiss();
//                                }
//                                startActivity(new Intent(MainActivity.this,AzureLoginActivity.class));
////                                SimpleAlertDialog.showAlertDialog(MainActivity.this,
////                                        "Failed to get token", exc.getMessage());
//                            }
//
//                            @Override
//                            public void onSuccess(AuthenticationResult result) {
//                                if (mLoginProgressDialog.isShowing()) {
//                                    mLoginProgressDialog.dismiss();
//                                }
//
//                                if (result != null && !result.getToken().isEmpty()) {
//                                    setLocalToken(result);
//                                    updateLoggedInUser();
//                                    getTasks();
//
//                                    MainActivity.sResult = result;
////                                    Toast.makeText(getApplicationContext(), "Token is returned", Toast.LENGTH_SHORT)
////                                            .show();
//
//                                    if (sResult.getUserInfo() != null) {
//                                        name.setText(result.getUserInfo().getName());
//                                        Toast.makeText(getApplicationContext(),
//                                                "User:" + sResult.getUserInfo().getName(), Toast.LENGTH_SHORT)
//                                                .show();
//                                    }
//                                } else {
//                                    //TODO: popup error alert
//                                }
//                            }
//                        });
//            } catch (Exception e) {
//                SimpleAlertDialog.showAlertDialog(MainActivity.this, "Exception caught", e.getMessage());
//            }
////            Toast.makeText(MainActivity.this, TAG + "done", Toast.LENGTH_SHORT).show();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }}
//
//    private void clearSessionCookie() {
//
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.removeSessionCookie();
//        CookieSyncManager.getInstance().sync();
//    }
//
//    private void updateLoggedInUser() {
//        TextView textView = (TextView) findViewById(R.id.userLoggedIn);
//        textView.setText("");
//        if (sResult != null) {
//            if (sResult.getToken() != null)
//                textView.setText(sResult.getUserInfo().getName());
//            else {
//                textView.setText("User with No ID Token");
//            }
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.swash_logo);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        newsLayout=(LinearLayout)findViewById(R.id.news);
        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
            }
        });
        policyLayout=(LinearLayout)findViewById(R.id.policy);
        policyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AboutUs.class));
            }
        });

        trainingLayout=(LinearLayout)findViewById(R.id.training);
        trainingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TrainingActivity.class));
            }
        });
        productLayout=(LinearLayout)findViewById(R.id.product);
        productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProductActivity.class));
            }
        });
        orderLayout=(LinearLayout)findViewById(R.id.order);
        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OrderActivity.class));
            }
        });
        TextView more=(TextView)findViewById(R.id.view_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewsActivity.class));
            }
        });

        Button apply= (Button)findViewById(R.id.applynow);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    dialog = new Dialog(MainActivity.this);
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
                                    startActivity(new Intent(MainActivity.this, Individual_new.class));
                                } else if (selectedId == company.getId()) {
                                    startActivity(new Intent(MainActivity.this, Company_new.class));

                                }
                                dialog.dismiss();

                            }
                        });
//                        dialog.dismiss();
                    }

                });
                dialog.show();
//               startActivity(new Intent(HomeActivity.this, FormMainActivity.class));
            }
        });
//        toolbar.inflateMenu(R.menu.main);
//        toolbar.setOnMenuItemClickListener(this);

//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
////        mRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
//        app = new ArrayList<>();
//        adapter1 = new PartnerAdapter(this, app);

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
//        mRecyclerView1.setLayoutManager(mLayoutManager);
//        mRecyclerView1.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView1.setAdapter(adapter1);
//        setupAdapter1();
//      //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setHasFixedSize(true);
//        setupAdapter();

        // sliding images by suchi


        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
//
//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Banner1",R.drawable.banner01);
        file_maps.put("Banner2",R.drawable.poster3);
        file_maps.put("Banner3",R.drawable.poster1);
        file_maps.put("Banner4", R.drawable.poster4);
        file_maps.put("Banner5", R.drawable.poster5);
        file_maps.put("Banner6", R.drawable.poster2);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }
//



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    // sata

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putBoolean(ORIENTATION, mHorizontal);

    }
//    private void setupAdapter() {
//        List<App> apps = getApps();
//
//        SnapAdapter snapAdapter = new SnapAdapter();
//
////            snapAdapter.addSnap(new Snap(Gravity.CENTER_HORIZONTAL, "Partners", apps));
////            snapAdapter.addSnap(new Snap(Gravity.START, "Products", apps));
//            snapAdapter.addSnap(new Snap(Gravity.END, "Upcoming Products", apps));
////        } else {
////            snapAdapter.addSnap(new Snap(Gravity.CENTER_VERTICAL, "Snap center", apps));
////            snapAdapter.addSnap(new Snap(Gravity.TOP, "Snap top", apps));
//
////
//
////            snapAdapter.addSnap(new Snap(Gravity.BOTTOM, "Snap bottom", apps));
////        }
//
//        mRecyclerView.setAdapter(snapAdapter);
//    }
//    private void setupAdapter1() {
////        List<HApp> apps1 = getApps1();
//        int[] covers = new int[]{
//                R.drawable.product,
//                R.drawable.customer,
////                R.drawable.product3,
//////                R.drawable.product4,
////                R.drawable.product5,
////                R.drawable.product6,
////                R.drawable.product7,
////                R.drawable.product8,
////                R.drawable.product9
//        };
//
//        HApp model = new HApp( covers[0], "Most Trending Products");
//
//      app1.add(model);
//        model= new HApp(covers[1], "Most Profitable Customer");
//app1.add(model);
//
//        adapter1.notifyDataSetChanged();
//    }
//
//    private List<App> getApps() {
//        List<App> apps = new ArrayList<>();
//        apps.add(new App("Product 1", R.drawable.ic_docs_48dp, 4.6f));
//        apps.add(new App("Product 2", R.drawable.ic_slides_48dp, 4.8f));
//        apps.add(new App("Product 3", R.drawable.ic_sheets_48dp, 4.5f));
//        apps.add(new App("Product 4", R.drawable.ic_docs_48dp, 4.2f));
//        apps.add(new App("Product 5", R.drawable.ic_slides_48dp, 4.6f));
//        apps.add(new App("Product 6", R.drawable.ic_sheets_48dp, 3.9f));
//        apps.add(new App("Product 7", R.drawable.ic_docs_48dp, 4.6f));
//        apps.add(new App("Product 8", R.drawable.ic_slides_48dp, 4.2f));
//        apps.add(new App("Product 9", R.drawable.ic_sheets_48dp, 4.2f));
////        apps.add(new App("Product 1", R.drawable.ic_slides_48dp, 4.2f));
////        apps.add(new App("Product 1", R.drawable.ic_docs_48dp, 4.2f));
//        return apps;
//    }
//    private List<HApp> getApps1() {
//        List<HApp> apps1 = new ArrayList<>();
//        apps1.add(new HApp( R.drawable.banner01, "105 Partners"));
//        return apps1;
//    }


//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        if (item.getItemId() == R.id.layoutType) {
//            mHorizontal = !mHorizontal;
//            setupAdapter();
//            item.setTitle(mHorizontal ? "Vertical" : "Horizontal");
//        }
//        return false;
//    }

//
//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }
//
//    /**
//     * Converting dp to pixel
//     */
//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }




//  Azure login



//    private String getUniqueId() {
//        if (sResult != null && sResult.getUserInfo() != null
//                && sResult.getUserInfo().getUniqueId() != null) {
//            return sResult.getUserInfo().getUniqueId();
//        }
//
//        return null;
//    }
//
//    private UserIdentifier getUserInfo() {
//
//        final TextView names = (TextView)findViewById(R.id.userLoggedIn);
//        String name = names.getText().toString();
//        return new UserIdentifier(name, UserIdentifier.UserIdentifierType.OptionalDisplayableId);
//    }
//    private void getTasks() {
//        if (sResult == null || sResult.getToken().isEmpty())
//            return;
//
////        List<String> items = new ArrayList<>();
////        try {
////            items = new HttpService().getAllItems(sResult.getToken());
////        } catch (Exception e) {
////            items = new ArrayList<>();
////        }
////
////        ListView listview = (ListView) findViewById(R.id.listViewToDo);
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_1, android.R.id.text1, items);
////        listview.setAdapter(adapter);
//    }
//
//    private URL getEndpointUrl() {
//        URL endpoint = null;
//        try {
//            endpoint = new URL(Constants.SERVICE_URL);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return endpoint;
//    }
//
//    private void initAppTables() {
//        try {
//            ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
//            listViewToDo.setAdapter(mAdapter);
//
//        } catch (Exception e) {
//            createAndShowDialog(new Exception(
//                    "There was an error creating the Mobile Service. Verify the URL"), "Error");
//        }
//    }
//
//    private void getToken(final AuthenticationCallback callback) {
//
//        String policy = getIntent().getStringExtra("thePolicy");
//
//        // one of the acquireToken overloads
//        mAuthContext.acquireToken(MainActivity.this, Constants.SCOPES, Constants.ADDITIONAL_SCOPES,
//                policy, Constants.CLIENT_ID, Constants.REDIRECT_URL, getUserInfo(),
//                PromptBehavior.Always, "nux=1&" + Constants.EXTRA_QP, callback);
//    }
//
//    private AuthenticationResult getLocalToken() {
//        return Constants.CURRENT_RESULT;
//    }
//
//    private void setLocalToken(AuthenticationResult newToken) {
//
//
//        Constants.CURRENT_RESULT = newToken;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume(); // Always call the superclass method first
//
//        updateLoggedInUser();
//        // User can click logout, it will come back here
//        // It should refresh list again
//        getTasks();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mAuthContext.onActivityResult(requestCode, resultCode, data);
//    }
//
//    /**
//     * Creates a dialog and shows it
//     *
//     * @param exception The exception to show in the dialog
//     * @param title     The dialog title
//     */
//    private void createAndShowDialog(Exception exception, String title) {
//        createAndShowDialog(exception.toString(), title);
//    }
//
//    /**
//     * Creates a dialog and shows it
//     *
//     * @param message The dialog message
//     * @param title   The dialog title
//     */
//    private void createAndShowDialog(String message, String title) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setMessage(message);
//        builder.setTitle(title);
//        builder.create().show();
//    }

}
