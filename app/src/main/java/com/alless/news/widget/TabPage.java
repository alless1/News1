package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alless.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/17.
 */

public class TabPage extends RelativeLayout {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.menu)
    ImageView mMenu;
    private static final String TAG = "TabPage";
    @BindView(R.id.tab_frame)
    FrameLayout mTabFrameLayout;
    private OnTabPageChangeListener mOnTabPageChangeListener;

    public TabPage(Context context) {
        this(context, null);
    }

    public TabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_tabpage, this);
        ButterKnife.bind(this, this);
    }

    public void hideMenu() {
        mMenu.setVisibility(View.GONE);
    }

    public void showMenu() {
        mMenu.setVisibility(View.VISIBLE);
    }

    public void setTitle(String title) {
        mTitle.setText(title);

    }

    /**
     * 左侧菜单选项切换事件, 让子类去实现
     *
     * @param position 切换的位置
     */
    public void onMenuSwitch(int position) {
        Log.d(TAG, "onMenuSwitch: " + position);

    }

    @OnClick(R.id.menu)
    public void onClick() {
        //通知外界menu被点击了
        if (mOnTabPageChangeListener != null)
            mOnTabPageChangeListener.onTabPageMenuClick();
    }

    /**
     * 通知外界TabPage里面发生变化
     */
    public interface OnTabPageChangeListener {
        //菜单按钮的点击事件
        void onTabPageMenuClick();
    }

    public void setOnTabPageChangeListener(OnTabPageChangeListener listener) {
        mOnTabPageChangeListener = listener;
    }
}
