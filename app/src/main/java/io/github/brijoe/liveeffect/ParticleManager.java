package io.github.brijoe.liveeffect;

/**
 * 动态效果管理器
 * <p>
 * 开启特效
 * 关闭特效
 */
public class ParticleManager {

    private int mCurType;
    private ParticleView mParticleView;
    public static final int NO_EFFECT = -1;
    public static final int SAKURA = 1;
    public static final int FIREWORM = 2;
    public static final int RAIN = 3;
    public static final int METEOR = 4;


    private static class EffectsManagerHolder {
        static ParticleManager mInstance = new ParticleManager();
    }

    private ParticleManager() {

    }

    public static ParticleManager getInstance() {
        return EffectsManagerHolder.mInstance;
    }

    /**
     * 设置要显示特效的View
     *
     * @param particleViewa
     */

    public void setParticleView(ParticleView particleViewa) {
        mParticleView = particleViewa;
    }

    /**
     * 设置特效，启动动画
     *
     * @param type
     */
    public void setParticleType(@ParticleDraw.EffectType int type) {
        //屏蔽多次设置
        if (mCurType != type) {
            mCurType = type;
        }
    }

    /**
     * 获取当前正在执行的特效Id
     *
     * @return
     */
    public @ParticleDraw.EffectType
    int getParticleId() {
        return mCurType;
    }


    public void startEffect() {
        startInternal();
    }

    public void startEffect(@ParticleDraw.EffectType int type) {
        if (mCurType != type) {
            mCurType = type;
            startInternal();
        }

    }

    private void startInternal() {
        if (mCurType == NO_EFFECT) {
            return;
        }
        if (mParticleView != null) {
            mParticleView.start(mCurType);
        }

    }

    /**
     * 关闭特效,
     */
    public void stopEffect() {
        if (mParticleView != null) {
            mParticleView.stop();
        }
    }

    public void release() {
        mParticleView = null;
    }

}
