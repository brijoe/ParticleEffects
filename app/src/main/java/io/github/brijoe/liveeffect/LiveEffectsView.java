package io.github.brijoe.liveeffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 承载动态效果的View extends SurfaceView
 */
public class LiveEffectsView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "LiveEffectsView";

    private SurfaceHolder mSurfaceHolder;

    private DrawThread mDrawThread;
    private static int mViewWidth;
    private static int mViewHeight;
    private BaseEffectDraw mEffectDraw;
    private Paint mPaint = new Paint();

    private Canvas mCanvas;

    public LiveEffectsView(Context context) {
        this(context, null);
    }

    public LiveEffectsView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public LiveEffectsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public static int getViewWidth() {
        return mViewWidth;
    }

    public static int getViewHeight() {

        return mViewHeight;
    }


    private void init() {
        EffectsManager.getInstance().setEffectView(this);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        this.setZOrderOnTop(true);
        mDrawThread = new DrawThread();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated");
        if (mDrawThread != null) {
            mDrawThread.startDraw();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed");
        if (mDrawThread != null) {
            mDrawThread.stopDraw();
        }
    }


    /**
     * 设置显示View
     *
     * @param drawView
     */
    public void setDrawView(@NonNull BaseEffectDraw drawView) {
        this.mEffectDraw = drawView;
        start();
    }

    private void start() {
        //执行初始化操作
        if (mDrawThread != null && mEffectDraw != null) {
            mEffectDraw.init();
            mDrawThread.startDraw();
        }

    }

    /**
     * 释放资源操作
     */
    public void release() {
        //销毁绘制线程
        if (mDrawThread != null) {
            mDrawThread.stopDraw();
            mDrawThread = null;
        }
        //销毁特效View
        if (mEffectDraw != null)
            mEffectDraw.destroy();
        //fix memory leak
        if (mSurfaceHolder != null) {
            Surface surface = mSurfaceHolder.getSurface();
            if (surface != null)
                surface.release();
        }

    }


    private void drawOnCanvas() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                mEffectDraw.draw(mCanvas, mPaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "onDetachedFromWindow");
    }

    //绘制线程
    private class DrawThread extends Thread {

        public DrawThread() {
            super("DrawThread");
        }

        // 用来停止线程的标记
        private volatile boolean isRunning = false;

        private AtomicBoolean hasStarted = new AtomicBoolean(false);

        public void startDraw() {
            if (hasStarted.compareAndSet(false, true)) {
                super.start();
            }
            isRunning = true;
        }

        public void stopDraw() {
            isRunning = false;
        }

        @Override
        public void run() {
            // 无限循环绘制
            while (true) {
                if (isRunning) {
                    drawOnCanvas();
                }
                SystemClock.sleep(5);
            }
        }
    }


}
