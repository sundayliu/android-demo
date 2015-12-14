package com.sundayliu.android.listview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.sundayliu.android.adapter.PcsaltAdapter;
import com.sundayliu.android.model.PcsaltData;
import com.sundayliu.demo.R;
public class PcsaltActivity extends Activity {
    ListView mDetail;
    Context mContext = PcsaltActivity.this;
    ArrayList<PcsaltData> mDataSource = new ArrayList<PcsaltData>();
    
    String[] title = new String[]{
            "Title 1", "Title 2", "Title 3", "Title 4",
            "Title 5", "Title 6", "Title 7", "Title 8"
    };
    
    String[] desc = new String[]{
            "Desc 1", "Desc 2", "Desc 3", "Desc 4",
            "Desc 5", "Desc 6", "Desc 7", "Desc 8"
    };
    int[] img = new int[]{
            R.drawable.star1, R.drawable.star2, R.drawable.star3, R.drawable.star4,
            R.drawable.star5, R.drawable.star6, R.drawable.star7, R.drawable.star8
    };
    
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mDetail = (ListView)findViewById(R.id.listview_custom);
        getDataInList();
        mDetail.setAdapter(new PcsaltAdapter(mContext, mDataSource));
    }
    
    private void getDataInList(){
        for (int i = 0; i < title.length; i++) {
            // Create a new object for each list item
            PcsaltData ld = new PcsaltData();
            ld.setTitle(title[i]);
            ld.setDescription(desc[i]);
            ld.setImageResId(img[i]);
            // Add this object into the ArrayList myList
            mDataSource.add(ld);
        }
    }
}
