package com.zzq.cardo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by apple on 2018/2/3.
 */

public class welcomActivity extends Activity {
    ImageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welpage);
        Animation animation = AnimationUtils.loadAnimation(welcomActivity.this,R.anim.alpha_anim);
        img = (ImageView)findViewById(R.id.bgimg);
        img.startAnimation(animation);
        mHandler.sendEmptyMessageDelayed(0,2000);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            gethome();
            super.handleMessage(msg);
        }
    };
    public void gethome(){
        Intent intent = new Intent(welcomActivity.this,CardListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
