package com.sohu110.airapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Aaron on 2016/3/28.
 */
public class LaunchActivity extends Activity {

    //倒计时
    private CountDownTimer countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        countDown = new CountDownTimer(3000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            /** 倒计时结束后在这里实现activity跳转  */
            @Override
            public void onFinish() {
                Log.e("aaron", "jump");
                jump();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            countDown.start();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            countDown.cancel();
        } catch (Exception e) {
        }
    }

    private void jump() {
        Intent intent;
        intent = new Intent(LaunchActivity.this, MainActivity.class);
//        PushMessage message = (PushMessage) getIntent().getSerializableExtra(Const.EXTRA_PUSH_MESSAGE);
//        intent.putExtra(Const.EXTRA_PUSH_MESSAGE, message);
        startActivity(intent);
        //销毁自身防止用户返回
        finish();
    }
}
