package com.alless.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/17.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * onCreateView创建Fragment的视图
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResId(), null);
        ButterKnife.bind(this, root);//将视图root绑定到Fragment上
        init();
        return root;
    }

    /**
     * 子类可以覆写该方法来完成自己Fragment的初始化
     */
    protected void init() {}

    /**
     *  子类必须实现该方法来返回一个Fragment的布局
     */
    public abstract int getLayoutResId();
}
