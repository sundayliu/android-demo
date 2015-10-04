package com.zhf.frameworkdemo02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhf.frameworkdemo02.R;
import com.zhf.frameworkdemo02.fragments.GeneralFragment;

/**
 * 更多页面
 * @author ZHF
 *
 */
public class MoreView extends GeneralFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setTitle("更多");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.more, container, false);
	}
}
