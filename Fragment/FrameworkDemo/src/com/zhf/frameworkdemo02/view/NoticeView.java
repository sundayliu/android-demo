package com.zhf.frameworkdemo02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhf.frameworkdemo02.R;
import com.zhf.frameworkdemo02.fragments.GeneralFragment;

/**
 * 公告页面
 * @author ZHF
 *
 */
public class NoticeView extends GeneralFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setTitle("公告");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.notice, container, false);
	}
}
