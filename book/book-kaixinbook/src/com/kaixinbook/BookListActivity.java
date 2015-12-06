package com.kaixinbook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Intent.ShortcutIconResource;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixinbook.R;
import com.kaixinbook.adapter.LocAdapter;
import com.kaixinbook.helper.LocalBook;
import com.kaixinbook.helper.MarkHelper;
import com.kaixinbook.mydialog.AboutDialog;
import com.kaixinbook.util.PinyinListComparator;

/**
 * �������
 * 
 * @author
 * 
 */

public class BookListActivity extends Activity {

	class AsyncSetApprove extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			if (!isInit) {
				File path = getFilesDir();
				String[] strings = getResources().getStringArray(R.array.bookid);// ��ȡassetsĿ¼�µ��ļ��б�
				for (int i = 0; i < strings.length; i++) {
					try {
						FileOutputStream out = new FileOutputStream(path + "/" + strings[i]);
						BufferedInputStream bufferedIn = new BufferedInputStream(getResources().openRawResource(R.raw.book0 + i));
						BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
						byte[] data = new byte[2048];
						int length = 0;
						while ((length = bufferedIn.read(data)) != -1) {
							bufferedOut.write(data, 0, length);
						}
						// ���������е�����ȫ��д��
						bufferedOut.flush();
						// �ر���
						bufferedIn.close();
						bufferedOut.close();
						sp.edit().putBoolean("isInit", true).commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				ArrayList<HashMap<String, String>> insertList = new ArrayList<HashMap<String, String>>();
				File[] f1 = path.listFiles();
				int len = f1.length;
				for (int i = 0; i < len; i++) {
					if (f1[i].isFile()) {
						if (f1[i].toString().contains(".txt")) {
							HashMap<String, String> insertMap = new HashMap<String, String>();
							insertMap.put("parent", f1[i].getParent());
							insertMap.put("path", f1[i].toString());
							insertList.add(insertMap);
						}
					}
				}
				SQLiteDatabase db = localbook.getWritableDatabase();
				db.delete("localbook", "type='" + 2 + "'", null);

				for (int i = 0; i < insertList.size(); i++) {
					try {
						if (insertList.get(i) != null) {
							String s = insertList.get(i).get("parent");
							String s1 = insertList.get(i).get("path");
							String sql1 = "insert into " + "localbook" + " (parent,path" + ", type" + ",now,ready) values('" + s + "','" + s1 + "',2,0,null" + ");";
							db.execSQL(sql1);
						}
					} catch (SQLException e) {
						Log.e(TAG, "setApprove SQLException", e);
					} catch (Exception e) {
						Log.e(TAG, "setApprove Exception", e);
					}
				}
				db.close();
			}
			isInit = true;
			sp.edit().putBoolean("isInit", true).commit();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			closeProgressDialog();
			local();
			super.onPostExecute(result);
		}
	}

	private static final String TAG = "MainActivity";
	// private Boolean a = true, b = false, c = false;
	private SharedPreferences.Editor editor;
	private boolean isInit = false;
	private Intent it;
	private ListView list;
	private ArrayList<HashMap<String, Object>> listItem = null;
	private SimpleAdapter listItemAdapter = null;
	ListView listView;
	private LocAdapter locAdapter = null;
	private LocalBook localbook;
	private HashMap<String, Object> map = null;
	private Map<String, Integer[]> map2;// ��ű����Ƽ�Ŀ¼��С����ͼƬ����
	private MarkHelper markhelper;
	protected ProgressDialog mDialog = null;
	private int[] menu_toolbar_image_array = { R.drawable.reading, R.drawable.local, R.drawable.favourite };
	private String[] menu_toolbar_name_array = { "����Ķ�", "�������", "�ҵ��ղ�" };
	private AdapterView.AdapterContextMenuInfo menuInfo;
	View menuView;
	private ProgressDialog mpDialog;

	private SharedPreferences sp;

	private TextView titletext, titletext1;

	private GridView toolbarGrid;

