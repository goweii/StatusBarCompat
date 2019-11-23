package per.goweii.statusbarcompat.compat;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import per.goweii.statusbarcompat.utils.DarkModeUtils;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/3/1
 */
public class OsCompatMiui implements OsCompat {

    @Override
    public boolean isDarkIconMode(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            return false;
        }
        return isDarkIconMode(activity);
    }

    @Override
    public boolean isDarkIconMode(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) {
            return false;
        }
        return isDarkIconMode(window);
    }

    @Override
    public boolean isDarkIconMode(@NonNull Window window) {
        return MiuiStatusBarUtils.isDarkIconMode(window);
    }

    @Override
    public void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            return;
        }
        setDarkIconMode(activity, darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        setDarkIconMode(window, darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Window window, boolean darkIconMode) {
        MiuiStatusBarUtils.setDarkIconMode(window, darkIconMode);
    }

    private static class MiuiStatusBarUtils {

        private static boolean isDarkIconMode(Window window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return DarkModeUtils.isDarkIconMode(window);
            } else {
                return isMiuiDarkIconMode(window);
            }
        }

        private static boolean isMiuiDarkIconMode(Window window) {
            Class<? extends Window> clazz = window.getClass();
            try {
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("getExtraFlags");
                int miuiFlags = (int) extraFlagField.invoke(window);
                return darkModeFlag == (darkModeFlag & miuiFlags);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        private static void setDarkIconMode(Window window, boolean darkIconMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                DarkModeUtils.setDarkIconMode(window, darkIconMode);
            } else {
                setMiuiDarkIconMode(window, darkIconMode);
            }
        }

        private static void setMiuiDarkIconMode(Window window, boolean darkIconMode) {
            Class<? extends Window> clazz = window.getClass();
            try {
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(window, darkIconMode ? darkModeFlag : 0, darkModeFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
