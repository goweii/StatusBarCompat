package per.goweii.statusbarcompat.compat;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Window;

import per.goweii.statusbarcompat.utils.DarkModeUtils;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/3/1
 */
public class OsCompatOppo implements OsCompat {

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
        return OppoStatusBarUtils.isDarkIconMode(window);
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
        OppoStatusBarUtils.setDarkIconMode(window, darkIconMode);
    }

    private static class OppoStatusBarUtils {

        private static final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

        private static boolean isDarkIconMode(Window window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return DarkModeUtils.isDarkIconMode(window);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int vis = window.getDecorView().getSystemUiVisibility();
                return SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT == (SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT & vis);
            }
            return false;
        }

        private static void setDarkIconMode(Window window, boolean darkMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                DarkModeUtils.setDarkIconMode(window, darkMode);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int vis = window.getDecorView().getSystemUiVisibility();
                if (darkMode) {
                    vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                } else {
                    vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                }
                window.getDecorView().setSystemUiVisibility(vis);
            }
        }
    }

}
