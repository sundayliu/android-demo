package com.horse.bean;

/**
 * 章节信息，包括标题和内容，及顺序
 * @author MJZ
 *
 */
public class Chapter {	
	private String title;     // 章名
	private String content;   // 章节内容
	private int order;        // 章节序号
	
	// 属于哪一本书 书名
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