	/**
	 * ����listview ��adapter
	 */
	private void adapter() {
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		listItemAdapter = new SimpleAdapter(this, listItem,// ����Դ
				R.layout.item,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "itemback", "ItemImage", "BookName", "ItemTitle", "ItemTitle1", "ItemTitle2", "ItemImage9", "LastImage" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.itemback, R.id.ItemImage, R.id.bookName, R.id.ItemTitle, R.id.ItemTitle1, R.id.ItemTitle2, R.id.ItemImage9, R.id.last });
		// ��Ӳ�����ʾ
		list.setAdapter(listItemAdapter);
	}

	public void closeProgressDialog() {
		if (mpDialog != null) {
			mpDialog.dismiss();
		}
	}

	/**
	 * ������adapter�Ļ�ȡ
	 * 
	 * @param menuNameArray
	 * @param imageResourceArray
	 * @return simperAdapter
	 */
	private SimpleAdapter getMenuAdapter(String[] menuNameArray, int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data, R.layout.item_menu, new String[] { "itemImage", "itemText" }, new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}

	// /**
	// * ����xml����ȡ�ļ���������
	// *
	// * @return
	// */
	// public Map<String, String[]> getNovelAuthor() {
	// Map<String, String[]> map = new HashMap<String, String[]>();
	//
	// XmlResourceParser xml = getResources().getXml(R.xml.filelist);
	// try {
	// xml.next();
	// int eventType = xml.getEventType();
	// while (eventType != XmlPullParser.END_DOCUMENT) {
	// String[] array = new String[2];
	// String id = "";
	// if (eventType == XmlPullParser.START_TAG) {
	// if (xml.getName().equals("file")) {
	// array[0] = xml.getAttributeValue(null, "name");
	// array[1] = xml.getAttributeValue(null, "author");
	// id = xml.getAttributeValue(null, "id");
	// map.put(id, array);
	// }
	// }
	// xml.next();
	// eventType = xml.getEventType();
	// }
	// } catch (Exception e) {
	// }
	// return map;
	// }

	/**
	 * ��ȡSD����Ŀ¼
	 * 
	 * @return
	 */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();
	}

	/**
	 * �����������
	 */
	public void local() {
		SQLiteDatabase db = localbook.getReadableDatabase();
		String col[] = { "path" };
		Cursor cur = db.query("localbook", col, "type=1", null, null, null, null);
		Cursor cur1 = db.query("localbook", col, "type=2", null, null, null, null);
		Integer num = cur.getCount();
		Integer num1 = cur1.getCount();
		ArrayList<String> arraylist = new ArrayList<String>();
		while (cur1.moveToNext()) {
			String s = cur1.getString(cur1.getColumnIndex("path"));
			arraylist.add(s);
		}
		while (cur.moveToNext()) {
			String s = cur.getString(cur.getColumnIndex("path"));
			arraylist.add(s);
		}
		db.close();
		cur.close();
		cur1.close();
		if (listItem == null)
			listItem = new ArrayList<HashMap<String, Object>>();
		listItem.clear();
		String[] bookids = getResources().getStringArray(R.array.bookid);
		String[] booknames = getResources().getStringArray(R.array.bookname);
		String[] bookauthors = getResources().getStringArray(R.array.bookauthor);
		Map<String, String[]> maps = new HashMap<String, String[]>();
		for (int i = 0; i < bookids.length; i++) {
			String[] value = new String[2];
			value[0] = booknames[i];
			value[1] = bookauthors[i];
			maps.put(bookids[i], value);
		}
		for (int i = 0; i < num + num1; i++) {
			if (i < num1) {
				File file1 = new File(arraylist.get(i));
				String m = file1.getName().substring(0, file1.getName().length() - 4);
				if (m.length() > 8) {
					m = m.substring(0, 8) + "...";
				}
				String id = arraylist.get(i).substring(arraylist.get(i).lastIndexOf("/") + 1);
				String[] array = maps.get(id);
				String auther = array != null && array[1] == null ? "δ֪" : array[1];
				String name = array[0] == null ? m : array[0];
				map = new HashMap<String, Object>();

				if (i == 0) {
					map.put("itemback", R.drawable.itemback);
				} else if ((i % 2) == 0) {
					map.put("itemback", R.drawable.itemback);
				}
				map.put("ItemImage", map2 != null ? map2.get(file1.getName())[0] : R.drawable.cover);
				map.put("BookName", "");
				map.put("ItemTitle", name == null ? m : name);
				map.put("ItemTitle1", "���ߣ�" + auther);
				map.put("LastImage", "�Ƽ���Ŀ");
				map.put("path", file1.getPath());
				map.put("com", 0 + file1.getName());// ������������
				listItem.add(map);
			} else {
				map = new HashMap<String, Object>();

				File file1 = new File(arraylist.get(i));
				String m = file1.getName().substring(0, file1.getName().length() - 4);
				if (m.length() > 8) {
					m = m.substring(0, 8) + "...";
				}
				if (i == 0) {
					map.put("itemback", R.drawable.itemback);
				} else if ((i % 2) == 0) {
					map.put("itemback", R.drawable.itemback);
				}
				map.put("ItemImage", R.drawable.cover);
				map.put("BookName", m);
				map.put("ItemTitle", m);
				map.put("ItemTitle1", "���ߣ�δ֪");
				map.put("LastImage", "���ص���");
				map.put("path", file1.getPath());
				map.put("com", "1");
				listItem.add(map);
			}
		}
		Collections.sort(listItem, new PinyinListComparator());
		if (locAdapter == null) {
			locAdapter = new LocAdapter(this, listItem, num1);
			list.setAdapter(locAdapter);
		}
		locAdapter.notifyDataSetChanged();
	}

	/**
	 * ����Ķ�����
	 * 
	 * public void near() { SQLiteDatabase db = localbook.getReadableDatabase();
	 * String col[] = { "path", "ready" }; Cursor cur = db.query("localbook",
	 * col, "now = 1", null, null, null, null); Integer num = cur.getCount();
	 * ArrayList<String> arraylist = new ArrayList<String>(); while
	 * (cur.moveToNext()) { String s =
	 * cur.getString(cur.getColumnIndex("path")); arraylist.add(s); }
	 * db.close(); cur.close(); listItem = new ArrayList<HashMap<String,
	 * Object>>(); for (int i = 0; i < num; i++) { try { map = new
	 * HashMap<String, Object>(); File file1 = new File(arraylist.get(i));
	 * String m = file1.getName().substring(0, file1.getName().length() - 4); if
	 * (m.length() >= 8) { m = m.substring(0, 8) + "..."; } int a =
	 * sp.getInt(arraylist.get(i) + "jumpPage", 1); long b =
	 * sp.getLong(arraylist.get(i) + "count", 1); double c = (double) (a * 100 /
	 * b * 0.01) * 100; int d = (int) ((c * 27) / 100); if (d != 0) { d = d - 1;
	 * } if (i == 0) { map.put("itemback", R.drawable.itemback); } else if ((i %
	 * 2) == 0) { map.put("itemback", R.drawable.itemback); } String cc =
	 * String.valueOf(c); String ccc[] = cc.split("\\."); if (ccc[1] != null) {
	 * if (ccc[1].length() > 2) { cc = ccc[0] + "." + ccc[1].substring(0, 2); }
	 * } map.put("ItemImage", R.drawable.cover); map.put("BookName", m);
	 * map.put("ItemTitle", m); map.put("ItemTitle1", "���ߣ�δ֪");
	 * map.put("ItemTitle2", "���Ķ�����" + cc + "%"); map.put("ItemImage9", pro[d]);
	 * map.put("LastImage", "���ص���"); map.put("path", file1.getPath()); } catch
	 * (Exception e) { Log.e(TAG, "near Exception", e); } listItem.add(map); }
	 * titletext.setText("����Ķ�"); titletext1.setText("(" + listItem.size() +
	 * ")"); adapter(); }
	 */

	/**
	 * �����˵���Ӧ����
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:// ɾ���ļ�
				// ��ȡ���������һ���ļ�
			menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			HashMap<String, Object> imap = listItem.get(menuInfo.position);
			String path0 = (String) imap.get("path");
			SQLiteDatabase db = localbook.getWritableDatabase();
			try {
				ContentValues values = new ContentValues();
				values.put("now", 0);// keyΪ�ֶ�����valueΪֵ
				values.put("type", 0);
				values.putNull("ready");
				// db.update("localbook", values, "path=?", new String[] { s
				// });// �޸�״̬Ϊͼ�鱻�ѱ�����
				db.update("localbook", values, "path=? and type=1", new String[] { path0 });// �޸�״̬Ϊͼ�鱻�ѱ�����
				// ��նԱ���ļ�¼
				editor = sp.edit();
				editor.remove(path0 + "jumpPage");
				editor.remove(path0 + "count");
				editor.remove(path0 + "begin");
				editor.commit();
				markhelper = new MarkHelper(this);
				// ɾ�����ݿ���ǩ��¼
				SQLiteDatabase db2 = markhelper.getWritableDatabase();
				db2.delete("markhelper", "path='" + path0 + "'", null);
				db2.close();
			} catch (SQLException e) {
				Log.e(TAG, "onContextItemSelected-> SQLException error", e);
			} catch (Exception e) {
				Log.e(TAG, "onContextItemSelected-> Exception error", e);
			}
			db.close();
			// ��������ҳ��
			local();
			break;

		case 1:// ������ݷ�ʽ
				// ��ȡ���������һ���ļ�
			menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			// ͨ��position��ȡͼ���ַ
			HashMap<String, Object> imap1 = listItem.get(menuInfo.position);
			String path1 = (String) imap1.get("path");

			Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon); // ��ȡ��ݼ���ͼ��
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, imap1.get("ItemTitle").toString() + ".txt");// ��ݷ�ʽ�ı���

			addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);//
			// ��ݷ�ʽ��ͼ��
			ComponentName comp = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
			// ���ؼ���"bbb"����intent �����¿�ݷ�ʽ�������뱾ҳʱ �Զ���ת������
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).putExtra("bbb", path1).setComponent(comp));//
			// ��ݷ�ʽ�Ķ���
			sendBroadcast(addIntent);// ���͹㲥

			Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
			shortcut.putExtra("duplicate", false); // �������ظ�����
			// ָ����ǰ��ActivityΪ��ݷ�ʽ�����Ķ���: �� com.everest.video.VideoPlayer //ע��:
			// ComponentName�ĵڶ�������������ϵ��(.)�������ݷ�ʽ�޷�������Ӧ���� // ComponentName comp
			// =
			// new ComponentName(this.getPackageName(),
			// "."+this.getLocalClassName()); //
			// shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new
			// Intent(Intent.ACTION_MAIN).setComponent(comp));
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).putExtra("bbb", path1).setComponent(comp));
			// ��ݷ�ʽ��ͼ��
			ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
			sendBroadcast(shortcut);
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		map2 = new HashMap<String, Integer[]>();
		String[] bookids = getResources().getStringArray(R.array.bookid);
		for (int i = 0; i < bookids.length; i++) {
			map2.put(bookids[i], new Integer[] { R.drawable.book0 + i });
		}

		// ���빤���� �����ײ��˵� Toolbar
		toolbarGrid = (GridView) findViewById(R.id.GridView_toolbar);
		// ���ñ���
		toolbarGrid.setBackgroundResource(R.drawable.channelgallery1);
		// ����ÿ������
		toolbarGrid.setNumColumns(3);
		// λ�þ���
		toolbarGrid.setGravity(Gravity.CENTER);
		// ��ֱ���
		toolbarGrid.setVerticalSpacing(10);
		// ˮƽ���
		toolbarGrid.setHorizontalSpacing(10);
		// ���ò˵�Adapter
		toolbarGrid.setAdapter(getMenuAdapter(menu_toolbar_name_array, menu_toolbar_image_array));
		// ��ȡ��Ϊ"mark"��sharedpreferences
		sp = getSharedPreferences("mark", MODE_PRIVATE);
		isInit = sp.getBoolean("isInit", false);
		list = (ListView) findViewById(R.id.ListView01);
		titletext = (TextView) findViewById(R.id.titletext);
		titletext1 = (TextView) findViewById(R.id.titletext1);
		localbook = new LocalBook(this, "localbook");
		// ��ӳ������
		list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

				menu.add(Menu.NONE, 0, 0, "���Ķ��б���ɾ��");
				menu.add(Menu.NONE, 1, 0, "������ݷ�ʽ");
			}

		});

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				try {
					showProgressDialog("���ڼ��ص�����...");
					// �޸����ݿ���ͼ�������Ķ�״̬Ϊ1
					String s = (String) listItem.get(arg2).get("path");
					SQLiteDatabase db = localbook.getWritableDatabase();

					File f = new File(s);
					if (f.length() == 0) {
						Toast.makeText(BookListActivity.this, "���ļ�Ϊ���ļ�", Toast.LENGTH_SHORT).show();
						if (mpDialog != null) {
							mpDialog.dismiss();
						}
					} else {
						ContentValues values = new ContentValues();
						values.put("now", 1);// keyΪ�ֶ�����valueΪֵ
						db.update("localbook", values, "path=?", new String[] { s });// �޸�״̬Ϊͼ�鱻�ѱ�����
						db.close();
						String path = (String) listItem.get(arg2).get("path");
						it = new Intent();
						it.setClass(BookListActivity.this, Read.class);
						it.putExtra("aaa", path);
						startActivity(it);
					}
				} catch (SQLException e) {
					Log.e(TAG, "list.setOnItemClickListener-> SQLException error", e);
				} catch (Exception e) {
					Log.e(TAG, "list.setOnItemClickListener Exception", e);
				}
			}
		});
		// �������ĵ���¼�����
		toolbarGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				switch (arg2) {
				/*
				 * case 0: toolbarGrid
				 * .setBackgroundResource(R.drawable.channelgallery1); near(); a
				 * = true; b = false; c = false;
				 * 
				 * break;
				 */
				case 1:

					toolbarGrid.setBackgroundResource(R.drawable.channelgallery2);
					local();
					break;
				case 2:
					titletext.setText("�ҵ��ղ�");
					toolbarGrid.setBackgroundResource(R.drawable.channelgallery3);
					listItem = new ArrayList<HashMap<String, Object>>();
					adapter();
					titletext1.setText("(0)");
					break;
				}
			}
		});
	}

	/**
	 * ����ϵͳmenu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onDestroy() {
		closeProgressDialog();
		super.onDestroy();
		finish();
	}

	/**
	 * ����menu���������¼�
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int it = item.getItemId();
		switch (it) {
		case R.id.it1:
			// ���뵼��ͼ�����
			Intent intent = new Intent();
			intent.setClass(BookListActivity.this, InActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.it2:
			// ���뷴������
			break;
		case R.id.it3:
			// ������ڽ���
			AboutDialog about;
			about = new AboutDialog(BookListActivity.this, R.style.FullHeightDialog);
			about.show();
			about.setMessage1(getString(R.string.about_book));
			about.setMessage2(getString(R.string.app_name));
			about.setMessage3(getString(R.string.aboue_text1));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();
		closeProgressDialog();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent itt = getIntent();
		String ss = itt.getStringExtra("nol");
		String bb = itt.getStringExtra("bbb");
		Boolean bad = itt.getBooleanExtra("bad", false);
		if (bad) {
			Toast.makeText(BookListActivity.this, "��ʧ��", Toast.LENGTH_SHORT).show();
		}
		// �ж��Ƿ��ɿ�ݷ�ʽ����ĳ���
		if (bb != null) {

			it = new Intent();
			it.setClass(BookListActivity.this, Read.class);
			it.putExtra("aaa", bb);
			it.putExtra("ccc", "ccc");
			startActivity(it);
			this.finish();
		}
		// �ж��Ǵ�������˽�������� ���������� �Դ�ʵ��ͬ������
		if (ss != null) {
			if (ss.equals("n")) {
				local();
			} else {
				toolbarGrid.setBackgroundResource(R.drawable.channelgallery2);
				local();
			}
		} else {
			local();
		}
		if (!isInit) {
			new AsyncSetApprove().execute("");
			showProgressDialog("���ڳ�ʼ��,���Ժ�...");
		}
	}

	/**
	 * ����Ĭ���鼮
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setApprove() {
		if (!isInit) {
			synchronized (this) {
				String[] strings = getResources().getStringArray(R.array.bookname);
				for (int i = 0; i < strings.length; i++) {
					try {
						FileOutputStream out = new FileOutputStream(getFilesDir() + "/" + strings[i]);
						BufferedInputStream bufferedIn = new BufferedInputStream(getResources().openRawResource(R.raw.book0 + i));
						BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
						byte[] data = new byte[2048];
						int length = 0;
						while ((length = bufferedIn.read(data)) != -1) {
							bufferedOut.write(data, 0, length);
						}
						// ���������е�����ȫ��д��
						bufferedOut.flush();
						// �ر���
						bufferedIn.close();
						bufferedOut.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sp.edit().putBoolean("isInit", true).commit();
			}

			ArrayList<HashMap<String, String>> insertList = new ArrayList<HashMap<String, String>>();
			File[] f1 = getFilesDir().listFiles();
			int len = f1.length;
			for (int i = 0; i < len; i++) {
				if (f1[i].isFile()) {
					if (f1[i].toString().contains(".txt")) {
						HashMap insertMap = new HashMap<String, String>();
						insertMap.put("parent", f1[i].getParent());
						insertMap.put("path", f1[i].toString());
						insertList.add(insertMap);
					}
				}
			}

			SQLiteDatabase db = localbook.getWritableDatabase();
			db.delete("localbook", "type='" + 2 + "'", null);

			for (int i = 0; i < insertList.size(); i++) {
				try {
					if (insertList.get(i) != null) {
						String s = insertList.get(i).get("parent");
						String s1 = insertList.get(i).get("path");
						String sql1 = "insert into " + "localbook" + " (parent,path" + ", type" + ",now,ready) values('" + s + "','" + s1 + "',2,0,null" + ");";
						db.execSQL(sql1);
					}
				} catch (SQLException e) {
					Log.e(TAG, "setApprove SQLException", e);
				} catch (Exception e) {
					Log.e(TAG, "setApprove Exception", e);
				}
			}
			db.close();
			local();
		}
	}

	public void showProgressDialog(String msg) {
		mpDialog = new ProgressDialog(BookListActivity.this);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���÷��ΪԲ�ν�����
		mpDialog.setMessage(msg);
		mpDialog.setIndeterminate(false);// ���ý������Ƿ�Ϊ����ȷ
		mpDialog.setCancelable(true);// ���ý������Ƿ���԰��˻ؼ�ȡ��
		mpDialog.show();
	}
}