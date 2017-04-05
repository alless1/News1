package alless.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    public void next(View view) {
        startActivity(new Intent(this,SendMessageActivity.class));
    }

    /**
     * 一行注释就可以接收到远程的信息
     * @param "远程发送的对象"
     */
    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onThreadMain(MyEvent event) {
        Log.d(TAG, "onMessageEvent: "+Thread.currentThread().getName());
    };
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onThreadPosting(MyEvent event) {
        Log.d(TAG, "onThreadPosting: "+Thread.currentThread().getName());
    };
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onThreadBackground(MyEvent event) {
        Log.d(TAG, "onThreadBackground: "+event.msg+Thread.currentThread().getName());
    };
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onThreadAsync(MyEvent event) {
        Log.d(TAG, "onThreadAsync: "+Thread.currentThread().getName());
    };*/


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent1(MyEvent array) {
        Log.d(TAG, "onEvent1: "+array.msg);
    };
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent2(String i) {
        Log.d(TAG, "onEvent2: "+i);
    };


    @Override
    protected void onDestroy() {
        //取消注册EventBus
        EventBus.getDefault().unregister(this);
    }
}
