package com.sundayliu.android.model;

public class BookData {
    private String author;
    private int currentChapter;
    private int totalChapter;
    private String description;
    private int id;
    private String imageUrl;
    private String lastChapterTitle;
    private String name;
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setImageUrl(String url){
        this.imageUrl = url;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    
}
