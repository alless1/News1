package com.alless.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/17.
 */

public class NewsCenterTabPage extends TabPage {
    public NewsCenterTabPage(Context context) {
        super(context);
    }

    public NewsCenterTabPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMenuSwitch(int position) {
        View view = null;
        Toast.makeText(getContext(), "newsCenter收到" + position,Toast.LENGTH_SHORT ).show();
        switch (position) {
            case 0:
//                textView.setText("新闻");
                NewsPage newsPage = new NewsPage(getContext());
                view = newsPage;
                break;
            case 1:
                TextView textView = new TextView(getContext());
                textView.setText("专题");
                view = textView;
                break;
            case 2:
                TextView zutu = new TextView(getContext());
                zutu.setText("组图");
                view = zutu;
                break;
            case 3:
                TextView hudong = new TextView(getContext());
                hudong.setText("互动");
                view = hudong;
                break;
        }
        mTabFrameLayout.removeAllViews();
        mTabFrameLayout.addView(view);
    }
}
