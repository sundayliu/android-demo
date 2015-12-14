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

public class NetworkActivity extends ListActivity {
    
    private HashMap<String,String> mDatas = new HashMap<String,String>(){
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("000 HttpClient", "com.sundayliu.android.network.HttpActivity");
            put("000 Http2Client", "com.sundayliu.android.network.Http2Activity");
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
