package com.gc.testflashview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/**
 * 声明：由于此案例仅是一个Demo，故在此就没有使用UIL进行处理图片，本案例重点不在于防止OOM，重点在于轮播图片
 * 		仅限用于学习交流。
 * @author Android将军 
 *
 */
public class MainActivity extends Activity {

	private SlideShowView mSlideShowView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 设置图片主要是传入图片路径，可以是int类型的，也 可以像博客中的一样，传入String类型的
         */
        List<Integer> imageUris=new ArrayList<Integer>();
        imageUris.add(R.drawable.one);  
        imageUris.add(R.drawable.two);  
        imageUris.add(R.drawable.three);
        /**
         * 获取控件
         */
        mSlideShowView=(SlideShowView)findViewById(R.id.slideshowView);
        /**
         * 为控件设置图片
         */
        mSlideShowView.setImageUris(imageUris);
        
    }
}
