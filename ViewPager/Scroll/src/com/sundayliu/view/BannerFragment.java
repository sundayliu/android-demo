package com.sundayliu.view;
 
import java.util.ArrayList;
import java.util.HashMap;
 
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
 
import com.xinye.R;
import com.xinye.domain.Bargain;
import com.xinye.domain.Screen;
import com.xinye.domain.ViewFrame;
import com.xinye.util.ApplicationUtils;
import com.xinye.util.ConstantsUtils;
import com.xinye.util.FileUtils;
import com.xinye.util.ImagePathUtils;
import com.xinye.util.LogUtils;
import com.xinye.util.StringUtils;
import com.xinye.view.SlidingView;
 
/**
 * Fragment
 *
 *
 *
 */
public class BannerFragment extends BaseFragment implements OnPageChangeListener {
   // 当前Fragment的RootView
   private View mRootView = null;
   // 当前Fragment中的ViewPager
   private ViewPager mViewPager = null;
   // 展示当前页面标识的TextView
   private TextView mTextView = null;
   // 布局填充器对象
   private LayoutInflater mLayoutInflater = null;
   // 保存Bargain信息的ArrayList
   private ArrayList<Bargain> mBargainList = new ArrayList<Bargain>();
   // 保存所有的ViewPager的View
   private ArrayList<ImageView> mViewList = new ArrayList<ImageView>();
   // Bitmap载入工具类
   private FinalBitmap mFinalBitmap;
   // 切换到左边的ImageView
   private ImageView mToLeftImageView = null;
   // 切换到右侧的ImageView
   private ImageView mToRightImageView = null;
   // 图片URL列表
   private static ArrayList<String> mImageUrlList = new ArrayList<String>();
   @Override
   protected void initContent() {
      mFinalBitmap = FinalBitmap.create(mActivity, FileUtils.getCacheDirectory(mActivity));
      mTopbarTextView.setText(R.string.popup_banner);
      mPopupBannerTextView.setTextColor(Color.argb(0xff, 0xa1, 0x01, 0x01));
      mTopbarLeftImageView.setImageResource(R.drawable.topbar_banner);
 
      mLayoutInflater = mActivity.getLayoutInflater();
 
      mRootView = mLayoutInflater.inflate(R.layout.fragment_banner, null);
      mbaseLinearLayout.addView(mRootView);
 
      mViewPager = (ViewPager) mRootView.findViewById(R.id.bannerFragmentViewPager);
      mTextView = (TextView) mRootView.findViewById(R.id.countBannerFragmentTextView);
 
      mToLeftImageView = (ImageView) mRootView.findViewById(R.id.toLeftBannerFragmentImageView);
      mToLeftImageView.setOnClickListener(BannerFragment.this);
 
      mToRightImageView = (ImageView) mRootView.findViewById(R.id.toRightBannerFragmentImageView);
      mToRightImageView.setOnClickListener(BannerFragment.this);
 
      try {
         if(mImageUrlList != null && mImageUrlList.size() > 0){
            initViewPager();
         }else{
            initData();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
 
   // 初始化数据
   private void initData() throws Exception {
      HashMap<String, Object> data = new HashMap<String, Object>();
      data.put("loginUserId", UserLoginActivity.userID);
      data.put("checkStr", UserLoginActivity.token);
      data.put("isMember", UserLoginActivity.isMember);
      String url = StringUtils.composeUrl(ConstantsUtils.BANNER_LIST_URL, data);
      LogUtils.printLog(Log.INFO, LogUtils.LOG_TAG, "BannerFragment initData url = " + url);
      new FinalHttp().get(url, new AjaxCallBack<String>() {
         @Override
         public void onSuccess(String t) {
            LogUtils.printLog(Log.INFO, LogUtils.LOG_TAG, "banner list JSON:" + t);
            parseJSON(t);
            super.onSuccess(t);
         }
 
         @Override
         public void onFailure(Throwable t, String strMsg) {
            if(t != null){
               mImageUrlList = FileUtils.readListFromFile(mActivity, FileUtils.LIST_BANNER_FILE_NAME);
               if(mImageUrlList != null){
                  initViewPager();
               }
            }
            super.onFailure(t, strMsg);
         }
      });
 
   }
 
   // 解析JSON
   private void parseJSON(String t) {
      if (t == null) {
         return;
      }
      try {
         JSONObject tObj = new JSONObject(t);
         int status = tObj.getInt("status");
// String message = tObj.getString("message");
         if (status == 0) {
            String dataString = tObj.getString("data");
            if (dataString != null && !dataString.equalsIgnoreCase("null")) {
               JSONObject dataObj = new JSONObject(dataString);
               String partnerBargainFormListString = dataObj.getString("partnerBargainFormList");
               if (partnerBargainFormListString != null && !partnerBargainFormListString.equalsIgnoreCase("null")) {
                  JSONArray array = new JSONArray(partnerBargainFormListString);
                  Screen screen = ApplicationUtils.getScreen(mActivity);
                  if (array != null) {
                     int len = array.length();
                     for (int i = 0; i < len; i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String bargainString = obj.getString("partnerBargain");
                        Bargain bargain = new Bargain();
                        if (bargainString != null && !bargainString.equalsIgnoreCase("null")) {
                           JSONObject bargainObj = new JSONObject(bargainString);
                           bargain.partnerCategoryId = bargainObj.getLong("partnerCategoryId");
                           bargain.partnerId = bargainObj.getLong("partnerId");
                           bargain.pictureId = bargainObj.getLong("pictureId");
                           bargain.title = bargainObj.getString("title");
                           bargain.displayOrder = bargainObj.getLong("displayOrder");
                           bargain.dailySpecialsId = bargainObj.getLong("dailySpecialsId");
                           bargain.bargainId = bargainObj.getLong("bargainId");
                        }
                        String pic = obj.getString("pic");
                        pic = ImagePathUtils.getBannerRealPath(pic,screen);
                        bargain.pic = pic;
                        mBargainList.add(bargain);
                        mImageUrlList.add(bargain.pic);
                     }
                     FileUtils.writeListToFile(mActivity, FileUtils.LIST_BANNER_FILE_NAME, mImageUrlList);
                     initViewPager();
                  }
               } else {
                  return;
               }
            } else {
               return;
            }
         }
      } catch (JSONException e) {
         e.printStackTrace();
      }
   }
 
   // 初始化ViewPager
   private void initViewPager() {
      if(!(mImageUrlList != null && mImageUrlList.size() > 0)){
         return;
      }
      int length = mImageUrlList.size() + 2;
      for (int i = 0; i < length; i++) {
         ImageView mViewPagerImageView = new ImageView(mActivity);
         ViewGroup.LayoutParams viewPagerImageViewParams =
               new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                     ViewGroup.LayoutParams.FILL_PARENT);
         mViewPagerImageView.setLayoutParams(viewPagerImageViewParams);
         mViewPagerImageView.setScaleType(ScaleType.FIT_XY);
         mViewPagerImageView.setOnClickListener(BannerFragment.this);
         mViewList.add(mViewPagerImageView);
      }
      if (mViewList != null && mViewList.size() > 0) {
         mViewPager.setAdapter(new ViewpagerAdapter());
      }
      mViewPager.setOnPageChangeListener(BannerFragment.this);
 
      if (mImageUrlList.size() == 0) {
         mTextView.setText(String.format("0 / %s", mImageUrlList.size()));
      } else {
         mTextView.setText(String.format("1 / %s", mImageUrlList.size()));
      }
      mViewPager.setCurrentItem(1);
      if (mActivity.getSlidingMenu() != null && mActivity.getSlidingMenu().getSlidingView() != null) {
 
         SlidingView sv = mActivity.getSlidingMenu().getSlidingView();
         sv.setOnInterceptListener(new SlidingView.OnInterceptListener() {
            @Override
            public ViewFrame getInterceptViewFrame() {
               ViewFrame frame = new ViewFrame(0, 50, mViewPager.getWidth(), mViewPager.getHeight());
               return frame;
            }
         });
      }
   }
   @Override
   protected void init() {
 
   }
   /**
    * PagerAdapter
    * @author
    *
    */
   class ViewpagerAdapter extends PagerAdapter {
      @Override
      public void destroyItem(View container, int position, Object object) {
         ImageView view = mViewList.get(position % mViewList.size());
         ((ViewPager) container).removeView(view);
         view.setImageBitmap(null);
      }
 
      @Override
      public Object instantiateItem(View container, int position) {
         ((ViewPager) container).addView(mViewList.get(position));
            return mViewList.get(position);
      }
 
      @Override
      public int getCount() {
         return mViewList.size();
      }
 
      @Override
      public boolean isViewFromObject(View view, Object object) {
         return view == object;
      }
   }
 
   @Override
   public void onPageScrollStateChanged(int state) {
 
   }
 
   @Override
   public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
 
   }
 
   @Override
   public void onPageSelected(int position) {
      if(position == 0){
         mFinalBitmap.display(mViewList.get(position), mImageUrlList.get(mImageUrlList.size() - 1));
      }else if(position == mViewList.size() - 1){
         mFinalBitmap.display(mViewList.get(position), mImageUrlList.get(0));
      }else{
         mFinalBitmap.display(mViewList.get(position), mImageUrlList.get(position - 1));
      }
      int pageIndex = position;
       if(position == 0){
          pageIndex = mImageUrlList.size();
       }else if(position == mImageUrlList.size() + 1){
          pageIndex = 1;
       }
       if(position != pageIndex){
          mViewPager.setCurrentItem(pageIndex, false);
          return;
       }
       int count = mViewList.size() - 2;
       if(mViewList != null && count > 0){
          int index = (position);
          String text = index + "/" + count;
          mTextView.setText(text);
       }else{
         String text = 0 + " / " + 0;
         mTextView.setText(text);
       }
   }
 
   @Override
   public void onClick(View v) {
      switch (v.getId()) {
      // 切换到左边的ImageView
      case R.id.toLeftBannerFragmentImageView: {
         if (mViewPager != null && mViewPager.getCurrentItem() > 0) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
         } else {
            Toast.makeText(mActivity, R.string.banner_is_first, Toast.LENGTH_SHORT).show();
         }
      }
         break;
      // 切换到右边的ImageView
      case R.id.toRightBannerFragmentImageView: {
         if (mViewPager != null && mViewList != null && mViewPager.getCurrentItem() < Integer.MAX_VALUE - 1) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
         } else {
            Toast.makeText(mActivity, R.string.banner_is_last, Toast.LENGTH_SHORT).show();
         }
      }
         break;
      }
      super.onClick(v);
   }
 
   @Override
   public void onPause() {
      if (mFinalBitmap != null) {
         mFinalBitmap.onPause();
      }
      super.onPause();
   }
 
   @Override
   public void onResume() {
      if (mFinalBitmap != null) {
         mFinalBitmap.onResume();
      }
      super.onResume();
   }
 
   @Override
   public void onDestroy() {
      if (mFinalBitmap != null) {
         mFinalBitmap.onDestroy();
      }
      super.onDestroy();
   }
}