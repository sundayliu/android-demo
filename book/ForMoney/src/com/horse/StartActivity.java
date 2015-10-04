package com.horse;

import com.horse.R;
import com.horse.util.IOHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		
		gotoHome();
	}
	
	/**
	 * ������������ת��������
	 */
	public void gotoHome(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				initBook();				
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}			
		}).start();
	}
	
	/**
	 * ��Book���ʼ����
	 * ��Ϊ�������棬һ�����ʾ����������ת�������档
	 * ��Book����Ҫ���ļ�����ȡ���������Ϣ���������Ҫһ��ʱ�䡣���Խ���ĳ�ʼ���������������������
	 * ���Խ�ʡ�û��ȴ���ʱ�䡣
	 */
	public void initBook(){
		IOHelper.getBook(this);		
	}
}
