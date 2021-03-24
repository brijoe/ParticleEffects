package io.github.brijoe.liveeffect.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.github.brijoe.liveeffect.ParticleBase;
import io.github.brijoe.liveeffect.util.CommonUtils;

/**
 * 下雨特效
 */

public class Rain extends ParticleBase {

    //透明度，速度，长度,线条宽度 四个属性
    private int alpha, speed, length, width;
    //四个坐标  start(x,y) end(x,y)
    private int startX, startY, endX, endY;


    @Override
    protected void reset() {
        //随机生成属性
        alpha = CommonUtils.getRandom(50, 140);
        speed = CommonUtils.getRandom(20, 45);
        length = CommonUtils.getRandom(130, 170);
        width = CommonUtils.getRandom(1, 3);
        startX = CommonUtils.getRandom(0, mXRange);
        startY = -length;
        endX = startX;
        endY = 0;
    }


    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        super.drawNextFrame(canvas, paint);
        //处理边界
        if (startY >= 2 * mYRange) {
            reset();
        }
        //绘制
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(width);
        paint.setAlpha(alpha);
        canvas.drawLine(startX, startY, endX, endY, paint);
        //下落
        startY += speed;
        endY += speed;
    }

    @Override
    public boolean isLifeEnd() {
        return (startY >= mYRange) && (startY < 2 * mYRange);
    }

    @Override
    public int getMaxNum() {
        return 100;
    }

    @Override
    public int getMaxAddDelayTime() {
        return 20;
    }
}
