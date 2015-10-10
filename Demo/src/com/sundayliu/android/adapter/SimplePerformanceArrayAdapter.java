package com.sundayliu.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sundayliu.demo.R;

public class SimplePerformanceArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] names;

    static class ViewHolder {
      public TextView text;
      public ImageView image;
    }

    public SimplePerformanceArrayAdapter(Activity context, String[] names) {
      super(context, R.layout.listview_row1, names);
      this.context = context;
      this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View rowView = convertView;
      // reuse views
      if (rowView == null) {
        LayoutInflater inflater = context.getLayoutInflater();
        rowView = inflater.inflate(R.layout.listview_row1, null);
        // configure view holder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) rowView.findViewById(R.id.label);
        viewHolder.image = (ImageView) rowView
            .findViewById(R.id.icon);
        rowView.setTag(viewHolder);
      }

      // fill data
      ViewHolder holder = (ViewHolder) rowView.getTag();
      String s = names[position];
      holder.text.setText(s);
      if (s.startsWith("Windows7") || s.startsWith("iPhone")
          || s.startsWith("Solaris")) {
        holder.image.setImageResource(R.drawable.ic_no);
      } else {
        holder.image.setImageResource(R.drawable.ic_yes);
      }

      return rowView;
    }
}
