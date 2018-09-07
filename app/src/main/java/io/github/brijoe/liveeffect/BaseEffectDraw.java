package io.github.brijoe.liveeffect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 所有特效的父类，抽象公共方法
 */
public abstract class BaseEffectDraw<T extends BaseEffectBean> {

    protected ArrayList<T> effectBeanList;
    protected EffectHandler mEffectHandler;
    protected int maxNum;

    protected Random mRandom = new Random();


    protected final int ADD_EFFECT_BEAN = 0;
    protected final HandlerThread mHandlerThread;

    protected Bitmap originBitmap;
    public static List<Bitmap> mBitmapsList = new ArrayList<>();

    protected class EffectHandler extends Handler {

        public EffectHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADD_EFFECT_BEAN:
                    addEffectBean();
                    break;
            }
        }
    }

    public BaseEffectDraw(int maxNum, int resourceId) {
        this.maxNum = maxNum;
        if (resourceId != 0) {
            originBitmap = Util.getBitmap(resourceId);
        }
        mHandlerThread = new HandlerThread(getClass().getSimpleName());
        mHandlerThread.start();
        effectBeanList = new ArrayList<>();
        mEffectHandler = new EffectHandler(mHandlerThread.getLooper());
        init();
    }

    private void init() {
        mBitmapsList.clear();
        initEffectBitmaps();

        if (originBitmap != null) {
            originBitmap.recycle();
        }

        mEffectHandler.sendEmptyMessage(ADD_EFFECT_BEAN);
    }

    /**
     * 初始化每个特效bitmap，添加进mBitmaps
     */
    public abstract void initEffectBitmaps();


    /**
     * 初始化每个特效bean，添加进mBitmapsList
     */
    public abstract void addEffectBean();


    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < effectBeanList.size(); i++) {
            effectBeanList.get(i).drawNextFrame(canvas, paint);
        }
    }

    /**
     * 销毁所有的数据，帮助gc
     */
    public void destroy() {
        if (mEffectHandler != null) {
            mEffectHandler.removeCallbacksAndMessages(null);
            mEffectHandler = null;
            mHandlerThread.quit();
        }
        if (effectBeanList != null && effectBeanList.size() > 0) {
            for (T bean : effectBeanList) {
                bean.destroy();
            }
        }
        if (mBitmapsList != null && mBitmapsList.size() > 0) {
            for (Bitmap bitmap : mBitmapsList) {
                bitmap.recycle();
            }
        }
    }
}
