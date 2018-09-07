package io.github.brijoe.liveeffect;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 基本粒子的属性行为定义
 */
public abstract class BaseEffectBean {

    protected Paint mPaint = new Paint();
    protected int mXRange = LiveEffectsView.getViewWidth();
    protected int mYRange = LiveEffectsView.getViewHeight();

    /**
     * 绘制下一帧
     *
     * @param canvas
     * @param paint
     */
    public abstract void drawNextFrame(Canvas canvas, Paint paint);

    /**
     * 释放资源
     */
    public abstract void destroy();

}
