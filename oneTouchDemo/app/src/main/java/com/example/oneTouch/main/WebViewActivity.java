// WebViewActivity.java
package com.example.oneTouch.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.oneTouch.app.R;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        
        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient()); // 使得网页在当前 WebView 中打开而不是调用浏览器
        webView.getSettings().setJavaScriptEnabled(true); // 启用 JavaScript 支持
        webView.loadUrl("https://www.honor.com/cn/tech/connect/"); // 加载指定网址
    }
}
