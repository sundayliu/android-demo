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
	 * 初始化组件
	 */
	private void initView(){
		// 提示getActionBar方法一定在setContentView后面
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

		actionBar.addTab(actionBar.newTab().setText("Tab选项卡一").setTabListener(new MyTabListener<FragmentPage1>(this,FragmentPage1.class)));

		actionBar.addTab(actionBar.newTab().setText("Tab选项卡二").setTabListener(new MyTabListener<FragmentPage2>(this,FragmentPage2.class)));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
