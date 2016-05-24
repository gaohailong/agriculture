package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.TopBarUtil;

/**
 * 首页文章跳转的webView
 *
 * @author 高海龙
 */
public class WebViewActivity extends BaseActivity {
    private WebView wv_article;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wv_article = (WebView) findViewById(R.id.wv_article);
//        handler = new Handler();
        initWebView();
        iniTitle();
    }

    private void initWebView() {
        WebSettings webSettings = wv_article.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        wv_article.requestFocus();
        wv_article.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //设置点击链接 在当前webView中显示（网页中显示网页）
        wv_article.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //处理标题图标，等等
        wv_article.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        Intent intent = getIntent();
        String articleUrl = intent.getStringExtra("ArticleUrl");
        wv_article.loadUrl(articleUrl);
    }

    /**
     * 初始化标题
     */
    private void iniTitle() {
        TopBarUtil topBar = (TopBarUtil) findViewById(R.id.tb_webview);
        topBar.setLeftImageIsVisible(true);
        topBar.setLeftImage(R.mipmap.ic_back_left);
        topBar.setOnTopbarClickListener(new TopBarUtil.TopbarClickListner() {
            @Override
            public void onClickLeftRoundImage() {
            }

            @Override
            public void onClickLeftImage() {
                finish();
            }

            @Override
            public void onClickRightImage() {

            }

        });
    }

    //回退键设置
    //设置返回，不用直接跳转到主界面
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_article.canGoBack()) {
            wv_article.goForward();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

}
