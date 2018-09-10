package io.github.brijoe.liveeffect;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 所有特效的父类，抽象公共方法
 */
public abstract class BaseEffectDraw {

    private final String TAG = "BaseEffectDraw";
    protected int maxNum;
    protected int maxAddDelayTime;

    //共享控制线程和并发List
    private static ControlThread sControlThread = new ControlThread("control");
    //并发，需要保证读取，绘制线程(读)，控制线程(读，写)
    private static List<BaseEffectBean> sParticleList = new CopyOnWriteArrayList<>();


    public BaseEffectDraw() {
    }

    //初始化
    public final void init() {
        sParticleList.clear();
        sControlThread.setData(maxNum, maxAddDelayTime, this, sParticleList);
        sControlThread.reset();
    }

    //获取一个粒子的方法,子类实现
    protected abstract BaseEffectBean getParticle();

    public void draw(Canvas canvas, Paint paint) {
        if (sParticleList == null || sParticleList.size() == 0)
            return;
        //遍历粒子集合进行下一帧绘制
        Log.d(TAG, "draw: "+sParticleList.size());
        for (int i = 0; i < sParticleList.size(); i++)
            sParticleList.get(i).drawNextFrame(canvas, paint);
    }

    public void destroy() {
        if (sControlThread != null) {
            sControlThread.quit();
            sControlThread = null;
        }
        if (sParticleList != null) {
            sParticleList.clear();
            sParticleList = null;
        }
    }

}
