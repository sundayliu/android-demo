package com.example.myslidingmenudemo08;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	private CanvasTransformer mTransformer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("Zoom");
		
		initAnimation();
		
		initSlidingMenu();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(){
		// ������������ͼ
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SampleListFragment()).commit();
				
		// ���û����˵���ͼ
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SampleListFragment()).commit();

		// ���û����˵�������ֵ
		SlidingMenu sm = getSlidingMenu();		
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setBehindScrollScale(0.0f);
		sm.setBehindCanvasTransformer(mTransformer);
		
		setSlidingActionBarEnabled(true);
	}

	/**
	 * ��ʼ������Ч��
	 */
	private void initAnimation(){
		mTransformer = new CanvasTransformer(){
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen*0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);				
			}
			
		};
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
