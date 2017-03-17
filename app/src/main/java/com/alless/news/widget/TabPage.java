package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alless.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/17.
 */

public class TabPage extends RelativeLayout {
    @BindView(R.id.menu)
    ImageView mMenu;
    @BindView(R.id.title)
    TextView mTitle;
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

    public void setTitle(String title) {
        mTitle.setText(title);

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
