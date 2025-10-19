// MarkdownActivity.java
package com.example.oneTouch.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import com.example.oneTouch.app.R;

public class MarkdownActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown);
        
        WebView webView = findViewById(R.id.markdown_webview);
        webView.getSettings().setJavaScriptEnabled(false);
        
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
        String htmlContent = "<html><head>"
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
        
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }
}
