package com.sundayliu.demo;

import java.util.HashMap;
import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends ListActivity {
    
    private HashMap<String,String> mDatas = new HashMap<String,String>(){
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("000 Simple", "com.sundayliu.android.listview.SimpleListActivityExample");
            put("001 Custom", "com.sundayliu.android.listview.CustomListActivityExample");
            put("002 ActionBar", "com.sundayliu.android.listview.ListViewActionBarActivity");
            put("003 Undo", "com.sundayliu.android.listview.ListViewUndoActivity");
            put("004 Multiple Choice", "com.sundayliu.android.listview.MultipleListViewActivity");
            put("005 Single Choice", "com.sundayliu.android.listview.SingleListViewActivity");
            put("006 Simple Cursor", "com.sundayliu.android.listview.SimpleCursorActivity");
            put("007 Simple Performance", "com.sundayliu.android.listview.SimpleListActivityPerformanceAdapter");
            put("008 Custom Adapter", "com.sundayliu.android.listview.SimpleListActivityCustomAdapter");
            put("009 Two List Items", "com.sundayliu.android.listview.TwoListItemsActivity");
            put("010 Example", "com.sundayliu.android.listview.ListViewExampleActivity");
            put("011 BookShelf", "com.sundayliu.android.listview.BookShelfListView");
            put("012 PcsaltActivity", "com.sundayliu.android.listview.PcsaltActivity");
        }
        
    };
    
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = getValues();
        ArrayAdapter<String> adapter = new DemoArrayAdapter(this,
                values);
            setListAdapter(adapter);
      }
    
    private String[] getValues(){
        
        Set<String> keys = mDatas.keySet();
        String[] values = new String[keys.size()];
        keys.toArray(values);
        return values;
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        l.getItemAtPosition(position);
        String key = ((TextView)v.findViewById(R.id.label)).getText().toString();
        String value = mDatas.get(key);
        try{
            Intent intent = new Intent(this, Class.forName(value));
            startActivity(intent);
        }
        catch(ClassNotFoundException e){
          Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    
    public void startActivityOnClickHandler(View v){
        String key = (String)v.getTag();
        String value = mDatas.get(key);
        try{
            Intent intent = new Intent(this, Class.forName(value));
            startActivity(intent);
        }
        catch(ClassNotFoundException e){
          Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        
        
    }
}
