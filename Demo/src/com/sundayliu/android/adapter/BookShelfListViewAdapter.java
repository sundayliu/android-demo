package com.sundayliu.android.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sundayliu.demo.R;
import com.sundayliu.android.model.BookData;
import com.sundayliu.android.utility.HttpUtility;

public class BookShelfListViewAdapter extends BaseAdapter {

    private ArrayList<BookData> bookshelfList = null;
    private Context             context       = null;

    /**
     * 构造函数,初始化Adapter,将数据传入
     * @param bookshelfList
     * @param context
     */
    public BookShelfListViewAdapter(ArrayList<BookData> bookshelfList, Context context) {
        this.bookshelfList = bookshelfList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookshelfList == null ? 0 : bookshelfList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookshelfList == null ? null : bookshelfList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //装载view
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.listview_bookshelf, null);

        //获取控件
        ImageView bookImageView = (ImageView) view.findViewById(R.id.book_image);
        TextView bookNameTextView = (TextView) view.findViewById(R.id.book_name);
        TextView bookNoReadNumTextView = (TextView) view.findViewById(R.id.book_no_read_num);
        TextView bookLastTitleView = (TextView) view.findViewById(R.id.book_lasttitle);
        ImageView bookHasUpdateImageView = (ImageView) view.findViewById(R.id.book_has_update);
        //对控件赋值
        BookData bookData = (BookData) getItem(position);
        if (bookData != null) {
            bookImageView.setImageBitmap(HttpUtility.getHttpBitmap(bookData.getImageUrl()));
            bookNameTextView.setText(bookData.getName());
            Integer noReadNum = bookData.getTotalNum() - bookData.getCurrentNum();
            if (noReadNum > 0) {
                bookNoReadNumTextView.setText(noReadNum + "章节未读");
                //显示更新小图标
                bookHasUpdateImageView.setVisibility(View.VISIBLE);
            } else {
                bookNoReadNumTextView.setText("无未读章节");
                //隐藏更新小图标
                bookHasUpdateImageView.setVisibility(View.GONE);
            }
            bookLastTitleView.setText("更新至:" + bookData.getLastTitle());
        }

        return view;
    }

}
