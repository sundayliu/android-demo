package com.horse.bean;

/**
 * 章节信息，包括标题和内容，及顺序
 * @author MJZ
 *
 */
public class Chapter {	
	private String title;
	private String content;
	private int order;
	
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
