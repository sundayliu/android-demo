package com.yangyu.myactionbar02;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class MyTabListener<T extends Fragment> implements TabListener {
	private Fragment fragment;
	
	private final Activity mActivity;
	
	private final Class<T> mClass;
	
	public MyTabListener(Activity activity, Class<T> clz){
		mActivity = activity;  
		
		mClass = clz; 
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(fragment == null){
			fragment = Fragment.instantiate(mActivity, mClass.getName());
			ft.add(android.R.id.content, fragment, null); 		
		}
		ft.attach(fragment);  
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (fragment != null) {
			ft.detach(fragment);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
}
