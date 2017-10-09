package com.best.hihappy.mvp.view.activity;

import android.annotation.TargetApi;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.best.hihappy.R;
import com.best.hihappy.base.BaseActivity;
import com.best.hihappy.sonic.SonicSessionClientImpl;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by FuKaiqiang on 2017-09-10.
 */

public class News_WebView extends BaseActivity {

    @BindView(R.id.news_details)
    WebView newsDetails;
    @BindView(R.id.newsDetails_pb)
    ProgressBar newsDetailsPb;
    @BindView(R.id.news_details_webview_toolbar)
    Toolbar newsDetailsWebviewToolbar;
    @BindView(R.id.news_details_back)
    AppCompatImageView newsDetailsBack;
    @BindView(R.id.news_details_share)
    AppCompatImageView newsDetailsShare;


    private static final String TAG = "News_WebView";
    public String mUrl;
    private SonicSession sonicSession;
    private SonicSessionClientImpl mSonicSessionClient;


    @Override
    protected int getLayoutResource() {
        mUrl = getIntentData("url");
        initSonic(mUrl);
        return R.layout.newswebview_layout;
    }

    private void initSonic(String mUrl) {

        mSonicSessionClient = null;

        // if it's sonic mode , startup sonic session at first time
        // sonic mode
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        // create sonic session and run sonic flow
        sonicSession = SonicEngine.getInstance().createSession(mUrl, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(mSonicSessionClient = new SonicSessionClientImpl());
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
            // throw new UnknownError("create session fail!");
            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initView() {
        setStatusBarColor();
        initToolbar();
        initWebView(newsDetails, newsDetailsPb);
    }

    private void initToolbar() {
        newsDetailsWebviewToolbar.setTitle("");
        newsDetailsWebviewToolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(newsDetailsWebviewToolbar);
    }

    @OnClick({R.id.news_details_back, R.id.news_details_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.news_details_back:
                finish();
                break;
            case R.id.news_details_share:
                break;
        }
    }

    protected void initWebView(WebView webview, final ProgressBar mProgressbar) {

        //init Webview settings
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress != 100) {
                    mProgressbar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressbar.setProgress(newProgress);//设置进度值
                } else {
                    mProgressbar.setVisibility(View.GONE);//加载完网页进度条消失
                }

            }
        });
        // webview is ready now, just tell session client to bind
        if (mSonicSessionClient != null) {
            mSonicSessionClient.bindWebView(webview);
            mSonicSessionClient.clientReady();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }
}
