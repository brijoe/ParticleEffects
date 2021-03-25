package io.github.brijoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.github.brijoe.effect.ParticleManager;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnStart, btnEnd;

    private Button btn1, btn2, btn3, btn4;
    private View backContainer, controlContainer, effectsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init() {
        btnStart = findViewById(R.id.btn_start);
        btnEnd = findViewById(R.id.btn_stop);
        btn1 = findViewById(R.id.btn_effect_1);
        btn2 = findViewById(R.id.btn_effect_2);
        btn3 = findViewById(R.id.btn_effect_3);
        btn4 = findViewById(R.id.btn_effect_4);
        backContainer = findViewById(R.id.rl_back);
        controlContainer = findViewById(R.id.ll_control);
        effectsContainer = findViewById(R.id.ll_effects);
        backContainer.setOnClickListener(this);
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
            case R.id.rl_back:
                if (controlContainer.getVisibility() == View.VISIBLE) {
                    controlContainer.setVisibility(View.INVISIBLE);
                    effectsContainer.setVisibility(View.INVISIBLE);
                } else {
                    controlContainer.setVisibility(View.VISIBLE);
                    effectsContainer.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_start:
                ParticleManager.getInstance().startEffect();
                break;
            case R.id.btn_stop:
                ParticleManager.getInstance().stopEffect();
                break;
            case R.id.btn_effect_1:
                ParticleManager.getInstance().startEffect(ParticleManager.SAKURA);
                backContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_sakura));
                break;
            case R.id.btn_effect_2:
                ParticleManager.getInstance().startEffect(ParticleManager.FIREWORM);
                backContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fireworm));
                break;
            case R.id.btn_effect_3:
                ParticleManager.getInstance().startEffect(ParticleManager.RAIN);
                backContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rain));
                break;
            case R.id.btn_effect_4:
                ParticleManager.getInstance().startEffect(ParticleManager.METEOR);
                backContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_meteor));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ParticleManager.getInstance().release();
    }
}
