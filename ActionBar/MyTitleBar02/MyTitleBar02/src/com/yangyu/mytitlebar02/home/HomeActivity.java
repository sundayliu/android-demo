package com.yangyu.mytitlebar02.home;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.yangyu.mytitlebar02.R;

/**
 * @author yangyu
 *	功能描述：贴吧首页Activity页面
 */
public class HomeActivity extends ActivityGroup implements OnClickListener{
	//定义帧布局对象
	private FrameLayout mContent;
	
	//定义图片按钮对象
	private ImageButton myButton,markButton;
	
	//定义标示符
	private static final String HOME_LIKE_ID = "like";
	private static final String HOME_MARK_ID = "mark"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		mContent = (FrameLayout) findViewById(R.id.content);
		
		//得到按钮对象
		myButton = (ImageButton) findViewById(R.id.home_bt_like);
		markButton = (ImageButton) findViewById(R.id.home_bt_mark);
		
		//按钮设置监听
		myButton.setOnClickListener(this);
		markButton.setOnClickListener(this);	
		
		//初始化默认显示的页面
		showMyView();
	}
	
	/**
	 * 添加视图
	 */
	public void addView(String id, Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		//移除这个布局中所有的组件
		mContent.removeAllViews();
		mContent.addView(getLocalActivityManager().startActivity(id, intent).getDecorView());
	}	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_bt_like:
			showMyView();
			break;
		case R.id.home_bt_mark:
			showMarkView();
			break;
		default:
			break;
		}
	}

	/**
	 * 显示我的贴吧页面
	 */
	private void showMyView(){
		addView(HOME_LIKE_ID, MyActivity.class);
		myButton.setBackgroundResource(R.drawable.home_topbar_bt);
		myButton.setImageResource(R.drawable.home_bt_like_on);
		markButton.setBackgroundDrawable(null);
		markButton.setImageResource(R.drawable.home_bt_mark);
	}
	
	/**
	 * 显示我的标签页面
	 */
	private void showMarkView(){
		addView(HOME_MARK_ID, MarkActivity.class);
		markButton.setBackgroundResource(R.drawable.home_topbar_bt);
		markButton.setImageResource(R.drawable.home_bt_mark_on);
		myButton.setBackgroundDrawable(null);
		myButton.setImageResource(R.drawable.home_bt_like);
	}
}

























