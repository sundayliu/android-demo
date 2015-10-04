package com.horse;

import com.horse.R;
import com.horse.bean.Chapter;
import com.horse.util.BookPage;
import com.horse.util.IOHelper;
import com.horse.view.PageWidget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class ViewBookActivity extends Activity{
	/*private TextView booktitleTv;
	private TextView bookcontentTv;*/
	private PageWidget pageWidget;
	private Bitmap curBitmap, nextBitmap;
	private Canvas curCanvas, nextCanvas;
	private BookPage bookpage ;
	
	private Chapter chapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		initChapter();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int w = dm.widthPixels;
		int h = dm.heightPixels;
		System.out.println(w + "\t" + h);
		curBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		nextBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

		curCanvas = new Canvas(curBitmap);
		nextCanvas = new Canvas(nextBitmap);
		bookpage = new BookPage(w, h, chapter);

		bookpage.setBgBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.bg));		
		
		bookpage.draw(curCanvas);
		
		pageWidget = new PageWidget(this, w, h);
		setContentView(pageWidget);
		pageWidget.setBitmaps(curBitmap, curBitmap);

		pageWidget.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub

				boolean ret = false;
				boolean toAnotherChapter = true;
				if (v == pageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						pageWidget.abortAnimation();
						pageWidget.calcCornerXY(e.getX(), e.getY());

						bookpage.draw(curCanvas);
						if (pageWidget.DragToRight()) {
							if(bookpage.prePage()){
								bookpage.draw(nextCanvas);
							} else
								return false;
						} else {
							if (bookpage.nextPage()){
								bookpage.draw(nextCanvas);
							}
							else
								return false;
						}
						pageWidget.setBitmaps(curBitmap, nextBitmap);
					}

					ret = pageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}

		});
	}
	
	private void init(){
		initChapter();
		/*booktitleTv.setText(chapter.getTitle());
		bookcontentTv.setText(chapter.getContent());*/
	}
	
	private void initChapter(){
		Intent intent = getIntent();
		int order = intent.getIntExtra("listorder", -1);
		chapter = IOHelper.getChapter(order);
	}
}
