package com.kencloud.partner.user.network_util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Kranti on 18/3/2016.
 */
public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }


    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag("App");
        getRequestQueue().add(req);
    }

    }

