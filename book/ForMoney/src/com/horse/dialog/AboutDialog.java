package com.horse.dialog;

import com.horse.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class AboutDialog extends Dialog {

	public AboutDialog(Context context) {
		super(context);
	}
	
	private TextView aboutTv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_dialog);
		
		aboutTv = (TextView) findViewById(R.id.about_tv);
	}

	public void setAboutTv(String str){
		aboutTv.setText(str);
	}
}
