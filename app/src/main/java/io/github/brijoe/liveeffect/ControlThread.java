package io.github.brijoe.liveeffect;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.List;

import io.github.brijoe.liveeffect.util.Util;

/**
 * 粒子控制线程  控制粒子的添加
 */
class ControlThread extends HandlerThread {


    private final int ADD_EFFECT_BEAN = 0x01;
    private List<BaseEffectBean> effectBeanList;

    private int maxNum, maxAddDelayTime;

    private EffectHandler mEffectHandler;

    private BaseEffectDraw mDraw;

    public ControlThread(String name) {
        super(name);
    }

    public void setData(int maxNum, int maxAddDelayTime, BaseEffectDraw draw, List<BaseEffectBean> effectBeanList) {
        this.maxNum = maxNum;
        this.mDraw = draw;
        this.maxAddDelayTime = maxAddDelayTime;
        this.effectBeanList = effectBeanList;
    }

    public void reset() {
        if (!isAlive())
            start();
        if (mEffectHandler == null) {
            mEffectHandler = new EffectHandler(getLooper());
        }
        mEffectHandler.removeMessages(ADD_EFFECT_BEAN);
        mEffectHandler.sendEmptyMessage(ADD_EFFECT_BEAN);
    }

    //粒子控制线程调用
    private void performControl() {
        if (!isAlive())
            return;
        mEffectHandler.sendEmptyMessageDelayed(ADD_EFFECT_BEAN, Util.getRandom(0, maxAddDelayTime));
        float v = Util.getRandom();

        //粒子数量小于三分之一maxNum,80%概率执行添加，20%概率执行删除
        if (effectBeanList.size() < maxNum * 1 / 3) {
            if (v <= 0.8) {
                addOne();
            } else {
                removeOne();
            }
        }
        //粒子数量小于三分之二maxNum,60%概率执行添加，40%概率执行删除
        else if (effectBeanList.size() < maxNum * 2 / 3)
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

    private void addOne() {
        if (effectBeanList.size() < maxNum) {
            effectBeanList.add(mDraw.getParticle());
        }
    }

    private void removeOne() {
        int index = 0;
        while (index < effectBeanList.size()) {
            if (effectBeanList.get(index).isLifeEnd()) {
                effectBeanList.remove(index);
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