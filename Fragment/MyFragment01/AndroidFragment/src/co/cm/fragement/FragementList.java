package co.cm.fragement;

import co.cm.fragement.R;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * @author yuyang
 *	功能描述：左面fragment界面类，该类提供了选项操作
 */
public class FragementList extends Fragment {
	//点击切换到wifi存储界面
	private TextView wifi;
	
	//点击切换到save存储界面
	private TextView saveBut;
	
	//定义右面fragment实例
	private FragementDetails frag_detail;
	
	//打开关闭wifi按钮
	private ToggleButton toggleButton;
		
	//toggleButton按钮是否被点击
	private boolean isChecked = false;
	
	//监听button状态线程标志位
	private boolean butIsRunning = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// 在这里初始化fragment的页面
		return inflater.inflate(R.layout.frag_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 由于fragment不是activity，不是oncreated，而是onActivityCreated
		setView();
		setListener();

		startThread();// 启动控制button的线程，当wifi状态不是在1或者3的时候，不可点击，
		// if (frag != null && frag.isInLayout()) {
		// switch (arg2) {
		// case 0:
		// frag.setText("0000");
	}

	/**
	 * 给按钮设置监听
	 */
	public void setListener() {	
		saveBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				frag_detail.setSaveShow();
			}
		});
		
		wifi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				frag_detail.setWifiShow();
				Log.i("111", WifiAdmin.getWifiAdmin().checkState() + "===-=-");
				checktoggleButton();// 当点回到wifi界面时，刷新button的状态
			}
		});

		toggleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("111", isChecked + "/" + WifiAdmin.getWifiAdmin().checkState());
				if (isChecked) {
					WifiAdmin.getWifiAdmin().OpenWifi();
					frag_detail.setWifiShow();
					// toggleButton.setText("关闭");
					toggleButton.setChecked(false);
					isChecked = false;
				} else {
					WifiAdmin.getWifiAdmin().CloseWife();
					frag_detail.setWifiShow();
					// toggleButton.setText("打开");
					toggleButton.setChecked(true);
					isChecked = true;
				}
				toggleButton.setClickable(false);
			}
		});
	}

	//
	public void checktoggleButton() {
		if (WifiAdmin.getWifiAdmin().checkState() == 1) {
			toggleButton.setChecked(true);
			isChecked = true;
		}
		if (WifiAdmin.getWifiAdmin().checkState() == 3) {
			toggleButton.setChecked(false);
			isChecked = false;
		}
	}

	public void setView() {
		wifi = (TextView) getView().findViewById(R.id.wifi);
		toggleButton = (ToggleButton) getView().findViewById(R.id.toggleButton);
		saveBut = (TextView) getView().findViewById(R.id.saveBut);
		
		// 实例化右面界面，以便操纵里面的方法F
		frag_detail = (FragementDetails) getFragmentManager().findFragmentById(R.id.frag_detail);
		
		// 初始化button的装态
		if (WifiAdmin.getWifiAdmin().checkState() == 3) {
			toggleButton.setChecked(false);
			isChecked = false;
		}
		if (WifiAdmin.getWifiAdmin().checkState() == 1) {
			toggleButton.setChecked(true);
			isChecked = true;
		}
		toggleButton.setClickable(true);
	}

	@Override
	public void onDestroy() {
		frag_detail.stopWifiThread();
		butIsRunning = false;
		super.onDestroy();
	}

	private void startThread() {
		butIsRunning = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (butIsRunning) {
					//只有wifi状态改变变化完毕之后才能允许点击按钮
					if (WifiAdmin.getWifiAdmin().checkState() == 3) {
						if (!isChecked) {
							toggleButton.setClickable(true);
						}

					} else if (WifiAdmin.getWifiAdmin().checkState() == 1) {
						if (isChecked) {
							toggleButton.setClickable(true);
						}
					}
				}
			}
		}).start();
	}

}
