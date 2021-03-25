package io.github.brijoe.effect.util;

import android.content.Context;
import android.util.Log;

import com.facebook.device.yearclass.YearClass;


public class ConfigUtils {

    private static final String TAG = "ConfigUtils";


    private static final Performance PERFORMANCE = getPerformance(CommonUtils.getContext());
    public static final int DRAW_FRAME_RATE = getConfigRate();

    public enum Performance {
        //低端机
        LOW(0x01),
        //中端机
        MEDIUM(0x02),
        //高端机
        HIGH(0x03);


        private int value;


        Performance(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }


    public static Performance getPerformance(Context context) {
        int year = YearClass.get(context);

        Log.d(TAG, "getPerformance: " + year);

        if (year >= YearClass.CLASS_2013) {
            // Do advanced animation
            return Performance.HIGH;
        } else if (year > YearClass.CLASS_2010) {
            // Do simple animation
            return Performance.MEDIUM;
        } else {
            // Phone too slow, don't do any animations
            return Performance.LOW;
        }
    }

    public static int getConfigRate() {
        int rate = 5;
        switch (PERFORMANCE) {
            case LOW:
                rate = 16;
                break;
            case MEDIUM:
                rate = 10;
                break;
            case HIGH:
                rate = 5;
                break;
        }

        return rate;
    }
}
