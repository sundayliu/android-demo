package com.yangyu.mycustomtab02;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @author yangyu
 *	�����������Զ���TabHost
 */
public class MainActivity extends TabActivity{
	
	//����TabHost����
	private TabHost	tabHost;
		
	//ÿһ��Tab����
	private Class mTabClassArray[]= {Activity1.class,Activity2.class,Activity3.class,Activity4.class};
	
	//Tabѡ�������
	private String mTextviewArray[] = {"��̬", "�������", "�ҵĿռ�", "����"};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
    }
	 
	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ����TabHost���󣬵õ�TabHost
		tabHost = getTabHost();
						
		//�õ�Activity�ĸ���
		int count = mTabClassArray.length;	
				
		for(int i = 0; i < count; i++){	
			//Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = tabHost.newTabSpec(mTextviewArray[i]).setIndicator(mTextviewArray[i]).setContent(getTabItemIntent(i));
			//��Tab��ť��ӽ�Tabѡ���
			tabHost.addTab(tabSpec);
			//����Tab��ť�ı���
			tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.toolbar_bg_pressed);
		}
	}
				
	/**
	 * ��Tabѡ��������ݣ�ÿ�����ݶ���һ��Activity��
	 */
	private Intent getTabItemIntent(int index){
		Intent intent = new Intent(this, mTabClassArray[index]);	
		return intent;
	}
}
