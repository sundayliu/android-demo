package com.yangyu.myslidingmenudemo07;

import android.os.Bundle;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("Sliding Content only");
		
		initSlidingMenu();
	}

	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(){
		// ���û����˵��򿪺����ͼ����
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SampleListFragment()).commit();

		// ������������ͼ
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SampleListFragment()).commit();
		
		// ���õ��򿪻����˵�ʱ��ActionBar���ܹ�������һ�𻬶�
		setSlidingActionBarEnabled(false);
		
		// ���û����˵�������ֵ
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getActionBar().setDisplayHomeAsUpEnabled(true);			
	}

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
