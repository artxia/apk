package com.bftv.myapplication.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bftv.myapplication.R;
import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.webview.X5WebView;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;

/**
 * Created by Helloworld on 2018/7/21.
 */

public class OnLinePlayerWebview extends Activity {


    private X5WebView mWebView;
    private WebView root;
    private String url;
    private ProgressBar progressBar;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.load_html);
        url = getIntent().getStringExtra(KeyParam.PLAYURL);
        mWebView = findViewById(R.id.root);
        progressBar = findViewById(R.id.progressBar);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient());
        mWebView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(com.tencent.smtt.sdk.WebView webView, String s) {
               // hideHtmlContent();
                return super.shouldInterceptRequest(webView, s);
            }

            @Override
            public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onPageFinished(webView, s);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadResource(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onLoadResource(webView, s);
               // hideHtmlContent();
            }
        });

        mWebView.loadUrl(url);


    }

    /**
     * ??????js????????????div??????????????????????????????js??????????????????
     */
    private void hideHtmlContent() {
        //?????? javaScript??????
        String javascriptq =  "javascript:function hideOther() {" +
                "var headers = document.getElementsByClassName('logo');" +
                "var lastHeader = headers[headers.length-1];" +
                "lastHeader.innerHTML='<font style=\"float:left;margin-left:5px;font-size:.42rem;margin-top:3px;\">????????????</font>';" +
                "}";
        String javascript1 =  "javascript:function hideFooter() {" +
                "var headers = document.getElementsByClassName('iconfont icon-hot');" +
                "var lastHeader = headers[headers.length-1];" +
                "lastHeader.remove();" +
                "}";
        String javascript2 =  "javascript:function hideOthers() {" +
                "var divs = document.getElementsByClassName('footerText');" +
                "var lastDiv = divs[divs.length-1];" +
                "lastDiv.remove();" +
                "}";
        String javascript3 =  "javascript:function hideRighttab() {" +
                "var divs = document.getElementsByClassName('iconfont icon-creative');" +
                "var lastDiv = divs[divs.length-1];" +
                "lastDiv.remove();" +
                "}";
        String javascript4 =  "javascript:function hideBottomTab() {" +
                "var divs = document.getElementsByClassName('mnav');" +
                "var lastDiv = divs[divs.length-1];" +
                "lastDiv.remove();" +
                "}";
        String javascript5 =  "javascript:function hideFavorTab() {" +
                "var divs = document.getElementsByClassName('iconfont icon-like');" +
                "var lastDiv = divs[divs.length-1];" +
                "lastDiv.remove();" +
                "}";
        String javascript7 =  "javascript:function hideNewTab() {" +
                "var divs = document.getElementsByClassName('iconfont icon-time');" +
                "var lastDiv = divs[divs.length-1];" +
                "lastDiv.remove();" +
                "}";
        String javascript6 =  "javascript:function  hidepanum() {" +
                        "var divs = document.getElementsByClassName('mod-main clearfix').previousElementSibling;" +
                        "var lastDiv = divs[divs.length-1];" +
                        "lastDiv.remove();" +
                        "}";

        //????????????mnav
        mWebView.loadUrl(javascriptq);
        mWebView.loadUrl(javascript1);
        mWebView.loadUrl(javascript2);
        mWebView.loadUrl(javascript3);
        mWebView.loadUrl(javascript4);
        mWebView.loadUrl(javascript5);
        mWebView.loadUrl(javascript6);
        mWebView.loadUrl(javascript7);

        //????????????
        mWebView.loadUrl("javascript:hideOther();");
        mWebView.loadUrl("javascript:hideOthers();");
        mWebView.loadUrl("javascript:hideRighttab();");
        mWebView.loadUrl("javascript:hideBottomTab();");
        mWebView.loadUrl("javascript:hideFooter();");
        mWebView.loadUrl("javascript:hideFavorTab();");
        mWebView.loadUrl("javascript:hidepanum();");
        mWebView.loadUrl("javascript:hideNewTab();");
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        mWebView.destroyDrawingCache();
        mWebView.destroy();
        super.onDestroy();
    }



}
