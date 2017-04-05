package com.alless.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ShareUtils {
    private static final String FILE_NAME = "NewsConfig";

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences share = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        share.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences share = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return share.getBoolean(key, false);
    }
}
