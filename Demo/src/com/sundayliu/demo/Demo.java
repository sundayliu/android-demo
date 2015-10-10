package com.sundayliu.demo;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Demo extends ListActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[] { 
                "Demo For ListView", 
                "iPhone", 
                "WindowsMobile",
                "Blackberry", 
                "WebOS", 
                "Ubuntu", 
                "Windows7", 
                "Max OS X",
                "Linux", "OS/2" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, values);
        
        //SimpleArrayAdapter adapter = new SimpleArrayAdapter(this, values);
        setListAdapter(adapter);
      }
}
