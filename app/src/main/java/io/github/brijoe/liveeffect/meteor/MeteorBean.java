package io.github.brijoe.liveeffect.meteor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.Util;

/**
 * 流星Bean
 */
public class MeteorBean extends BaseEffectBean {

    /**
     * 当前坐标
     */
    public Point mCurrentPoint;

    /**
     * 透明度
     */
    private int mAlpha;
    /**
     * 透明度
     */
    private int alphaReduce;

    /**
     * 流星速度
     */
    private int speed;
    private Bitmap mBitmap;


    public MeteorBean() {
        reset();
    }

    private void reset() {
        mAlpha = Util.getRandom(200, 256);
        speed = Util.getRandom(12, 21);
        alphaReduce = Util.getRandom(10, 15);
        mPaint.setAlpha(mAlpha);
        mBitmap = MeteorDraw.mBitmapsList.get(Util.getRandom(0, MeteorDraw.mBitmapsList.size() - 1));
        float v = Util.getRandom();
        if (v > 0.75) {
            mAlpha = 255;
            alphaReduce = 0;
        }
        if (v < 0.4) {
            mCurrentPoint = new Point(Util.getRandom(mXRange / 4, mXRange / 4 + mXRange / 4 * 3), -mBitmap.getHeight());
        } else {
            mCurrentPoint = new Point(mXRange, Util.getRandom(-mBitmap.getHeight(), mYRange / 5 * 2 - mBitmap.getHeight()));
        }
    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        if (mBitmap != null) {
            mCurrentPoint.x = mCurrentPoint.x - speed;
            mCurrentPoint.y = mCurrentPoint.y + speed;
            if (mCurrentPoint.x <= -mBitmap.getWidth() || mAlpha == 0) {
                reset();
                return;
            }
            if (mCurrentPoint.x < mXRange / 3) {
                mAlpha = mAlpha - alphaReduce;
                if (mAlpha <= 0) {
                    mAlpha = 0;
                }
            }
            mPaint.setAlpha(mAlpha);
            canvas.drawBitmap(mBitmap, mCurrentPoint.x, mCurrentPoint.y, mPaint);
        } else {
            reset();
        }
    }

    @Override
    public void destroy() {

    }
}
