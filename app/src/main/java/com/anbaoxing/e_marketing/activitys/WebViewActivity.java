package com.anbaoxing.e_marketing.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.utils.Constant;
import com.anbaoxing.e_marketing.widget.CommonTitle;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.aty_webview_mywebview)
    WebView atyWebviewMywebview;
    @BindView(R.id.aty_webview_share)
    LinearLayout atyWebviewShare;
    @BindView(R.id.aty_webview_commontitle)
    CommonTitle atyWebviewCommontitle;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        String showUrl = intent.getStringExtra(Constant.SHOW_URL);
        initView();
        initWebView(showUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView(){
        atyWebviewCommontitle.setTitle("消息详情");
    }

    private void initWebView(String url) {
        atyWebviewMywebview.loadUrl(url);
        atyWebviewMywebview.requestFocusFromTouch();
        WebSettings webSettings = atyWebviewMywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//LOAD_CACHE_ELSE_NETWORK

        atyWebviewMywebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("网页加载进度：", String.valueOf(newProgress));
            }
        });
        atyWebviewMywebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.d("onPageFinished");
                dismissProgressDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.d("onPageStarted");
                showProgressDialog();
            }
        });

    }

    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setTitle("");
        progressDialog.setMessage("正在加载中...");
        progressDialog.show();
    }

    private void dismissProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)&&atyWebviewMywebview.canGoBack()){
            atyWebviewMywebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.aty_webview_share)
    public void onClick() {
        startActivity(new Intent(this,PopWindowActivity.class));
    }
}
