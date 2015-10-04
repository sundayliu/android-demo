package com.yangyu.myactionbar05;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author yangyu
 *	��Activity�����������
 */
public class MainActivity extends Activity {
	//����ActionBar
	private ActionBar actionBar;
	
	//����Handler���
	private final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		
		initData();
	}

	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//�õ�ActionBar
		actionBar = getActionBar();
	}
	
	/**
	 * ��ʼ������
	 */
	private void initData(){
		//����ActionBar���ⲻ��ʾ
		actionBar.setDisplayShowTitleEnabled(false);
		
		//����ActionBar�ı���
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient_bg));
		
		//����ActionBar���Ĭ�ϵ�ͼ���Ƿ����
		actionBar.setDisplayUseLogoEnabled(true);
		
		//���õ���ģʽΪTabѡ���ǩ����ģʽ
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//����ActinBar���Tabѡ���ǩ
		actionBar.addTab(actionBar.newTab().setText("TAB1").setTabListener(new MyTabListener<FragmentPage1>(this,FragmentPage1.class)));
		actionBar.addTab(actionBar.newTab().setText("TAB2").setTabListener(new MyTabListener<FragmentPage2>(this,FragmentPage2.class)));
		actionBar.addTab(actionBar.newTab().setText("TAB3").setTabListener(new MyTabListener<FragmentPage3>(this,FragmentPage3.class)));
				
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);	
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			//ѡ��ˢ�°�ť��ˢ��һ����
			item.setActionView(R.layout.actionbar_progress);
			handler.postDelayed(new Runnable() {
				public void run() {
					item.setActionView(null);
				}
			}, 1000);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
