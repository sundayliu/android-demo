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
		//��ʼ��Ĭ�ϵ��ýӿ���itemѡ�з���
		onItemSelected(R.id.fragment_bottom_home);
	}

	@Override
	public void onItemSelected(int item) {
		Bundle arguments = new Bundle();
		arguments.putInt(Item, item); //��ѡ�еĵײ�radio��Id�Ž�ȥ
		GeneralFragment generalFragment = new GeneralFragment();
		generalFragment.setArguments(arguments); //���ò���ֵ
		//�������item��ID���н�����ת
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.main_detail_FrameLayout, generalFragment).commit();
	}
}
