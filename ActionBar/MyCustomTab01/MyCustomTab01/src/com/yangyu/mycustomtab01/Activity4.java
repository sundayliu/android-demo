package com.yangyu.mycustomtab01;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Activity4 extends Activity{

	private final static String TAG = "Activity4";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_layout);

        Log.i(TAG, "=============>onCreate");
    }
	
	@Override
	protected void onResume() {
		super.onResume();	
		Log.i(TAG, "=============>onResume");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "=============>onDestroy");
	}
}