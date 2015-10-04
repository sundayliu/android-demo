package com.yangyu.mycustomtab01;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.yangyu.mycustomtab01.Constant.ConValue;

/**
 * @author yangyu
 *	������������һ��ʵ�ַ���,�Զ���TabHost
 */
public class CustomTabActivity1 extends TabActivity{
	
	//����TabHost����
	private TabHost	tabHost;
	
	//����һ������
	private LayoutInflater layoutInflater;
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout1);
        
        initView();
    }
	 
	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ����TabHost���󣬵õ�TabHost
		tabHost = getTabHost();
		
		//ʵ�������ֶ���
		layoutInflater = LayoutInflater.from(this);
		
		//�õ�Activity�ĸ���
		int count = ConValue.mTabClassArray.length;	
				
		for(int i = 0; i < count; i++){	
			//Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = tabHost.newTabSpec(ConValue.mTextviewArray[i]).setIndicator(getTabItemView(i)).setContent(getTabItemIntent(i));
			//��Tab��ť��ӽ�Tabѡ���
			tabHost.addTab(tabSpec);
			//����Tab��ť�ı���
			tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
	}
			
	/**
	 * ��Tab��ť����ͼ�������
	 */
	private View getTabItemView(int index){
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
	
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

		if (imageView != null){
			imageView.setImageResource(ConValue.mImageViewArray[index]);
		}		
		TextView textView = (TextView) view.findViewById(R.id.textview);
		
		textView.setText(ConValue.mTextviewArray[index]);
	
		return view;
	}
	
	/**
	 * ��Tabѡ��������ݣ�ÿ�����ݶ���һ��Activity��
	 */
	private Intent getTabItemIntent(int index){
		Intent intent = new Intent(this, ConValue.mTabClassArray[index]);
		
		return intent;
	}
}
