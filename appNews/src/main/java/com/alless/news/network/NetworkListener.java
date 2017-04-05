package com.alless.news.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * 用一个自定义接口替代Response的两个回调接口.
 */

public class NetworkListener<T> implements Response.Listener<T>,Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(T response) {

    }
}
