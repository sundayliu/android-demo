package com.sundayliu.android.model;

public class PcsaltData {
    private String mDescription;
    private String mTitle;
    private int mImageResId;
    
    public String getDescription(){
        return mDescription;
    }
    
    public String getTitle(){
        return mTitle;
    }
    
    public int getImageResId(){
        return mImageResId;
    }
    
    public void setDescription(String description){
        mDescription = description;
    }
    
    public void setTitle(String title){
        mTitle = title;
    }
    
    public void setImageResId(int imageResId){
        mImageResId = imageResId;
    }

}
