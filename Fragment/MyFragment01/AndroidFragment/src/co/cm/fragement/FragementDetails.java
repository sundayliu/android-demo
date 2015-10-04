package co.cm.fragement;

import java.util.ArrayList;
import java.util.List;
import co.cm.fragement.R;
import android.app.Fragment;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author yangyu
 *	功能描述：右面fragment界面类，该类实现了右面显示的操作
 */
public class FragementDetails extends Fragment {
	private TextView mac_address, bssid, ip_address, id, info, wifiText;
	
	private ListView listView;
	
	private LinearLayout wifiLinear;
	
	private RelativeLayout save, wifi;
	
	private boolean ThreadFlag = false;
	
	//wifi数据适配器
	private WifiAdapter wifiAdapter;
	
	// 扫描出的网络连接列表
	private List<ScanResult> mWifiList = new ArrayList<ScanResult>();
	
	// 网络连接列表
	private List<WifiConfiguration> mWifiConfiguration = null;
	
	private int nowWifiState = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_detail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setView();
		// setListener();
		setWifiShow();

	}

	/**
	 * 显示wifi界面
	 */
	public void setWifiShow() {	
		//通过隐藏显示来达到不同页面内容的切换
		save.setVisibility(View.GONE);
		wifi.setVisibility(View.VISIBLE);
		stopWifiThread();
		refreshWifi();

	}

	/**
	 * 显示保存界面
	 */
	public void setSaveShow() {
		stopWifiThread();
		save.setVisibility(View.VISIBLE);
		wifi.setVisibility(View.GONE);
	}

	/**
	 * 初始化组件
	 */
	public void setView() {
		// -----------------wifi-----------------
		wifiText = (TextView) getView().findViewById(R.id.wifiText);
		mac_address = (TextView) getView().findViewById(R.id.mac_address);
		bssid = (TextView) getView().findViewById(R.id.bssid);
		ip_address = (TextView) getView().findViewById(R.id.ip_address);
		id = (TextView) getView().findViewById(R.id.id);
		info = (TextView) getView().findViewById(R.id.info);
		listView = (ListView) getView().findViewById(R.id.listview);
		wifiLinear = (LinearLayout) getView().findViewById(R.id.wifiLinear);
		save = (RelativeLayout) getView().findViewById(R.id.save);
		wifi = (RelativeLayout) getView().findViewById(R.id.wifi);
		wifiAdapter = new WifiAdapter();
		listView.setAdapter(wifiAdapter);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			nowWifiState = WifiAdmin.getWifiAdmin().checkState();
			// 当wifi打开时，刷新wifi列表的内容
			if (nowWifiState == 3) {
				mWifiList = WifiAdmin.getWifiAdmin().GetWifiList();
				// 如果刚开始检测的wifi列表为空，则创建一个实例化的wifi而不是null，负责会在adpter里面报错
				if (mWifiList != null) {
					// 如果wifi列表发生改变，则更新，else不更新
					if (!mWifiList.toString().equals(
							WifiAdmin.getWifiAdmin().getLastWifiList().toString())) {
						WifiAdmin.getWifiAdmin().setLastWifiList(mWifiList);
						wifiAdapter.notifyDate();
					}
				} else {
					mWifiList = new ArrayList<ScanResult>();
				}
			}
			refreshMeathod();

			super.handleMessage(msg);
		}
	};

	/**
	 * 刷新wifi的状态 
	 */
	public void refreshWifi() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ThreadFlag = true;
				while (ThreadFlag) {
					// Log.i("111", WifiAdmin.getWifiAdmin().checkState() +
					// "!!!");
					Message msg = handler.obtainMessage();
					handler.sendMessage(msg);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void refreshMeathod() {		
		// 此处可用switch
		if (nowWifiState == 3) {		
			wifiLinear.setVisibility(View.VISIBLE);
			wifiText.setVisibility(View.INVISIBLE);
			mac_address.setText(WifiAdmin.getWifiAdmin().GetMacAddress() + "");
			bssid.setText(WifiAdmin.getWifiAdmin().GetBSSID() + "");
			ip_address.setText(WifiAdmin.getWifiAdmin().GetIPAddress() + "");
			id.setText(WifiAdmin.getWifiAdmin().GetNetworkId() + "");
			info.setText(WifiAdmin.getWifiAdmin().GetWifiInfo() + "");			
		} else if (nowWifiState == 1) {
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("要查看可用的网络，请打开wifi");
		} else if (nowWifiState == 2) {
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("wifi正在打开");
		} else if (nowWifiState == 4) {
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("wifi正在关闭");
		} else {
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("我不知道wifi正在做什么");
		}
	}

	public void stopWifiThread() {
		ThreadFlag = false;
	}

	public class WifiAdapter extends BaseAdapter {
		@Override
		public int getCount() {			
			return mWifiList.size();
		}

		@Override
		public Object getItem(int position) {
			return mWifiList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			final ChatViewHolder vh;

			if (convertView == null) {
				vh = new ChatViewHolder();
				view = View.inflate(WifiAdmin.getWifiAdmin().getmContext(),
						R.layout.wifi_list, null);
				vh.wifi_name = (TextView) view.findViewById(R.id.wifi_name);

				vh.wifi_name_state = (TextView) view
						.findViewById(R.id.wifi_name_state);

				view.setTag(vh);
			} else {
				vh = (ChatViewHolder) view.getTag();
			}
			vh.wifi_name.setText(mWifiList.get(position).SSID.toString());// 网络的名字，唯一区别WIFI网络的名字
			vh.wifi_name_state.setText(mWifiList.get(position).level + "");
			return view;
		}

		public void notifyDate() {
			notifyDataSetChanged();
		}

	}

	public class ChatViewHolder {
		TextView wifi_name;// 网络的名字，唯一区别WIFI网络的名字
		TextView wifi_name_state;// 所发现的WIFI网络信号强度
	}

}
