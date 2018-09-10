package io.github.brijoe.liveeffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 承载动态效果的View extends SurfaceView
 */
public class LiveEffectsView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;

    private DrawThread mDrawThread;
    private static int mViewWidth;
    private static int mViewHeight;
    private BaseEffectDraw mEffectDraw;
    private Paint mPaint = new Paint();

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
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mDrawThread = new DrawThread();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
//        if (mType != null) {
//            mType.onSizeChanged(mContext, w, h);
//        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("LiveEffectsView", "surfaceCreated");
        EffectsManager.getInstance().setEffectView(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("LiveEffectsView", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("LiveEffectsView", "surfaceDestroyed");
//        stop();
    }


    /**
     * 设置显示View
     *
     * @param drawView
     */
    public void setDrawView(BaseEffectDraw drawView) {
        if (drawView == null)
            throw new IllegalArgumentException("DrawView 不能为空");
        this.mEffectDraw = drawView;
        start();
    }

    private void start() {
        //执行初始化操作
        if (mDrawThread != null && mEffectDraw != null) {
            mEffectDraw.init();
            if (!mDrawThread.isAlive())
                mDrawThread.start();
            mDrawThread.setRunning(true);
        }

    }

    /**
     * 释放资源操作
     */
    public void release() {
        //销毁绘制线程
        if (mDrawThread != null) {
            mDrawThread.setRunning(false);
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


    /**
     * 绘制线程
     */
    private class DrawThread extends Thread {

        // 用来停止线程的标记
        private boolean isRunning = false;

        public void setRunning(boolean running) {
            isRunning = running;
        }

        @Override
        public void run() {
            Canvas canvas;
            // 无限循环绘制
            while (isRunning) {
                if (mEffectDraw != null && mViewWidth != 0 && mViewHeight != 0) {
                    canvas = mSurfaceHolder.lockCanvas();
                    if (canvas != null) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        mEffectDraw.draw(canvas, mPaint);
                        if (isRunning) {
                            mSurfaceHolder.unlockCanvasAndPost(canvas);
                        } else {
                            // 停止线程
                            break;
                        }
                        // sleep
                        SystemClock.sleep(16);
                    }
                }
            }
        }
    }


}
