package per.goweii.statusbarcompat.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.view.Window;

import per.goweii.statusbarcompat.StatusBarCompat;

/**
 * @author CuiZhen
 * @date 2019/11/17
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class LuminanceUtils {

    public static boolean isLight(double luminance) {
        return luminance >= 0.382;
    }

    public static double calcLuminance(int color) {
        return ColorUtils.calculateLuminance(color);
    }

    public static double calcLuminanceByCapture(Window window) {
        Bitmap bitmap = captureStatusBar(window);
        double luminance = calcBitmapLuminance(bitmap);
        bitmap.recycle();
        return luminance;
    }

    private static Bitmap captureStatusBar(Window window) {
        View decor = window.getDecorView();
        int w = decor.getMeasuredWidth();
        int h = StatusBarCompat.getHeight(window.getContext());
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        decor.draw(canvas);
        return bitmap;
    }

    private static double calcBitmapLuminance(Bitmap bitmap) {
        if (bitmap == null) return 0;
        if (bitmap.isRecycled()) return 0;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (w == 0 || h == 0) return 0;
        int y_m = h / 2;
        int x_m = w / 2;
        int light = 0, dark = 0;
        for (int x = 0; x < w; x++) {
            float f = (float) x / (float) w;
            int y_t2b = (int) (h * f);
            int y_b2t = h - y_t2b - 1;
            if (isLight(bitmap.getPixel(x, y_t2b))) ++light;
            else ++dark;
            if (isLight(bitmap.getPixel(x, y_b2t))) ++light;
            else ++dark;
            if (isLight(bitmap.getPixel(x, y_m))) ++light;
            else ++dark;
            if (x == x_m) for (int y = 0; y < h; y++)
                if (isLight(bitmap.getPixel(x, y))) ++light;
                else ++dark;
        }
        return dark > light ? 0 : 1;
    }

    private static boolean isLight(int color) {
        return isLight(calcLuminance(color));
    }
}
