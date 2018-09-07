package io.github.brijoe.liveeffect.fireworm;

import android.graphics.Bitmap;

import io.github.brijoe.liveeffect.BaseEffectDraw;
import io.github.brijoe.liveeffect.Util;

/**
 * 萤火虫动画
 */
public class FirewormDraw extends BaseEffectDraw<FirewormBean> {


    public FirewormDraw( int maxNum, int resourceId) {
        super( maxNum, resourceId);
    }

    @Override
    public void initEffectBitmaps() {
        for (int i = 0; i < 8; i++) {
            Bitmap bitmap = Util.getScaleBitmap(originBitmap, (i + 2) * 1.00f / 10, 0);
            mBitmapsList.add(bitmap);
        }
    }

    @Override
    public void addEffectBean() {
        if (effectBeanList.size() < maxNum) {
            effectBeanList.add(new FirewormBean());
            mEffectHandler.sendEmptyMessageDelayed(ADD_EFFECT_BEAN, mRandom.nextInt(2000));
        } else {
            mEffectHandler.removeCallbacksAndMessages(null);
            mEffectHandler = null;
            mHandlerThread.quit();
        }
    }
}
