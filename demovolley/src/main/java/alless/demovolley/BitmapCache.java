package alless.demovolley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2017/3/19.
 */

public class BitmapCache implements ImageLoader.ImageCache {
    private static final String TAG = "BitmapCache";
    //LruCache是基于内存的缓存类
    private LruCache<String,Bitmap> lruCache;
    //LruCache的最大缓存大小
    private int max = 5 * 1024 * 1024;
    public  BitmapCache(){
        lruCache = new LruCache<String,Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
