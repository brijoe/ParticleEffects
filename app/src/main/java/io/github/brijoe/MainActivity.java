package io.github.brijoe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.github.brijoe.liveeffect.ParticleManager;
import io.github.brijoe.liveeffect.ParticleView;


public class MainActivity extends Activity implements View.OnClickListener {


    private Handler handler = new Handler(Looper.getMainLooper());


    private Button btnStart, btnEnd;

    private Button btn1, btn2, btn3, btn4;

    private ParticleView mParticleView;

    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        HandlerThread handlerThread = new HandlerThread("test");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {

            private int msg = 1;

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Log.e("test", "hah");
                        handler.sendEmptyMessageDelayed(1, 5);
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }


    private void init() {
        btnStart = findViewById(R.id.btn_start);
        btnEnd = findViewById(R.id.btn_stop);
        btn1 = findViewById(R.id.btn_effect_1);
        btn2 = findViewById(R.id.btn_effect_2);
        btn3 = findViewById(R.id.btn_effect_3);
        btn4 = findViewById(R.id.btn_effect_4);
        contentView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_start:
//                handler.removeCallbacksAndMessages(null);
//                handler.sendEmptyMessageDelayed(1,5);
                ParticleManager.getInstance().startEffect();
                break;
            case R.id.btn_stop:
//                handler.removeCallbacksAndMessages(null);
                ParticleManager.getInstance().stopEffect();
                break;
            case R.id.btn_effect_1:
                ParticleManager.getInstance().setParticleType(ParticleManager.SAKURA);
                ParticleManager.getInstance().startEffect();
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_sakura));
                break;
            case R.id.btn_effect_2:
                ParticleManager.getInstance().setParticleType(ParticleManager.FIREWORM);
                ParticleManager.getInstance().startEffect();
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fireworm));
                break;
            case R.id.btn_effect_3:
                ParticleManager.getInstance().setParticleType(ParticleManager.RAIN);
                ParticleManager.getInstance().startEffect();
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rain));
                break;
            case R.id.btn_effect_4:
                ParticleManager.getInstance().setParticleType(ParticleManager.METEOR);
                ParticleManager.getInstance().startEffect();
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_meteor));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ParticleManager.getInstance().release();
    }
}
