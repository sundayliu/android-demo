package com.sundayliu.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DemoArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public DemoArrayAdapter(Context context, String[] values) {
      super(context, R.layout.listview_row1, values);
      this.context = context;
      this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.listview_row1, parent, false);
      TextView textView = (TextView) rowView.findViewById(R.id.label);
      ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
      textView.setText(values[position]);
      // Change the icon for Windows and iPhone
      String s = values[position];
      if (s.startsWith("Windows7") || s.startsWith("iPhone")
          || s.startsWith("Solaris")) {
        imageView.setImageResource(R.drawable.ic_no);
      } else {
        imageView.setImageResource(R.drawable.ic_yes);
      }

      return rowView;
    }
}
