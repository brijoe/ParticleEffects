package io.github.brijoe.liveeffect.rain;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.Util;


/**
 * 雨滴下落
 */
class RainBean extends BaseEffectBean {

    /**
     * 起点,当前坐标
     */

    private Point mStartPoint, mCurrentPoint;

    private int alpha,speed,length;

    private List<Point> mPathPointList;
    private int currentIndex;

    public RainBean() {
        mPaint.setColor(Color.WHITE);
        reset();
    }

    public void reset() {
        //随机生成属性
        speed= Util.getRandom(20,45);
        length=Util.getRandom(130,170);
        alpha = Util.getRandom(50,140);
        mStartPoint = new Point(Util.getRandom(0,mXRange), -length);
        mPaint.setAlpha(alpha);
        mPaint.setStrokeWidth(Util.getRandom(1,3));
        currentIndex = 0;
        setPathPointList();
    }

    private void setPathPointList() {
        mPathPointList = new ArrayList<>();
        while (mStartPoint.y < mYRange) {
            Point point = new Point(mStartPoint.x, mStartPoint.y);
            mPathPointList.add(point);
            mStartPoint.y += speed;
        }
        Point point = new Point(mStartPoint.x, mStartPoint.y);
        mPathPointList.add(point);

    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        if (currentIndex > mPathPointList.size() - 1) {
            reset();
            return;
        }
        mCurrentPoint = mPathPointList.get(currentIndex);
        canvas.drawLine(mCurrentPoint.x, mCurrentPoint.y, mCurrentPoint.x, mCurrentPoint.y + length, mPaint);
        currentIndex++;
    }

    @Override
    public void destroy() {
        if (mPathPointList != null) {
            mPathPointList.clear();
            mPathPointList = null;
        }
    }
}