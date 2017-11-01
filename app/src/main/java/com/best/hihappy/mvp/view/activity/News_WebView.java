package com.best.hihappy.mvp.view.activity;

import android.annotation.TargetApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.best.hihappy.R;
import com.best.hihappy.base.BaseActivity;
import com.best.hihappy.sonic.SonicSessionClientImpl;
import com.bumptech.glide.Glide;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    private String mUrl;
    private String mImageUrl;
    @BindView(R.id.fruit_image_view)
    ImageView fruitImageView;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private SonicSession sonicSession;
    private SonicSessionClientImpl mSonicSessionClient;
    private String mCategory;
    private String mTitle;


    @Override
    protected int getLayoutResource() {
        initData();
        initSonic(mUrl);
        return R.layout.newswebview_layout;
    }

    private void initData() {
        mUrl = getIntentData("url");
        mImageUrl = getIntentData("imageUrl");
        mCategory = getIntentData("category");
        mTitle = getIntentData("title");
    }

    private void initSonic(String mUrl) {

        mSonicSessionClient = null;
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sonicSession = SonicEngine.getInstance().createSession(mUrl, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(mSonicSessionClient = new SonicSessionClientImpl());
        } else {
            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initView() {
        setStatusBarColor();
        initToolbar();
        initImage();
        initTitle();
        initWebView(newsDetails, newsDetailsPb);
    }

    private void initTitle() {
        collapsingToolbar.setTitle(mCategory);
    }

    private void initImage() {
        Glide.with(this).load(mImageUrl).into(fruitImageView);
    }

    private void initToolbar() {
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
                showShare();
            default:
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mTitle);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("看新闻、视频、直播首选嗨娱乐APP,快去下载试试吧");
        oks.setImageUrl(mImageUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mUrl);
        // 启动分享GUI
        oks.show(this);
    }

    protected void initWebView(WebView webview, final ProgressBar mProgressbar) {

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
