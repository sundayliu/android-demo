package com.yangyu.myguideview03;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/** 
 * @author yangyu 
 * 功能描述：导引界面(每张图片都执行的动画顺序，渐现、放大和渐隐，结束后切换图片和文字 
 * 又开始执行 渐现、放大和渐隐,当最后一张执行完渐隐，切换到第一张，从而达到循环效果) 
 */
public class GuideActivity extends Activity implements OnClickListener{
	//定义注册、登录和看看我认识的人按钮
	private Button btnRegister,btnLogin,btnIKnowPeople;
	 
    //显示图片的ImageView组件 
    private ImageView ivGuidePicture;  
    
    //要展示的一组图片资源 
    private Drawable[] pictures; 
    
    //每张展示图片要执行的一组动画效果
    private Animation[] animations;
    
    //当前执行的是第几张图片（资源索引）
    private int currentItem = 0;  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		initView();
		
		initData();
	}

	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化ImageView引导图片
		ivGuidePicture = (ImageView) findViewById(R.id.iv_guide_picture);  
        
		//实例化按钮
		btnRegister = (Button) findViewById(R.id.btn_register);  
        btnIKnowPeople = (Button) findViewById(R.id.btn_look_at_the_people_i_know);  
        btnLogin = (Button) findViewById(R.id.btn_login);  
  
        //实例化引导图片数组
        pictures = new Drawable[] { getResources().getDrawable(R.drawable.v5_3_0_guide_pic1),getResources().getDrawable(R.drawable.v5_3_0_guide_pic2),
        		                    getResources().getDrawable(R.drawable.v5_3_0_guide_pic3)};  
  
        //实例化动画效果数组
        animations = new Animation[] { AnimationUtils.loadAnimation(this, R.anim.guide_fade_in),  
                					   AnimationUtils.loadAnimation(this, R.anim.guide_fade_in_scale),  
                					   AnimationUtils.loadAnimation(this, R.anim.guide_fade_out) };  
	}

	/**
	 * 初始化数据
	 */
	private void initData(){
		//给按钮设置监听
		btnRegister.setOnClickListener(this);  
        btnIKnowPeople.setOnClickListener(this);  
        btnLogin.setOnClickListener(this);
                     
        //给每个动画效果设置播放时间
        animations[0].setDuration(1500);  
        animations[1].setDuration(3000);  
        animations[2].setDuration(1500);  
  
        //给每个动画效果设置监听事件
        animations[0].setAnimationListener(new GuideAnimationListener(0));  
        animations[1].setAnimationListener(new GuideAnimationListener(1));  
        animations[2].setAnimationListener(new GuideAnimationListener(2));  
        
        //设置图片动画初始化默认值为0
        ivGuidePicture.setImageDrawable(pictures[currentItem]);  
        ivGuidePicture.startAnimation(animations[0]); 
	}

	/**
	 * 实现了动画监听接口，重写里面的方法
	 */
	class GuideAnimationListener implements AnimationListener {  		  
        private int index;  
  
        public GuideAnimationListener(int index) {  
            this.index = index;  
        }  
  
        @Override  
        public void onAnimationStart(Animation animation) {  
        }  
        
        //重写动画结束时的监听事件，实现了动画循环播放的效果
        @Override  
        public void onAnimationEnd(Animation animation) {  
            if (index < (animations.length - 1)) {  
                ivGuidePicture.startAnimation(animations[index + 1]);  
            } else {  
            	currentItem++;  
                if (currentItem > (pictures.length - 1)) {  
                	currentItem = 0;  
                }  
                ivGuidePicture.setImageDrawable(pictures[currentItem]);  
                ivGuidePicture.startAnimation(animations[0]);  
            }  
        }  
  
        @Override  
        public void onAnimationRepeat(Animation animation) {  
  
        }  
  
    } 
	
	@Override
	public void onClick(View v) {
		 switch (v.getId()) {  
	        case R.id.btn_register: 
	        	Toast.makeText(this, "点击了注册按钮", Toast.LENGTH_SHORT).show();
	            break;  
	        case R.id.btn_look_at_the_people_i_know:
	        	Toast.makeText(this, "点击了我认识的人按钮", Toast.LENGTH_SHORT).show();
	            break;  
	        case R.id.btn_login:  	  
	        	Toast.makeText(this, "点击了登录按钮", Toast.LENGTH_SHORT).show();
	            break;  
	        default:  
	            break;  
	        }  
	}
}
