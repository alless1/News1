package com.alless.news.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/3/20.
 */

public class NetworkManager {

    private static RequestQueue sRequestQueue;
    private static final int MAX_CACHE_SIZE = 5 * 1024 * 1024;
    private static ImageLoader sImageLoader;

    /**
     * 初始化全局的请求队列
     */
    public static void init(Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
        sImageLoader = new ImageLoader(sRequestQueue, new SmartBJImageCache(MAX_CACHE_SIZE));
    }

    /**
     * 发送网络请求的封装
     * @param request
     */
    public static void sendRequest(Request request) {
        sRequestQueue.add(request);
    }
    private static class SmartBJImageCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        public SmartBJImageCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }

        public static ImageLoader getImageLoader() {
            return sImageLoader;
        }
    }
    public static ImageLoader getImageLoader() {
        return sImageLoader;
    }
}

