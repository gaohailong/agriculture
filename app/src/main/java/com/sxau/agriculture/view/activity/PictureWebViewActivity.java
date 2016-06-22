package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
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
    private CommonWebView mWebView;
    private CircleImageView mAvatarView;
    private ImageView headerImageView;
    private TextView titleView;
    private TextView authorTextView;
    private ZhuanLanWebViewClient zhuanLanWebViewClient;
    private HomeArticle homeArticle;
    private TitleBarTwo topBarUtil;
    private TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_webview);
        mWebView = (CommonWebView) findViewById(R.id.web_view);
        scrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        titleView = (TextView) findViewById(R.id.tv_title);
        authorTextView = (TextView) findViewById(R.id.tv_author);
        headerImageView = (ImageView) findViewById(R.id.iv_article_header);
        mAvatarView = (CircleImageView) findViewById(R.id.iv_avatar);
        tv_data = (TextView) findViewById(R.id.tv_data);
        topBarUtil = (TitleBarTwo) findViewById(R.id.titlebar);
        mWebView.setWebViewClient(new ZhuanLanWebViewClient(PictureWebViewActivity.this));
        initTitlebar();
        getSourceData();
        setStory();
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

    public void getSourceData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        homeArticle = (HomeArticle) bundle.getSerializable("ArticleData");
    }

    private void setStory() {
        loadHtmlContent(homeArticle.getContent());
        if (homeArticle.getImage() == null) {
            headerImageView.setVisibility(View.GONE);
        } else {
            WindowManager wm = this.getWindowManager();
            int width = wm.getDefaultDisplay().getWidth();
            Picasso.with(PictureWebViewActivity.this).load(ConstantUtil.BASE_PICTURE_URL + homeArticle.getImage()).resize(width, 480).into(headerImageView);
        }
        titleView.setText(homeArticle.getTitle());
        topBarUtil.setTitle(homeArticle.getTitle());
        tv_data.setText(TimeUtil.format(homeArticle.getWhenCreated()));
        authorTextView.setText(homeArticle.getAdmin().getName());
        CommonExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                injectCSS();
            }
        }, 200);
    }

    private void loadHtmlContent(String section) {
        String content = String.format(readFile("template.txt"), section);
        mWebView.loadDataWithBaseURL(null, content, CommonWebView.MIME_TYPE, CommonWebView.ENCODING_UTF_8, null);
    }

    private void injectCSS() {
        String encoded = Base64.encodeToString(readFile("zhuanlan.main.css").getBytes(), Base64.NO_WRAP);
        mWebView.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "style.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(style)" +
                "})()");
    }

    private String readFile(String fileName) {
        AssetManager manager = getAssets();
        try {
            Scanner scanner = new Scanner(manager.open(fileName));
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
