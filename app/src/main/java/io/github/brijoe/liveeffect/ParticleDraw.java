package io.github.brijoe.liveeffect;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.util.Log;
import android.util.SparseArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.github.brijoe.liveeffect.particle.Fireworm;
import io.github.brijoe.liveeffect.particle.Meteor;
import io.github.brijoe.liveeffect.particle.Rain;
import io.github.brijoe.liveeffect.particle.Sakura;
import io.github.brijoe.liveeffect.util.CommonUtils;

import static io.github.brijoe.liveeffect.ParticleManager.FIREWORM;
import static io.github.brijoe.liveeffect.ParticleManager.METEOR;
import static io.github.brijoe.liveeffect.ParticleManager.RAIN;
import static io.github.brijoe.liveeffect.ParticleManager.SAKURA;

/**
 * 管理粒子的公共绘制类
 */
public class ParticleDraw {

    private final String TAG = "BaseEffectDraw";


    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {SAKURA, FIREWORM, RAIN, METEOR})
    public @interface EffectType {
    }

    private static SparseArray<Class<? extends ParticleBase>> effectDrawMap = new SparseArray<>(4);

    static {

        effectDrawMap.put(SAKURA, Sakura.class);
        effectDrawMap.put(FIREWORM, Fireworm.class);
        effectDrawMap.put(RAIN, Rain.class);
        effectDrawMap.put(METEOR, Meteor.class);
    }

    //控制线程
    private ControlThread sControlThread = new ControlThread("ControlThread");
    //需要保证读取，绘制线程(读)，控制线程(读，写)
    private List<ParticleBase> sParticleList = new CopyOnWriteArrayList<>();
    //默认特效
    private Class<? extends ParticleBase> mParticleClz;

    private int mMaxAddNum;

    private long mMaxDelayTime;


    public ParticleDraw() {
    }

    //初始化方法
    public final void init(@ParticleDraw.EffectType int particleType) {
        mParticleClz = effectDrawMap.get(particleType);
        ParticleBase particleEffect = getParticle();
        mMaxAddNum = particleEffect.getMaxNum();
        mMaxDelayTime = particleEffect.getMaxAddDelayTime();
        sParticleList.clear();
        sControlThread.start();
    }

    private ParticleBase getParticle() {
        try {
            return mParticleClz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Rain();
    }

    //调用draw 方法进行绘制，遍历粒子集合并调用每个粒子的drawFrame 方法
    public final void draw(Canvas canvas, Paint paint) {
        if (sParticleList == null || sParticleList.size() == 0)
            return;
        //遍历粒子集合进行下一帧绘制
        Log.d(TAG, "draw: " + sParticleList.size());
        for (int i = 0; i < sParticleList.size(); i++)
            sParticleList.get(i).drawNextFrame(canvas, paint);
    }

    public final void destroy() {
        if (sControlThread != null) {
            sControlThread.quit();
            sControlThread = null;
        }
        if (sParticleList != null) {
            sParticleList.clear();
            sParticleList = null;
        }
    }

    class ControlThread extends HandlerThread {


        private final int ADD_EFFECT_BEAN = 0x01;

        private EffectHandler mEffectHandler;


        public ControlThread(String name) {
            super(name);
        }


        public void start() {
            if (!isAlive()) {
                super.start();
            }

            if (mEffectHandler == null) {
                mEffectHandler = new EffectHandler(getLooper());
            }
            mEffectHandler.removeCallbacksAndMessages(null);
            mEffectHandler.sendEmptyMessage(ADD_EFFECT_BEAN);
        }

        //粒子控制线程调用
        private void performControl() {
            if (!isAlive())
                return;
            mEffectHandler.sendEmptyMessageDelayed(ADD_EFFECT_BEAN,
                    CommonUtils.getRandom(0, (int) mMaxDelayTime));
            float v = CommonUtils.getRandom();
            //粒子数量小于三分之一maxNum,80%概率执行添加，20%概率执行删除
            if (sParticleList.size() < mMaxAddNum * 1 / 3) {
                if (v <= 0.8) {
                    addOne();
                } else {
                    removeOne();
                }
            }
            //粒子数量小于三分之二maxNum,60%概率执行添加，40%概率执行删除
            else if (sParticleList.size() < mMaxAddNum * 2 / 3)
                if (v <= 0.6) {
                    addOne();
                } else {
                    removeOne();
                }
                //粒子数量超过三分之二maxNum,40%概率执行添加，60%概率执行删除
            else {
                if (v <= 0.4) {
                    addOne();
                } else {
                    removeOne();
                }
            }
        }

        //添加粒子
        private void addOne() {
            if (sParticleList.size() < mMaxAddNum) {
                sParticleList.add(getParticle());
            }
        }

        //移除超出生命周期的粒子
        private void removeOne() {
            int index = 0;
            while (index < sParticleList.size()) {
                if (sParticleList.get(index).isLifeEnd()) {
                    sParticleList.remove(index);
                    break;
                } else
                    index++;
            }
        }

        private class EffectHandler extends Handler {

            public EffectHandler(Looper looper) {
                super(looper);
            }


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ADD_EFFECT_BEAN:
                        performControl();
                        break;
                }
            }
        }
    }

}
