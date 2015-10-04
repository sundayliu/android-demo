package co.cm.fragement;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

/**
 * @author yangyu
 *	wifiAdmin提供了wifi操作的方法
 */
public class WifiAdmin {
	private static WifiAdmin wifiAdmin;
	
	private WifiManager mWifiManager = null;
	
	private WifiInfo mWifiInfo = null;
	
	// 扫描出的网络连接列表
	private List<ScanResult> mWifiList = new ArrayList<ScanResult>();
	
	// 扫描出的网络连接列表
	private List<ScanResult> lastWifiList = new ArrayList<ScanResult>();
	
	// 网络连接列表
	private List<WifiConfiguration> mWifiConfiguration = null;
	
	private WifiLock mWifiLock = null;
		
	// 上次网络状态
	private int lastWifiState = 0;

	//定义上下文Context
	Context mContext;

	public List<ScanResult> getLastWifiList() {
		return lastWifiList;
	}

	public void setLastWifiList(List<ScanResult> lastWifiList) {
		this.lastWifiList = lastWifiList;
	}

	public int getLastWifiState() {
		return lastWifiState;
	}

	public void setLastWifiState(int lastWifiState) {
		this.lastWifiState = lastWifiState;
	}

	public static WifiAdmin getWifi() {
		return wifiAdmin;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public static WifiAdmin getWifiAdmin() {
		if (wifiAdmin == null) {
			wifiAdmin = new WifiAdmin();

		}
		return wifiAdmin;
	}

	public void getWifiMeathod() {
		mWifiManager = (WifiManager) mContext
				.getSystemService(mContext.WIFI_SERVICE);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	/**
	 * 打开wifi
	 */
	public void OpenWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		} else {
			Log.i("111", "open 失败");
		}
	}

	/**
	 * 关闭wifi 
	 */
	public void CloseWife() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		} else {
			Log.i("111", "close 失败");
		}
	}

	/**
	 * 锁定wifi
	 */
	public void lockWifi() {
		mWifiLock.acquire();
	}

	public void rlockWifi() {
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	// 检查当前wifi状态WIFI网卡的状态是由一系列的整形常量来表示的。
	//1.WIFI_STATE_DISABLED : WIFI网卡不可用（1）
	//2.WIFI_STATE_DISABLING : WIFI网卡正在关闭（0）
	//3.WIFI_STATE_ENABLED : WIFI网卡可用（3）
	//4.WIFI_STATE_ENABLING : WIFI网正在打开（2） （WIFI启动需要一段时间）
	//5.WIFI_STATE_UNKNOWN : 未知网卡状态
	public int checkState() {
		return mWifiManager.getWifiState();
	}

	/**
	 * 创建一个wifilock
	 */
	public void Createwifilock() {
		mWifiLock = mWifiManager.createWifiLock("Testss");
	}

	/**
	 * 得到配置好的网络
	 * @return
	 */
	public List<WifiConfiguration> GetConfinguration() {
		return mWifiConfiguration;
	}

	/**
	 * 连接配置好的指定ID的网络
	 * @param index
	 */
	public void ConnectConfiguration(int index) {
		if (index > mWifiConfiguration.size()) {
			return;
		}
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,true);
	}

	/**
	 * 开始扫描网络
	 */
	public void StartScan() {
		mWifiManager.startScan();
		// 得到扫描结果
		mWifiList = mWifiManager.getScanResults();
		// 得到配置好的网络连接
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();
	}

	/**
	 * 得到网络列表
	 * @return
	 */
	public List<ScanResult> GetWifiList() {
		mWifiManager.startScan();
		// 得到扫描结果
		mWifiList = mWifiManager.getScanResults();
		return mWifiList;
	}

	public List<WifiConfiguration> getmWifiConfiguration() {
		return mWifiConfiguration;
	}
	
	/**
	 * 查看扫描结果
	 */
	public StringBuilder LookUpScan() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
			// 将ScanResult信息转换成一个字符串包
			// 其中把包括：BSSID、SSID、capabilities、frequency、level
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}
	
	/**
	 * 得到MAC地址
	 */
	public String GetMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}
	
	/**
	 * 得到接入点的BSSID
	 */
	public String GetBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}
	
	/**
	 * 得到IP地址
	 */
	public int GetIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}
	
	/**
	 * 得到连接的ID
	 */
	public int GetNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}
	
	/**
	 * 得到WifiInfo的所有信息包
	 */
	public String GetWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}
	
	/**
	 * 添加一个网络并连接
	 */
	public void AddNetwork(WifiConfiguration wcg) {
		int wcgID = mWifiManager.addNetwork(wcg);
		mWifiManager.enableNetwork(wcgID, true);
	}
	
	/**
	 * 断开指定ID的网络
	 */
	public void DisconnectWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}
}