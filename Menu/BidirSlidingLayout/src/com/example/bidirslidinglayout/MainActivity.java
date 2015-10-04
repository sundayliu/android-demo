package com.example.bidirslidinglayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * 滑动菜单Demo主Activity
 * 
 * @author guolin
 */
public class MainActivity extends Activity {

	/**
	 * 双向滑动菜单布局
	 */
	private BidirSlidingLayout bidirSldingLayout;

	/**
	 * 在内容布局上显示的ListView
	 */
	private ListView contentList;

	/**
	 * ListView的适配器
	 */
	private ArrayAdapter<String> contentListAdapter;

	/**
	 * 用于填充contentListAdapter的数据源。
	 */
	private String[] contentItems = { "Content Item 1", "Content Item 2", "Content Item 3",
			"Content Item 4", "Content Item 5", "Content Item 6", "Content Item 7",
			"Content Item 8", "Content Item 9", "Content Item 10", "Content Item 11",
			"Content Item 12", "Content Item 13", "Content Item 14", "Content Item 15",
			"Content Item 16" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bidirSldingLayout = (BidirSlidingLayout) findViewById(R.id.bidir_sliding_layout);
		Button showLeftButton = (Button) findViewById(R.id.show_left_button);
		Button showRightButton = (Button) findViewById(R.id.show_right_button);
		contentList = (ListView) findViewById(R.id.contentList);
		contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				contentItems);
		contentList.setAdapter(contentListAdapter);
		bidirSldingLayout.setScrollEvent(contentList);
		showLeftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bidirSldingLayout.isLeftLayoutVisible()) {
					bidirSldingLayout.scrollToContentFromLeftMenu();
				} else {
					bidirSldingLayout.initShowLeftState();
					bidirSldingLayout.scrollToLeftMenu();
				}
			}
		});
		showRightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bidirSldingLayout.isRightLayoutVisible()) {
					bidirSldingLayout.scrollToContentFromRightMenu();
				} else {
					bidirSldingLayout.initShowRightState();
					bidirSldingLayout.scrollToRightMenu();
				}
			}
		});
	}

}
