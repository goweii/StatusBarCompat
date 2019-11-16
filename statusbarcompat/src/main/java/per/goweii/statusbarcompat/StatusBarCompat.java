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

    public static void setColor(Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static int getHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static boolean isIconDark(Fragment fragment) {
        return OsStatusBarCompatHolder.get().isDarkIconMode(fragment);
    }

    public static boolean isIconDark(Activity activity) {
        return OsStatusBarCompatHolder.get().isDarkIconMode(activity);
    }

    public static boolean isIconDark(Window window) {
        return OsStatusBarCompatHolder.get().isDarkIconMode(window);
    }

    public static void setIconMode(Fragment fragment, boolean darkIconMode) {
        OsStatusBarCompatHolder.get().setDarkIconMode(fragment, darkIconMode);
    }

    public static void setIconMode(Activity activity, boolean darkIconMode) {
        OsStatusBarCompatHolder.get().setDarkIconMode(activity, darkIconMode);
    }

    public static void setIconMode(Window window, boolean darkIconMode) {
        OsStatusBarCompatHolder.get().setDarkIconMode(window, darkIconMode);
    }

    public static boolean isTransparent(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            return isTransparent(activity);
        }
        return false;
    }

    public static void transparent(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            transparent(activity);
        }
    }

    public static void unTransparent(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            unTransparent(activity);
        }
    }

    public static boolean isTransparent(Activity activity) {
        return isTransparent(activity.getWindow());
    }

    public static void transparent(Activity activity) {
        transparent(activity.getWindow());
    }

    public static void unTransparent(Activity activity) {
        unTransparent(activity.getWindow());
    }

    public static boolean isTransparent(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return isTransparentStatusBarAbove21(window);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return isTransparentStatusBarAbove19(window);
        }
        return false;
    }

    public static void transparent(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transparentStatusBarAbove21(window);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transparentStatusBarAbove19(window);
        }
    }

    public static void unTransparent(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            unTransparentStatusBarAbove21(window);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            unTransparentStatusBarAbove19(window);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean isTransparentStatusBarAbove21(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int ui = window.getDecorView().getSystemUiVisibility();
        final int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        return flag == (ui & flag);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void transparentStatusBarAbove21(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void unTransparentStatusBarAbove21(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int ui = window.getDecorView().getSystemUiVisibility();
        ui = ui & ~(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.getDecorView().setSystemUiVisibility(ui);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean isTransparentStatusBarAbove19(Window window) {
        final int flag = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        return flag == (window.getAttributes().flags & flag);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBarAbove19(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void unTransparentStatusBarAbove19(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
