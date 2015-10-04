/*
 * @Title: ContentFragment.java
 * @Copyright: MKTech Corporation. Ltd. Copyright 1998-2018, All rights reserved
 * @Description: TODO<请描述此文件是做什么的>
 * @author: xjp
 * @data: 2015年1月28日 上午10:42:03
 * @version: V1.0
 */
package com.example.drawlayout1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * TODO<请描述这个类是干什么的>
 * 
 * @author xjp
 * @data: 2015年1月28日 上午10:42:03
 * @version: V1.0
 */
public class ContentFragment extends Fragment {
	private TextView mTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_content, container,
				false);
		mTextView = (TextView) view.findViewById(R.id.textView);
		String textString = getArguments().getString("text");
		mTextView.setText(textString);
		return view;
	}
}
