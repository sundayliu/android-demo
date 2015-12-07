package com.sundayliu.android.listview;

//import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import android.support.v4.app.ListFragment;
//import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;

public class SimpleCursorFragment extends ListFragment
    implements LoaderCallbacks<Cursor>{
    
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] { ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME };
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
            + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
            + " COLLATE LOCALIZED ASC";
        CursorLoader loader = new CursorLoader(
                getActivity(),
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
        return loader;
    }
    
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(cursor);
    }
    
    public void onLoaderReset(Loader<Cursor> loader){
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(null);
    }
    /** Called when the activity is first created. */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      
      //getSupportLoaderManager();
      //Cursor mCursor = getContacts();
      getLoaderManager().initLoader(0, null, this);
      //startManagingCursor(mCursor);
      // now create a new list adapter bound to the cursor.
      // SimpleListAdapter is designed for binding to a Cursor.
      ListAdapter adapter = new SimpleCursorAdapter(
              getActivity(), // Context.
              android.R.layout.two_line_list_item, // Specify the row template
                              // to use (here, two
                              // columns bound to the
                              // two retrieved cursor
                              // rows).
              null, // Pass in the cursor to bind to.
                          // Array of cursor columns to bind to.
              new String[] { ContactsContract.Contacts._ID,
              ContactsContract.Contacts.DISPLAY_NAME },
          // Parallel array of which template objects to bind to those
          // columns.
              new int[] { android.R.id.text1, android.R.id.text2 },
              CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

      // Bind to our new adapter.
      setListAdapter(adapter);
    }

    public Cursor getContacts() {
      // Run query
      Uri uri = ContactsContract.Contacts.CONTENT_URI;
      String[] projection = new String[] { ContactsContract.Contacts._ID,
          ContactsContract.Contacts.DISPLAY_NAME };
      String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
          + ("1") + "'";
      String[] selectionArgs = null;
      String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
          + " COLLATE LOCALIZED ASC";

      return getActivity().getContentResolver().query(uri, 
              projection, 
              selection, 
              selectionArgs,
              sortOrder);
    }
}
