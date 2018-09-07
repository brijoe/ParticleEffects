package io.github.brijoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.github.brijoe.liveeffect.EffectsManager;
import io.github.brijoe.liveeffect.LiveEffectsView;


public class MainActivity extends Activity implements View.OnClickListener {


    private Button btn1, btn2, btn3, btn4;

    private LiveEffectsView mEffectsView;

    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        Log.e("test", (mEffectsView == null) + "");
        btn1 = findViewById(R.id.btn_effect_1);
        btn2 = findViewById(R.id.btn_effect_2);
        btn3 = findViewById(R.id.btn_effect_3);
        btn4 = findViewById(R.id.btn_effect_4);
        contentView=((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_effect_1:
                EffectsManager.getInstance().setEffect(1);
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_sakura));
                break;
            case R.id.btn_effect_2:
                EffectsManager.getInstance().setEffect(2);
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fireworm));
                break;
            case R.id.btn_effect_3:
                EffectsManager.getInstance().setEffect(3);
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rain));
                break;
            case R.id.btn_effect_4:
                EffectsManager.getInstance().setEffect(4);
                contentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_meteor));
                break;
        }
    }
}
