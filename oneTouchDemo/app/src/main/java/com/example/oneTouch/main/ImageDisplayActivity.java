// ImageDisplayActivity.java
package com.example.oneTouch.main;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import com.example.oneTouch.app.R;
import com.hihonor.android.onetouchsharesdk.IOneTouchShareCallback;
import com.hihonor.android.onetouchsharesdk.OneTouchShare;

import java.util.ArrayList;
import java.util.List;

public class ImageDisplayActivity extends AppCompatActivity {

    private static final String TAG = "ImageDisplayActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        Log.i(TAG, "onCreate");
        // 获取ImageView并设置图片
        ImageView imageView = findViewById(R.id.display_image);
        // 这里假设你有一张名为 sample_image 的图片资源
        imageView.setImageResource(R.drawable.magic8);
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
            shareData.setDataType(OneTouchShare.SHARE_DATA_TYPE_FILE);

            Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                        getResources().getResourcePackageName(R.drawable.magic8) + "/" +
                        getResources().getResourceTypeName(R.drawable.magic8) + "/" +
                        getResources().getResourceEntryName(R.drawable.magic8));
            List<Uri> urilist = new ArrayList<>();
            urilist.add(imageUri);
            grantUriPermission("com.hihonor.android.instantshare", imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareData.setUris(urilist);

            OneTouchShare.getInstance().share(shareData);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        OneTouchShare.getInstance().deactivateShareIntention(this);
    }
}
