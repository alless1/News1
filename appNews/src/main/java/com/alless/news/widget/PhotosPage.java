package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alless.news.App.Constant;
import com.alless.news.R;
import com.alless.news.bean.NewsListBean;
import com.alless.news.network.NetworkListener;
import com.alless.news.network.NetworkManager;
import com.alless.news.network.SmartBJRequest;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/21.
 */
public class PhotosPage extends RelativeLayout {
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.grid_view)
    GridView mGridView;
    private List<NewsListBean.DataBean.NewsBean> mDataList;
    private String mUrl;

    public PhotosPage(Context context) {
        this(context, null);
    }

    public PhotosPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_photos_page, this);
        ButterKnife.bind(this, this);

        mListView.setAdapter(mBaseAdapter);
        mGridView.setAdapter(mBaseAdapter);

    }

    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            if (mDataList == null) {
                return 0;
            }else {
                return mDataList.size();
            }
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
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.view_photos_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.image.setImageUrl(mDataList.get(position).getListimage(), NetworkManager.getImageLoader());
            holder.title.setText(mDataList.get(position).getTitle());
            return convertView;
        }
    };
    public class ViewHolder {
        NetworkImageView image;
        TextView title;
        public ViewHolder(View view){
            image = (NetworkImageView) view.findViewById(R.id.photo_image);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public void setData(String data) {
        mUrl = Constant.HOST + data;
        SmartBJRequest<NewsListBean> request = new SmartBJRequest<>(NewsListBean.class,mUrl,mNewsListBeanNetworkListener);
        NetworkManager.sendRequest(request);
    }
    private NetworkListener<NewsListBean> mNewsListBeanNetworkListener = new NetworkListener<NewsListBean>(){
        @Override
        public void onResponse(NewsListBean response) {
            mDataList = response.getData().getNews();
            mBaseAdapter.notifyDataSetChanged();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
        }
    };
    public void switchPhotoStyle(boolean isList){
        if(isList){
            mGridView.setVisibility(GONE);
            mListView.setVisibility(VISIBLE);
        }else{
            mGridView.setVisibility(VISIBLE);
            mListView.setVisibility(GONE);
        }
    }



}
