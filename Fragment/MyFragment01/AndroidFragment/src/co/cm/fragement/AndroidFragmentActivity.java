package co.cm.fragement;

import co.cm.fragement.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class AndroidFragmentActivity extends Activity {
	// Ö÷activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		WifiAdmin.getWifiAdmin().setmContext(AndroidFragmentActivity.this);
		WifiAdmin.getWifiAdmin().getWifiMeathod();
	}
}