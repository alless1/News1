package com.alless.news;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.alless.news.ui.fragment.HomeFragment;
import com.alless.news.ui.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    private MenuFragment mMenuFragment;
    private HomeFragment mHomeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        initLeftMenu();
        initContent();
        initSlidingMenu();
    }

    /**
     * 初始化左边菜单布局
     */
    private void initLeftMenu() {
        // set the Behind View
        setBehindContentView(R.layout.menu_frame);// 设置左边菜单布局
        FragmentTransaction t = this.getSupportFragmentManager()
                .beginTransaction();
        mMenuFragment = new MenuFragment();
        t.replace(R.id.menu_frame, mMenuFragment);
        t.commit();
    }

    /**
     * 初始化内容区域的布局
     */
    private void initContent() {
        setContentView(R.layout.home_frame);//设置内容布局
        mHomeFragment = new HomeFragment();
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


}
