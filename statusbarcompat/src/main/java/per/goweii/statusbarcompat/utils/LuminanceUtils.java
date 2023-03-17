package per.goweii.statusbarcompat.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
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
        return calcBitmapLuminance(bitmap);
    }

    private static double calcBitmapLuminance(Bitmap bitmap) {
        if (bitmap == null) return 0;
        if (bitmap.isRecycled()) return 0;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (w == 0 || h == 0) return 0;
        int count = 0;
        int a = 0, r = 0, g = 0, b = 0;
        final int stepx = w / 10;
        final int stepy = h / 4;
        for (int y = (h % 4) / 2; y < h; y += stepy) {
            for (int x = (w % 10) / 2; x < w; x += stepx) {
                int color = bitmap.getPixel(x, y);
                a += Color.alpha(color);
                r += Color.red(color);
                g += Color.green(color);
                b += Color.blue(color);
                count++;
            }
        }
        a = a / count;
        r = r / count;
        g = g / count;
        b = b / count;
        return calcLuminance(Color.argb(a, r, g, b));
    }
}
