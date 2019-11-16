package per.goweii.statusbarcompat;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/3/1
 */
class OsStatusBarCompatOppo implements OsStatusBarCompat {

    @Override
    public boolean isDarkIconMode(@NonNull Fragment fragment) {
        if (fragment.getActivity() != null) {
            isDarkIconMode(fragment.getActivity());
        }
        return false;
    }

    @Override
    public boolean isDarkIconMode(@NonNull Activity activity) {
        return isDarkIconMode(activity.getWindow());
    }

    @Override
    public boolean isDarkIconMode(@NonNull Window window) {
        return OppoStatusBarUtils.isDarkIconMode(window);
    }

    @Override
    public void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode) {
        if (fragment.getActivity() != null) {
            setDarkIconMode(fragment.getActivity(), darkIconMode);
        }
    }

    @Override
    public void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode) {
        setDarkIconMode(activity.getWindow(), darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Window window, boolean darkIconMode) {
        OppoStatusBarUtils.setDarkIconMode(window, darkIconMode);
    }

    private static class OppoStatusBarUtils {

        private static final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

        private static boolean isDarkIconMode(Window window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return DefStatusBarUtils.isDarkIconMode(window);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int vis = window.getDecorView().getSystemUiVisibility();
                return SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT == (SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT & vis);
            }
            return false;
        }

        private static void setDarkIconMode(Window window, boolean darkMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                DefStatusBarUtils.setDarkIconMode(window, darkMode);
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
