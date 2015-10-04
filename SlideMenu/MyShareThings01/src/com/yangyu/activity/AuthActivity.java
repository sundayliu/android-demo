package com.yangyu.activity;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.Toast;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

import com.yangyu.mysharethings.R;

/**
 * @author yangyu
 *	������������Ȩ��ȡ����ȨActivity������UI��ʾ��Ҫ��Ȩ����ƽ̨��ʾ�˻������ƣ�
 *	  ��˴�ҳ����ʵ��չʾ���ǡ���ȡ�û����ϡ��͡�ȡ����Ȩ���������ܡ�
 */
public class AuthActivity extends Activity implements Callback, OnClickListener, WeiboActionListener {
	//����CheckedTextView����
	private CheckedTextView	 sinaCt,qzoneCt,tengxunCt,renrenCt;
	
	//����Handler����
	private Handler handler;

	//�������������
	private TitleLayout llTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		
		initView();
		
		initData();
	}

	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ����Handler����������Ϣ�ص������ӿ�
		handler = new Handler(this);

		//�õ�����������
		llTitle = (TitleLayout) findViewById(R.id.llTitle);		
		
		//�õ��������
		sinaCt    = (CheckedTextView)findViewById(R.id.ctvSw);
		qzoneCt   = (CheckedTextView)findViewById(R.id.ctvQz);
		tengxunCt = (CheckedTextView)findViewById(R.id.ctvTc);
		renrenCt  = (CheckedTextView)findViewById(R.id.ctvRr);		
	}
	
	/**
	 * ��ʼ������
	 */
	private void initData(){
		llTitle.getBtnBack().setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		llTitle.getTvTitle().setText("�û���Ȩ��¼");
		
		//���ü���
		sinaCt.setOnClickListener(this);
		qzoneCt.setOnClickListener(this);
		tengxunCt.setOnClickListener(this);
		renrenCt.setOnClickListener(this);

		//��ȡƽ̨�б�
		AbstractWeibo[] weibos = AbstractWeibo.getWeiboList(this);
		
		for(int i = 0;i < weibos.length;i++){
			if (!weibos[i].isValid()) {
				continue;
			}
			
			CheckedTextView ctv = getView(weibos[i]);
			if (ctv != null) {
				ctv.setChecked(true);
				// �õ���Ȩ�û����û�����
				String userName = weibos[i].getDb().get("nickname"); 
				if (userName == null || userName.length() <= 0 || "null".equals(userName)) {
					// ���ƽ̨�Ѿ���Ȩȴû���õ��ʺ����ƣ����Զ���ȡ�û����ϣ��Ի�ȡ����
					userName = getWeiboName(weibos[i]);
					//���ƽ̨�¼�����
					weibos[i].setWeiboActionListener(this);
					//��ʾ�û����ϣ�null��ʾ��ʾ�Լ�������
					weibos[i].showUser(null);
				}
				ctv.setText(userName);
			}
		}
	}
	
	/**
	 * ��CheckedTextView�������ʾ��Ȩ�û�������
	 */
	private CheckedTextView getView(AbstractWeibo weibo) {
		if (weibo == null) {
			return null;
		}
		
		String name = weibo.getName();
		if (name == null) {
			return null;
		}
		
		View v = null;
		if (SinaWeibo.NAME.equals(name)) {
			v = findViewById(R.id.ctvSw);
		}
		else if (TencentWeibo.NAME.equals(name)) {
			v = findViewById(R.id.ctvTc);
		}		
		else if (Renren.NAME.equals(name)) {
			v = findViewById(R.id.ctvRr);
		}
		else if (QZone.NAME.equals(name)) {
			v = findViewById(R.id.ctvQz);
		}		
		
		if (v == null) {
			return null;
		}
		
		if (! (v instanceof CheckedTextView)) {
			return null;
		}
		
		return (CheckedTextView) v;
	}
	
	/**
	 * �õ���Ȩ�û����û�����
	 */
	private String getWeiboName(AbstractWeibo weibo) {
		if (weibo == null) {
			return null;
		}
		
		String name = weibo.getName();
		if (name == null) {
			return null;
		}
		
		int res = 0;
		if (SinaWeibo.NAME.equals(name)) {
			res = R.string.sinaweibo;
		}
		else if (TencentWeibo.NAME.equals(name)) {
			res = R.string.tencentweibo;
		}		
		else if (Renren.NAME.equals(name)) {
			res = R.string.renren;
		}
		else if (QZone.NAME.equals(name)) {
			res = R.string.qzone;
		}		
		
		if (res == 0) {
			return name;
		}		
		return this.getResources().getString(res);
	}
	
	/**
	 * ��Ȩ��ȡ����Ȩ�İ�ť��������¼�
	 */
	@Override
	public void onClick(View v) {				
		AbstractWeibo weibo = getWeibo(v.getId());
		
		CheckedTextView ctv = (CheckedTextView) v;
		if (weibo == null) {
			ctv.setChecked(false);
			ctv.setText(R.string.not_yet_authorized);
			return;
		}
		
		if (weibo.isValid()) {
			weibo.removeAccount();
			ctv.setChecked(false);
			ctv.setText(R.string.not_yet_authorized);
			return;
		}
		
		weibo.setWeiboActionListener(this);
		weibo.showUser(null);		
	}

	/**
	 * �����Ȩ
	 */
	private AbstractWeibo getWeibo(int vid) {
		String name = null;
		switch (vid) {
		// ��������΢������Ȩҳ��
		case R.id.ctvSw:
			name = SinaWeibo.NAME;
			break;
		// ������Ѷ΢������Ȩҳ��
		case R.id.ctvTc:
			name = TencentWeibo.NAME;
			break;
		// ��������������Ȩҳ��
		case R.id.ctvRr:
			name = Renren.NAME;
			break;
		// ����QQ�ռ����Ȩҳ��
		case R.id.ctvQz:
			name = QZone.NAME;
			break;
		}
		
		if (name != null) {
			return AbstractWeibo.getWeibo(this, name);
		}
		return null;
	}		

	/**
	 * ��Ȩ�ɹ��Ļص�
	 *  weibo - �ص���ƽ̨
	 *	action - ����������
	 *	res - ���������ͨ��res����
	 */
	@Override
	public void onComplete(AbstractWeibo weibo, int action,HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);		
	}

	/**
	 * ��Ȩʧ�ܵĻص�
	 */
	@Override
	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		t.printStackTrace();
		
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);	
	}
	
	/**
	 * ȡ����Ȩ�Ļص�
	 */
	@Override
	public void onCancel(AbstractWeibo weibo, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);	
	}

	/** 
	 * �������Ȩҳ�淵�صĽ��
	 * 
	 * �����ȡ���û������ƣ�����ʾ���ƣ���������Ѿ���Ȩ������ʾƽ̨����
	 */
	@Override
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);

		switch (msg.arg1) {
			case 1: { // �ɹ�
				text = weibo.getName() + " completed at " + text;
				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			}
			break;
			case 2: { // ʧ��
				text = weibo.getName() + " caught error at " + text;
				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
				return false;
			}
			case 3: { // ȡ��
				text = weibo.getName() + " canceled at " + text;
				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
				return false;
			}
		}

		CheckedTextView ctv = getView(weibo);
		if (ctv != null) {
			ctv.setChecked(true);
			String userName = weibo.getDb().get("nickname"); // getAuthedUserName();
			if (userName == null || userName.length() <= 0
					|| "null".equals(userName)) {
				userName = getWeiboName(weibo);
			}
			ctv.setText(userName);
		}
		return false;
	}
}
