package io.github.brijoe.liveeffect.rain;

import android.util.Log;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.BaseEffectDraw;

public class RainDraw extends BaseEffectDraw {

    public RainDraw() {
        maxAddDelayTime=50;
        maxNum=40;
    }
    @Override
    protected BaseEffectBean getParticle() {

        Log.d("BaseEffectDraw", "getParticle:RainBean");
        return new RainBean();
    }

}
