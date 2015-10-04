package com.yangyu.mycustomtab01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	//定义Button按钮对象
	private Button btn1,btn2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化按钮对象
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);

		//设置监听，进入CustomTab界面
		btn1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,CustomTabActivity1.class));
			}
		});
		
		//设置监听，进入RadioGroup界面
		btn2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,CustomTabActivity2.class));
			}
		});
	}
}
