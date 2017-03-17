package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Leon on 2017/3/17.
 */

public class GovAffairsTabPage extends TabPage {

    private static final String TAG = "GovAffairsTabPage";

    public GovAffairsTabPage(Context context) {
        super(context);
    }

    public GovAffairsTabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMenuSwitch(int position) {
        Log.d(TAG, "onMenuSwitch: " + position);
    }
}
