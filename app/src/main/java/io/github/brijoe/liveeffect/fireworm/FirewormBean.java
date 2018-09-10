package io.github.brijoe.liveeffect.fireworm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import io.github.brijoe.R;
import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.util.Util;

/**
 * 萤火虫
 */

//基本特征，忽明忽暗，速度不均匀，纵向非直线运动，出现位置和消失位置不固定
public class FirewormBean extends BaseEffectBean {

    private int mAlpha;
    private float mScale;

    private int mSpeed;

    private Bitmap mBitmap;

    private int mDrawX, mDrawY, mDisappearY;

    //横向运动幅度
    private int mXDistance;


    @Override
    public void reset() {
        mScale = Util.getRandom(1, 10) * 1.00f / 10;
//        mAlpha = Util.getRandom(50, 256);
        mBitmap = Util.getScaleBitmap(R.drawable.icon_fireworm, mScale, 0);
        //横纵向绘制起始区域
        mDrawX = Util.getRandom(1, mXRange);
        mDrawY = Util.getRandom(mYRange * 3 / 4, mYRange);
        //纵向消失区域
        mDisappearY = Util.getRandom(1, mYRange * 2 / 5);
        mSpeed = Util.getRandom(2, 6);
        mXDistance = Util.getRandom(2, 3);
    }


    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        //边界
        if (mDrawY <= -2 * mYRange)
            reset();
        //计算透明度
        calculateAlpha();
        //计算X轴绘制位置
        calculateXAxis();
        //绘制
        paint.setAlpha(mAlpha);
        canvas.drawBitmap(mBitmap, mDrawX, mDrawY, paint);
        //移动
        mDrawY -= mSpeed;
    }

    //透明度变化
    private void calculateAlpha() {
        //进入消失区域
        if (mDrawY <= mDisappearY) {
            mAlpha -= 5;
            if (mAlpha < 0)
                mAlpha = 0;
        } else {
            double x = mYRange - mDrawY;
            mAlpha = (int) (251 * Math.abs(Math.sin(x / 100)) + 5);

        }
    }

    //X轴横向变化
    private void calculateXAxis() {
        double x = mYRange - mDrawY;
        mDrawX += mXDistance * Math.sin(x / 70);
    }

    @Override
    public boolean isLifeEnd() {
        return (mDrawY < -mBitmap.getHeight()) && (mDrawX > -2 * mYRange);
    }
}
