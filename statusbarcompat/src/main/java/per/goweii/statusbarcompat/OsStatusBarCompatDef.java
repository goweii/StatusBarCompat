package per.goweii.statusbarcompat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Window;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/3/1
 */
class OsStatusBarCompatDef implements OsStatusBarCompat {

    @Override
    public boolean isDarkIconMode(@NonNull Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            return false;
        }
        return DefStatusBarUtils.isDarkIconMode(activity.getWindow());

    }

    @Override
    public boolean isDarkIconMode(@NonNull Activity activity) {
        return DefStatusBarUtils.isDarkIconMode(activity.getWindow());
    }

    @Override
    public boolean isDarkIconMode(@NonNull Window window) {
        return DefStatusBarUtils.isDarkIconMode(window);
    }

    @Override
    public void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode) {
        if (fragment.getActivity() != null) {
            DefStatusBarUtils.setDarkIconMode(fragment.getActivity().getWindow(), darkIconMode);
        }
    }

    @Override
    public void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode) {
        DefStatusBarUtils.setDarkIconMode(activity.getWindow(), darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Window window, boolean darkIconMode) {
        DefStatusBarUtils.setDarkIconMode(window, darkIconMode);
    }
}
