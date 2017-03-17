package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HomeTabPage extends TabPage {
    private static final String TAG = "HomeTabPage";
    public HomeTabPage(Context context) {
        super(context);
    }

    public HomeTabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMenuSwitch(int position) {
        Log.d(TAG, "onMenuSwitch: " + position);
    }
}
