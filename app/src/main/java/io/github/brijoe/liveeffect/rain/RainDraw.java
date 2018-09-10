package io.github.brijoe.liveeffect.rain;

import android.util.Log;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.BaseEffectDraw;

public class RainDraw extends BaseEffectDraw {

    public RainDraw() {
        maxAddDelayTime=100;
        maxNum=30;
    }
    @Override
    protected BaseEffectBean getParticle() {

        Log.d("BaseEffectDraw", "getParticle:RainBean");
        return new RainBean();
    }

}
