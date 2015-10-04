package com.jay.example.viewpagerfragment;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class MainActivity extends FragmentActivity {

	//定义四个Fragment
	private Fg1 fg1;
	private Fg2 fg2;
	private Fg3 fg3;
	private Fg4 fg4;
	//定义一个ViewPager容器
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private MyFragmentPageAadpter mAdapter;
	//下面每个Layout对象
	private RelativeLayout weixin_layout;
	private RelativeLayout tongxunlu_layout;
	private RelativeLayout faxian_layout;
	private RelativeLayout me_layout;
	//依次获得ImageView与TextView
	private ImageView weixin_img;
	private ImageView tongxunlu_img;
	private ImageView faxian_img;
	private ImageView me_img;
	private TextView weixin_txt;
	private TextView tongxunlu_txt;
	private TextView faxian_txt;
	private TextView me_txt;
	//定义颜色值
	private int Gray = 0xFF999999;
	private int Green =0xFF45C01A;
	//定义FragmentManager对象
	public FragmentManager fManager;
	//定义一个Onclick全局对象
	public MyOnClick myclick;
	public MyPageChangeListener myPageChange;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		fManager = getSupportFragmentManager();
		initViewPager();
		initViews();
		initState();
	}
	
	
	
	
	private void initViews() {
		myclick = new MyOnClick();
		myPageChange = new MyPageChangeListener();
		mPager = (ViewPager) findViewById(R.id.vPager);
		weixin_layout = (RelativeLayout) findViewById(R.id.weixin_layout);
		tongxunlu_layout = (RelativeLayout) findViewById(R.id.tongxunlu_layout);
		faxian_layout = (RelativeLayout) findViewById(R.id.faxian_layout);
		me_layout = (RelativeLayout) findViewById(R.id.me_layout);
		weixin_img = (ImageView) findViewById(R.id.weixin_img);
		tongxunlu_img = (ImageView) findViewById(R.id.tongxunlu_img);
		faxian_img = (ImageView) findViewById(R.id.faxian_img);
		me_img = (ImageView) findViewById(R.id.me_img);
		weixin_txt = (TextView) findViewById(R.id.weixin_txt);
		tongxunlu_txt = (TextView) findViewById(R.id.tongxunlu_txt);
		faxian_txt = (TextView) findViewById(R.id.faxian_txt);
		me_txt = (TextView) findViewById(R.id.me_txt);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(myPageChange);
		weixin_layout.setOnClickListener(myclick);
		tongxunlu_layout.setOnClickListener(myclick);
		faxian_layout.setOnClickListener(myclick);
		me_layout.setOnClickListener(myclick);
	}
	
	private void initViewPager()
	{
		fragmentsList = new ArrayList<Fragment>();    
        fg1 = new Fg1();
        fg2 = new Fg2();
        fg3 = new Fg3();
        fg4 = new Fg4();
        fragmentsList.add(fg1);
        fragmentsList.add(fg2);
        fragmentsList.add(fg3);
        fragmentsList.add(fg4);
        mAdapter = new MyFragmentPageAadpter(fManager,fragmentsList);
	}
	
	//定义一个设置初始状态的方法
	private void initState()
	{
		weixin_img.setImageResource(R.drawable.ahj);
		weixin_txt.setTextColor(Green);
		mPager.setCurrentItem(0);
	}
	
	
	
	
	public class MyOnClick implements OnClickListener
	{
		@Override
		public void onClick(View view) {
			clearChioce();
			iconChange(view.getId());
		}
	}
	
	public class MyPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
			if(arg0 == 2)
			{
				int i = mPager.getCurrentItem();
				clearChioce();
				iconChange(i);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		@Override
		public void onPageSelected(int index){}
		
	}
	
	
	
	//建立一个清空选中状态的方法
	public void clearChioce()
	{
		weixin_img.setImageResource(R.drawable.ahk);
		weixin_txt.setTextColor(Gray);
		tongxunlu_img.setImageResource(R.drawable.ahi);
		tongxunlu_txt.setTextColor(Gray);
		faxian_img.setImageResource(R.drawable.ahm);
		faxian_txt.setTextColor(Gray);
		me_img.setImageResource(R.drawable.aho);
		me_txt.setTextColor(Gray);	
	}
	
	//定义一个底部导航栏图标变化的方法
	public void iconChange(int num)
	{
		switch (num) {
		case R.id.weixin_layout:case 0:
			weixin_img.setImageResource(R.drawable.ahj);
			weixin_txt.setTextColor(Green);
			mPager.setCurrentItem(0);
			break;
		case R.id.tongxunlu_layout:case 1:
			tongxunlu_img.setImageResource(R.drawable.ahh);
			tongxunlu_txt.setTextColor(Green);
			mPager.setCurrentItem(1);
			break;
		case R.id.faxian_layout:case 2:
			faxian_img.setImageResource(R.drawable.ahl);
			faxian_txt.setTextColor(Green);
			mPager.setCurrentItem(2);
			break;
		case R.id.me_layout:case 3:
			me_img.setImageResource(R.drawable.ahn);
			me_txt.setTextColor(Green);	
			mPager.setCurrentItem(3);
			break;
		}
		
	}
	
	
}
