package com.example.oneTouch.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Utils {

    private static final String TAG = "Utils";

    public static File saveImgFile(Context context, Bitmap bitmap, String dir, String name) {
        try {
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File file = new File(dirFile, name);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // 第二个参数是质量，100表示最高质量
            out.flush();
            out.close();
            return file;
        } catch (Exception e) {
            e.fillInStackTrace();
            Log.e(TAG, "saveImgFile exception: " + e.getMessage());
        }
        return null;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 使用PNG格式压缩，质量为100%
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }
}
