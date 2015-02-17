package tech.jm.myappandroid2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MyContentProvider extends ContentProvider
{
    //content uri to access the content provider by.
    public static final Uri CONTENT_URI = Uri.parse("content://tech.jm.myappandroid2.mycontentprovider/");

    //this will simulate the db part of the data holder.
    private Map<Integer,String> dbMap = new HashMap<Integer,String>();

    public MyContentProvider()
    {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int num_rows = -1;

        String requestIdString = uri.getLastPathSegment();
        String path = uri.getPath();
        String auth = uri.getAuthority();

        for(String s : uri.getPathSegments())
        {
            Log.d("","path___ = "+s);
        }

        //check to see if both inputs are good and they have wat we need to do a delete
        //based on the sql type of checking
        //if(selection != null && selection.equals("id") && selectionArgs != null && selectionArgs.length > 0)
        if(requestIdString != null )
        {
            //first item is the id.
            //int id = Integer.parseInt(selectionArgs[0]);
            int id = Integer.parseInt(requestIdString);//this is the id to remove.

            if(this.dbMap.containsKey(id))
            {
               this.dbMap.remove(id);
               num_rows = 1;
            }
            else
            {
                num_rows = 0;
            }
        }

        Log.d("","req_id_str = "+requestIdString+", auth = "+auth+", num_rows = "+num_rows+", path = "+path);

        return num_rows;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        Uri uri_tmp = uri;

        String requestIdString = uri.getLastPathSegment();
        int id = Integer.parseInt(requestIdString);
        String my_name = values.getAsString("name");

        if(this.dbMap.containsKey(id))
        {
            this.dbMap.remove(id);
        }

        this.dbMap.put(id,my_name);

        return uri_tmp;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        String requestIdString = uri.getLastPathSegment();
        int id = Integer.parseInt(requestIdString);

        String [] cols = new String[]{"name_col"};
        MatrixCursor mc = new MatrixCursor(cols);//create a matrix cursor with cols defined in array.

        if(this.dbMap.containsKey(id))
        {
            //create cursor obj here and return what u want.
            String name = this.dbMap.get(id);
            mc.addRow(new Object[]{name});
        }

        return mc;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }
}
