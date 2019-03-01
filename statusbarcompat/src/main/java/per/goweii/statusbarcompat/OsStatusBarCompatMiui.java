package per.goweii.statusbarcompat;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/3/1
 */
class OsStatusBarCompatMiui implements OsStatusBarCompat {

    private OsStatusBarCompatDef mOsStatusBarCompatDef = new OsStatusBarCompatDef();

    @Override
    public void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode) {
        MiuiStatusBarUtils.setDarkIconMode(activity.getWindow(), darkIconMode);
        mOsStatusBarCompatDef.setDarkIconMode(activity, darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode) {
        if (fragment.getActivity() != null) {
            MiuiStatusBarUtils.setDarkIconMode(fragment.getActivity().getWindow(), darkIconMode);
        }
        mOsStatusBarCompatDef.setDarkIconMode(fragment, darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Window window, boolean darkIconMode) {
        MiuiStatusBarUtils.setDarkIconMode(window, darkIconMode);
        mOsStatusBarCompatDef.setDarkIconMode(window, darkIconMode);
    }

    private static class MiuiStatusBarUtils{
        private static void setDarkIconMode(Window window, boolean darkIconMode) {
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
