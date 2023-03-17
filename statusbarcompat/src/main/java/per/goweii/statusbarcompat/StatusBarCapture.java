package per.goweii.statusbarcompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author CuiZhen
 * @date 2019/11/30
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class StatusBarCapture {
    private static volatile StatusBarCapture sInstance = null;

    private int mStatusBarHeight = 0;
    private Bitmap mBitmap = null;
    private Canvas mCanvas = null;

    private StatusBarCapture() {
    }

    public static StatusBarCapture get() {
        if (sInstance == null) {
            synchronized (StatusBarCapture.class) {
                if (sInstance == null) {
                    sInstance = new StatusBarCapture();
                }
            }
        }
        return sInstance;
    }

    public void recycle() {
        if (mCanvas != null) {
            mCanvas.setBitmap(null);
            mCanvas = null;
        }
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
        sInstance = null;
    }

    private boolean ensureBitmapCreate(@NonNull View decor) {
        if (mStatusBarHeight == 0) {
            mStatusBarHeight = StatusBarCompat.getHeight(decor.getContext());
        }
        if (mStatusBarHeight <= 0) {
            if (mBitmap != null) {
                mBitmap.recycle();
                mBitmap = null;
            }
            mCanvas = null;
            return false;
        }
        if (mBitmap != null) {
            if (mBitmap.isRecycled()) {
                mBitmap = null;
                mCanvas = null;
            } else {
                if (mBitmap.getWidth() != mStatusBarHeight || mBitmap.getHeight() != mStatusBarHeight) {
                    mBitmap.recycle();
                    mBitmap = null;
                    mCanvas = null;
                }
            }
        }
        if (mBitmap == null) {
            try {
                //noinspection SuspiciousNameCombination
                mBitmap = Bitmap.createBitmap(mStatusBarHeight, mStatusBarHeight, Bitmap.Config.RGB_565);
                mCanvas = new Canvas(mBitmap);
            } catch (OutOfMemoryError ignore) {
            }
        }
        return mBitmap != null && mCanvas != null;
    }

    private boolean isViewLaidOut(@NonNull View decor) {
        return decor.getWidth() > 0 && decor.getHeight() > 0;
    }

    @Nullable
    public Bitmap capture(@NonNull View decor) {
        if (!isViewLaidOut(decor)) return null;
        if (!ensureBitmapCreate(decor)) return null;
        mCanvas.save();
        mCanvas.scale((float) mBitmap.getWidth() / (float) decor.getWidth(), 1.0f);
        decor.draw(mCanvas);
        mCanvas.restore();
        return mBitmap;
    }

}
