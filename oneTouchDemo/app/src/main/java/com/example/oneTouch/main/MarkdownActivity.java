// MarkdownActivity.java
package com.example.oneTouch.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneTouch.app.R;
import com.hihonor.android.onetouchsharesdk.IOneTouchShareCallback;
import com.hihonor.android.onetouchsharesdk.OneTouchShare;


public class MarkdownActivity extends AppCompatActivity {
    private static final String TAG = "MarkdownActivity";
    private WebView mWebView;
    private String htmlContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown);
        
        mWebView = findViewById(R.id.markdown_webview);
        mWebView.getSettings().setJavaScriptEnabled(false);
        
        // 沁园春·雪的 Markdown 内容
        String markdownContent = "# 沁园春·雪\n\n"
                + "北国风光，千里冰封，万里雪飘。\n"
                + "望长城内外，惟余莽莽；大河上下，顿失滔滔。\n"
                + "山舞银蛇，原驰蜡象，欲与天公试比高。\n"
                + "须晴日，看红装素裹，分外妖娆。\n\n"
                + "江山如此多娇，引无数英雄竞折腰。\n"
                + "惜秦皇汉武，略输文采；唐宗宋祖，稍逊风骚。\n"
                + "一代天骄，成吉思汗，只识弯弓射大雕。\n"
                + "俱往矣，数风流人物，还看今朝。";
        
        // 简单的 HTML 模板
        htmlContent = "<html><head>"
                + "<meta charset='UTF-8'>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<style>"
                + "body { font-family: 'Microsoft YaHei', sans-serif; padding: 20px; line-height: 1.6; }"
                + "h1 { text-align: center; color: #333; }"
                + "p { text-indent: 2em; margin: 10px 0; }"
                + "</style>"
                + "</head><body>"
                + "<h1>沁园春·雪</h1>"
                + "<p>北国风光，千里冰封，万里雪飘。</p>"
                + "<p>望长城内外，惟余莽莽；大河上下，顿失滔滔。</p>"
                + "<p>山舞银蛇，原驰蜡象，欲与天公试比高。</p>"
                + "<p>须晴日，看红装素裹，分外妖娆。</p>"
                + "<p>江山如此多娇，引无数英雄竞折腰。</p>"
                + "<p>惜秦皇汉武，略输文采；唐宗宋祖，稍逊风骚。</p>"
                + "<p>一代天骄，成吉思汗，只识弯弓射大雕。</p>"
                + "<p>俱往矣，数风流人物，还看今朝。</p>"
                + "</body></html>";

        // 简单的 HTML 模板
        htmlContent = "<html><head>"
                + "<meta charset='UTF-8'>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<style>"
                + "body { font-family: 'Microsoft YaHei', sans-serif; padding: 20px; line-height: 1.6; }"
                + "h1 { text-align: center; color: #333; }"
                + "p { text-indent: 2em; margin: 10px 0; }"
                + "</style>"
                + "</head><body>"
                + "<h1>静夜思</h1>"
                + "<p>床前明月光，</p>"
                + "<p>疑是地上霜。</p>"
                + "<p>举头望明月，</p>"
                + "<p>低头思故乡。</p>"
                + "</body></html>";


        Intent intent = getIntent();
        String appData = intent.getStringExtra("APP_DATA");
        if (!TextUtils.isEmpty(appData)) {
            htmlContent = appData;
        }
        mWebView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    @Override
    public void onResume() {
        super.onResume();
        OneTouchShare.getInstance().activateShareIntention(this, OneTouchShare.BUSINESS_HONOR_SHARE, mOneTouchShareCallback);
    }


    IOneTouchShareCallback mOneTouchShareCallback = new IOneTouchShareCallback() {

        @Override
        public void onStateChange(int type, int state, String para) {
            Log.i(TAG, "onStateChange state = " + state + ", para = " + para);
        }

        @Override
        public void onOneTouchEvent(int touchType, String para) {
            Log.i(TAG, "onOneTouchEvent touchType = " + touchType + ", para = " + para);

            OneTouchShare.ShareData shareData = new OneTouchShare.ShareData();

            Bitmap bitmap = Bitmap.createBitmap(mWebView.getWidth(), mWebView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            mWebView.draw(canvas);

            shareData.setDataType(OneTouchShare.SHARE_DATA_TYPE_APPDATA);
            shareData.setAction("com.example.oneTouch.action.MarkdownActivity");
            Log.i(TAG, "onOneTouchEvent bitmap = " + bitmap);
            shareData.setThumbnail(Utils.bitmapToByteArray(bitmap));
            shareData.setTitle("分享文本");
            shareData.setContent(htmlContent);
            OneTouchShare.getInstance().share(shareData);
            Log.i(TAG, "onOneTouchEvent share = " + htmlContent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        OneTouchShare.getInstance().deactivateShareIntention(this);
    }
}
