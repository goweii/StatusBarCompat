package per.goweii.statusbarcompat.utils;

import android.graphics.Bitmap;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.view.Window;

import per.goweii.statusbarcompat.StatusBarCapture;

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
        Bitmap bitmap = StatusBarCapture.get().capture(window.getDecorView());
        if (bitmap == null) return 0;
        return calcBitmapLuminance(bitmap, true);
    }

    private static double calcBitmapLuminance(Bitmap bitmap, boolean rough) {
        if (bitmap == null) return 0;
        if (bitmap.isRecycled()) return 0;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (w == 0 || h == 0) return 0;
        int y_m = h / 2;
        int light = 0, dark = 0;
        for (int x = 0; x < w; x++) {
            if (rough) {
                if (x % 10 == 0) {
                    if (isLight(bitmap.getPixel(x, y_m))) ++light;
                    else ++dark;
                }
                continue;
            }
            if (isLight(bitmap.getPixel(x, y_m))) ++light;
            else ++dark;
            float f = (float) x / (float) w;
            int y_t2b = (int) (h * f);
            int y_b2t = h - y_t2b - 1;
            if (isLight(bitmap.getPixel(x, y_t2b))) ++light;
            else ++dark;
            if (isLight(bitmap.getPixel(x, y_b2t))) ++light;
            else ++dark;
            if (x == w / 2) for (int y = 0; y < h; y++)
                if (isLight(bitmap.getPixel(x, y))) ++light;
                else ++dark;
        }
        return dark > light ? 0 : 1;
    }

    private static boolean isLight(int color) {
        return isLight(calcLuminance(color));
    }
}
