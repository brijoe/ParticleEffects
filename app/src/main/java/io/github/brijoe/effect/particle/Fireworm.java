package io.github.brijoe.effect.particle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import io.github.brijoe.R;
import io.github.brijoe.effect.ParticleBase;
import io.github.brijoe.effect.util.CommonUtils;

/**
 * 萤火虫
 */

//基本特征，忽明忽暗不均匀，速度不均匀，纵向非直线运动，出现位置和消失位置不固定
public class Fireworm extends ParticleBase {

    private int mAlpha;
    private float mScale;

    private int mSpeed;

    private Bitmap mBitmap;

    private int mDrawX, mDrawY, mDisappearY;

    //横向运动幅度
    private int mXDistance;

    private int mStartX, mStartY;

    private long startTime;

    //三阶贝塞尔曲线，起点，控制点1，控制点2，终点
    private PointF pointF0, pointF1, pointF2, pointF3;


    @Override
    public void reset() {
        mScale = CommonUtils.getRandom(1, 10) * 1.00f / 10;
        mBitmap = CommonUtils.getScaleBitmap(R.drawable.icon_fireworm, mScale, 0);
        //横纵向绘制起始区域
        mDrawX = CommonUtils.getRandom(1, mXRange);
        mDrawY = CommonUtils.getRandom(mYRange * 3 / 4, mYRange);
        mStartY = mDrawY;
        //纵向消失区域
        mDisappearY = CommonUtils.getRandom(1, mYRange * 2 / 5);
        mSpeed = CommonUtils.getRandom(2, 4);
        mXDistance = CommonUtils.getRandom(100, 150);

        pointF0 = new PointF(mDrawX, mDrawY);
        pointF1 = new PointF(mDrawX + mXDistance, mYRange * 3 / 4);
        pointF2 = new PointF(mDrawX - mXDistance, mYRange / 4);
        pointF3 = new PointF(mDrawX + mXDistance, 0);

        startTime = System.currentTimeMillis();
    }


    @Override
    public boolean isLifeEnd() {
        return (mDrawY < -mBitmap.getHeight()) && (mDrawX > -2 * mYRange);
    }

    @Override
    public int getMaxNum() {
        return 30;
    }

    @Override
    public int getMaxAddDelayTime() {
        return 1000;
    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        super.drawNextFrame(canvas, paint);
        //边界
//        if (mDrawY <= -2 * mYRange)
//            reset();
        if (mDrawY == mYRange || mDrawX == 0)
            reset();
        //计算透明度
        calculateAlpha();
        //计算绘制位置
        calculatePosition();
        //绘制
        Log.d("mAlpha", mAlpha + "");
        paint.setAlpha(mAlpha);
        canvas.drawBitmap(mBitmap, mDrawX, mDrawY, paint);
    }

    private void calculateAlpha() {
        //进入消失区域,执行递减策略
        int delta = CommonUtils.getRandom(5, 10);
        if (mDrawY <= mDisappearY) {
            mAlpha -= delta;
            if (mAlpha < 0)
                mAlpha = 0;
        } else {
            //未进入消失区域之前，执行动态调整策略
            mAlpha = (int) Math.abs(255 * Math.sin(getFactor() * 10));
            float v = CommonUtils.getRandom();
            //随机在 正弦曲线基础上增加幅度微调
            if (v <= 0.5)
                mAlpha += delta;
            else
                mAlpha -= delta;
            if (mAlpha > 255)
                mAlpha = 255;
            if (mAlpha < 0)
                mAlpha = 0;
        }
    }

    private float getFactor() {
        //根据（当前时间-初始时间)*速度/总距离 计算t 因子
        return (System.currentTimeMillis() - startTime) / 16f * mSpeed * 1f / mStartY * 1f;
    }
    //三阶贝塞尔曲线计算位置

    private void calculatePosition() {
        PointF point = CommonUtils.getPointForCubic(getFactor(), pointF0, pointF1, pointF2, pointF3);
        mDrawX = (int) point.x;
        mDrawY = (int) point.y;
    }
}
