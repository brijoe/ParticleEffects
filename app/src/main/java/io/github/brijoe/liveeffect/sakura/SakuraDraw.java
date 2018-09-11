package io.github.brijoe.liveeffect.sakura;

import android.util.Log;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.BaseEffectDraw;

/**
 * 樱花绘制
 *
 * @author Brijoe
 */
public class SakuraDraw extends BaseEffectDraw {

    public SakuraDraw() {
        maxNum = 30;
        maxAddDelayTime = 1000;
    }

    @Override
    protected BaseEffectBean getParticle() {
        Log.d("BaseEffectDraw", "getParticle:SakuraDraw");
        return new SakuraBean();
    }


    @Override
    public void destroy() {

    }
}
