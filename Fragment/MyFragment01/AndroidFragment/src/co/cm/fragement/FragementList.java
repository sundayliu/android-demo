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
 *	��������������fragment�����࣬�����ṩ��ѡ�����
 */
public class FragementList extends Fragment {
	//����л���wifi�洢����
	private TextView wifi;
	
	//����л���save�洢����
	private TextView saveBut;
	
	//��������fragmentʵ��
	private FragementDetails frag_detail;
	
	//�򿪹ر�wifi��ť
	private ToggleButton toggleButton;
		
	//toggleButton��ť�Ƿ񱻵��
	private boolean isChecked = false;
	
	//����button״̬�̱߳�־λ
	private boolean butIsRunning = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// �������ʼ��fragment��ҳ��
		return inflater.inflate(R.layout.frag_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// ����fragment����activity������oncreated������onActivityCreated
		setView();
		setListener();

		startThread();// ��������button���̣߳���wifi״̬������1����3��ʱ�򣬲��ɵ����
		// if (frag != null && frag.isInLayout()) {
		// switch (arg2) {
		// case 0:
		// frag.setText("0000");
	}

	/**
	 * ����ť���ü���
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
				checktoggleButton();// ����ص�wifi����ʱ��ˢ��button��״̬
			}
		});

		toggleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("111", isChecked + "/" + WifiAdmin.getWifiAdmin().checkState());
				if (isChecked) {
					WifiAdmin.getWifiAdmin().OpenWifi();
					frag_detail.setWifiShow();
					// toggleButton.setText("�ر�");
					toggleButton.setChecked(false);
					isChecked = false;
				} else {
					WifiAdmin.getWifiAdmin().CloseWife();
					frag_detail.setWifiShow();
					// toggleButton.setText("��");
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
		
		// ʵ����������棬�Ա��������ķ���F
		frag_detail = (FragementDetails) getFragmentManager().findFragmentById(R.id.frag_detail);
		
		// ��ʼ��button��װ̬
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
					//ֻ��wifi״̬�ı�仯���֮�������������ť
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
