package com.yangyu.myactionbar04;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView(){
		final ActionBar actionBar = getActionBar();
		
		actionBar.setHomeButtonEnabled(false);	
		
		this.findViewById(R.id.other_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                startActivity(intent);
            }
        });
		
		this.findViewById(R.id.external_btn).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {      
            	 //调用图片浏览器
                 Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                 externalActivityIntent.setType("image/*");
                 externalActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                 startActivity(externalActivityIntent);
             }
         });
	}
}
