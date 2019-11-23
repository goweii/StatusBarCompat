package per.goweii.statusbarcompat;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import per.goweii.statusbarcompat.utils.ContextUtils;
import per.goweii.statusbarcompat.utils.LuminanceUtils;
import per.goweii.statusbarcompat.utils.TransparentUtils;

/**
 * @author CuiZhen
 */
public class StatusBarCompat {

    public static int getHeight(@NonNull Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        try {
            return context.getResources().getDimensionPixelSize(resourceId);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void setColor(@NonNull Fragment fragment, @ColorInt int color) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        setColor(activity, color);
    }

    public static void setColor(@NonNull Context context, @ColorInt int color) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        setColor(activity, color);
    }

    public static void setColor(@NonNull Activity activity, @ColorInt int color) {
        Window window = activity.getWindow();
        if (window == null) return;
        setColor(window, color);
    }

    public static void setColor(@NonNull Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static boolean isBgLight(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return false;
        return isBgLight(activity);
    }

    public static boolean isBgLight(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return false;
        return isBgLight(activity);
    }

    public static boolean isBgLight(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return false;
        return isBgLight(window);
    }

    public static boolean isBgLight(@NonNull Window window) {
        return LuminanceUtils.isLight(calcBgLuminance(window));
    }

    public static double calcBgLuminance(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return 0;
        return calcBgLuminance(activity);
    }

    public static double calcBgLuminance(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return 0;
        return calcBgLuminance(activity);
    }

    public static double calcBgLuminance(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return 0;
        return calcBgLuminance(window);
    }

    public static double calcBgLuminance(@NonNull Window window) {
        if (StatusBarCompat.isTransparent(window)) {
            return LuminanceUtils.calcLuminanceByCapture(window);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return LuminanceUtils.calcLuminance(window.getStatusBarColor());
        }
        return 0;
    }

    public static boolean isIconDark(@NonNull Fragment fragment) {
        return OsCompatHolder.get().isDarkIconMode(fragment);
    }

    public static boolean isIconDark(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return false;
        return OsCompatHolder.get().isDarkIconMode(activity);
    }

    public static boolean isIconDark(@NonNull Activity activity) {
        return OsCompatHolder.get().isDarkIconMode(activity);
    }

    public static boolean isIconDark(@NonNull Window window) {
        return OsCompatHolder.get().isDarkIconMode(window);
    }

    public static void registerToAutoChangeIconMode(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        registerToAutoChangeIconMode(activity);
    }

    public static void registerToAutoChangeIconMode(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        registerToAutoChangeIconMode(activity);
    }

    public static void registerToAutoChangeIconMode(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return;
        registerToAutoChangeIconMode(window);
    }

    public static void registerToAutoChangeIconMode(final @NonNull Window window) {
        final View decorView = window.getDecorView();
        Object tag = decorView.getTag();
        if (tag instanceof ViewTreeObserver.OnPreDrawListener) {
            return;
        }
        final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                setIconModeAuto(window);
                return true;
            }
        };
        decorView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                decorView.removeOnAttachStateChangeListener(this);
                decorView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
            }
        });
        decorView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
        decorView.setTag(onPreDrawListener);
    }

    public static void setIconModeAuto(@NonNull Fragment fragment) {
        setIconMode(fragment, isBgLight(fragment));
    }

    public static void setIconModeAuto(@NonNull Context context) {
        setIconMode(context, isBgLight(context));
    }

    public static void setIconModeAuto(@NonNull Activity activity) {
        setIconMode(activity, isBgLight(activity));
    }

    public static void setIconModeAuto(@NonNull Window window) {
        setIconMode(window, isBgLight(window));
    }

    public static void setIconMode(@NonNull Fragment fragment, boolean darkIconMode) {
        OsCompatHolder.get().setDarkIconMode(fragment, darkIconMode);
    }

    public static void setIconMode(@NonNull Context context, boolean darkIconMode) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        OsCompatHolder.get().setDarkIconMode(activity, darkIconMode);
    }

    public static void setIconMode(@NonNull Activity activity, boolean darkIconMode) {
        OsCompatHolder.get().setDarkIconMode(activity, darkIconMode);
    }

    public static void setIconMode(@NonNull Window window, boolean darkIconMode) {
        OsCompatHolder.get().setDarkIconMode(window, darkIconMode);
    }

    public static void setIconDark(@NonNull Fragment fragment) {
        setIconMode(fragment, true);
    }

    public static void setIconDark(@NonNull Context context) {
        setIconMode(context, true);
    }

    public static void setIconDark(@NonNull Activity activity) {
        setIconMode(activity, true);
    }

    public static void setIconDark(@NonNull Window window) {
        setIconMode(window, true);
    }

    public static void setIconLight(@NonNull Fragment fragment) {
        setIconMode(fragment, false);
    }

    public static void setIconLight(@NonNull Context context) {
        setIconMode(context, false);
    }

    public static void setIconLight(@NonNull Activity activity) {
        setIconMode(activity, false);
    }

    public static void setIconLight(@NonNull Window window) {
        setIconMode(window, false);
    }

    public static boolean isTransparent(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return false;
        return isTransparent(activity);
    }

    public static boolean isTransparent(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return false;
        return isTransparent(activity);
    }

    public static boolean isTransparent(@NonNull Activity activity) {
        return isTransparent(activity.getWindow());
    }

    public static boolean isTransparent(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return TransparentUtils.isTransparentStatusBarAbove21(window);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return TransparentUtils.isTransparentStatusBarAbove19(window);
        }
        return false;
    }

    public static void transparent(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        transparent(activity);
    }

    public static void transparent(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        transparent(activity);
    }

    public static void transparent(@NonNull Activity activity) {
        transparent(activity.getWindow());
    }

    public static void transparent(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransparentUtils.transparentStatusBarAbove21(window);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransparentUtils.transparentStatusBarAbove19(window);
        }
    }

    public static void unTransparent(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        unTransparent(activity);
    }

    public static void unTransparent(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        unTransparent(activity);
    }

    public static void unTransparent(@NonNull Activity activity) {
        unTransparent(activity.getWindow());
    }

    public static void unTransparent(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransparentUtils.unTransparentStatusBarAbove21(window);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransparentUtils.unTransparentStatusBarAbove19(window);
        }
    }
}
