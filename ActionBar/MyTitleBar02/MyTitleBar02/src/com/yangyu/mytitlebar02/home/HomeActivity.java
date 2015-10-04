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
 *	����������������ҳActivityҳ��
 */
public class HomeActivity extends ActivityGroup implements OnClickListener{
	//����֡���ֶ���
	private FrameLayout mContent;
	
	//����ͼƬ��ť����
	private ImageButton myButton,markButton;
	
	//�����ʾ��
	private static final String HOME_LIKE_ID = "like";
	private static final String HOME_MARK_ID = "mark"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		mContent = (FrameLayout) findViewById(R.id.content);
		
		//�õ���ť����
		myButton = (ImageButton) findViewById(R.id.home_bt_like);
		markButton = (ImageButton) findViewById(R.id.home_bt_mark);
		
		//��ť���ü���
		myButton.setOnClickListener(this);
		markButton.setOnClickListener(this);	
		
		//��ʼ��Ĭ����ʾ��ҳ��
		showMyView();
	}
	
	/**
	 * �����ͼ
	 */
	public void addView(String id, Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		//�Ƴ�������������е����
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
	 * ��ʾ�ҵ�����ҳ��
	 */
	private void showMyView(){
		addView(HOME_LIKE_ID, MyActivity.class);
		myButton.setBackgroundResource(R.drawable.home_topbar_bt);
		myButton.setImageResource(R.drawable.home_bt_like_on);
		markButton.setBackgroundDrawable(null);
		markButton.setImageResource(R.drawable.home_bt_mark);
	}
	
	/**
	 * ��ʾ�ҵı�ǩҳ��
	 */
	private void showMarkView(){
		addView(HOME_MARK_ID, MarkActivity.class);
		markButton.setBackgroundResource(R.drawable.home_topbar_bt);
		markButton.setImageResource(R.drawable.home_bt_mark_on);
		myButton.setBackgroundDrawable(null);
		myButton.setImageResource(R.drawable.home_bt_like);
	}
}

























