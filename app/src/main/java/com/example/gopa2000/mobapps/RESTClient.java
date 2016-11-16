package com.example.gopa2000.mobapps;

import com.loopj.android.http.*;

/**
 * Created by gopa2000 on 11/16/16.
 */

public class RESTClient {
    private static final String BASE_URL = "https://ancient-plains-84917.herokuapp.com/";

    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        asyncHttpClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        asyncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void syncGet(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        syncHttpClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void syncPost(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        syncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }

}
