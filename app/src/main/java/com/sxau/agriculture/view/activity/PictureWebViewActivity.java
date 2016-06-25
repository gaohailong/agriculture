package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.TimeUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.widgets.CommonWebView;
import com.sxau.agriculture.widgets.ObservableScrollView;
import com.sxau.agriculture.widgets.ZhuanLanWebViewClient;

import java.io.IOException;
import java.util.Scanner;

import io.bxbxbai.common.utils.CommonExecutor;
import io.bxbxbai.common.view.CircleImageView;


/**
 * 首页文章跳转的webView
 *
 * @author 高海龙
 */
public class PictureWebViewActivity extends BaseActivity {
    private ObservableScrollView scrollView;
    private WebView mWebView;
    private CircleImageView mAvatarView;
    private ImageView headerImageView;
    private TextView titleView;
    private TextView authorTextView;
    private ZhuanLanWebViewClient zhuanLanWebViewClient;
    private HomeArticle homeArticle;
    private TitleBarTwo topBarUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_webview);
        mWebView = (WebView) findViewById(R.id.webView);
        topBarUtil= (TitleBarTwo) findViewById(R.id.titlebar);
        initTitlebar();
        setWebView();
    }

    private void initTitlebar() {
        topBarUtil.setBackgroundColor(Color.parseColor("#00b5ad"));
        topBarUtil.setLeftImageResource(R.mipmap.ic_back_left);
        topBarUtil.setLeftTextColor(Color.WHITE);
        topBarUtil.setDividerColor(Color.GRAY);
        topBarUtil.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBarUtil.setTitleColor(Color.WHITE);
    }

    public void setWebView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("ArticleUrl");
        //TODO 图片拼接问题
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//是否支持缩放
        mWebView.requestFocus();//设置软键盘是否可以打开
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(url);
        //设置点击链接 在当前webView中显示
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(url);
    }

}
