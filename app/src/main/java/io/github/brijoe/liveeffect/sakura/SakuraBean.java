package io.github.brijoe.liveeffect.sakura;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.LiveEffectsView;

/**
 * 樱花动画bean
 */
public class SakuraBean extends BaseEffectBean {

    private String TAG = getClass().getSimpleName();

    /**
     * 第一个控制点的区域
     */
    private Rect mRect0;

    /**
     * 第二个控制点的区域
     */
    private Rect mRect1;

    private int mScreenHeight;

    private int mScreenWidth;

    private int currentDegree;
    private int degreeAdd;


    /**
     * 起点,控制点0,控制点1,终点,当前坐标
     */
    public Point mStartPoint, mControlPoint0, mControlPoint1, mEndPoint, mCurrentPoint;

    /**
     * 透明度
     */
    public int alpha;
    /**
     * icon
     */
    private Bitmap mBitmap;
    /**
     * 产生随机数
     */
    private Random random = new Random();
    /**
     * 动画是否结束
     */
    private boolean isEnd;
    /**
     * 动画生命
     */
    private int maxTime = 10;
    private int minTime = 8;
    private int lifeTime;
    private Paint mPaint;

    private ArrayList<Point> mPathPointList;
    private int currentIndex;

    private int framerate = 50;

    public SakuraBean() {
        mScreenHeight = LiveEffectsView.getViewHeight();
        mScreenWidth = LiveEffectsView.getViewWidth();
        mPaint = new Paint();
        reset();
    }

    public void reset() {
        currentDegree = random.nextInt(360);
        degreeAdd = random.nextInt(6) + 1;
        mBitmap = SakuraDraw.mBitmapsList.get(random.nextInt(SakuraDraw.mBitmapsList.size()));
        mPaint.setAlpha(random.nextInt(100) + 80);
        if (random.nextFloat() < 0.3) {
            mStartPoint = new Point(random.nextInt(mScreenWidth / 4 * 3) + mScreenWidth / 4, 0);
        } else {
            mStartPoint = new Point(mScreenWidth, random.nextInt(mScreenHeight / 4 * 3));
        }
        mEndPoint = new Point((mStartPoint.x - mScreenWidth * 4 / 3) + (-mScreenWidth / 4 + random.nextInt(mScreenWidth / 2)),
                mStartPoint.y + mScreenHeight / 4 * 3 + (-mScreenHeight / 4 + random.nextInt(mScreenHeight / 2)));
        lifeTime = random.nextInt(maxTime - minTime + 1) + minTime;
        currentIndex = 0;
        mRect0 = new Rect(mEndPoint.x, mStartPoint.y, mStartPoint.x, mEndPoint.y);
        mControlPoint0 = getPointFromRect(mRect0);
        mControlPoint1 = getPointFromRect(mRect0);
        mPathPointList = getPathPointList(mStartPoint, mEndPoint, mControlPoint0, mControlPoint1);
        isEnd = false;
    }


    private Point getPointFromRect(Rect rect) {
        if (rect.width() <= 0 || rect.height() <= 0) {
            Log.e(TAG, rect.left + "--" + rect.top + "--" + rect.right + "--" + rect.bottom);
        }
        int x = random.nextInt(Math.abs(rect.width()) + 1) + rect.left;
        int y = random.nextInt(Math.abs(rect.height()) + 1) + rect.top;

        return new Point(x, y);
    }

    private ArrayList<Point> getPathPointList(Point startPoint, Point endPoint, Point controlPoint0, Point controlPoint1) {
        ArrayList<Point> arrayList = new ArrayList<>();
        float timeLeft;
        float time;
        for (int i = 0; i < framerate * lifeTime; i++) {
            Point point = new Point();
            timeLeft = (framerate * lifeTime - i) * 1.0f / (framerate * lifeTime);
            time = i / (framerate * lifeTime * 1.0f);
            point.x = (int) (timeLeft * timeLeft * timeLeft * (startPoint.x)
                    + 3 * timeLeft * timeLeft * time * (controlPoint0.x)
                    + 3 * timeLeft * time * time * (controlPoint1.x)
                    + time * time * time * (endPoint.x));

            point.y = (int) (timeLeft * timeLeft * timeLeft * (startPoint.y)
                    + 3 * timeLeft * timeLeft * time * (controlPoint0.y)
                    + 3 * timeLeft * time * time * (controlPoint1.y)
                    + time * time * time * (endPoint.y));
            arrayList.add(point);
        }
        return arrayList;
    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        if (mBitmap != null && !isEnd) {
            currentIndex++;
            if (currentIndex > mPathPointList.size() - 1) {
                isEnd = true;
                reset();
                return;
            }
            mCurrentPoint = mPathPointList.get(currentIndex);
            if (mCurrentPoint.x < -mBitmap.getWidth() - 1 && mCurrentPoint.y > mScreenHeight + mBitmap.getHeight() + 1) {
                isEnd = true;
                reset();
                return;
            }
            currentDegree += degreeAdd;
            canvas.save();
            canvas.rotate(currentDegree, mCurrentPoint.x + mBitmap.getWidth() / 2, mCurrentPoint.y + mBitmap.getHeight() / 2);
            canvas.drawBitmap(mBitmap, mCurrentPoint.x, mCurrentPoint.y, mPaint);
            canvas.restore();
        }
    }

    @Override
    public void destroy() {
        if (mPathPointList != null) {
            mPathPointList.clear();
            mPathPointList = null;
        }
    }
}
