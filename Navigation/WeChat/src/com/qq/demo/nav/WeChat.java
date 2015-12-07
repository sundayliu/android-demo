package com.qq.demo.nav;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class WeChat extends Activity
    implements android.view.View.OnClickListener
{
    
    private ViewPager mViewPager;
    private PagerAdapter mPageAdapter;
    private List<View> mViews = new ArrayList<View>();
    
    private LinearLayout mTabHome;
    private LinearLayout mTabContact;
    private LinearLayout mTabDiscover;
    private LinearLayout mTabMe;
    
    private ImageButton mHomeImage;
    private ImageButton mContactImage;
    private ImageButton mDiscoverImage;
    private ImageButton mMeImage;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        initView();
        initViewPage();
        initEvent();
    }
    
    private void initView(){
        mViewPager = (ViewPager)findViewById(R.id.id_viewpage);
        
        mTabHome = (LinearLayout)findViewById(R.id.id_tab_weixin);
        mTabContact = (LinearLayout)findViewById(R.id.id_tab_address);
        mTabDiscover = (LinearLayout)findViewById(R.id.id_tab_discover);
        mTabMe = (LinearLayout)findViewById(R.id.id_tab_settings);
        
        mHomeImage = (ImageButton)findViewById(R.id.id_tab_weixin_img);
        mContactImage = (ImageButton)findViewById(R.id.id_tab_address_img);
        mDiscoverImage = (ImageButton)findViewById(R.id.id_tab_frd_img);
        mMeImage = (ImageButton)findViewById(R.id.id_tab_settings_img);
        
    }
    
    private void initViewPage(){
        LayoutInflater layout = LayoutInflater.from(this);
        View tab1 = layout.inflate(R.layout.tab_home, null);
        View tab2 = layout.inflate(R.layout.tab_contact, null);
        View tab3 = layout.inflate(R.layout.tab_discover, null);
        View tab4 = layout.inflate(R.layout.tab_me, null);
        
        mViews.add(tab1);
        mViews.add(tab2);
        mViews.add(tab3);
        mViews.add(tab4);
        
        mPageAdapter = new PagerAdapter(){
            public void destroyItem(ViewGroup container, int position, Object obj){
                container.removeView(mViews.get(position));
            }
            
            public Object instantiateItem(ViewGroup container, int position){
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }
            
            public boolean isViewFromObject(View arg0, Object arg1){
                return arg0 == arg1;
            }
            
            public int getCount(){
                return mViews.size();
            }
        };
        
        mViewPager.setAdapter(mPageAdapter);
    }
    
    private void initEvent(){
        mTabHome.setOnClickListener(this);
        mTabContact.setOnClickListener(this);
        mTabDiscover.setOnClickListener(this);
        mTabMe.setOnClickListener(this);
        
        mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
            public void onPageSelected(int id){
                int currentItem = mViewPager.getCurrentItem();
                switch(currentItem){
                case 0:
                    resetImage();
                    mHomeImage.setImageResource(R.drawable.tab_weixin_pressed);
                    break;
                case 1:
                    resetImage();
                    mContactImage.setImageResource(R.drawable.tab_address_pressed);
                    break;
                case 2:
                    resetImage();
                    mDiscoverImage.setImageResource(R.drawable.tab_find_frd_pressed);
                    break;
                case 3:
                    resetImage();
                    mMeImage.setImageResource(R.drawable.tab_settings_pressed);
                    break;
                default:
                    break;
                }
            }
            
            public void onPageScrolled(int arg0, float arg1, int arg2){
                
            }
            
            public void onPageScrollStateChanged(int arg0){
                
            }
        });
    }
    
    private void resetImage(){
        mHomeImage.setImageResource(R.drawable.tab_weixin_normal);
        mContactImage.setImageResource(R.drawable.tab_address_normal);
        mDiscoverImage.setImageResource(R.drawable.tab_find_frd_normal);
        mMeImage.setImageResource(R.drawable.tab_settings_normal);
    }
    public void onClick(View v){
        switch(v.getId()){
        case R.id.id_tab_weixin:
            mViewPager.setCurrentItem(0);
            resetImage();
            mHomeImage.setImageResource(R.drawable.tab_weixin_pressed);
            break;
        case R.id.id_tab_address:
            mViewPager.setCurrentItem(1);
            resetImage();
            mContactImage.setImageResource(R.drawable.tab_address_pressed);
            break;
        case R.id.id_tab_discover:
            mViewPager.setCurrentItem(2);
            resetImage();
            mDiscoverImage.setImageResource(R.drawable.tab_find_frd_pressed);
            break;
        case R.id.id_tab_settings:
            mViewPager.setCurrentItem(3);
            resetImage();
            mMeImage.setImageResource(R.drawable.tab_settings_pressed);
            break;
        default:
            break;
        }
    }
}
