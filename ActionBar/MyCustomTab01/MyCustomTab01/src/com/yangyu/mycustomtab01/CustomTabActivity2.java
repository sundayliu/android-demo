package com.yangyu.mycustomtab01;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.yangyu.mycustomtab01.Constant.ConValue;

/**
 * @author yangyu
 *	�����������ڶ���ʵ�ַ�ʽ���Զ���RadioGroup
 */
public class CustomTabActivity2 extends TabActivity{
	
	//����TabHost����
	private TabHost tabHost;
	
	//����RadioGroup����
	private RadioGroup radioGroup;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout2);
        
        initView();
        
        initData();
    }
	
	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ����TabHost���õ�TabHost����
		tabHost = getTabHost();
		
		//�õ�Activity�ĸ���
		int count = ConValue.mTabClassArray.length;				
				
		for(int i = 0; i < count; i++){	
			//Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = tabHost.newTabSpec(ConValue.mTextviewArray[i]).setIndicator(ConValue.mTextviewArray[i]).setContent(getTabItemIntent(i));
			//��Tab��ť��ӽ�Tabѡ���
			tabHost.addTab(tabSpec);
		}
		
		//ʵ����RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
	}
	
	/**
	 * ��ʼ�����
	 */
	private void initData() {
		// ��radioGroup���ü����¼�
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.RadioButton0:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[0]);
					break;
				case R.id.RadioButton1:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[1]);
					break;
				case R.id.RadioButton2:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[2]);
					break;
				case R.id.RadioButton3:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[3]);
					break;
				case R.id.RadioButton4:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[4]);
					break;
				}
			}
		});
		((RadioButton) radioGroup.getChildAt(0)).toggle();
	}
	
	/**
	 * ��Tabѡ��������ݣ�ÿ�����ݶ���һ��Activity��
	 */
	private Intent getTabItemIntent(int index){
		Intent intent = new Intent(this, ConValue.mTabClassArray[index]);	
		return intent;
	}
}
