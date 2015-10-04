package com.zhf.frameworkdemo02.fragments;

import com.zhf.frameworkdemo02.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
/**
 *MainView
 * @author ZHF
 *
 */
public class MainFragment extends FragmentActivity implements BottomFragment.Callbacks {
	
	public final static String Item = "item";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//初始化默认调用接口中item选中方法
		onItemSelected(R.id.fragment_bottom_home);
	}

	@Override
	public void onItemSelected(int item) {
		Bundle arguments = new Bundle();
		arguments.putInt(Item, item); //将选中的底部radio的Id放进去
		GeneralFragment generalFragment = new GeneralFragment();
		generalFragment.setArguments(arguments); //设置参数值
		//这里根据item的ID进行界面跳转
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.main_detail_FrameLayout, generalFragment).commit();
	}
}
