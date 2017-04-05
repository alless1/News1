package com.alless.news.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alless.news.App.Constant;
import com.alless.news.R;
import com.alless.news.bean.CategoriesBean;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsPage extends RelativeLayout {

    @BindView(R.id.indicator)
    TabPageIndicator mIndicator;
    @BindView(R.id.pager)
    ViewPager mPager;
    private static final String TAG = "NewsPage";
    private CategoriesBean.DataBean mData;
  // private String[] CONTENT = {"北京","中国","国际","体育","生活","旅游","科技","军事","新闻","八卦","军事","新闻","八卦","军事","新闻","八卦"};
    public NewsPage(Context context) {
        this(context, null);
    }

    public NewsPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_news_page, this);
        ButterKnife.bind(this, this);
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mPager);
       // mIndicator.setVisibility(INVISIBLE);
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            if (mData != null) {
                return mData.getChildren().size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*TextView tv = new TextView(getContext());
            tv.setText(CONTENT[position]);
            container.addView(tv);
            return tv;*/
            NewsPullToRefreshListView pullToRefreshListView = new NewsPullToRefreshListView(getContext());
            String url = Constant.HOST + mData.getChildren().get(position).getUrl();
            pullToRefreshListView.setUrl(url);
            container.addView(pullToRefreshListView);
            return pullToRefreshListView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * 返回页面的标题 TabPageIndicator会调用到这个方法获取显示的标题
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mData.getChildren().get(position).getTitle();
        }
    };


    public void setData(CategoriesBean.DataBean data) {
        mData = data;
        //通知TabPageIndicator刷新数据
        mIndicator.notifyDataSetChanged();
        //通知ViewPager刷新
        mPagerAdapter.notifyDataSetChanged();
    }
}
