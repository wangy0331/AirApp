package com.sohu110.airapp.ui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sohu110.airapp.R;

/**
 * Created by Aaron on 2016/5/3.
 */
public class AboutUsActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        setTitle(R.string.about_us);

        mWebView = (WebView) findViewById(R.id.about_us_text);

        WebSettings webSettings = mWebView.getSettings();

        webSettings= mWebView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示

        mWebView.loadUrl("file:///android_asset/about_us.html");
    }
}
