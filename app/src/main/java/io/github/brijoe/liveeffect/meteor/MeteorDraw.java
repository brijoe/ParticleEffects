package io.github.brijoe.liveeffect.meteor;

import android.util.Log;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.BaseEffectDraw;

/**
 * 流星动画
 */
public class MeteorDraw extends BaseEffectDraw {


    public MeteorDraw() {
        maxAddDelayTime=2000;
        maxNum=10;
    }

    @Override
    protected BaseEffectBean getParticle() {
        Log.d("BaseEffectDraw", "getParticle:MeteorBean");
        return new MeteorBean();
    }
}
