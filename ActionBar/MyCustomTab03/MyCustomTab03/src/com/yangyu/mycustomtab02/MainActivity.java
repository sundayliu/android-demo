package com.yangyu.mycustomtab02;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @author yangyu
 *	功能描述：自定义TabHost
 */
public class MainActivity extends TabActivity{
	
	//定义TabHost对象
	private TabHost	tabHost;
		
	//每一个Tab界面
	private Class mTabClassArray[]= {Activity1.class,Activity2.class,Activity3.class,Activity4.class};
	
	//Tab选项卡的文字
	private String mTextviewArray[] = {"动态", "与我相关", "我的空间", "更多"};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
    }
	 
	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化TabHost对象，得到TabHost
		tabHost = getTabHost();
						
		//得到Activity的个数
		int count = mTabClassArray.length;	
				
		for(int i = 0; i < count; i++){	
			//为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = tabHost.newTabSpec(mTextviewArray[i]).setIndicator(mTextviewArray[i]).setContent(getTabItemIntent(i));
			//将Tab按钮添加进Tab选项卡中
			tabHost.addTab(tabSpec);
			//设置Tab按钮的背景
			tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.toolbar_bg_pressed);
		}
	}
				
	/**
	 * 给Tab选项卡设置内容（每个内容都是一个Activity）
	 */
	private Intent getTabItemIntent(int index){
		Intent intent = new Intent(this, mTabClassArray[index]);	
		return intent;
	}
}
