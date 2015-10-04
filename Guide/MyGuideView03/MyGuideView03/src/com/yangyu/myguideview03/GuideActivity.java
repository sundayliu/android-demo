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
 * ������������������(ÿ��ͼƬ��ִ�еĶ���˳�򣬽��֡��Ŵ�ͽ������������л�ͼƬ������ 
 * �ֿ�ʼִ�� ���֡��Ŵ�ͽ���,�����һ��ִ���꽥�����л�����һ�ţ��Ӷ��ﵽѭ��Ч��) 
 */
public class GuideActivity extends Activity implements OnClickListener{
	//����ע�ᡢ��¼�Ϳ�������ʶ���˰�ť
	private Button btnRegister,btnLogin,btnIKnowPeople;
	 
    //��ʾͼƬ��ImageView��� 
    private ImageView ivGuidePicture;  
    
    //Ҫչʾ��һ��ͼƬ��Դ 
    private Drawable[] pictures; 
    
    //ÿ��չʾͼƬҪִ�е�һ�鶯��Ч��
    private Animation[] animations;
    
    //��ǰִ�е��ǵڼ���ͼƬ����Դ������
    private int currentItem = 0;  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		initView();
		
		initData();
	}

	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ����ImageView����ͼƬ
		ivGuidePicture = (ImageView) findViewById(R.id.iv_guide_picture);  
        
		//ʵ������ť
		btnRegister = (Button) findViewById(R.id.btn_register);  
        btnIKnowPeople = (Button) findViewById(R.id.btn_look_at_the_people_i_know);  
        btnLogin = (Button) findViewById(R.id.btn_login);  
  
        //ʵ��������ͼƬ����
        pictures = new Drawable[] { getResources().getDrawable(R.drawable.v5_3_0_guide_pic1),getResources().getDrawable(R.drawable.v5_3_0_guide_pic2),
        		                    getResources().getDrawable(R.drawable.v5_3_0_guide_pic3)};  
  
        //ʵ��������Ч������
        animations = new Animation[] { AnimationUtils.loadAnimation(this, R.anim.guide_fade_in),  
                					   AnimationUtils.loadAnimation(this, R.anim.guide_fade_in_scale),  
                					   AnimationUtils.loadAnimation(this, R.anim.guide_fade_out) };  
	}

	/**
	 * ��ʼ������
	 */
	private void initData(){
		//����ť���ü���
		btnRegister.setOnClickListener(this);  
        btnIKnowPeople.setOnClickListener(this);  
        btnLogin.setOnClickListener(this);
                     
        //��ÿ������Ч�����ò���ʱ��
        animations[0].setDuration(1500);  
        animations[1].setDuration(3000);  
        animations[2].setDuration(1500);  
  
        //��ÿ������Ч�����ü����¼�
        animations[0].setAnimationListener(new GuideAnimationListener(0));  
        animations[1].setAnimationListener(new GuideAnimationListener(1));  
        animations[2].setAnimationListener(new GuideAnimationListener(2));  
        
        //����ͼƬ������ʼ��Ĭ��ֵΪ0
        ivGuidePicture.setImageDrawable(pictures[currentItem]);  
        ivGuidePicture.startAnimation(animations[0]); 
	}

	/**
	 * ʵ���˶��������ӿڣ���д����ķ���
	 */
	class GuideAnimationListener implements AnimationListener {  		  
        private int index;  
  
        public GuideAnimationListener(int index) {  
            this.index = index;  
        }  
  
        @Override  
        public void onAnimationStart(Animation animation) {  
        }  
        
        //��д��������ʱ�ļ����¼���ʵ���˶���ѭ�����ŵ�Ч��
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
	        	Toast.makeText(this, "�����ע�ᰴť", Toast.LENGTH_SHORT).show();
	            break;  
	        case R.id.btn_look_at_the_people_i_know:
	        	Toast.makeText(this, "���������ʶ���˰�ť", Toast.LENGTH_SHORT).show();
	            break;  
	        case R.id.btn_login:  	  
	        	Toast.makeText(this, "����˵�¼��ť", Toast.LENGTH_SHORT).show();
	            break;  
	        default:  
	            break;  
	        }  
	}
}
