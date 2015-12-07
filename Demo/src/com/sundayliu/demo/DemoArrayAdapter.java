package com.sundayliu.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
//import android.widget.ImageView;
import android.widget.TextView;

public class DemoArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public DemoArrayAdapter(Context context, String[] values) {
      super(context, R.layout.listview_row_main, values);
      this.context = context;
      this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View row = convertView;
      if (row == null){
          LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          row = inflater.inflate(R.layout.listview_row_main, parent, false);
          
          ImageButton button = (ImageButton)row.findViewById(R.id.start);
          String tag = values[position];
          button.setTag(tag);
      }
      
      TextView text = (TextView)row.findViewById(R.id.label);
      text.setText(values[position]);
      return row;
    }
}
