package com.example.yangchaoming.bappdemo.blur_image.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class BlurUtil {
    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(c);
        return bitmap;
    }


}
