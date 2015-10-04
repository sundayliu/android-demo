package com.yangyu.mycustomtab01;

/**
 * @author yangyu
 *	功能描述：常量工具类
 */
public class Constant {

	
	public static final class ConValue{
		
		/**
		 * Tab选项卡的图标
		 */
		public static int   mImageViewArray[] = {R.drawable.tab_icon1,
											     R.drawable.tab_icon2,
											     R.drawable.tab_icon3,
											     R.drawable.tab_icon4,
											     R.drawable.tab_icon5};

		/**
		 * Tab选项卡的文字
		 */
		public static String mTextviewArray[] = {"主页", "关于", "设置", "搜索", "更多"};
		
		
		/**
		 * 每一个Tab界面
		 */
		public static Class mTabClassArray[]= {Activity1.class,
											   Activity2.class,
											   Activity3.class,
											   Activity4.class,
											   Activity5.class};
	}
}
