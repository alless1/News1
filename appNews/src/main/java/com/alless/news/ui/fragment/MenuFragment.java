package com.alless.news.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alless.news.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/16.
 */

public class MenuFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.list_view)
    ListView mListView;
    private String[] mMenuTitles = {"新闻", "专题", "组图", "互动"};
    private int mLastSelectedPosition = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void init() {
        mListView.setAdapter(mBaseAdapter);
        mListView.setOnItemClickListener(this);


    }
    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mMenuTitles.length;
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
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.view_menu_item, null);
            }
            ((TextView)convertView).setText(mMenuTitles[position]);
            //默认新闻是enable
            if (position == 0) {
                convertView.setEnabled(true);
            } else {
                convertView.setEnabled(false);
            }
            return convertView;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击时发出通知
        boolean isSwitch = (position!=mLastSelectedPosition);//是否发生切换
        if (mOnMenuChangeListener != null) {
            mOnMenuChangeListener.onMenuItemSwitch(position,isSwitch);
        }
        //点击条目able属性发生变化,如果还是原来条目则不发生变化
        if(mLastSelectedPosition==position){
            return;
        }
        View childView = parent.getChildAt(position);
        childView.setEnabled(true);
        View lastView = parent.getChildAt(mLastSelectedPosition);
        lastView.setEnabled(false);
        mLastSelectedPosition = position;
    }

    private OnMenuChangeListener mOnMenuChangeListener;
    //设置接口回调点击事件
    public interface OnMenuChangeListener {
        //菜单选项切换事件

        /**
         * @param  position 切换到位置
         * @param isSwitch true表示真的发生切换
         */
        void onMenuItemSwitch(int position, boolean isSwitch);
    }

    public void setOnMenuChangeListener(OnMenuChangeListener menuChangeListener) {
        mOnMenuChangeListener = menuChangeListener;
    }
}
