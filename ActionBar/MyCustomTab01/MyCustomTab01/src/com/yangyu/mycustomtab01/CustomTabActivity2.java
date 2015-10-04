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
 *	功能描述：第二种实现方式，自定义RadioGroup
 */
public class CustomTabActivity2 extends TabActivity{
	
	//定义TabHost对象
	private TabHost tabHost;
	
	//定义RadioGroup对象
	private RadioGroup radioGroup;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout2);
        
        initView();
        
        initData();
    }
	
	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化TabHost，得到TabHost对象
		tabHost = getTabHost();
		
		//得到Activity的个数
		int count = ConValue.mTabClassArray.length;				
				
		for(int i = 0; i < count; i++){	
			//为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = tabHost.newTabSpec(ConValue.mTextviewArray[i]).setIndicator(ConValue.mTextviewArray[i]).setContent(getTabItemIntent(i));
			//将Tab按钮添加进Tab选项卡中
			tabHost.addTab(tabSpec);
		}
		
		//实例化RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
	}
	
	/**
	 * 初始化组件
	 */
	private void initData() {
		// 给radioGroup设置监听事件
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
	 * 给Tab选项卡设置内容（每个内容都是一个Activity）
	 */
	private Intent getTabItemIntent(int index){
		Intent intent = new Intent(this, ConValue.mTabClassArray[index]);	
		return intent;
	}
}
