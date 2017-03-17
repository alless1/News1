package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Leon on 2017/3/17.
 */

public class SettingsTabPage extends TabPage {

    private static final String TAG = "SettingsTabPage";

    public SettingsTabPage(Context context) {
        super(context);
    }

    public SettingsTabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMenuSwitch(int position) {
        Log.d(TAG, "onMenuSwitch: " + position);
    }
}
