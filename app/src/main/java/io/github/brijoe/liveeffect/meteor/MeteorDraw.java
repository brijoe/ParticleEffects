package io.github.brijoe.liveeffect.meteor;

import android.graphics.Bitmap;

import io.github.brijoe.liveeffect.BaseEffectDraw;
import io.github.brijoe.liveeffect.Util;

/**
 * 流星动画
 */
public class MeteorDraw extends BaseEffectDraw<MeteorBean> {

    public MeteorDraw( int maxNum, int resourceId) {
        super( maxNum, resourceId);
    }

    @Override
    public void initEffectBitmaps() {
        for (int i = 0; i < 8; i++) {
            Bitmap bitmap = Util.getScaleBitmap(originBitmap, (i + 2) * 1.00f / 10, 45.0f);
            mBitmapsList.add(bitmap);
        }
    }

    @Override
    public void addEffectBean() {
        if (effectBeanList.size() < maxNum) {
            effectBeanList.add(new MeteorBean());
            mEffectHandler.sendEmptyMessageDelayed(ADD_EFFECT_BEAN, mRandom.nextInt(2000));
        } else {
            mEffectHandler.removeCallbacksAndMessages(null);
            mEffectHandler = null;
            mHandlerThread.quit();
        }
    }
}
