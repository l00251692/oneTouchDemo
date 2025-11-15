// WebViewActivity.java
package com.example.oneTouch.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneTouch.app.R;
import com.hihonor.android.onetouchsharesdk.IOneTouchShareCallback;
import com.hihonor.android.onetouchsharesdk.OneTouchShare;

public class WebViewActivity extends AppCompatActivity {

    private static final String DEFAULT_URL = "https://www.honor.com/cn/tech/connect/";
    private static final String TAG = "WebViewActivity";

    private WebView mWebView;

    IOneTouchShareCallback mOneTouchShareCallback = new IOneTouchShareCallback() {

        @Override
        public void onStateChange(int type, int state, String para) {
            Log.i(TAG, "onStateChange state = " + state + ", para = " + para);
        }

        @Override
        public void onOneTouchEvent(int devType, String para) {
            Log.i(TAG, "onOneTouchEvent touchType = " + devType + ", para = " + para);
            // 获取缩略图，用于卡片显示
            Bitmap bitmap = Bitmap.createBitmap(mWebView.getWidth(), mWebView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            mWebView.draw(canvas);

            mWebView.post(() -> {
                OneTouchShare.ShareData shareData = new OneTouchShare.ShareData();
                // 设置网页分享类型
                shareData.setDataType(OneTouchShare.SHARE_DATA_TYPE_LINK);
                Log.i(TAG, "onOneTouchEvent Url = " + mWebView.getUrl());
                // 设置分享的网址内容
                shareData.setContent(mWebView.getUrl());
                // 设置显示的标题
                shareData.setTitle("分享网站");
                // 设置缩略图
                shareData.setThumbnail(Utils.bitmapToByteArray(bitmap));
                // 开始分享
                OneTouchShare.getInstance().share(shareData);
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        
        mWebView = findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient()); // 使得网页在当前 WebView 中打开而不是调用浏览器
        WebSettings webSettings = mWebView.getSettings();

        // 启用 JavaScript 支持
        webSettings.setJavaScriptEnabled(true);

        // 启用缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 获取传入的 Intent 数据
        String urlToLoad = getUrlFromIntent(getIntent());
        if (TextUtils.isEmpty(urlToLoad)) {
            urlToLoad = DEFAULT_URL;
        }

        mWebView.loadUrl(urlToLoad); // 加载网址
    }

    private String getUrlFromIntent(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();
        String url = null;

        if (Intent.ACTION_VIEW.equals(action)) {
            // 处理直接打开链接的情况
            Uri data = intent.getData();
            if (data != null) {
                url = data.toString();
            }
        } else if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
            // 处理分享文本的情况
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText != null) {
                // 简单验证是否为 URL
                if (sharedText.startsWith("http://") || sharedText.startsWith("https://")) {
                    url = sharedText;
                }
            }
        }

        return url;
    }

    @Override
    public void onResume() {
        super.onResume();
        OneTouchShare.getInstance().activateShareIntention(this, OneTouchShare.BUSINESS_HONOR_SHARE, mOneTouchShareCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        OneTouchShare.getInstance().deactivateShareIntention(this);
    }

}
