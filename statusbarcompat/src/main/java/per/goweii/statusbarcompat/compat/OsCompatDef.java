package per.goweii.statusbarcompat.compat;

import android.app.Activity;
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
public class OsCompatDef implements OsCompat {

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
        return DarkModeUtils.isDarkIconMode(window);
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
        DarkModeUtils.setDarkIconMode(window, darkIconMode);
    }
}
