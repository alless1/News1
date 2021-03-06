package com.alless.news.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.alless.news.R;
import com.alless.news.utils.ShareUtils;

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
        set.setAnimationListener(mListener);
        v.startAnimation(set);

    }
    private Animation.AnimationListener mListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(ShareUtils.getBoolean(SplashActivity.this,"tutorialEnd")){
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }else{
                startActivity(new Intent(SplashActivity.this,Tutorial.class));
            }
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
