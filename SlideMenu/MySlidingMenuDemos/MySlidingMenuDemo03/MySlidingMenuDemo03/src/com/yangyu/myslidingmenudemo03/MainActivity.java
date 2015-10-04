package com.yangyu.myslidingmenudemo03;


import android.os.Bundle;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ���ñ���
		setTitle("Left and Right");	
		
		// ��ʼ�������˵�
		initSlidingMenu(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(Bundle savedInstanceState){
		// ���û����˵�������ֵ
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);	
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
		
		// �������������ͼ
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SampleListFragment()).commit();		
				
		// ���û����˵�������ͼ����
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SampleListFragment()).commit();
	
		// ���û����˵�������ͼ����
		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_two, new SampleListFragment()).commit();
	}

	/**
	 * �˵���ť����¼���ͨ�����ActionBar��Homeͼ�갴ť���򿪻����˵�
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;	
		}		
		return super.onOptionsItemSelected(item);
	}
	
}
