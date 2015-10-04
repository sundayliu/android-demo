package com.yangyu.myguideview03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * @author yangyu
 *  ������������ӭ����Activity��Logo��
 */
public class WelcomeActivity extends Activity {  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_welcome);  
  
        /**
		 * millisInFuture:�ӿ�ʼ����start()������ʱ��ɲ�onFinish()���������õĺ�����
		 * countDownInterval:����onTick(long)�ص��ļ��ʱ��
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
