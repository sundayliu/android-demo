package com.kaixinbook.mydialog;

import com.kaixinbook.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

/**
 * "����"�Զ���Ի���
 * 
 * @author
 */
public class AboutDialog extends Dialog {
	public AboutDialog(Context context, int theme) {
		super(context, theme);
	}

	private TextView aboutText1, aboutText2, aboutText3, aboutText4,
			aboutText5, aboutText6;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.about);
		aboutText1 = (TextView) findViewById(R.id.aboutText1);
		aboutText2 = (TextView) findViewById(R.id.aboutText2);
		aboutText3 = (TextView) findViewById(R.id.aboutText3);
		aboutText4 = (TextView) findViewById(R.id.aboutText4);
		aboutText5 = (TextView) findViewById(R.id.aboutText5);
		aboutText6 = (TextView) findViewById(R.id.aboutText6);
	}

	// ����
	public void setMessage1(String mess1) {
		aboutText1.setText(mess1);
	}

	// �汾
	public void setMessage2(String mess2) {
		aboutText2.setText(mess2);
	}

	// ֧��
	public void setMessage3(String mess3) {
		aboutText3.setText(mess3);
	}

	// ������
	public void setMessage4(String mess4) {
		aboutText4.setText(mess4);
	}

	// ����
	public void setMessage5(String mess5) {
		aboutText5.setText(mess5);
	}

	// ��Ϣ
	public void setMessage6(String mess6) {
		aboutText6.setText(mess6);
	}
}