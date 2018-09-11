package io.github.brijoe.liveeffect.sakura;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import io.github.brijoe.R;
import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.util.BezierUtil;
import io.github.brijoe.liveeffect.util.Util;

/**
 * 樱花
 *
 * @author Brijoe
 */

//从画布右边向左方向移动，运动曲线随机，透明度有变化，旋转角度缓动
public class SakuraBean extends BaseEffectBean {

    private Bitmap mBitmap;

    private float mScale;

    private int mAlpha;

    private int mRotate;

    private int mRotateSpeed;

    private int mSpeed;


    private int mDrawX, mDrawY;

    private int startX, startY, endX, endY;

    //二阶贝塞尔曲线，起点，控制点,终点
    private PointF pointF0, pointF1, pointF2;

    private long startTime;

    @Override
    protected void reset() {
        mScale = Util.getRandom(1, 10) * 1.00f / 10;
        mAlpha = Util.getRandom(50, 256);
        mBitmap = Util.getScaleBitmap(R.drawable.icon_sakura, mScale, 0);
        //横纵向绘制起始区域
        float v = Util.getRandom();
        //50%概率 起点在top,终点在left
        if (v <= 0.5) {
            startX = Util.getRandom(mXRange / 4, mXRange);
            startY = -mBitmap.getHeight();
            endX = -mBitmap.getWidth();
            endY = Util.getRandom(mYRange / 5, mYRange);
            pointF1 = new PointF(Util.getRandom(1, startX), Util.getRandom(1, endY));
        } else {
            //50%概率 起点在right,终点在bottom
            startX = mXRange;
            startY = Util.getRandom(-mBitmap.getHeight(), mYRange * 3 / 5);
            endX = Util.getRandom(0, mXRange * 3 / 4);
            endY = mYRange + mBitmap.getHeight();
            pointF1 = new PointF(Util.getRandom(1, startX), Util.getRandom(startY, mYRange));
        }

        pointF0 = new PointF(startX, startY);
        pointF2 = new PointF(endX, endY);


        mRotate = Util.getRandom(0, 359);
        mRotateSpeed = Util.getRandom(1, 4);
        mSpeed = Util.getRandom(1, 2);
        startTime = System.currentTimeMillis();
    }


    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {


        //继续运动 让控制线程有机会移除
        if (mDrawX <= -mBitmap.getWidth()) {
            mDrawX -= mSpeed;
            return;
        }
        if (mDrawY >= (mYRange + mBitmap.getHeight())) {
            mDrawY += mSpeed;
            return;
        }
        //边界
        if (mDrawX <= -1.5 * mXRange || mDrawY >= 1.5 * mYRange) {
            reset();
            return;
        }
        //绘制方法
        calculatePosition();
        paint.setAlpha(mAlpha);
        canvas.save();
        canvas.rotate(mRotate, mDrawX + mBitmap.getWidth() / 2, mDrawY + mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mDrawX, mDrawY, paint);
        canvas.restore();
        //旋转
        mRotate += mRotateSpeed;
    }


    private float getFactor() {
        //根据（当前时间初-始时间)*速度/总距离 计算t 因子
        return (System.currentTimeMillis() - startTime) / 16f * mSpeed * 1f / (startX - endX) * 1f;
    }

    //二阶贝塞尔曲线计算位置
    private void calculatePosition() {
        PointF point = BezierUtil.getPointForQuadratic(getFactor(), pointF0, pointF1, pointF2);
        mDrawX = (int) point.x;
        mDrawY = (int) point.y;
    }

    @Override
    public boolean isLifeEnd() {
        return (mDrawX < -mBitmap.getWidth()) || (mDrawY > (mYRange + mBitmap.getHeight()));
    }
}
