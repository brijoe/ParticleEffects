package io.github.brijoe.liveeffect.fireworm;

import android.util.Log;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.BaseEffectDraw;

/**
 * @author Brijoe
 */
public class FirewormDraw extends BaseEffectDraw {


    public FirewormDraw() {
        maxNum = 30;
        maxAddDelayTime = 1000;
    }

    @Override
    protected BaseEffectBean getParticle() {
        Log.d("BaseEffectDraw", "getParticle:Fireworm Bean");
        return new FirewormBean();
    }
}
