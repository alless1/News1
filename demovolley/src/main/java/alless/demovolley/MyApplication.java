package alless.demovolley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 初始化创建一个全局的请求队列
 */

public class MyApplication extends Application {
    private static RequestQueue mQueues;
    @Override
    public void onCreate() {
        super.onCreate();
        mQueues  = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getQueue() {
        return mQueues;
    }
}
