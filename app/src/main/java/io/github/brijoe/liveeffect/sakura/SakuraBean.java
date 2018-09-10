package io.github.brijoe.liveeffect.sakura;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import io.github.brijoe.R;
import io.github.brijoe.liveeffect.BaseEffectBean;
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


    public SakuraBean() {
        init();
    }


    private void init() {
        mScale = Util.getRandom(1, 10) * 1.00f / 10;
        mAlpha = Util.getRandom(50, 256);
        mBitmap = Util.getScaleBitmap(R.drawable.icon_sakura, mScale, 0);
        //横纵向绘制起始区域
        float v = Util.getRandom();
        //40%概率 在横向[0.25,1]处开始绘制
        if (v < 0.4) {
            mDrawX = Util.getRandom(mXRange / 4, mXRange);
            mDrawY = -mBitmap.getHeight();
        } else {
            //60% 概率
            mDrawX = mXRange;
            mDrawY = Util.getRandom(-mBitmap.getHeight(), mYRange * 3 / 4);
        }
        mRotate = Util.getRandom(0, 359);
        mRotateSpeed = Util.getRandom(1, 4);
        mSpeed = Util.getRandom(2, 6);
    }


    @Override
    public boolean isAlive() {
        return true;
    }

    public void drawNextFrame(Canvas canvas, Paint paint) {

        //边界
        if (mDrawX < -mBitmap.getWidth() || mDrawY >= mDrawY + mBitmap.getHeight())
            init();
        //绘制方法
        paint.setAlpha(mAlpha);
        canvas.save();
        canvas.rotate(mRotate, mDrawX + mBitmap.getWidth() / 2, mDrawY + mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mDrawX, mDrawY, paint);
        canvas.restore();
        //移动
        mDrawX -= mSpeed;
        mDrawY += mSpeed;
        //旋转
        mRotate += mRotateSpeed;
    }


    public void destroy() {

    }
}
