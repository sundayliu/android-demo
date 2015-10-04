package com.yangyu.myactionbar02;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

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
		// ��ʾgetActionBar����һ����setContentView����
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

		actionBar.addTab(actionBar.newTab().setText("Tabѡ�һ").setTabListener(new MyTabListener<FragmentPage1>(this,FragmentPage1.class)));

		actionBar.addTab(actionBar.newTab().setText("Tabѡ���").setTabListener(new MyTabListener<FragmentPage2>(this,FragmentPage2.class)));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
