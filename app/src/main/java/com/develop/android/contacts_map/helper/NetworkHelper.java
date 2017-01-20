package com.develop.android.contacts_map.helper;

import com.develop.android.contacts_map.interfaces.ResponseInterface;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sakshiagarwal on 18/01/17.
 */

public class NetworkHelper {

    public void getRequestForServer(String url, final ResponseInterface responseInterface)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                responseInterface.onSuccess(jsonArray);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String mString, Throwable e) {
                responseInterface.onFailure(mString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                responseInterface.onFailure(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                responseInterface.onFailure(errorResponse.toString());
            }
        });
    }
}
