package io.github.brijoe.liveeffect.meteor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import io.github.brijoe.R;
import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.util.Util;

/**
 * 流星Bean
 */
public class MeteorBean extends BaseEffectBean {

    //粒子bitmap
    private Bitmap mBitmap;
    //缩放和旋转
    private float mScale, mRotate;
    //基本属性：
    //绘制点坐标(x,y)，当前透明度，透明度减少速度 值/帧，运动速度
    private int mDrawX, mDrawY, mAlpha, mAlphaReduce, speed;

    public MeteorBean() {
        init();
    }

    private void init() {
        mScale = Util.getRandom(1, 10) * 1.00f / 10;
        mRotate = 45.0f;
        mBitmap = Util.getScaleBitmap(R.drawable.icon_meteor, mScale, mRotate);
        speed = Util.getRandom(12, 21);
        float v = Util.getRandom();
        //25% 概率固定值
        if (v > 0.75) {
            mAlpha = 255;
            mAlphaReduce = 0;
        }
        //75% 概率在随机值
        else{
            mAlpha = Util.getRandom(200, 255);
            mAlphaReduce = Util.getRandom(10, 15);
        }
        //40%概率 在横向[0.25,1]处开始绘制
        if (v < 0.4) {
            mDrawX = Util.getRandom(mXRange / 4, mXRange);
            mDrawY = -mBitmap.getHeight();
        } else {
            //60% 概率
            mDrawX= mXRange;
            mDrawY = Util.getRandom(-mBitmap.getHeight(), mYRange / 5 * 2 - mBitmap.getHeight());
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        //边界条件，x方法移出绘制区域或者透明度为0
        if (mDrawX <= -mBitmap.getWidth() || mAlpha == 0) {
            init();
        }
        //移到绘制区域横向三分之一区域时，执行透明度渐变
        if (mDrawX < mXRange / 3) {
            mAlpha = mAlpha - mAlphaReduce;
            if (mAlpha <= 0) {
                mAlpha = 0;
            }
        }
        //执行绘制
        paint.setAlpha(mAlpha);
        canvas.drawBitmap(mBitmap, mDrawX, mDrawY, paint);
        //移动
        mDrawX = mDrawX - speed;
        mDrawY = mDrawY + speed;

    }

    @Override
    public void destroy() {

    }
}
