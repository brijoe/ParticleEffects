package io.github.brijoe.liveeffect.rain;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.github.brijoe.liveeffect.BaseEffectBean;
import io.github.brijoe.liveeffect.util.Util;

/**
 * 下雨特效
 */

class RainBean extends BaseEffectBean {

    //透明度，速度，长度,线条宽度 四个属性
    private int alpha, speed, length, width;
    //四个坐标  start(x,y) end(x,y)
    private int startX, startY, endX, endY;


    @Override
    protected void reset() {
        //随机生成属性
        alpha = Util.getRandom(50, 140);
        speed = Util.getRandom(20, 45);
        length = Util.getRandom(130, 170);
        width = Util.getRandom(1, 3);
        startX = Util.getRandom(0, mXRange);
        startY = -length;
        endX = startX;
        endY = 0;
    }


    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
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
      return (startY>=mYRange)&&(startY<2*mYRange);
    }
}
