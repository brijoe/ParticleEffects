package io.github.brijoe.liveeffect.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;

import java.lang.reflect.Method;
import java.util.Random;

public class CommonUtils {
    /**
     * 获取给定两数之间的一个随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 介于最大值和最小值之间的一个随机数
     */


    private static Random random = new Random();
    /**
     * Context对象
     */
    private static Context mContext;

    /**
     * 获取缩放bitmap
     *
     * @param bitmap
     * @param scale
     * @param rotate
     * @return
     */

    public static Bitmap getScaleBitmap(Bitmap bitmap, float scale, float rotate) {
        try {
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            matrix.postRotate(rotate);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            width = width == 0 ? width + 1 : width;
            height = height == 0 ? height + 1 : height;
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            return resizeBmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getScaleBitmap(int resId, float scale, float rotate) {
        try {
            Bitmap bitmap = getBitmap(resId);
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            matrix.postRotate(rotate);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            width = width == 0 ? width + 1 : width;
            height = height == 0 ? height + 1 : height;
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            return resizeBmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 反射获取在主线程调用
     *
     * @return Application Context
     */

    public static Context getContext() {
        if (mContext == null) {
            synchronized (CommonUtils.class) {
                if (mContext == null) {
                    try {
                        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
                        Method method = ActivityThread.getMethod("currentActivityThread");
                        Object currentActivityThread = method.invoke(ActivityThread);//获取currentActivityThread 对象
                        Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                        mContext = (Context) method2.invoke(currentActivityThread);//获取 Context对象
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mContext;
    }

    public static Bitmap getBitmap(int resourceId) {
        return BitmapFactory.decodeResource(getContext().getResources(), resourceId, new BitmapFactory.Options());
    }

    public static int getRandom(int min, int max) {
        if (max < min)
            return 1;
        if (min == max)
            return min;
        return min + random.nextInt(max - min) + 1;
    }

    /**
     * 获取 0-1随机数
     *
     * @return
     */
    public static float getRandom() {
        return random.nextFloat();
    }


    /**
     * 二阶贝塞尔曲线
     * B(t) = Po*(1-t)^2 + 2*p1*t*(1-t)+t^2*p2
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点
     * @param p2 终止点
     * @return t对应的点
     */
    public static PointF getPointForQuadratic(float t, PointF p0, PointF p1, PointF p2) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = (float) (Math.pow(temp, 2) * p0.x + 2 * t * temp * p1.x + Math.pow(t, 2) * p2.x);
        point.y = (float) (Math.pow(temp, 2) * p0.y + 2 * t * temp * p1.y + Math.pow(t, 2) * p2.y);
        return point;
    }

    /**
     * 三阶贝塞尔曲线
     * B(t) = Po*(1-t)^3 + 3*p1*t*(1-t)^2+3*p2*t^2*(1-t)+p3*t^3,
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点1
     * @param p2 控制点2
     * @param p3 终止点
     * @return t对应的点
     */
    public static PointF getPointForCubic(float t, PointF p0, PointF p1, PointF p2, PointF p3) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = (float) (p0.x * Math.pow(temp, 3) + 3 * p1.x * t * Math.pow(temp, 2) + 3 * p2.x * Math.pow(t, 2) * temp + p3.x * Math.pow(t, 3));
        point.y = (float) (p0.y * Math.pow(temp, 3) + 3 * p1.y * t * Math.pow(temp, 2) + 3 * p2.y * Math.pow(t, 2) * temp + p3.y * Math.pow(t, 3));
        return point;
    }


}
