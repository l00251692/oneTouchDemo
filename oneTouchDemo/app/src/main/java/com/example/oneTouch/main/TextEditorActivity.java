package com.example.oneTouch.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneTouch.app.R;
import com.hihonor.android.onetouchsharesdk.IOneTouchShareCallback;
import com.hihonor.android.onetouchsharesdk.OneTouchShare;

public class TextEditorActivity extends AppCompatActivity {
    private static final String TAG = "MarkdownActivity";

    private EditText mEditTextContent;
    private Button mBtnBold;
    private Button mBtnItalic;
    private Button mBtnUnderline;
    private Button mBtnHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        mEditTextContent = findViewById(R.id.edit_text_content);
        mBtnBold = findViewById(R.id.btn_bold);
        mBtnItalic = findViewById(R.id.btn_italic);
        mBtnUnderline = findViewById(R.id.btn_underline);
        mBtnHeading = findViewById(R.id.btn_heading);
    }

    private void setupClickListeners() {
        mBtnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatText("bold");
            }
        });

        mBtnItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatText("italic");
            }
        });

        mBtnUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatText("underline");
            }
        });

        mBtnHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatText("heading");
            }
        });

        Intent intent = getIntent();
        String appData = intent.getStringExtra("APP_DATA");
        if (!TextUtils.isEmpty(appData)) {
            mEditTextContent.setText(Html.fromHtml(appData, Html.FROM_HTML_MODE_LEGACY));
        }
    }

    private void formatText(String formatType) {
        int start = mEditTextContent.getSelectionStart();
        int end = mEditTextContent.getSelectionEnd();
        
        if (start == end) {
            Toast.makeText(this, "Please select some text to format", Toast.LENGTH_SHORT).show();
            return;
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder(mEditTextContent.getText());
        
        switch (formatType) {
            case "bold":
                ssb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "italic":
                ssb.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "underline":
                ssb.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "heading":
                // For simplicity, we'll just make it bold and larger (simulated with prefix)
                ssb.insert(start, "# ");
                ssb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }
        
        mEditTextContent.setText(ssb);
        mEditTextContent.setSelection(end);
    }

    private String convertToHtml(String plainText) {
        StringBuilder html = new StringBuilder();
        
        // HTML header
        html.append("<html><head>")
            .append("<meta charset='UTF-8'>")
            .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
            .append("<style>")
            .append("body { font-family: 'Microsoft YaHei', sans-serif; padding: 20px; line-height: 1.6; }")
            .append("h1 { color: #333; }")
            .append("p { margin: 10px 0; }")
            .append("b, strong { font-weight: bold; }")
            .append("i, em { font-style: italic; }")
            .append("u { text-decoration: underline; }")
            .append("</style>")
            .append("</head><body>");

        // Process text lines
        String[] lines = plainText.split("\n");
        for (String line : lines) {
            if (line.startsWith("# ")) {
                // Heading
                html.append("<h1>").append(escapeHtml(line.substring(2))).append("</h1>");
            } else if (line.trim().isEmpty()) {
                // Empty line
                html.append("<br>");
            } else {
                // Regular paragraph
                html.append("<p>").append(escapeHtml(line)).append("</p>");
            }
        }

        // Close HTML
        html.append("</body></html>");
        
        return html.toString();
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;");
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

            String plainText = mEditTextContent.getText().toString();
            String htmlContent = convertToHtml(plainText);

            Bitmap bitmap = Bitmap.createBitmap(mEditTextContent.getWidth(), mEditTextContent.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            mEditTextContent.draw(canvas);

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
