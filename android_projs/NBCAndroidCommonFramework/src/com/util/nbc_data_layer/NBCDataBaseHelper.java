package com.util.nbc_data_layer;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoMaster.OpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * this class will be used for wrapper class to green dao helper for table
 * management. may need to be an iface later...
 * TODO: 
 * 
 * Author J.Mena
 *
 */
public class NBCDataBaseHelper extends OpenHelper 
{	
	//used for logging
	private String FILETAGNAME = "NBCDataBaseHelper";
	
    public NBCDataBaseHelper(Context context, String name, CursorFactory factory) 
    {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
    	if(newVersion > oldVersion)
    	{
	        //TODO: need to determine what to do when a DB upgrade is needed.
	        Log.d(FILETAGNAME, "JM...Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
	        
	        //dropAllTables(db, true);
	        //onCreate(db);
    	}
    }
}
