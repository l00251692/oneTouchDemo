// ImageDisplayActivity.java
package com.example.oneTouch.main;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.oneTouch.app.R;
import com.hihonor.android.onetouchsharesdk.IOneTouchShareCallback;
import com.hihonor.android.onetouchsharesdk.OneTouchShare;

import java.io.File;
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
        imageView.setImageResource(R.drawable.magic8_1);
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
            shareData.setDataType(OneTouchShare.SHARE_DATA_TYPE_FILE);
            Drawable drawable = getResources().getDrawable(R.drawable.magic8_1);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            File file = Utils.saveImgFile(getApplicationContext(), bitmap, getFilesDir().getAbsolutePath() + File.separator + "share", "img.png");
            Uri imageUri = null;
            if (file != null) {
                imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.oneTouch.fileprovider", file);
            } else {
                Log.e(TAG, "file null");
                OneTouchShare.getInstance().onAppStatus(OneTouchShare.STATUS_ERR_COMMON, "-1");
                return;
            }

            Log.i(TAG, "onOneTouchEvent imageUri = " + imageUri);
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
