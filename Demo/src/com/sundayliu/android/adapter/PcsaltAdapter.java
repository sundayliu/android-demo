package com.sundayliu.android.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sundayliu.android.model.*;
import com.sundayliu.demo.R;
public class PcsaltAdapter extends BaseAdapter {

    ArrayList<PcsaltData> mDataSource = new ArrayList<PcsaltData>();
    LayoutInflater mInflater;
    Context mContext;
    
    public PcsaltAdapter(Context context, ArrayList<PcsaltData> dataSource){
        mDataSource = dataSource;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDataSource.size();
    }

    @Override
    public PcsaltData getItem(int position) {
        // TODO Auto-generated method stub
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.listview_item_pcsalt, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        
        PcsaltData data = getItem(position);
        viewHolder.getTitle().setText(data.getTitle());
        viewHolder.getDesc().setText(data.getDescription());
        viewHolder.getIcon().setImageResource(data.getImageResId());
        return convertView;
    }
    
    private class ViewHolder{
        TextView mTitle;
        TextView mDesc;
        ImageView mIcon;
        
        public ViewHolder(View v){
            mTitle = (TextView)v.findViewById(R.id.tvTitle);
            mDesc = (TextView)v.findViewById(R.id.tvDesc);
            mIcon = (ImageView)v.findViewById(R.id.ivIcon);
        }
        
        public TextView getTitle(){
            return mTitle;
        }
        
        public TextView getDesc(){
            return mDesc;
        }
        
        public ImageView getIcon(){
            return mIcon;
        }
        
    }

}
