package com.horse;

import com.horse.R;
import com.horse.bean.Book;
import com.horse.dialog.AboutDialog;

import android.app.Activity;
//import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity {

	private ListView booklistLv;
	private TextView booknameTv;
	private Book book;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		booklistLv = (ListView) findViewById(R.id.booklist_lv);
		booknameTv = (TextView) findViewById(R.id.bookname_tv);
		book = Book.getInstance();

		booklistLv.setOnItemClickListener(itemListener);
		init();
	}

	private void init() {
		booknameTv.setText(book.getBookname());
		fillBooklistLv();
	}

	private void fillBooklistLv(){
		BooklistAdapter bAdapter = new BooklistAdapter(book.getChapterList(),
				this);
		booklistLv.setAdapter(bAdapter);
	}

	private OnItemClickListener itemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(HomeActivity.this,
					ViewBookActivity.class);
			intent.putExtra("listorder", position);
			startActivity(intent);
		}
	};
	
	public boolean onCreateOptionsMenu(Menu menu) {  
	    menu.add(0, 1, 0, "关于");  
	    return super.onCreateOptionsMenu(menu);  
	}  
	
	public boolean onOptionsItemSelected(MenuItem item) {
		AboutDialog dia = new AboutDialog(HomeActivity.this);
		dia.show();
		dia.setAboutTv(getString(R.string.developer));
		return true;
	}
}
