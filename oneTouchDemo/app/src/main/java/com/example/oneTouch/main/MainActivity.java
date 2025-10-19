/*
 *
 *  * Copyright (C) 2018 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.example.oneTouch.main;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.example.oneTouch.app.R;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button firstButton = findViewById(R.id.activate_first);
        firstButton.setOnClickListener( view -> {
            Log.i(TAG, "first button clicked");
            Intent intent = new Intent(MainActivity.this, ImageDisplayActivity.class);
            startActivity(intent);
        });

        Button secondButton = findViewById(R.id.activate_second);
        secondButton.setOnClickListener( view -> {
            Log.i(TAG, "second button clicked");
            Intent intent = new Intent(MainActivity.this, MarkdownActivity.class);
            startActivity(intent);
        });

        Button thirdButton = findViewById(R.id.activate_third);
        thirdButton.setOnClickListener( view -> {
            Log.i(TAG, "third button clicked");
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            startActivity(intent);
        });

        preloadWebView();

        /*Button testDeepLinkButton = findViewById(R.id.activate_third); // 假设有这个按钮
        if (testDeepLinkButton != null) {
            testDeepLinkButton.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.honor.com/cn/tech/connect/"));
                intent.setPackage(getPackageName()); // 限制只在当前应用内打开
                startActivity(intent);
            });
        }*/

    }
    private void preloadWebView() {
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.loadUrl("https://www.honor.com/cn/tech/connect/");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


}
