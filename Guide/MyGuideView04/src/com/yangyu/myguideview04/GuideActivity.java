package com.yangyu.myguideview04;

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

/**
 * @author yangyu
 *	功能描述：引导界面activity类
 */
public class GuideActivity extends Activity implements OnPageChangeListener{
	// 定义ViewPager对象
	private ViewPager viewPager;

	// 定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;

	// 定义一个ArrayList来存放View
	private ArrayList<View> views;

	// 定义各个界面View对象
	private View view1, view2, view3, view4;
	
	//定义开始按钮对象
	private Button startBt;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		initView();
		
		initData();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		//实例化各个界面的布局对象 
		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.guide_view01, null);
		view2 = mLi.inflate(R.layout.guide_view02, null);
		view3 = mLi.inflate(R.layout.guide_view03, null);
		view4 = mLi.inflate(R.layout.guide_view04, null);
	
		// 实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		// 实例化ArrayList对象
		views = new ArrayList<View>();

		// 实例化ViewPager适配器
		vpAdapter = new ViewPagerAdapter(views);
		
		//实例化开始按钮
		startBt = (Button) view4.findViewById(R.id.startBtn);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 设置监听
		viewPager.setOnPageChangeListener(this);
		// 设置适配器数据
		viewPager.setAdapter(vpAdapter);

		//将要分页显示的View装入数组中		
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);		
								
		// 给开始按钮设置监听
		startBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 startbutton();
			}
		});
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
			
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		
	}
	
	/**
	 * 相应按钮点击事件
	 */
	private void startbutton() {  
      	Intent intent = new Intent();
		intent.setClass(GuideActivity.this,MainActivity.class);
		startActivity(intent);
		this.finish();
      }
}
