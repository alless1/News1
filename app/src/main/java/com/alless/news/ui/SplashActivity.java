package com.alless.news.ui;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.alless.news.R;

/**
 * Created by Administrator on 2017/3/16.
 */

public class SplashActivity extends BaseActivity {

    private ImageView mViewById;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        mViewById = (ImageView) findViewById(R.id.image);
        startAnimation(mViewById);

    }
    public void startAnimation(View v){
        AnimationSet set = new AnimationSet(false);
        RotateAnimation rAnima = new RotateAnimation(0,360);
        ScaleAnimation sAnima = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation aAnima = new AlphaAnimation(0,1);
        set.addAnimation(rAnima);
        set.addAnimation(sAnima);
        set.addAnimation(aAnima);
        set.setDuration(2000);
        v.startAnimation(set);
    }
}
