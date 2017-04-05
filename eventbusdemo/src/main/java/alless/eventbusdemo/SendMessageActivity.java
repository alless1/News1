package alless.eventbusdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/3/23.
 */

public class SendMessageActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_activity);
    }

    public void mainThread(View view) {
        EventBus.getDefault().post(new MyEvent("MyEvent"));

    }

    public void subThread(View view) {
        new Thread(){
            @Override
            public void run() {
                EventBus.getDefault().post(new MyEvent("MyEvent"));
            }
        }.start();

    }

    public void event1(View view) {
        EventBus.getDefault().post(new MyEvent("123"));
    }

    public void event2(View view) {
        EventBus.getDefault().post("abc");
    }
}

