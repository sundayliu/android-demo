package com.zeng.reader;

import static com.zeng.reader.MarkManager.single;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainReader extends Activity {
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;

	//
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPageWidget = new PageWidget(this);
		setContentView(mPageWidget); 
		// 若是要修改分辨率的话， 请自己手动该 480 800 两个值。
		mCurPageBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
		//
		// 两画布
		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(480, 800);
		// 设置一张背景图片
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bg));
		//
		try {
			pagefactory.openbook(bookfillPath);// 打开文件 获取到一个缓存
			pagefactory.onDraw(mCurPageCanvas);//
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Toast.makeText(this, "电子书不存在,请将《test.txt》放在SD卡根目录下", Toast.LENGTH_SHORT).show();
		}

		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);
		mPageWidget.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub

				boolean ret = false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(e.getX(), e.getY());

						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.DragToRight()) {// 右边点击的时候为false; 前一页
							try {
								pagefactory.prePage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.isfirstPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							try {
								pagefactory.nextPage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.islastPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}

					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}

		});
	}

	// 菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "添加书签");
		menu.add(0, 1, 0, "查看书签");
		menu.add(0, 2, 0, "字体放大");
		menu.add(0, 3, 0, "字体缩小");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			addTag();
			break;
		case 1:
			checkTag();
			break;
		case 2:
			// 这种方式调字体，不太好；
			int size = pagefactory.getM_fontSize();
			int newsize = size + 2;
			pagefactory.setM_fontSize(newsize);
			//
			int begin = pagefactory.getM_mbBufBegin();
			//
			pagefactory.setM_mbBufEnd(begin);
			try {
				pagefactory.nextPage();
				pagefactory.onDraw(mCurPageCanvas);
				pagefactory.onDraw(mNextPageCanvas);
				mPageWidget.invalidate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// pagefactory.clearM_lines();
			// pagefactory.onDraw(mCurPageCanvas);//
			// pagefactory.onDraw(mNextPageCanvas);//
			// mPageWidget.invalidate();

			break;
		case 3:
			int size1 = pagefactory.getM_fontSize();
			int newsize1 = size1 - 2;
			pagefactory.setM_fontSize(newsize1);
			int begin1 = pagefactory.getM_mbBufBegin();
			//
			pagefactory.setM_mbBufEnd(begin1);
			pagefactory.clearM_lines();//
			pagefactory.onDraw(mCurPageCanvas);//
			pagefactory.onDraw(mNextPageCanvas);//
			mPageWidget.invalidate();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
//添加书签
	private void addTag() {
		// 获取3个值
		int begin = pagefactory.getM_mbBufBegin();
		int end = pagefactory.getM_mbBufEnd();
		String firstLine = ((pagefactory.getM_lines()).elementAt(0)).toString();
		String percent = pagefactory.getStrPercent();//
		String currentTime = sdf.format(new Date());
		// 创建一个书签
		Items item = new Items(begin, end, firstLine, percent, currentTime);
		int isExist = single.isExist(bookName);
		if (isExist >= 0) {
			single.get(isExist).list.add(item);
			producXML();
			Toast.makeText(MainReader.this, "添加书签成功", Toast.LENGTH_SHORT).show();
			return;
		}
		BookMark bm = new BookMark(bookName, item);
		single.add(bm);
		producXML();
		Toast.makeText(MainReader.this, "添加书签成功", Toast.LENGTH_SHORT).show();
	}
	private void checkTag() {
		try {
			XmlParser.parseXml(bookMarkPath);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final AlertDialog adb = new AlertDialog.Builder(this).create();
		adb.setIcon(R.drawable.face248);
		adb.setTitle("书签");
		getData();
		LayoutInflater inflater = getLayoutInflater().from(this);
		View view = inflater.inflate(R.layout.listview, null);
		final ListView listView = (ListView) view.findViewById(R.id.listview);
		final TextView textView = (TextView) view.findViewById(R.id.listView_text);
		if (data.isEmpty()) {
			textView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			adb.setView(view);
			adb.show();
			return;
		}

		adb.setView(view);
		adapter = new SimpleAdapter(MainReader.this, data, R.layout.list_item, new String[] { "content",
				"percent", "time" }, new int[] { R.id.textView1, R.id.textView2, R.id.textView3 });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				int pos = single.isExist(bookName);
				BookMark bm = single.get(pos);
				Items item = bm.getList().get(position);
				// 这里我加一个注释， onDraw方法中 当m_lines是空的时候 ,执行pageDown() 该方法是以结尾点的
				// 字节点为起点去下载下一页，(好像是的...)，所以设置的时候开始点，结尾点，都设置为 开始点
				pagefactory.setM_mbBufEnd((int) item.getBegin());
				pagefactory.setM_mbBufBegin((int) item.getBegin());
				pagefactory.clearM_lines();
				pagefactory.onDraw(mCurPageCanvas);//
				pagefactory.onDraw(mNextPageCanvas);//
				adb.dismiss();
				mPageWidget.invalidate();
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				int pos = single.isExist(bookName);
				final BookMark bm = single.get(pos);
				Items item = bm.getList().get(position);
				final int position1 = position;// 下面使用
				AlertDialog.Builder ad = new AlertDialog.Builder(MainReader.this);
				ad.setTitle("删除该书签").setMessage("确定删除?");
				ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						bm.getList().remove(position1);// 删除该数据
						// 再写文件。。
						producXML();
						getData();
						adb.cancel();
						if (data.isEmpty()) {
							textView.setVisibility(View.VISIBLE);
							listView.setVisibility(View.GONE);
							adb.show();
							return;
						}
						adapter.notifyDataSetChanged();
						adapter = new SimpleAdapter(MainReader.this, data, R.layout.list_item, new String[] {
								"content", "percent", "time" }, new int[] { R.id.textView1, R.id.textView2,
								R.id.textView3 });
						listView.setAdapter(adapter);
						adb.show();

					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();

				return false;
			}
		});
		adb.show();
	}

	private SimpleAdapter adapter;
	ArrayList<Map<String, Object>> data;

	private void getData() {
		data = new ArrayList<Map<String, Object>>();
		if (single.count() == 0)
			return;
		int pos = single.isExist(bookName);
		BookMark bm = single.get(pos);
		for (int i = 0; i < bm.getList().size(); i++) {
			Map map = new HashMap<String, Object>();
			map.put("percent", bm.getList().get(i).getPercent());
			map.put("time", bm.getList().get(i).getTime());
			map.put("content", bm.getList().get(i).getContent());
			data.add(map);
		}
	}

	//
	private String bookName = "test";
	private long begin_pos;
	private long end_pos;
	private String content;// 只取第一行;
	private String bookfillPath = "/sdcard/test.txt";
	private String bookMarkPath = "/sdcard/BookMark/bookmark.xml";

	/**
	 * @return 该方法是否要返回值
	 */
	private boolean producXML() {
		//
		//

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			//
			// xmlSerializer.setOutput(stringWriter);
			String path = "/sdcard/BookMark/";
			File file = new File(path);
			// file.mkdirs();
			// if(!file.isDirectory()){
			if (!file.exists()) {
				file.mkdirs();
			}
			File f = new File(path + "bookmark.xml");
			if (!f.exists()) {
				f = new File(path + "bookmark.xml");
			}
			FileOutputStream fos = new FileOutputStream(f);
			xmlSerializer.setOutput(fos, "utf-8");
			xmlSerializer.startDocument("utf-8", true);
			xmlSerializer.startTag(null, "books");
			if (single.count() != 0)
				for (BookMark mark : single.getList()) {

					xmlSerializer.startTag(null, "book");
					// xmlSerializer.startTag(null, "book"+" name="+bookName);
					xmlSerializer.attribute(null, "name", mark.getBookName());
					for (Items item : mark.getList()) {
						xmlSerializer.startTag(null, "items");
						xmlSerializer.attribute(null, "begin", item.getBegin() + "");
						xmlSerializer.attribute(null, "end", item.getEnd() + "");
						xmlSerializer.attribute(null, "content", item.getContent());
						xmlSerializer.attribute(null, "percent", item.getPercent());
						xmlSerializer.attribute(null, "time", item.getTime());
						xmlSerializer.endTag(null, "items");
					}
					xmlSerializer.endTag(null, "book");
				}
			xmlSerializer.endTag(null, "books");
			xmlSerializer.endDocument();
			//
			xmlSerializer.flush();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
}