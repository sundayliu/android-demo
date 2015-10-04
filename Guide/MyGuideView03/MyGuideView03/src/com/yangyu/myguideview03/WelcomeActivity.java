package com.yangyu.myguideview03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * @author yangyu
 *  功能描述：欢迎界面Activity（Logo）
 */
public class WelcomeActivity extends Activity {  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_welcome);  
  
        /**
		 * millisInFuture:从开始调用start()到倒计时完成并onFinish()方法被调用的毫秒数
		 * countDownInterval:接收onTick(long)回调的间隔时间
		 */
        new CountDownTimer(5000, 1000) {  
            @Override  
            public void onTick(long millisUntilFinished) {  
            }  
  
            @Override  
            public void onFinish() {  
                Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);  
                startActivity(intent);  
                WelcomeActivity.this.finish();  
            }  
        }.start();  
    }  
  
}  
