package per.goweii.statusbarcompat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        @SuppressLint("InternalInsetResource")
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

    /**
     * 全局注册，所有Activity将默认实时切换暗亮色模式
     * 可实现{@link RealtimeStatusBarIconMode.Exclude}接口取消
     */
    public static void registerRealtimeIconMode(@NonNull Application application) {
        application.registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (activity instanceof RealtimeStatusBarIconMode.Exclude) {
                    registerRealtimeIconMode(activity);
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                unregisterRealtimeIconMode(activity);
            }
        });
    }

    public static void registerRealtimeIconMode(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        registerRealtimeIconMode(activity);
    }

    public static void registerRealtimeIconMode(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        registerRealtimeIconMode(activity);
    }

    public static void registerRealtimeIconMode(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return;
        registerRealtimeIconMode(window);
    }

    public static void registerRealtimeIconMode(final @NonNull Window window) {
        final View decorView = window.getDecorView();
        Object tag = decorView.getTag(R.id.statusbarcompat_realtime_icon_mode);
        if (tag instanceof RealtimeStatusBarIconMode) {
            RealtimeStatusBarIconMode realtimeStatusBarIconMode = (RealtimeStatusBarIconMode) tag;
            realtimeStatusBarIconMode.unregister();
            decorView.setTag(R.id.statusbarcompat_realtime_icon_mode, null);
        }
        RealtimeStatusBarIconMode realtimeStatusBarIconMode = RealtimeStatusBarIconMode.with(window);
        realtimeStatusBarIconMode.register();
        decorView.setTag(R.id.statusbarcompat_realtime_icon_mode, realtimeStatusBarIconMode);
    }

    public static void unregisterRealtimeIconMode(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        unregisterRealtimeIconMode(activity);
    }

    public static void unregisterRealtimeIconMode(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        unregisterRealtimeIconMode(activity);
    }

    public static void unregisterRealtimeIconMode(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return;
        unregisterRealtimeIconMode(window);
    }

    public static void unregisterRealtimeIconMode(final @NonNull Window window) {
        final View decorView = window.getDecorView();
        Object tag = decorView.getTag(R.id.statusbarcompat_realtime_icon_mode);
        if (tag instanceof RealtimeStatusBarIconMode) {
            RealtimeStatusBarIconMode realtimeStatusBarIconMode = (RealtimeStatusBarIconMode) tag;
            realtimeStatusBarIconMode.unregister();
            decorView.setTag(R.id.statusbarcompat_realtime_icon_mode, null);
        }
    }

    /**
     * 全局注册，所有Activity将默认自动切换暗亮色模式
     * 可实现{@link AutoStatusBarIconMode.Exclude}接口取消
     */
    public static void setAutoIconMode(@NonNull Application application) {
        application.registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (activity instanceof AutoStatusBarIconMode.Exclude) {
                    setAutoIconMode(activity);
                }
            }
        });
    }

    public static void setAutoIconMode(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        setAutoIconMode(activity);
    }

    public static void setAutoIconMode(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        setAutoIconMode(activity);
    }

    public static void setAutoIconMode(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return;
        setAutoIconMode(window);
    }

    public static void setAutoIconMode(final @NonNull Window window) {
        AutoStatusBarIconMode.register(window);
    }

    public static boolean setIconMode(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return false;
        return setIconMode(activity);
    }

    public static boolean setIconMode(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return false;
        return setIconMode(activity);
    }

    public static boolean setIconMode(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return false;
        return setIconMode(window);
    }

    public static boolean setIconMode(@NonNull Window window) {
        boolean darkIconMode = isBgLight(window);
        setIconMode(window, darkIconMode);
        return darkIconMode;
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

    public static void hideActionBar(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        hideActionBar(activity);
    }

    public static void hideActionBar(@NonNull Context context) {
        Activity activity = ContextUtils.getActivity(context);
        if (activity == null) return;
        hideActionBar(activity);
    }

    public static void hideActionBar(@NonNull Activity activity) {
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            android.support.v7.app.ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.hide();
            }
        }
    }
}
