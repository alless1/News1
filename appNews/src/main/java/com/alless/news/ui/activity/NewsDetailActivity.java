package com.alless.news.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.alless.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2017/3/21.
 */

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.text_size)
    ImageView mTextSize;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private WebSettings mSetting;
    private CharSequence[] options = {"最小", "较小", "正常", "较大", "最大"};

    private int checkedPosition = 2;//默认正常大小
    private static final String TAG = "NewsDetailActivity";
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void init() {

        ButterKnife.bind(this);
        //开启javascript js
        mSetting = mWebView.getSettings();
        mSetting.setJavaScriptEnabled(true);
        //加载网页
        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
        //设置webView监听,显示加载进度条
        mWebView.setWebChromeClient(mWebChromeClient);


    }
    private WebChromeClient mWebChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.d(TAG, "onProgressChanged: "+newProgress);
            if(newProgress==90)
                mProgressBar.setVisibility(View.GONE);
        }

    };


    @OnClick({R.id.back, R.id.share, R.id.text_size})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                showShare();
                break;
            case R.id.text_size:
                showChangeTextSizeDialog();
                break;
        }
    }

    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体大小").setSingleChoiceItems(options, checkedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                checkedPosition = which;
                changeTextSize();
            }
        });
        builder.show();
    }

    private void changeTextSize() {
        switch (checkedPosition) {
            case 0:
                mSetting.setTextZoom(50);//默认值100
                break;
            case 1:
                mSetting.setTextZoom(80);
                break;
            case 2:
                mSetting.setTextZoom(100);
                break;
            case 3:
                mSetting.setTextZoom(120);
                break;
            case 4:
                mSetting.setTextZoom(150);
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
