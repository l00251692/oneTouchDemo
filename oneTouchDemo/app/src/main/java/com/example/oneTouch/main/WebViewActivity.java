// WebViewActivity.java
package com.example.oneTouch.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.oneTouch.app.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String DEFAULT_URL = "https://www.honor.com/cn/tech/connect/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        
        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient()); // 使得网页在当前 WebView 中打开而不是调用浏览器
        WebSettings webSettings = webView.getSettings();

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

        webView.loadUrl(urlToLoad); // 加载网址
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
}
