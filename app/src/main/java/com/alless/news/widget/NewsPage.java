package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.alless.news.R;

/**
 * Created by Leon on 2017/3/17.
 */

public class NewsPage extends RelativeLayout {

    public NewsPage(Context context) {
        this(context, null);
    }

    public NewsPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_news_page, this);
    }
}
