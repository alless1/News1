package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alless.news.App.Constant;
import com.alless.news.bean.CategoriesBean;
import com.alless.news.network.NetworkListener;
import com.alless.news.network.NetworkManager;
import com.alless.news.network.SmartBJRequest;
import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2017/3/17.
 */

public class NewsCenterTabPage extends TabPage {
    private CategoriesBean mCategoriesBean;
    private static final String TAG = "NewsCenterTabPage";
    private View mView;

    public NewsCenterTabPage(Context context) {
        super(context);
    }

    public NewsCenterTabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMenuSwitch(int position) {
        mView = null;
        //切换到组图，就要显示组图切换按钮，其他情况隐藏切换按钮
        if (position == 2) {
            mSwitchPhotos.setVisibility(VISIBLE);
        } else {
            mSwitchPhotos.setVisibility(GONE);
        }
       // Toast.makeText(getContext(), "newsCenter收到" + position,Toast.LENGTH_SHORT ).show();
        switch (position) {
            case 0:
                //Toast.makeText(getContext(), "新闻被点击了", Toast.LENGTH_SHORT).show();
//                textView.setText("新闻");
                NewsPage newsPage = new NewsPage(getContext());
                newsPage.setData(mCategoriesBean.getData().get(0));//设置新闻页面的数据
                mView = newsPage;
                break;
            case 1:
                TextView textView = new TextView(getContext());
                textView.setText("专题");
                mView = textView;
                break;
            case 2:
                PhotosPage photosPage = new PhotosPage(getContext());//组图
                photosPage.setData(mCategoriesBean.getData().get(2).getUrl());
                mView = photosPage;
                break;
            case 3:
                TextView hudong = new TextView(getContext());
                hudong.setText("互动");
                mView = hudong;
                break;
        }
        mTabFrameLayout.removeAllViews();
        mTabFrameLayout.addView(mView);
    }

    /**
     * 实现父类的方法，加载新闻中心的网络数据
     */
    @Override
    public void loadDataFromServer() {
        Log.d(TAG, "loadDataFromServer: 执行了吗?");
        String  url = Constant.HOST+"/categories.json";
        //创建一个网络请求
        SmartBJRequest<CategoriesBean> smartBJRequest = new SmartBJRequest<CategoriesBean>(CategoriesBean.class, url, mListener);
        //发送网络请求
//        Volley.newRequestQueue(getContext()).add(smartBJRequest);
        NetworkManager.sendRequest(smartBJRequest);
    }

    private NetworkListener<CategoriesBean> mListener = new NetworkListener<CategoriesBean>() {

        /**
         * 在主线程回调onResponse
         * @param response
         */
        @Override
        public void onResponse(CategoriesBean response) {
            mCategoriesBean = response;//保存网络请求结果
            onMenuSwitch(0);//网络数据获取后，默认切换到新闻页面
        }


        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), "网络失败", Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * 事件发生的前提是已经切换到了组图页面
     * @param isList 是否是切换到列表样式 true表示要切换到列表ListView， false切换到GridView
     */
    @Override
    protected void onSwitchPhoto(boolean isList) {
        Log.d(TAG, "onSwitchPhoto: " + isList);
        //将事件传递给PhotosPage
        ((PhotosPage)mView).switchPhotoStyle(isList);
    }
}
