package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alless.news.R;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.switch_photos)
    ImageView mSwitchPhotos;
    private OnTabPageChangeListener mOnTabPageChangeListener;
    private boolean isList = true; //当前是列表样式, 默认是列表样式

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
        //Log.d(TAG, "onMenuSwitch: " + position);

    }

    @OnClick({R.id.menu,R.id.switch_photos})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu:
                //通知外界menu被点击了
                /*if (mOnTabPageChangeListener != null)
                    mOnTabPageChangeListener.onTabPageMenuClick();*/
                //使用EventBus发送通知
                EventBus.getDefault().post("menu被点击了");
                break;
            case R.id.switch_photos:
                isList = !isList;//当点击的时候切换样式
                if (isList) {
                    //如果是当前是列表样式, 显示网格图片
                    mSwitchPhotos.setImageResource(R.mipmap.icon_pic_grid_type);
                } else {
                    //如果是网格样式，GridView，显示列表图片
                    mSwitchPhotos.setImageResource(R.mipmap.icon_pic_list_type);
                }
                //通知切换组图内部ListView和GridView
                onSwitchPhoto(isList);
                break;
        }

    }
    /**
     * 切换组图样式 目前只需要子类NewsCenterTabPage处理
     * @param isList 是否是切换到列表样式
     */
    protected void onSwitchPhoto(boolean isList) {

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

    /**
     * 因为每个页面都可能要去加载数据，在基类封装， 由子类实现各自加载网络数据
     */
    public void loadDataFromServer() {
    }
}
