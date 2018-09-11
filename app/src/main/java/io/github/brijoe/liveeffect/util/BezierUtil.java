package io.github.brijoe.liveeffect.util;

import android.graphics.PointF;
import android.util.Log;


/**
 * 贝塞尔曲线工具类
 *
 * @author Brijoe
 */

public class BezierUtil {


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
        Log.d("getPointForQuadratic",
                String.format("t=%f,起始点p0=%s,控制点p1=%s,终点p2=%s",t,p0.toString(),p1.toString(),p2.toString()));
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