package com.alless.news.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alless.news.App.Constant;
import com.alless.news.R;
import com.alless.news.bean.NewsListBean;
import com.alless.news.network.NetworkListener;
import com.alless.news.network.NetworkManager;
import com.alless.news.network.SmartBJRequest;
import com.alless.news.ui.activity.NewsDetailActivity;
import com.alless.news.utils.ShareUtils;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.itheima.pulltorefreshlib.PullToRefreshBase;
import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.leon.loopviewpagerlib.FunBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 2017/3/17.
 */

public class NewsPullToRefreshListView extends PullToRefreshListView {

   // private int[] imageResIds = {R.mipmap.icon_1, R.mipmap.icon_2, R.mipmap.icon_3, R.mipmap.icon_4, R.mipmap.icon_5};
    private FunBanner mFunBanner;
    private String mUrl;
    private NewsListBean mNewsListbean;
    private String mMore;

    public NewsPullToRefreshListView(Context context) {
        this(context, null);
    }

    public NewsPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setAdapter(mBaseAdapter);
        setMode(Mode.BOTH);//既能上拉也能下拉
        //创建轮播图，加入listview的头里面
        int height = getResources().getDimensionPixelSize(R.dimen.indicator_height);
        FunBanner.Builder builder = new FunBanner.Builder(getContext());
        mFunBanner = builder.setEnableAutoLoop(true)
                .setDotSelectedColor(Color.RED)
                .setHeightWidthRatio(0.5556f)
                .setLoopInterval(5000)
                .setEnableAutoLoop(true)
                .setIndicatorBarHeight(height)
                .setIndicatorBackgroundColor(R.color.indicator_bg)
                .build();
        //getRefreshableView()获取到在这里就是ListView
        getRefreshableView().addHeaderView(mFunBanner);
        //设置下拉刷新和上拉加载监听
        setOnRefreshListener(mListViewOnRefreshListener2);

        //设置列表选项的点击事件
        setOnItemClickListener(mOnItemClickListener);


    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //调整position,包含两个头
            position = position - 2;

            //点击新闻列表，进入新闻详情
            Intent intent = new Intent(getContext(), NewsDetailActivity.class);
            //新闻详情的url
            String url = mNewsListbean.getData().getNews().get(position).getUrl();
            intent.putExtra("url", url);
            getContext().startActivity(intent);


            //把新闻标记已读，持久化存储
            //获取id， 如果已读 则为true
            int newsId = mNewsListbean.getData().getNews().get(position).getId();
            ShareUtils.setBoolean(getContext(), String.valueOf(newsId), true);

            //刷新列表
            mBaseAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 设置新闻列表url
     * @param url
     */
    public void setUrl(String url) {
        mUrl = url;
        SmartBJRequest<NewsListBean> request = new SmartBJRequest<NewsListBean>(
                NewsListBean.class,
                url,
                mNewsListBeanListener);
        //发送网络请求
//        Volley.newRequestQueue(getContext()).add(request);
        NetworkManager.sendRequest(request);
    }
    private NetworkListener<NewsListBean> mNewsListBeanListener = new NetworkListener<NewsListBean>(){
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
        }

        @Override
        public void onResponse(NewsListBean response) {
            updateNewsList(response);
        }
    };

    private void updateNewsList(NewsListBean response) {
        //保存网络数据结果
        mNewsListbean = response;
        //通知ListView刷新
        mBaseAdapter.notifyDataSetChanged();
        //刷新轮播图
        List<String> imageUrls = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();
        List<NewsListBean.DataBean.TopnewsBean> topnews = mNewsListbean.getData().getTopnews();
        for (int i = 0; i < topnews.size(); i++) {
            imageUrls.add(topnews.get(i).getTopimage());
            titles.add(topnews.get(i).getTitle());
        }
        mFunBanner.setImageUrlsAndTitles(imageUrls,titles);
        mMore = response.getData().getMore();
    }

    private OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new OnRefreshListener2<ListView>() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            //发送网络请求获取最新的数据
            SmartBJRequest request = new SmartBJRequest<NewsListBean>(NewsListBean.class,mUrl,mRefreshListener);
            NetworkManager.sendRequest(request);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            //上拉加载更多
            if (mMore.length() > 0) {
                //有更多数据,可以加载
                String url = Constant.HOST + mMore;
                SmartBJRequest<NewsListBean> request = new SmartBJRequest<NewsListBean>(NewsListBean.class, url, mLoadMoreListener);
                NetworkManager.sendRequest(request);

            }else {
                Toast.makeText(getContext(),"没有更多数据",Toast.LENGTH_SHORT).show();
                //延迟刷新
                post(new Runnable() {
                    @Override
                    public void run() {
                        onRefreshComplete();//重置刷新头
                    }
                });
            }
        }
    };

    private NetworkListener<NewsListBean> mLoadMoreListener = new NetworkListener<NewsListBean>(){
        @Override
        public void onResponse(NewsListBean response) {
            Toast.makeText(getContext(),"加载更多数据成功",Toast.LENGTH_SHORT).show();;
            mMore = response.getData().getMore();
            //获取到更多数据
            //将获取到更多的新闻数据添加到原来新闻列表的数据集合当中
            mNewsListbean.getData().getNews().addAll(response.getData().getNews());
            mBaseAdapter.notifyDataSetChanged();
            onRefreshComplete();
        }
    };

    private NetworkListener<NewsListBean> mRefreshListener = new NetworkListener<NewsListBean>(){
        @Override
        public void onResponse(NewsListBean response) {
            //刷新新闻列表
            updateNewsList(response);
            //下拉刷新结束 头复位
            onRefreshComplete();
        }
    };

    private BaseAdapter mBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            if (mNewsListbean!= null) {
                return mNewsListbean.getData().getNews().size();//返回新闻列表个数
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.view_news_list_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //刷新界面
            //刷新标题
            NewsListBean.DataBean.NewsBean newsBean = mNewsListbean.getData().getNews().get(position);
            viewHolder.mTitle.setText(newsBean.getTitle());
            //如果新闻已读，则设置为灰色，否则为黑色
            if (ShareUtils.getBoolean(getContext(), String.valueOf(newsBean.getId()))) {
                //true 表示已读
                viewHolder.mTitle.setTextColor(Color.GRAY);
            } else {
                viewHolder.mTitle.setTextColor(Color.BLACK);
            }

            //刷新发布时间
            viewHolder.mPublishTime.setText(newsBean.getPubdate());
            //刷新网络图片
            viewHolder.mNetworkImageView.setImageUrl(newsBean.getListimage(),NetworkManager.getImageLoader());
            return convertView;
        }
    };
    public class ViewHolder {
        TextView mTitle,mPublishTime;
        NetworkImageView mNetworkImageView;


        public ViewHolder(View root) {
            mTitle = (TextView) root.findViewById(R.id.title);
            mPublishTime = (TextView) root.findViewById(R.id.publish_time);
            mNetworkImageView = (NetworkImageView) root.findViewById(R.id.news_image);
        }
    }
}
