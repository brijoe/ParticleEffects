package io.github.brijoe.liveeffect;

import android.content.Context;

/**
 * 动态效果管理器
 */
public class EffectsManager {

    private String TAG = getClass().getSimpleName();
    private int mCurrentEffectId;
    private LiveEffectsView mLiveEffectsView;
    private Context mContext;

    private boolean isShowEffect;

    private static class EffectsManagerHolder {
        static EffectsManager mInstance = new EffectsManager();
    }

    private EffectsManager() {

    }

    public static EffectsManager getInstance() {
        return EffectsManagerHolder.mInstance;
    }

    /**
     * 设置要显示特效的View
     *
     * @param liveEffectsView
     */

    public void setEffectView(LiveEffectsView liveEffectsView) {
        mLiveEffectsView = liveEffectsView;
        if (mLiveEffectsView != null)
            mContext = mLiveEffectsView.getContext();
    }

    /**
     * 设置特效，启动动画
     *
     * @param effectId
     */
    public void setEffect(int effectId) {
        //屏蔽多次设置
        if (mCurrentEffectId == effectId)
            return;
        if (mLiveEffectsView != null)
            mLiveEffectsView.setDrawView(EffectFactory.getEffect(effectId));
        mCurrentEffectId = effectId;
    }


    /**
     * 获取当前正在执行的特效Id
     *
     * @return
     */
    public int getmCurrentEffectId() {
        return mCurrentEffectId;
    }

    /**
     * 关闭特效
     */
    public void stopEffect() {
        isShowEffect = false;
        if(mLiveEffectsView!=null)
            mLiveEffectsView.release();
    }

    /**
     * 释放资源操作
     */
    public void release() {

        mLiveEffectsView = null;
    }


}
