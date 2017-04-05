package com.alless.news.App;

import android.app.Application;

import com.alless.news.network.NetworkManager;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/3/20.
 */

public class SmartBJApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //用application上下文来初始化全局的变量
        NetworkManager.init(getApplicationContext());
        ShareSDK.initSDK(this);
    }
}
