package io.github.brijoe.liveeffect;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 基本粒子的属性行为定义
 */
public abstract class BaseEffectBean {

    protected int mXRange = LiveEffectsView.getViewWidth();
    protected int mYRange = LiveEffectsView.getViewHeight();


    public BaseEffectBean() {
        reset();
    }


    /**
     * @return
     */
    protected abstract boolean isLifeEnd();


    /**
     * 属性设置方法
     */
    protected abstract void reset();


    /**
     * 绘制下一帧
     *
     * @param canvas
     * @param paint
     */
    public abstract void drawNextFrame(Canvas canvas, Paint paint);

}
