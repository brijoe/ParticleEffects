package io.github.brijoe.liveeffect;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.List;

import io.github.brijoe.liveeffect.util.Util;

class ControlThread extends HandlerThread {


    private final int ADD_EFFECT_BEAN = 0x01;
    private List<BaseEffectBean> effectBeanList;

    private int maxNum, maxAddDelayTime;

    private  EffectHandler mEffectHandler;

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
    private void addParticle() {
        if (!isAlive())
            return;
        mEffectHandler.sendEmptyMessageDelayed(ADD_EFFECT_BEAN, Util.getRandom(0, maxAddDelayTime));
        if (effectBeanList.size() < maxNum) {
            //产生一个粒子并加入集合
            effectBeanList.add(mDraw.getParticle());
        } else {
            int removeCount = Util.getRandom(1, effectBeanList.size());
            for (int i = 0; i < removeCount; i++) {
                int index = Util.getRandom(0, effectBeanList.size() - 1);
                if (!effectBeanList.get(index).isAlive())
                    effectBeanList.remove(index);
            }
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
                    addParticle();
                    break;
            }
        }
    }
}