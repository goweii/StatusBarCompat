package per.goweii.statusbarcompat.compat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Window;

/**
 * @author Cuizhen
 * @date 2019/3/1
 */
public interface OsCompat {
    boolean isDarkIconMode(@NonNull Fragment fragment);
    boolean isDarkIconMode(@NonNull Activity activity);
    boolean isDarkIconMode(@NonNull Window window);
    void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode);
    void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode);
    void setDarkIconMode(@NonNull Window window, boolean darkIconMode);
}
