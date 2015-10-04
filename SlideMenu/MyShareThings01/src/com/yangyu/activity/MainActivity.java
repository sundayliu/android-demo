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
 *	功能描述：主Activity类，程序的入口类
 */
public class MainActivity extends Activity implements OnClickListener {
	//定义图片存放的地址
	public static String TEST_IMAGE;

	//定义"账号登陆"按钮，"有分享界面按钮"，"无分享界面"按钮，"得到用户资料"按钮
	private Button authLoginBtn,shareGuiBtn,shareBtn,getInfoBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化ShareSDK
		AbstractWeibo.initSDK(this);	
		
		initImagePath();					
		
		initView();
		
		initData();
	}

	/**
	 * 初始化组件
	 */
	private void initView(){
		authLoginBtn = (Button)findViewById(R.id.btnLogin);
		shareGuiBtn = (Button)findViewById(R.id.btnShareAllGui);
		shareBtn = (Button)findViewById(R.id.btnShareAll);
		getInfoBtn = (Button)findViewById(R.id.btnUserInfo);
	}
	
	/**
	 * 初始化数据
	 */
	private void initData(){
		//设置按钮监听事件
		authLoginBtn.setOnClickListener(this);
		shareGuiBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);
		getInfoBtn.setOnClickListener(this);
	}
	
	/**
	 * 初始化分享的图片
	 */
	private void initImagePath() {
		try {//判断SD卡中是否存在此文件夹
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
					&& Environment.getExternalStorageDirectory().exists()) {
				TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic.png";
			}
			else {
				TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + "/pic.png";
			}
			File file = new File(TEST_IMAGE);
			//判断图片是否存此文件夹中
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
	 * 按钮监听事件
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
			// 获取自己的资料
			Intent i = new Intent(this, GetInforActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
		
	}

	/**
	 * 使用快捷分享完成图文分享
	 */
	private void showGrid(boolean silent) {
		Intent i = new Intent(this, ShareAllGird.class);
		// 分享时Notification的图标
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// 分享时Notification的标题
		i.putExtra("notif_title", this.getString(R.string.app_name));

		// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
		i.putExtra("title", this.getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		i.putExtra("text", this.getString(R.string.share_content));
		// imagePath是本地的图片路径，所有平台都支持这个字段，不提供，则表示不分享图片
		i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// url仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("url", "http://sharesdk.cn");
		// thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("comment", this.getString(R.string.share));
		// site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
		i.putExtra("site", this.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
		i.putExtra("siteUrl", "http://sharesdk.cn");

		// 是否直接分享
		i.putExtra("silent", silent);
		this.startActivity(i);
	}
	
	/**
	 * 将action转换为String
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
		//结束ShareSDK的统计功能并释放资源
		AbstractWeibo.stopSDK(this);
		super.onDestroy();
	}
}
