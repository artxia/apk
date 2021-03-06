package com.bftv.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Helloworld on 2018/7/21.
 */

public class ParseWebUrlHelper {
    private static ParseWebUrlHelper parseWebUrlHelper;
    private String webUrl;
    private Activity mAct;
    private WebView webView;
    private int timeOut = 20 * 1000;
    private OnParseWebUrlListener onParseListener;

    public static ParseWebUrlHelper getInstance() {
        if (parseWebUrlHelper == null) parseWebUrlHelper = new ParseWebUrlHelper();
        return parseWebUrlHelper;
    }

    public ParseWebUrlHelper init(Activity act, String url) {
        this.mAct = act;
        this.webUrl = url;
        ViewGroup mainView = (ViewGroup) mAct.findViewById(android.R.id.content);
        this.webView = new WebView(mAct);
        this.webView.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
        mainView.addView(this.webView);
        initWebSettings();
        return this;
    }

    private void initWebSettings() {
        WebView mWebView = this.webView;
        mWebView.clearFocus();
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setBuiltInZoomControls(true);// ??????????????????
        mWebSettings.setUseWideViewPort(true);// ?????????????????????
        mWebSettings.setLoadWithOverviewMode(true);// setUseWideViewPort????????????webview????????????????????????setLoadWithOverviewMode???????????????webview???????????????????????????
        mWebSettings.setSavePassword(true);
        mWebSettings.setSaveFormData(true);// ??????????????????
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setTextZoom(100);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);// ??????//?????????????????????????????????????????????MD?????????????????????????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWebSettings.setMediaPlaybackRequiresUserGesture(true);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            mWebSettings.setAllowFileAccessFromFileURLs(true);
            mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setAppCachePath(mAct.getCacheDir().getAbsolutePath());
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setGeolocationDatabasePath(mAct.getDir("database", 0).getPath());
        mWebSettings.setGeolocationEnabled(true);
        CookieManager instance = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mAct.getApplicationContext());
        }
        instance.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            instance.setAcceptThirdPartyCookies(mWebView, true);
        }
        mWebView.setWebViewClient(new MyWebViewClient());
        enabledCookie(webView);//??????cookie
    }
    public ParseWebUrlHelper setLoadUrl(String url){
        this.webUrl=url;
        return this;
    }
    public ParseWebUrlHelper startParse(){
        webView.loadUrl(this.webUrl);
        return this;
    }
    /*??????cookie*/
    private void enabledCookie(WebView web) {
        CookieManager instance = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mAct);
        }
        instance.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            instance.setAcceptThirdPartyCookies(web, true);
        }
    }

    public ParseWebUrlHelper setOnParseListener(OnParseWebUrlListener onParseListener) {
        this.onParseListener = onParseListener;
        return this;
    }

    private class MyWebViewClient extends WebViewClient {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(request.getUrl().toString().startsWith("intent")||request.getUrl().toString().startsWith("youku")){
                return true;
            }else{
                return super.shouldOverrideUrlLoading(view, request);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("intent")||url.startsWith("youku")){
                return true;
            }else{
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        /*??????ssl????????????*/
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            onParseListener.onFindUrl(url);
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String url = request.getUrl().toString();
                onParseListener.onFindUrl(url);
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO: Implement this method
            super.onPageStarted(view, url, favicon);
            startConut();//??????????????????
        }

    }
    /*??????webview??????????????????*/
    private void startConut(){
        final Timer timer=new Timer();
        TimerTask timerTask=new TimerTask(){
            @Override
            public void run()
            {
                onParseListener.onError("???????????????????????????????????????????????????????????????...");
                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(timerTask,timeOut,1);
    }

    public interface OnParseWebUrlListener {
        void onFindUrl(String url);
        void onError(String errorMsg);
    }
}
