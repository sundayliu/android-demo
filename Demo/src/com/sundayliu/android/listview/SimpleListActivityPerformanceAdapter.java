package com.sundayliu.android.listview;


import com.sundayliu.android.adapter.*;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SimpleListActivityPerformanceAdapter extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //    android.R.layout.simple_list_item_1, values);
        
        SimplePerformanceArrayAdapter adapter = new SimplePerformanceArrayAdapter(this, values);
        setListAdapter(adapter);
      }

      @Override
      protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
      }
}
