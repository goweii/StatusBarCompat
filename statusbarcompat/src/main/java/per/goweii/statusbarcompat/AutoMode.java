package per.goweii.statusbarcompat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CuiZhen
 * @date 2019/11/30
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class AutoMode {

    private static AutoMode sInstance = null;

    static void register(Application application) {
        if (sInstance == null) {
            sInstance = new AutoMode(application);
        }
    }

    static void unregister() {
        if (sInstance == null) {
            return;
        }
        for (Target target : sInstance.mTargets) {
            ViewTreeObserver observer = target.activity.getWindow().getDecorView().getViewTreeObserver();
            observer.removeOnPreDrawListener(target.onPreDrawListener);
        }
        sInstance.mTargets.clear();
        sInstance = null;
        StatusBarCapture.get().recycle();
    }

    private final List<Target> mTargets = new ArrayList<>();
    private Target currTarget = null;

    private AutoMode(Application application) {
        Application.ActivityLifecycleCallbacks callback = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (currTarget == null) {
                            currTarget = findMode(this);
                        } else {
                            if (currTarget.onPreDrawListener != this) {
                                currTarget = findMode(this);
                            }
                        }
                        if (currTarget == null) {
                            return true;
                        }
                        if (currTarget.activity instanceof Exclude) {
                            return true;
                        }
                        View decor = activity.getWindow().getDecorView();
                        Object drawForCaptureTag = decor.getTag(R.id.statusbarcompat_draw_for_capture);
                        if (drawForCaptureTag instanceof Boolean) {
                            boolean drawForCapture = (boolean) drawForCaptureTag;
                            if (drawForCapture) {
                                decor.setTag(R.id.statusbarcompat_draw_for_capture, false);
                                return true;
                            }
                        }
                        decor.setTag(R.id.statusbarcompat_draw_for_capture, true);
                        boolean bgLight = StatusBarCompat.isBgLight(currTarget.activity);
                        if (currTarget.darkIcon != bgLight) {
                            currTarget.darkIcon = bgLight;
                            StatusBarCompat.setIconMode(currTarget.activity, bgLight);
                        }
                        return true;
                    }
                };
                final View decorView = activity.getWindow().getDecorView();
                decorView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
                final Target target = new Target(activity, onPreDrawListener);
                mTargets.add(target);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                for (int i = mTargets.size() - 1; i >= 0; i--) {
                    Target target = mTargets.get(i);
                    if (target.activity == activity) {
                        target.activity.getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(target.onPreDrawListener);
                        mTargets.remove(i);
                        break;
                    }
                }
            }
        };
        application.registerActivityLifecycleCallbacks(callback);
    }

    private Target findMode(ViewTreeObserver.OnPreDrawListener onPreDrawListener) {
        for (int i = mTargets.size() - 1; i >= 0; i--) {
            Target mode = mTargets.get(i);
            if (mode.onPreDrawListener == onPreDrawListener) {
                return mode;
            }
        }
        return null;
    }

    private static final class Target {
        private final Activity activity;
        private final ViewTreeObserver.OnPreDrawListener onPreDrawListener;
        private boolean darkIcon = false;

        public Target(Activity activity, ViewTreeObserver.OnPreDrawListener onPreDrawListener) {
            this.activity = activity;
            this.onPreDrawListener = onPreDrawListener;
        }
    }

    public interface Exclude {
    }
}
