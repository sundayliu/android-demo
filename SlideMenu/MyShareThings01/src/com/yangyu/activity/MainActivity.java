package com.yangyu.activity;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.onekeyshare.ShareAllGird;

import com.yangyu.mysharethings.R;

/**
 * @author yangyu
 *	������������Activity�࣬����������
 */
public class MainActivity extends Activity implements OnClickListener {
	//����ͼƬ��ŵĵ�ַ
	public static String TEST_IMAGE;

	//����"�˺ŵ�½"��ť��"�з�����水ť"��"�޷������"��ť��"�õ��û�����"��ť
	private Button authLoginBtn,shareGuiBtn,shareBtn,getInfoBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��ʼ��ShareSDK
		AbstractWeibo.initSDK(this);	
		
		initImagePath();					
		
		initView();
		
		initData();
	}

	/**
	 * ��ʼ�����
	 */
	private void initView(){
		authLoginBtn = (Button)findViewById(R.id.btnLogin);
		shareGuiBtn = (Button)findViewById(R.id.btnShareAllGui);
		shareBtn = (Button)findViewById(R.id.btnShareAll);
		getInfoBtn = (Button)findViewById(R.id.btnUserInfo);
	}
	
	/**
	 * ��ʼ������
	 */
	private void initData(){
		//���ð�ť�����¼�
		authLoginBtn.setOnClickListener(this);
		shareGuiBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);
		getInfoBtn.setOnClickListener(this);
	}
	
	/**
	 * ��ʼ�������ͼƬ
	 */
	private void initImagePath() {
		try {//�ж�SD�����Ƿ���ڴ��ļ���
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
					&& Environment.getExternalStorageDirectory().exists()) {
				TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic.png";
			}
			else {
				TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + "/pic.png";
			}
			File file = new File(TEST_IMAGE);
			//�ж�ͼƬ�Ƿ����ļ�����
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
	}
	
	/**
	 * ��ť�����¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			startActivity(new Intent(MainActivity.this,AuthActivity.class));
			break;
		case R.id.btnShareAllGui:
			showGrid(false);
			break;
		case R.id.btnShareAll:
			showGrid(true);
			break;
		case R.id.btnUserInfo:
			// ��ȡ�Լ�������
			Intent i = new Intent(this, GetInforActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
		
	}

	/**
	 * ʹ�ÿ�ݷ������ͼ�ķ���
	 */
	private void showGrid(boolean silent) {
		Intent i = new Intent(this, ShareAllGird.class);
		// ����ʱNotification��ͼ��
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// ����ʱNotification�ı���
		i.putExtra("notif_title", this.getString(R.string.app_name));

		// title���⣬��ӡ��ʼǡ����䡢��Ϣ��΢�ţ��������Ѻ�����Ȧ������������QQ�ռ�ʹ�ã�������Բ��ṩ
		i.putExtra("title", this.getString(R.string.share));
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ�ã�������Բ��ṩ
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		i.putExtra("text", this.getString(R.string.share_content));
		// imagePath�Ǳ��ص�ͼƬ·��������ƽ̨��֧������ֶΣ����ṩ�����ʾ������ͼƬ
		i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
		i.putExtra("url", "http://sharesdk.cn");
		// thumbPath������ͼ�ı���·��������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
		i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// appPath�Ǵ�����Ӧ�ó���ı���·��������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
		i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ�ã�������Բ��ṩ
		i.putExtra("comment", this.getString(R.string.share));
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ�ã�������Բ��ṩ
		i.putExtra("site", this.getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ�ã�������Բ��ṩ
		i.putExtra("siteUrl", "http://sharesdk.cn");

		// �Ƿ�ֱ�ӷ���
		i.putExtra("silent", silent);
		this.startActivity(i);
	}
	
	/**
	 * ��actionת��ΪString
	 */
	public static String actionToString(int action) {
		switch (action) {
			case AbstractWeibo.ACTION_AUTHORIZING: return "ACTION_AUTHORIZING";
			case AbstractWeibo.ACTION_GETTING_FRIEND_LIST: return "ACTION_GETTING_FRIEND_LIST";
			case AbstractWeibo.ACTION_FOLLOWING_USER: return "ACTION_FOLLOWING_USER";
			case AbstractWeibo.ACTION_SENDING_DIRECT_MESSAGE: return "ACTION_SENDING_DIRECT_MESSAGE";
			case AbstractWeibo.ACTION_TIMELINE: return "ACTION_TIMELINE";
			case AbstractWeibo.ACTION_USER_INFOR: return "ACTION_USER_INFOR";
			case AbstractWeibo.ACTION_SHARE: return "ACTION_SHARE";
			default: {
				return "UNKNOWN";
			}
		}
	}
	
	protected void onDestroy() {
		//����ShareSDK��ͳ�ƹ��ܲ��ͷ���Դ
		AbstractWeibo.stopSDK(this);
		super.onDestroy();
	}
}
