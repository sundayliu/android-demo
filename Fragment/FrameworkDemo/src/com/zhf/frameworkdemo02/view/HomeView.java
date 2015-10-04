package com.zhf.frameworkdemo02.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhf.frameworkdemo02.R;
import com.zhf.frameworkdemo02.fragments.GeneralFragment;

/**
 * Ö÷Ò³Ãæ
 * @author ZHF
 *
 */
public class HomeView extends GeneralFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setTitle("Ö÷Ò³");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.home, container, false);
	}
}
