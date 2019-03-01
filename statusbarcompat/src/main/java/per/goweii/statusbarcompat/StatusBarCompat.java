package per.goweii.statusbarcompat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarCompat {

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 设置状态栏图标主题
     */
    public static void setStatusBarIconMode(Fragment fragment, boolean isDarkMode) {
        createOsStatusBarCompat().setDarkIconMode(fragment, isDarkMode);
    }

    /**
     * 设置状态栏图标主题
     */
    public static void setStatusBarIconMode(Activity activity, boolean isDarkMode) {
        createOsStatusBarCompat().setDarkIconMode(activity, isDarkMode);
    }

    /**
     * 设置状态栏图标主题
     */
    public static void setStatusBarIconMode(Window window, boolean isDarkMode) {
        createOsStatusBarCompat().setDarkIconMode(window, isDarkMode);
    }

    /**
     * 设置状态栏透明
     */
    public static void transparentStatusBar(Activity activity) {
        transparentStatusBar(activity.getWindow());
    }

    /**
     * 设置状态栏透明
     */
    public static void transparentStatusBar(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            transparentStatusBar(activity);
        }
    }

    /**
     * 设置状态栏透明
     */
    public static void transparentStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transparentStatusBarAbove21(window);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transparentStatusBarAbove19(window);
        }
    }

    private static OsStatusBarCompat createOsStatusBarCompat(){
        if (OsUtils.isMiui()) {
            return new OsStatusBarCompatMiui();
        } else if (OsUtils.isFlyme()) {
            return new OsStatusBarCompatFlyme();
        } else if (OsUtils.isOppo()) {
            return new OsStatusBarCompatOppo();
        } else {
            return new OsStatusBarCompatDef();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void transparentStatusBarAbove21(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBarAbove19(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
