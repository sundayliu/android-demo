package com.zhf.frameworkdemo02.fragments;

import com.zhf.frameworkdemo02.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * ҳ��ײ�������
 * 
 * @author ZHF
 * 
 */
public class BottomFragment extends Fragment {
    //Ĭ�ϻص��ӿ�ʵ����Ķ���	
	private Callbacks callbacks = defaultCallbacks; 

	/** Fragment��Activity����������ʱ����� **/
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//��ǰ���Ƿ�ʵ���˵ײ�����������¼��ص��ӿ�
		if(!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implements fragment's callbacks !");
		}
		callbacks = (Callbacks) activity;
	}

	/** ΪFragment���ز���ʱ���� **/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RadioGroup radioGroup = (RadioGroup) inflater.inflate(R.layout.fragment_bottom, container, false);
		//�󶨼�����
		radioGroup.setOnCheckedChangeListener(changeListener);
		return radioGroup;
	}
	
	/**RadioGroup������**/
	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			System.out.println(checkedId);
			callbacks.onItemSelected(checkedId); //���ýӿ��з���
		}
	};

	public interface Callbacks{
		/**�������ص��ӿ�**/
		public void onItemSelected(int item);
	}
	/**Ĭ�ϻص�ʵ����Ķ���**/
	private static Callbacks defaultCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(int item) {
		//ʲô������
		}
	};
	
	/**Fragment��Activity���������ʱ�����**/
    @Override
    public void onDetach() {
        super.onDetach();
       callbacks = defaultCallbacks;
    }
}
