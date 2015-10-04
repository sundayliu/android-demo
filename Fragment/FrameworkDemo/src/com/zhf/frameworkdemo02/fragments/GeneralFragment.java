package com.zhf.frameworkdemo02.fragments;

import java.io.Serializable;
import com.zhf.frameworkdemo02.R;
import com.zhf.frameworkdemo02.view.OrderView;
import com.zhf.frameworkdemo02.view.HomeView;
import com.zhf.frameworkdemo02.view.MoreView;
import com.zhf.frameworkdemo02.view.NoticeView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 框架类，抽象公共方法
 * @author ZHF
 *
 */
public class GeneralFragment extends Fragment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int item; //用于区分底部菜单项
	protected static View main_title_RelativeLayout; //标题栏
	protected final static String key = "Bundle";   //跳转值传递key的名称
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(getArguments() != null) {  //根据接收子类传来的arguments判断item的哪一项
			if(getArguments().containsKey(MainFragment.Item)) {
				item = getArguments().getInt(MainFragment.Item);
			}
		}
	}
	
	/**为Fragment加载布局时调用 **/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_general, container, false);
		GeneralFragment fragment = null;
		switch(item) {
		case R.id.fragment_bottom_home:  //初始化主页
			fragment = new HomeView();
			break;
		case R.id.fragment_bottom_order:
			fragment = new OrderView();  //初始化订单
			 break;
		case R.id.fragment_bottom_notice:
			fragment = new NoticeView();   //初始化公告
			break;
		case R.id.fragment_bottom_more:
			fragment = new MoreView();  //初始化更多
			break;
		default:
			break;
		}
		if(fragment != null) {
			//更换mainView中间的内容（home,msg,at,more）
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.general_fragment, fragment).commit();
		}
		main_title_RelativeLayout =  ((View) container.getParent()).findViewById(R.id.main_title_RelativeLayout);
		//将生成的view返回
		return view;
	}
	
	/**设置标题**/
	protected void setTitle(Object title) {
		if(main_title_RelativeLayout != null) {
			//标题栏中的文字
			TextView mTvTitle = (TextView) main_title_RelativeLayout.findViewById(R.id.main_title_TextView);
			if(mTvTitle != null) {
				if(title instanceof Integer) {  //整型
					mTvTitle.setText((Integer)title);
				} else { //字符类型
					mTvTitle.setText((CharSequence)title);
				}
			}
		}
	}
	
	/**页面跳转值传递**/
	protected void setBundle(Object... objects) {
		Bundle arguments = new Bundle();
		arguments.putSerializable(key, objects);
		GeneralFragment generalFragment = new GeneralFragment();
		generalFragment.setArguments(arguments);
	}
	
	/**获取所传递的值**/
	protected Object[] getBundle() {
		if(getArguments() != null) {
			System.out.println("getBundle");
			if(getArguments().containsKey(key)) {
				Object[] object = (Object[]) getArguments().getSerializable(key);
				return object;
			}
		}
		return null;
	}
	
	/**无参页面跳转**/
	protected void toIntent(GeneralFragment generalFragment) {
		if(generalFragment != null) {
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.general_fragment, generalFragment).commit();
		}
	}

}
