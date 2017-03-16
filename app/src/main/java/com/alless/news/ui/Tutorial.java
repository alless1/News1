package com.alless.news.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alless.news.MainActivity;
import com.alless.news.R;
import com.alless.news.utils.ShareUtils;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Administrator on 2017/3/16.
 */

public class Tutorial extends BaseActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] mImageIds = {R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3};
    private Button mButton;
    private CirclePageIndicator mIndicator;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tutorial;
    }

    @Override
    public void init() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mButton = (Button) findViewById(R.id.btn_enter);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mPager.setAdapter(mAdapter);
        //关联开源ViewPagerIndicator
        mIndicator.setViewPager(mPager);
        mPager.addOnPageChangeListener(mPageChangeListener);
        mButton.setOnClickListener(this);
    }
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {
                mButton.setVisibility(View.VISIBLE);
            }else{
                mButton.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(Tutorial.this);
            iv.setImageResource(mImageIds[position]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
        ShareUtils.setBoolean(this,"tutorialEnd",true);
        finish();
    }
}
