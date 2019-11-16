package per.goweii.statusbarcompat;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/3/1
 */
class OsStatusBarCompatFlyme implements OsStatusBarCompat {

    @Override
    public boolean isDarkIconMode(@NonNull Fragment fragment) {
        if (fragment.getActivity() != null) {
           return FlymeStatusBarUtils.isStatusBarDarkIcon(fragment.getActivity());
        }
        return false;
    }

    @Override
    public boolean isDarkIconMode(@NonNull Activity activity) {
        return FlymeStatusBarUtils.isStatusBarDarkIcon(activity);
    }

    @Override
    public boolean isDarkIconMode(@NonNull Window window) {
        return FlymeStatusBarUtils.isStatusBarDarkIcon(window);
    }

    @Override
    public void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode) {
        if (fragment.getActivity() != null) {
            FlymeStatusBarUtils.setStatusBarDarkIcon(fragment.getActivity(), darkIconMode);
        }
    }

    @Override
    public void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode) {
        FlymeStatusBarUtils.setStatusBarDarkIcon(activity, darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Window window, boolean darkIconMode) {
        FlymeStatusBarUtils.setStatusBarDarkIcon(window, darkIconMode);
    }

    private static class FlymeStatusBarUtils {

        private static class MethodHolder {
            private static Method mSetStatusBarDarkIcon;

            static {
                try {
                    mSetStatusBarDarkIcon = Activity.class.getMethod("setStatusBarDarkIcon", boolean.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        private static boolean isStatusBarDarkIcon(Activity activity){
            return isStatusBarDarkIcon(activity.getWindow());
        }

        private static void setStatusBarDarkIcon(Activity activity, boolean dark) {
            if (MethodHolder.mSetStatusBarDarkIcon != null) {
                try {
                    MethodHolder.mSetStatusBarDarkIcon.invoke(activity, dark);
                } catch (Exception e) {
                    setStatusBarDarkIcon(activity.getWindow(), dark);
                }
            } else {
                setStatusBarDarkIcon(activity.getWindow(), dark);
            }
        }

        private static boolean isStatusBarDarkIcon(Window window){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return DefStatusBarUtils.isDarkIconMode(window);
            } else {
                return isMeizuStatusBarDarkIcon(window.getAttributes());
            }
        }

        private static void setStatusBarDarkIcon(Window window, boolean dark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                DefStatusBarUtils.setDarkIconMode(window, dark);
            } else {
                setMeizuStatusBarDarkIcon(window.getAttributes(), dark);
            }
        }

        private static boolean isMeizuStatusBarDarkIcon(WindowManager.LayoutParams winParams) {
            try {
                Field f = winParams.getClass().getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                f.setAccessible(true);
                int darkFlag = f.getInt(winParams);
                Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
                f2.setAccessible(true);
                int meizuFlags = f2.getInt(winParams);
                return darkFlag == (darkFlag & meizuFlags);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return false;
        }

        private static void setMeizuStatusBarDarkIcon(WindowManager.LayoutParams winParams, boolean dark) {
            try {
                Field f = winParams.getClass().getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                f.setAccessible(true);
                int bits = f.getInt(winParams);
                Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
                f2.setAccessible(true);
                int meizuFlags = f2.getInt(winParams);
                int oldFlags = meizuFlags;
                if (dark) {
                    meizuFlags |= bits;
                } else {
                    meizuFlags &= ~bits;
                }
                if (oldFlags != meizuFlags) {
                    f2.setInt(winParams, meizuFlags);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
