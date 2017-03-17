package com.alless.news.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.alless.news.R;
import com.alless.news.ui.fragment.HomeFragment;
import com.alless.news.ui.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements HomeFragment.OnHomeChangeListener, MenuFragment.OnMenuChangeListener {

    private MenuFragment mMenuFragment = new MenuFragment();;
    private HomeFragment mHomeFragment = new HomeFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        initLeftMenu();
        initContent();
        initSlidingMenu();
        init();
    }





    /**
     * 初始化内容区域的布局
     */
    private void initContent() {
        setContentView(R.layout.content_frame);//设置内容布局

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mHomeFragment)
                .commit();
    }

    /**
     * 配置侧滑菜单属性
     */
    private void initSlidingMenu() {
        SlidingMenu sm = getSlidingMenu();
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//侧滑菜单拉出后内容区在屏幕中的宽度
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//边缘拉出
        sm.setMode(SlidingMenu.LEFT);//只从左边拉出侧滑菜单
    }
    /**
     * 初始化左边菜单布局
     */
    private void initLeftMenu() {
        // set the Behind View
        setBehindContentView(R.layout.menu_frame);// 设置左边菜单布局
        FragmentTransaction t = this.getSupportFragmentManager()
                .beginTransaction();

        t.replace(R.id.menu_frame, mMenuFragment);
        t.commit();
    }

    private void init() {
        mHomeFragment.setOnHomeChangeListener(this);
        mMenuFragment.setOnMenuChangeListener(this);
    }

    /**
     * home页的监听事件,tab切换
     */
    @Override
    public void onTabSwitch(int checkId) {
        //首页和设置中心拉不出侧滑菜单
        if (checkId == R.id.tab_home || checkId == R.id.tab_settings) {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//不能拉
        } else {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//从边缘拉出
        }
    }

    /**
     * home页的监听事件,title的menu被点击
     */

    @Override
    public void onTabPageMenuClick() {
        //打开或者关闭侧滑菜单
        getSlidingMenu().toggle();
    }

    /**
     * menu页的监听事件
     */
    @Override
    public void onMenuItemSwitch(int position, boolean isSwitch) {
        //关闭侧滑菜单
        getSlidingMenu().toggle();
        //如果真的发生了切换， 才告诉HomeFragment发生了菜单切换事件
        if (isSwitch) {
            //switch到哪个页面
            mHomeFragment.onMenuSwitch(position);
        }
    }
}
