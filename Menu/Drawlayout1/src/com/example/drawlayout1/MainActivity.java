package com.example.drawlayout1;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private String[] menuLists;
	private ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String titleString;
	private int iSelect = -1;
	private View heandrView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*get the application title*/
		titleString = (String) getTitle();
		
		heandrView = LayoutInflater.from(this).inflate(R.layout.home_menu_list_header, null);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		mDrawerList.addHeaderView(heandrView);
		menuLists = getResources().getStringArray(R.array.menu_content);

		
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuLists);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);

		/*set the shadow for drawer at start(left) or end(right)*/
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		/*show the home icon*/
		getActionBar().setDisplayHomeAsUpEnabled(true);
		/*make sure the home icon enable click*/
		getActionBar().setHomeButtonEnabled(true);

		/*set the application ActionBar title changes*/
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(R.string.please);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (-1 == iSelect) {
					getActionBar().setTitle(titleString);
				} else {
					getActionBar().setTitle(menuLists[iSelect-1]);
				}
			}
		};
		/*set the DrawerLayout Listener*/
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*make sure the home icon enable click and display the DrawerLayout*/
		if (mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Fragment contentFragment = new ContentFragment();
		Bundle args = new Bundle();
		iSelect = arg2;
		if (0 == iSelect){
			iSelect = 1;
		}
		args.putString("text", menuLists[iSelect-1]);
		contentFragment.setArguments(args);

		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.frame_content, contentFragment)
				.commit();
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
