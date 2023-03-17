package per.goweii.statusbarcompat;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

public class RealtimeStatusBarIconMode {
    private final Window mWindow;
    private final View mDecorView;

    private final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            Object drawForCaptureTag = mDecorView.getTag(R.id.statusbarcompat_draw_for_capture);
            if (drawForCaptureTag instanceof Boolean) {
                boolean drawForCapture = (boolean) drawForCaptureTag;
                if (drawForCapture) {
                    mDecorView.setTag(R.id.statusbarcompat_draw_for_capture, false);
                    return true;
                }
            }
            mDecorView.setTag(R.id.statusbarcompat_draw_for_capture, true);
            StatusBarCompat.setIconMode(mWindow);
            return true;
        }
    };

    private RealtimeStatusBarIconMode(@NonNull Window window) {
        this.mWindow = window;
        this.mDecorView = window.getDecorView();
    }

    @NonNull
    static RealtimeStatusBarIconMode with(@NonNull final Window window) {
        return new RealtimeStatusBarIconMode(window);
    }

    public void register() {
        if (mDecorView.getViewTreeObserver().isAlive()) {
            mDecorView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
        }
    }

    public void unregister() {
        if (mDecorView.getViewTreeObserver().isAlive()) {
            mDecorView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
        }
    }

    public interface Exclude {
    }
}
