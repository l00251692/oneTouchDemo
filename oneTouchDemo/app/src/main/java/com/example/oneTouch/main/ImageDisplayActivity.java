// ImageDisplayActivity.java
package com.example.oneTouch.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.example.oneTouch.app.R;

public class ImageDisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        
        // 获取ImageView并设置图片
        ImageView imageView = findViewById(R.id.display_image);
        // 这里假设你有一张名为 sample_image 的图片资源
        imageView.setImageResource(R.drawable.magic8);
    }
}
