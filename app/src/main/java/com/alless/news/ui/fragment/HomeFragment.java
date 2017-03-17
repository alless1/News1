package com.alless.news.ui.fragment;

import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.alless.news.R;
import com.alless.news.widget.GovAffairsTabPage;
import com.alless.news.widget.HomeTabPage;
import com.alless.news.widget.NewsCenterTabPage;
import com.alless.news.widget.SettingsTabPage;
import com.alless.news.widget.SmartServiceTabPage;
import com.alless.news.widget.TabPage;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/16.
 */

public class HomeFragment extends BaseFragment {


    /* @BindView(R.id.tab_page_container)
     FrameLayout mTabPageContainer;*/
    @BindView(R.id.tabs_container)
    RadioGroup mRadioGroup;
    @BindView(R.id.tab_page_container)
    FrameLayout mTabPageContainer;
    //类似HashMap Integer 到 Object的映射
    private SparseArray<TabPage> mTabPageCache = new SparseArray<TabPage>();//TabPage的内存缓存
    private TabPage mTabPageView;
    // private SparseArray<TabPage> mTabPageCache = new SparseArray<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        super.init();

        //设置radio group点击事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //查找内存缓存中有没有缓存TabPage, 如果有则使用缓存的TabPage，没有就创建一个
                if (mTabPageCache.get(checkedId) != null) {
                    mTabPageView = mTabPageCache.get(checkedId);
                    //  Log.d(TAG, "从缓存中获取TabPage");
                    // Toast.makeText(getContext(), "从缓存中获取TabPage", Toast.LENGTH_SHORT).show();
                } else {
                    //创建新的TabPage
                    mTabPageView = createTabPage(checkedId);
                }
                mTabPageContainer.removeAllViews();
                mTabPageContainer.addView(mTabPageView);

                //通知外界发生切换事件
                if (mOnHomeChangeListener != null) {
                    mOnHomeChangeListener.onTabSwitch(checkedId);
                }
            }
        });

        //默认check到首页,放在后面会触发一次初始化init.
        mRadioGroup.check(R.id.tab_home);


    }

    private TabPage createTabPage(int checkedId) {
        //添加TabPage到FrameLayout里面
//        TabPage tabPage = new TabPage(getContext());
        //首页和设置中心没有标题栏的菜单按钮
        TabPage tabPage = null;
        switch (checkedId) {
            case R.id.tab_home:
                tabPage = new HomeTabPage(getContext());
                tabPage.hideMenu();
                tabPage.setTitle("首页");
                break;
            case R.id.tab_news_center:
                tabPage = new NewsCenterTabPage(getContext());
                tabPage.showMenu();
                tabPage.setTitle("新闻中心");
                break;
            case R.id.tab_smart_service:
                tabPage = new SmartServiceTabPage(getContext());
                tabPage.showMenu();
                tabPage.setTitle("智慧服务");
                break;
            case R.id.tab_gov_affairs:
                tabPage = new GovAffairsTabPage(getContext());
                tabPage.showMenu();
                tabPage.setTitle("政务");
                break;
            case R.id.tab_settings:
                tabPage = new SettingsTabPage(getContext());
                tabPage.hideMenu();
                tabPage.setTitle("设置中心");
                break;
        }
        //创建一个新的TabPage保存到内存缓存
        mTabPageCache.put(checkedId, tabPage);

        //设置menu的点击事件
        tabPage.setOnTabPageChangeListener(new TabPage.OnTabPageChangeListener() {
            @Override
            public void onTabPageMenuClick() {
                //双层回调,由table里面监听的事件传到homefragment,再传出去.
                if (mOnHomeChangeListener != null) {
                    mOnHomeChangeListener.onTabPageMenuClick();
                }
            }
        });

        return tabPage;

    }

    private OnHomeChangeListener mOnHomeChangeListener;

    /**
     * 传递menu点击事件给tabpage
     */
    public void onMenuSwitch(int position) {
        mTabPageView.onMenuSwitch(position);
    }


    /**
     * 通知外界发生tab按钮的切换
     */
    public interface OnHomeChangeListener {

        /**
         * tab按钮切换的回调
         *
         * @param checkId 切换到radio button的id
         */
        void onTabSwitch(int checkId);


        /**
         * TabPage菜单按钮的点击事件
         */
        void onTabPageMenuClick();
    }

    public void setOnHomeChangeListener(OnHomeChangeListener listener) {
        mOnHomeChangeListener = listener;
    }
}
