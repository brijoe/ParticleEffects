package io.github.brijoe.effect;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.CallSuper;

/**
 * 基本粒子效果特性定义
 */
public abstract class ParticleBase {

    protected int mXRange = ParticleView.getViewWidth();
    protected int mYRange = ParticleView.getViewHeight();


    public ParticleBase() {
        reset();
    }

    /**
     * 属性重设方法
     */
    protected abstract void reset();

    /**
     * 粒子生命周期是否结束
     *
     * @return
     */
    protected abstract boolean isLifeEnd();


    /**
     * 粒子最大数量，配置越大，粒子数量越多
     */
    public abstract int getMaxNum();

    /**
     * 粒子出现速率，最大添加延迟时间,配置越小，出现速度越快
     */
    public abstract int getMaxAddDelayTime();

    /**
     * 绘制下一帧 方法
     *
     * @param canvas 画布
     * @param paint  画笔
     */
    @CallSuper
    public void drawNextFrame(Canvas canvas, Paint paint) {
        if (canvas == null || paint == null) {
            return;
        }
    }
}
