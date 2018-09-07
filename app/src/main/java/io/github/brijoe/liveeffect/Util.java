package io.github.brijoe.liveeffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.lang.reflect.Method;
import java.util.Random;

public class Util {
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

    /**
     * 反射获取在主线程调用
     *
     * @return Application Context
     */

    public static Context getContext() {
        if (mContext == null) {
            synchronized (Util.class) {
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

}
