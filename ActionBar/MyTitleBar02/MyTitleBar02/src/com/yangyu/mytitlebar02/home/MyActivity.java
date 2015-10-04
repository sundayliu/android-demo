package com.yangyu.mytitlebar02.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.yangyu.mytitlebar02.R;

/**
 * @author yangyu
 *	�����������ҵ�����Activityҳ��
 */
public class MyActivity extends Activity {
	//���������
	private ProgressBar proBar;
	
	//����ͼƬ
	private ImageView xjImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_my_activity);
		
		proBar = (ProgressBar)findViewById(R.id.home_progress_like);
		
		xjImage = (ImageView)findViewById(R.id.image_xianjian);
		
		//����һ��Handler�¼�
		new Handler().postDelayed(new Runnable() {
			public void run() {
				proBar.setVisibility(8);
				xjImage.setVisibility(0);
			}
		}, 2000);
	}
	
}
