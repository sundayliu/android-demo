package com.yangyu.mycustomtab01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	//����Button��ť����
	private Button btn1,btn2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ������ť����
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);

		//���ü���������CustomTab����
		btn1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,CustomTabActivity1.class));
			}
		});
		
		//���ü���������RadioGroup����
		btn2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,CustomTabActivity2.class));
			}
		});
	}
}
