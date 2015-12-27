package com.horse.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.horse.R;
import com.horse.bean.Book;
import com.horse.bean.Chapter;

import android.content.Context;
import android.content.res.Resources;

/**
 * 设计此类的目的是便于统一管理，从资源文件中读取数据。 app中的所有数据来源都可以通过这个类来提供，这样在将来重用代码的时候，也方便修改。
 * 而且在各个类的逻辑任务上也清晰可见。 其目前所做的任务包括，初始化Book类，从资源文件中读取章节内容来初始化Chapter对象。
 * 
 * @author MJZ
 * 
 */
public class IOHelper {
	private static Book book;
	// private static Chapter chapter;
	private static String[] chapterPaths;
	private static Resources res;

	/**
	 * 初始化Book类的唯一对象。 这个函数一般只会调用一次。
	 * 
	 * @param context
	 *            由于从文件中读取资源，则需要通过Activity 来提供。因此在Activity调用此函数的时候，会传入 this。
	 * @return
	 */
	public static Book getBook(Context context) {
		book = Book.getInstance();

		res = context.getResources();
		String booklists[] = res.getStringArray(R.array.booklists);
		chapterPaths = res.getStringArray(R.array.content_path);

		// 设置Book 对象的信息
		book.setAuthor(res.getString(R.string.author));
		book.setBookname(res.getString(R.string.bookname));

		//下面這個if语句是因为出现个bug而写的，它其实不应该存在。
		//猜测的原因可能是在软件退出的时候，Book类对象没有被销毁，则再次启动软件的时候
		//又给它添加了一次章节信息
		if (book.getChapterList().size() != booklists.length)
			for (int i = 0; i < booklists.length; ++i)
				book.addChapter(booklists[i]);

		return book;
	}

	/**
	 * 得到章节内容。
	 * 
	 * @param order
	 *            要读取的章节的顺序。
	 * @param context
	 *            通过context来得到 Resources 对象，从而获取资源。
	 * @return
	 */
	public static Chapter getChapter(int order) {
		// Resources res = context.getResources();
		if (order < 0 || order >= chapterPaths.length)
			return null;
		InputStream is;
		InputStreamReader isr;
		BufferedReader br;
		StringBuffer strBuffer = new StringBuffer();
		try {
			String path = chapterPaths[order];
			is = res.getAssets().open(path);
			
			isr = new InputStreamReader(is, "GBK");
			br = new BufferedReader(isr);

			String str;
			while ((str = br.readLine()) != null)
				strBuffer.append(str + '\n');
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		Chapter chapter = new Chapter();
		chapter.setOrder(order);
		chapter.setTitle(book.getChapterName(order));
		chapter.setContent(strBuffer.toString());

		return chapter;
	}
}
