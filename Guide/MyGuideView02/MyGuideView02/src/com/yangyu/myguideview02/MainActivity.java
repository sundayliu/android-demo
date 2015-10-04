package com.yangyu.myguideview02;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author yangyu
 *	�������������������activity
 */
public class MainActivity extends Activity {
	// ����ViewPager����
	private ViewPager viewPager;

	// ����ViewPager������
	private ViewPagerAdapter vpAdapter;

	// ����һ��ArrayList�����View
	private ArrayList<View> views;

	//�����������View����
	private View view1,view2,view3,view4,view5,view6;
	
	// ����ײ�С��ͼƬ
	private ImageView pointImage0, pointImage1, pointImage2, pointImage3,pointImage4, pointImage5;

	//���忪ʼ��ť����
	private Button startBt;
	
	// ��ǰ��λ������ֵ
	private int currIndex = 0;

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
	private void initView() {
		//ʵ������������Ĳ��ֶ��� 
		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.guide_view01, null);
		view2 = mLi.inflate(R.layout.guide_view02, null);
		view3 = mLi.inflate(R.layout.guide_view03, null);
		view4 = mLi.inflate(R.layout.guide_view04, null);
		view5 = mLi.inflate(R.layout.guide_view05, null);
		view6 = mLi.inflate(R.layout.guide_view06, null);
				
		// ʵ����ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		// ʵ����ArrayList����
		views = new ArrayList<View>();

		// ʵ����ViewPager������
		vpAdapter = new ViewPagerAdapter(views);

		// ʵ�����ײ�С��ͼƬ����
		pointImage0 = (ImageView) findViewById(R.id.page0);
		pointImage1 = (ImageView) findViewById(R.id.page1);
		pointImage2 = (ImageView) findViewById(R.id.page2);
		pointImage3 = (ImageView) findViewById(R.id.page3);
		pointImage4 = (ImageView) findViewById(R.id.page4);
		pointImage5 = (ImageView) findViewById(R.id.page5);
		
		//ʵ������ʼ��ť
		startBt = (Button) view6.findViewById(R.id.startBtn);
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		// ���ü���
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		// ��������������
		viewPager.setAdapter(vpAdapter);

		//��Ҫ��ҳ��ʾ��Viewװ��������		
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
		views.add(view6);
		
						
		// ����ʼ��ť���ü���
		startBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 startbutton();
			}
		});
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			switch (position) {
			case 0:
				pointImage0.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImage1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 1:
				pointImage1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImage0.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImage2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 2:
				pointImage2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImage1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImage3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 3:
				pointImage3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImage4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImage2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 4:
				pointImage4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImage3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImage5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 5:
				pointImage5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImage4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			}
			currIndex = position;
			// animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			// animation.setDuration(300);
			// mPageImg.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}
	
	/**
	 * ��Ӧ��ť����¼�
	 */
	private void startbutton() {  
      	Intent intent = new Intent();
		intent.setClass(MainActivity.this,GuideViewDoor.class);
		startActivity(intent);
		this.finish();
      }
	
}
