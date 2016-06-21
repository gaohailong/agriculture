package com.sxau.agriculture.widgets;

import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.bxbxbai.common.StopWatch;

/**
 *
 * @author bxbxbai
 */
public class ZhuanLanWebViewClient extends WebViewClient {


    private Activity mActivity;

    public ZhuanLanWebViewClient(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        StopWatch.log("url " + url);
        if (url != null && url.startsWith("orpheus")) {
            return true;
        }
        if (url != null && url.startsWith("http")) {
//            WebActivity.start(mActivity, url);
            return true;
        }

        return true;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        StopWatch.log("shouldInterceptRequest_old: " + url);
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        StopWatch.log("shouldInterceptRequest_new: " + request);
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }
}
