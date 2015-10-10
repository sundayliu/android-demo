package com.sundayliu.android.listview;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.sundayliu.android.adapter.*;
import com.sundayliu.demo.R;

public class ListViewActionBarActivity extends ListActivity 
    implements ActionMode.Callback{
    protected Object mActionMode;
    public int selectedItem = -1;
    
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //    android.R.layout.simple_list_item_1, values);
        
        SimpleArrayAdapter adapter = new SimpleArrayAdapter(this, values);
        setListAdapter(adapter);
        
        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

              if (mActionMode != null) {
                return false;
              }
              selectedItem = position;

              // Start the CAB using the ActionMode.Callback defined above
              mActionMode = ListViewActionBarActivity.this.startActionMode(ListViewActionBarActivity.this);
              view.setSelected(true);
              return true;
            }
          });
      }

      @Override
      protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
      }
      
      private void show() {
          Toast.makeText(ListViewActionBarActivity.this, String.valueOf(selectedItem), Toast.LENGTH_LONG).show();
        }

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
          // Inflate a menu resource providing context menu items
          MenuInflater inflater = mode.getMenuInflater();
          // Assumes that you have "contexual.xml" menu resources
          inflater.inflate(R.menu.rowselection, menu);
          return true;
        }

        // Called each time the action mode is shown. Always called after
        // onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
          return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
          switch (item.getItemId()) {
          case R.id.menuitem_show:
            show();
            // Action picked, so close the CAB
            mode.finish();
            return true;
          default:
            return false;
          }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
          mActionMode = null;
          selectedItem = -1;
        }
}
