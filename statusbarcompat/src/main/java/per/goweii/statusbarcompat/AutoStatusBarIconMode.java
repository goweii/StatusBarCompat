package per.goweii.statusbarcompat;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

public class AutoStatusBarIconMode {
    public interface Exclude {
    }

    static void register(@NonNull final Window window) {
        final View decorView = window.getDecorView();
        if (decorView.getViewTreeObserver().isAlive()) {
            decorView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (decorView.getViewTreeObserver().isAlive()) {
                        decorView.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    Object drawForCaptureTag = decorView.getTag(R.id.statusbarcompat_draw_for_capture);
                    if (drawForCaptureTag instanceof Boolean) {
                        boolean drawForCapture = (boolean) drawForCaptureTag;
                        if (drawForCapture) {
                            decorView.setTag(R.id.statusbarcompat_draw_for_capture, false);
                            return true;
                        }
                    }
                    decorView.setTag(R.id.statusbarcompat_draw_for_capture, true);
                    StatusBarCompat.setIconMode(window);
                    return true;
                }
            });
            if (decorView.getWidth() > 0 && decorView.getHeight() > 0) {
                decorView.invalidate();
            }
        }
    }
}
