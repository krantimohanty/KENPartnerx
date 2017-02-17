package com.kencloud.partner.user;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.widget.IconTextView;
import com.kencloud.partner.user.GPS.ConnectionDetector;
import com.kencloud.partner.user.GPS.CustomPreference;
import com.kencloud.partner.user.app_adapter.NotificationAdapter;
import com.kencloud.partner.user.app_model.NotificationDataModel;
import com.kencloud.partner.user.app_util.Custom_app_util;
import com.kencloud.partner.user.app_util.OnLoadMoreListener;
import com.kencloud.partner.user.network_util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.kencloud.partner.user.R.id.url;

public class NotificationActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<NotificationDataModel> notiFeedModel = new ArrayList<>();
    NotificationAdapter notificationAdapter;
    private ContentLoadingProgressBar pgBar;
    private int page_count = 20;
    private int row_id = 0;
    protected Handler loadMorehandler;
    private String LoadStatus = "DEFAULT";
    private int type = 1;
    private int user_id;
    private IconTextView batchIcon;
    private AppCompatTextView heading1;
    private AppCompatTextView heading2;
    private SwipeRefreshLayout ViewSwipe;
    private SwipeRefreshLayout layoutSwipe;
    String url="http://kenmasterapi.azurewebsites.net/api/CompanyPersonalInformation";

    private AppCompatTextView news_details;
    //setting data to view elements


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (CustomPreference.with(NotificationActivity.this).getInt("user_id", 0) == 0) {
            user_id = 0;
        } else {
            user_id = CustomPreference.with(NotificationActivity.this).getInt("user_id", 0);
        }

        //progressbar
        pgBar = (ContentLoadingProgressBar) findViewById(R.id.progress);

        batchIcon = (IconTextView) findViewById(R.id.batchIcon);
        heading1 = (AppCompatTextView) findViewById(R.id.heading1);
        heading2 = (AppCompatTextView) findViewById(R.id.heading2);
        ViewSwipe = (SwipeRefreshLayout) findViewById(R.id.ViewSwipe);
        layoutSwipe = (SwipeRefreshLayout) findViewById(R.id.layoutSwipe);


        //recylcerview
        mRecyclerView = (RecyclerView) findViewById(R.id.notification_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        notificationAdapter = new NotificationAdapter(notiFeedModel, NotificationActivity.this, mRecyclerView);
        mRecyclerView.setAdapter(notificationAdapter);
        //calling get_notification service


        callServiceMethod();
//        callingUpdates();

        ViewSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                row_id = 0;
                LoadStatus = "DEFAULT";
                callingUpdates();
            }
        });

        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                row_id = 0;
                LoadStatus = "DEFAULT";
                callingUpdates();
            }
        });

        /* Load More Items on Infinite Scroll*/
        loadMorehandler = new Handler();
        notificationAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                notiFeedModel.add(null);
                notificationAdapter.notifyItemInserted(notiFeedModel.size() - 1);

                loadMorehandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notiFeedModel.remove(null);
                        notificationAdapter.notifyItemRemoved(notiFeedModel.size());
                        callingMoreUpdates();
                        notificationAdapter.setLoaded();
                    }
                }, 2000);
            }
        });
    }


    public void callServiceMethod() {
        //networkUnavailable.setVisibility(View.GONE);
        if (ConnectionDetector.staticisConnectingToInternet(getApplicationContext())) {
            callingUpdates();
        } else {
            loadOfflineLocationData(user_id, row_id, page_count);
            Snackbar.make(findViewById(android.R.id.content), "Network connection is not available.", Snackbar.LENGTH_LONG).show();
            pgBar.setVisibility(View.GONE);
        }
    }

    private void callingUpdates() {

        layoutSwipe.setVisibility(View.GONE);
        //getUpcoming wish List
        if (ConnectionDetector.staticisConnectingToInternet(NotificationActivity.this)) {
            batchIcon.setVisibility(View.GONE);
            heading1.setVisibility(View.GONE);
            heading2.setVisibility(View.GONE);
//            clearData();
            getNewNotification(row_id, page_count, user_id);
        } else {
//            clearData();
            batchIcon.setText("{ic-noconnection 70dp #8C8989}");
            heading1.setText("Can't Connect");
            heading2.setText("Please swipe top to refresh");
            batchIcon.setVisibility(View.VISIBLE);
            heading1.setVisibility(View.VISIBLE);
            heading2.setVisibility(View.VISIBLE);
            pgBar.setVisibility(View.GONE);
            ViewSwipe.setRefreshing(false);
            layoutSwipe.setVisibility(View.VISIBLE);
            layoutSwipe.setRefreshing(false);
        }
    }

    private void callingMoreUpdates() {
        row_id = row_id + page_count;
        LoadStatus = "MORE";
        callingUpdates();
    }

    public void getNewNotification(int rowId, int pageCount, int user_id) {

        pgBar.setVisibility(View.VISIBLE);
        /*JSONObject params = new JSONObject();
        try {
            params.put("method", "getNotifications");
            params.put("user_id", user_id);
            params.put("row_id", rowId);
            params.put("page_count", pageCount);//for newNotification  news
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Log.d("Notification_url",url + "/getNotifications1/" + user_id + "/" + rowId + "/" + pageCount);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url + "/getNotifications1/" + user_id + "/" + rowId + "/" + pageCount, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("NotificationResult", response.toString());
                // getNotificationsResult
                try {
                    JSONArray jsonArray = response.getJSONArray("getNotificationsResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<NotificationDataModel>>() {
                    }.getType();
                    List<NotificationDataModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);

                    if (tempArrayList.size() == 0) {
                        Custom_app_util.customSnackbar("No more information", NotificationActivity.this, false, "");
                    }
                    if (LoadStatus.equals("DEFAULT")) {
                        notiFeedModel.clear();
                    }
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        //String  link= jsonArray.getJSONObject(i).getString("link").toString();
                        notiFeedModel.add(tempArrayList.get(i));
                    }

                    notificationAdapter.notifyDataSetChanged();
                    pgBar.setVisibility(View.GONE);

                    if (notiFeedModel.isEmpty()) {
                        notiFeedModel.clear();
                        notificationAdapter.notifyDataSetChanged();
                        batchIcon.setText("{ic-noconnection 70dp #8C8989}");
                        heading2.setText("No updates.");
                        batchIcon.setVisibility(View.VISIBLE);
                        heading1.setVisibility(View.GONE);
                        heading2.setVisibility(View.VISIBLE);
                        layoutSwipe.setRefreshing(false);
                        layoutSwipe.setVisibility(View.VISIBLE);

                    } else {
                        batchIcon.setVisibility(View.GONE);
                        heading1.setVisibility(View.GONE);
                        heading2.setVisibility(View.GONE);
                        layoutSwipe.setRefreshing(false);
                        layoutSwipe.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    notiFeedModel.clear();
                    notificationAdapter.notifyDataSetChanged();
                    batchIcon.setText("{ic-noconnection 70dp #8C8989}");
                    heading1.setText("Something Went wrong");
                    heading2.setText("Please try after sometimes.");
                    batchIcon.setVisibility(View.VISIBLE);
                    heading1.setVisibility(View.VISIBLE);
                    heading2.setVisibility(View.VISIBLE);
                    pgBar.setVisibility(View.GONE);
                    layoutSwipe.setRefreshing(false);
                    layoutSwipe.setVisibility(View.VISIBLE);
                }

                ViewSwipe.setRefreshing(false);
                //binding data to recycleview
                // notificationAdapter = new NotificationAdapter(notiFeedModel, getActivity(), mRecyclerView);
                //Applying animation
                //ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(notifiadapter);
                // pgBar.setVisibility(View.GONE);
                //mRecyclerView.setAdapter(notificationAdapter);


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                notiFeedModel.clear();
                notificationAdapter.notifyDataSetChanged();
                batchIcon.setText("{ic-noconnection 70dp #8C8989}");
                heading1.setText("Something Went wrong");
                heading2.setText("Please try after sometimes.");
                batchIcon.setVisibility(View.VISIBLE);
                heading1.setVisibility(View.VISIBLE);
                heading2.setVisibility(View.VISIBLE);
                pgBar.setVisibility(View.GONE);
                ViewSwipe.setRefreshing(false);
                layoutSwipe.setVisibility(View.VISIBLE);
                layoutSwipe.setRefreshing(false);

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

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);

    }

    private Context getActivity() {
        return getApplicationContext();
    }

    //Goto the HomePage

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();
        finish();*/
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            finish();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    //offline data store
    public void loadOfflineLocationData(int userid, int rowId, int pageCount) {
        String url1="http://kenmasterapi.azurewebsites.net/api/CompanyPersonalInformation";
        Cache cache = VolleySingleton.getInstance(NotificationActivity.this).getRequestQueue().getCache();
        //Url
        String url = url1 + "/getNotifications1/" + userid + "/" + rowId + "/" + pageCount;
        Cache.Entry entry = cache.get(url);
        Log.d("URL", url);
        Log.d("JSON DATA:", entry + "");
//        cache.invalidate(url, true);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    JSONArray locDt = new JSONObject(data).getJSONArray("getNotificationsResult");

                    Gson converter = new Gson();
                    Type type = new TypeToken<List<NotificationDataModel>>() {
                    }.getType();
                    List<NotificationDataModel> tempArrayList = converter.fromJson(String.valueOf(locDt), type);

                    if (tempArrayList.size() == 0) {
                        Custom_app_util.customSnackbar("No more information", NotificationActivity.this, false, "");
                    }
                    if (LoadStatus.equals("DEFAULT")) {
                        notiFeedModel.clear();
                    }
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        //String  link= jsonArray.getJSONObject(i).getString("link").toString();
                        notiFeedModel.add(tempArrayList.get(i));
                    }

                    mRecyclerView.setAdapter(notificationAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // linearLayout.setVisibility(View.VISIBLE);
        }
    }
}
