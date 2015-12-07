package com.sundayliu.android.model;

public class BookData {
    private int mId;
    private int mCurrentNum;
    private String mAuthor;
    private String mImageUrl;
    private String mName;
    private int mTotalNum;
    private String mDescription;
    private String mLastTitle;
    
    public void setAuthor(String author){
        mAuthor = author;
    }
    
    public String getAuthor(){
        return mAuthor;
    }
    
    public void setId(int id){
        mId = id;
    }
    
    public int getId(){
        return mId;
    }
    
    public void setCurrentNum(int currentNum){
        mCurrentNum = currentNum;
    }
    
    public int getCurrentNum(){
        return mCurrentNum;
    }
    
    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
    
    public String getImageUrl(){
        return mImageUrl;
    }
    
    public void setName(String name){
        mName = name;
    }
    
    public String getName(){
        return mName;
    }
    
    public void setTotalNum(int totalNum){
        mTotalNum = totalNum;
    }
    
    public int getTotalNum(){
        return mTotalNum;
    }
    public void setDescription(String Description){
        mDescription = Description;
    }
    
    public String getDescription(){
        return mDescription;
    }
    public void setLastTitle(String lastTitle){
        mLastTitle = lastTitle;
    }
    
    public String getLastTitle(){
        return mLastTitle;
    }
}
