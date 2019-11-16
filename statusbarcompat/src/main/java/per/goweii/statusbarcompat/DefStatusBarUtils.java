package per.goweii.statusbarcompat;

import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * @author CuiZhen
 * @date 2019/11/17
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
class DefStatusBarUtils {

    static boolean isDarkIconMode(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int ui = window.getDecorView().getSystemUiVisibility();
            final int flag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            return flag == (flag & ui);
        }
        return false;
    }

    static void setDarkIconMode(Window window, boolean darkIconMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int ui = window.getDecorView().getSystemUiVisibility();
            if (darkIconMode) {
                window.getDecorView().setSystemUiVisibility(ui | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(ui & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
