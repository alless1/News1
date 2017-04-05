package com.alless.news.network;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

/**
 * 封装一个Request,Get请求,省略不用的构造参数.
 */

public class SmartBJRequest<T> extends JsonRequest<T> {
    private Gson mGson = new Gson();
    private Class<T> mClass;
    private static final String TAG = "SmartBJRequest";
    public SmartBJRequest(Class classz, String url, NetworkListener<T> listener) {
        super(Method.GET, url, null, listener, listener);
        mClass = classz;
    }
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String request = new String(response.data,PROTOCOL_CHARSET);
            T bean = mGson.fromJson(request, mClass);
           //缓存相关字段， 里面包含缓存过期时间
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);//解析网络响应中缓存头
            //返回解析后的结果
           return Response.success(bean, cacheEntry);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


}
