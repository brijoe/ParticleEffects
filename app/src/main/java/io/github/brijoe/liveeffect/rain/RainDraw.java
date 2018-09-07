package io.github.brijoe.liveeffect.rain;

import io.github.brijoe.liveeffect.BaseEffectDraw;

public class RainDraw extends BaseEffectDraw {

    public RainDraw( int maxNum, int resourceId) {
        super( maxNum, resourceId);
    }

    @Override
    public void initEffectBitmaps() {

    }

    @Override
    public void addEffectBean() {
        if (effectBeanList.size() < maxNum) {
            effectBeanList.add(new RainBean());
            mEffectHandler.sendEmptyMessageDelayed(ADD_EFFECT_BEAN, mRandom.nextInt(100));
        } else {
            mEffectHandler.removeCallbacksAndMessages(null);
            mEffectHandler = null;
            mHandlerThread.quit();
        }
    }
}
