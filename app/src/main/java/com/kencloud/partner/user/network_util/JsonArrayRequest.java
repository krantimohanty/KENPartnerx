package com.kencloud.partner.user.network_util;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Kranti on 18/3/2016.
 */
public class JsonArrayRequest extends JsonRequest<JSONArray> {


    public JsonArrayRequest( String url,JSONObject param, Response.Listener<JSONArray>
            listener, Response.ErrorListener errorListener) {
        super(Method.POST, url,param.toString(), listener, errorListener);
    }






   /* @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        return null;
    }*/

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}
