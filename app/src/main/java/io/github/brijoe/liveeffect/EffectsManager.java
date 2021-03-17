package io.github.brijoe.liveeffect;

/**
 * 动态效果管理器
 */
public class EffectsManager {

    private String TAG = getClass().getSimpleName();
    private int mCurrentEffectId;
    private LiveEffectsView mLiveEffectsView;


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
    }

    /**
     * 设置特效，启动动画
     *
     * @param effectId
     */
    public void setEffect(int effectId) {
        //屏蔽多次设置
        if (mCurrentEffectId == effectId) {
            return;
        }
        mCurrentEffectId = effectId;
        if (mLiveEffectsView != null) {
            mLiveEffectsView.setDrawView(EffectFactory.getEffect(effectId));
        }

    }


    /**
     * 获取当前正在执行的特效Id
     *
     * @return
     */
    public int getCurrentEffectId() {
        return mCurrentEffectId;
    }

    /**
     * 关闭特效
     */
    public void stopEffect() {
        if (mLiveEffectsView != null)
            mLiveEffectsView.release();
    }

    /**
     * 释放资源操作
     */
    public void release() {

        mLiveEffectsView = null;
    }


}
