package com.alless.news.ui.fragment;

import android.widget.RadioGroup;

import com.alless.news.R;
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
    @BindView(R.id.tabpage_view)
    TabPage mTabPageView;
   // private SparseArray<TabPage> mTabPageCache = new SparseArray<>();

    @Override
    public int getLayoutResId() {
        return R.layout.home_frame;
    }

    @Override
    protected void init() {
        super.init();

        //设置menu的点击事件
        mTabPageView.setOnTabPageChangeListener(new TabPage.OnTabPageChangeListener() {
            @Override
            public void onTabPageMenuClick() {
                //双层回调,由table里面监听的事件传到homefragment,再传出去.
                mOnHomeChangeListener.onTabPageMenuClick();
            }
        });
        //设置radio group点击事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //查找内存缓存中有没有缓存TabPage, 如果有则使用缓存的TabPage，没有就创建一个
     /*           TabPage tabPage = null;
                if (mTabPageCache.get(checkedId) != null) {
                    tabPage = mTabPageCache.get(checkedId);
//                  //  Log.d(TAG, "从缓存中获取TabPage");
                    Toast.makeText(getContext(), "从缓存中获取TabPage", Toast.LENGTH_SHORT).show();
                } else {
                    //创建新的TabPage
                    tabPage = createTabPage(checkedId);
                }*/
                //移除原来FrameLayout里面的所有TabPage
               /* mTabPageContainer.removeAllViews();
                mTabPageContainer.addView(tabPage);*/
                switch (checkedId) {
                    case R.id.tab_home:
                        mTabPageView.hideMenu();
                        mTabPageView.setTitle("首页");
                        break;
                    case R.id.tab_news_center:
                        mTabPageView.showMenu();
                        mTabPageView.setTitle("新闻中心");
                        break;
                    case R.id.tab_smart_service:
                        mTabPageView.showMenu();
                        mTabPageView.setTitle("智慧服务");
                        break;
                    case R.id.tab_gov_affairs:
                        mTabPageView.showMenu();
                        mTabPageView.setTitle("政务");
                        break;
                    case R.id.tab_settings:
                        mTabPageView.hideMenu();
                        mTabPageView.setTitle("设置中心");
                        break;
                }

                //通知外界发生切换事件
                if (mOnHomeChangeListener != null) {
                    mOnHomeChangeListener.onTabSwitch(checkedId);
                }
            }
        });
        //默认check到首页
        mRadioGroup.check(R.id.tab_home);
    }

/*    private TabPage createTabPage(int checkedId) {
        //添加TabPage到FrameLayout里面
        TabPage tabPage = new TabPage(getContext());
        //首页和设置中心没有标题栏的菜单按钮
        switch (checkedId) {
            case R.id.tab_home:
                tabPage.hideMenu();
                tabPage.setTitle("首页");
                break;
            case R.id.tab_news_center:
                tabPage.setTitle("新闻中心");
                break;
            case R.id.tab_smart_service:
                tabPage.setTitle("智慧服务");
                break;
            case R.id.tab_gov_affairs:
                tabPage.setTitle("政务");
                break;
            case R.id.tab_settings:
                tabPage.hideMenu();
                tabPage.setTitle("设置中心");
                break;
        }
        //创建一个新的TabPage保存到内存缓存
      //  mTabPageCache.put(checkedId, tabPage);
//        Toast.makeText(getContext(), "创建新的TabPage", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "创建新的TabPage");

        //在创建TabPage时设置TabPage的监听器
        tabPage.setOnTabPageChangeListener(new TabPage.OnTabPageChangeListener() {
            @Override
            public void onTabPageMenuClick() {
                Toast.makeText(getContext(), "事件传递到了HomeFragment", Toast.LENGTH_SHORT).show();
                //通知到HomeFragment外界发生菜单按钮的点击事件
                if (mOnHomeChangeListener != null) {
                    mOnHomeChangeListener.onTabPageMenuClick();
                }
            }
        });

        return tabPage;

    }*/

    private OnHomeChangeListener mOnHomeChangeListener;


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
