package io.github.brijoe.liveeffect;


import android.util.SparseArray;

import io.github.brijoe.R;
import io.github.brijoe.liveeffect.fireworm.FirewormDraw;
import io.github.brijoe.liveeffect.meteor.MeteorDraw;
import io.github.brijoe.liveeffect.rain.RainDraw;
import io.github.brijoe.liveeffect.sakura.SakuraDraw;

/**
 * 动态效果工厂类
 */
class EffectFactory {


    private static final int SAKURA_EFFECTS = 1;
    private static final int FIREWORM_EFFECTS = 2;
    private static final int RAIN_EFFECTS = 3;
    private static final int METEOR_EFFECTS = 4;

    private static SparseArray<BaseEffectDraw> effectDrawMap = new SparseArray<>(4);

    static {
        effectDrawMap.put(SAKURA_EFFECTS, new SakuraDraw(15, R.drawable.icon_sakura));
        effectDrawMap.put(FIREWORM_EFFECTS, new FirewormDraw(15, R.drawable.icon_fireworm));
        effectDrawMap.put(RAIN_EFFECTS, new RainDraw(30, 0));
        effectDrawMap.put(METEOR_EFFECTS, new MeteorDraw(6, R.drawable.icon_meteor));
    }

    public static BaseEffectDraw getEffect(int effectId) {
        return effectDrawMap.get(effectId);
    }
}