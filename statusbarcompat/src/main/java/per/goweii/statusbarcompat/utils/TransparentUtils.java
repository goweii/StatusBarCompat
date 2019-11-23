package per.goweii.statusbarcompat.utils;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author CuiZhen
 * @date 2019/11/23
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class TransparentUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isTransparentStatusBarAbove21(@NonNull Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int ui = window.getDecorView().getSystemUiVisibility();
        final int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        return flag == (ui & flag);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isTransparentStatusBarAbove19(@NonNull Window window) {
        final int flag = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        return flag == (window.getAttributes().flags & flag);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void transparentStatusBarAbove21(@NonNull Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void transparentStatusBarAbove19(@NonNull Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void unTransparentStatusBarAbove21(@NonNull Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int ui = window.getDecorView().getSystemUiVisibility();
        ui = ui & ~(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.getDecorView().setSystemUiVisibility(ui);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void unTransparentStatusBarAbove19(@NonNull Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
