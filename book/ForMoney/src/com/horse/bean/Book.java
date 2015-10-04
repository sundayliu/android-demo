package com.horse.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类包含书的信息，书名，作者，章节名称。
 * 由于这个类在本工程中仅需要实例化一次，因此将它设为单例。
 * 其这个类的唯一对象软件启动时被初始化，在关闭软件之前一般是不会发生了。
 * @author MJZ
 *
 */
public class Book {
	private String bookname;
	private String author;
	private List<String> chapterList = new ArrayList<String>();
	
	private static Book book = new Book();
	
	private Book(){}
	
	public static Book getInstance(){
		return book;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List getChapterList() {
		return chapterList;
	}

	public void setChapterList(List chapterList) {
		this.chapterList = chapterList;
	}
	
	public String getChapterName(int order){
		return (String) chapterList.get(order);
	}
	
	public void addChapter(String chapterName){
		chapterList.add(chapterName);
	}
}
