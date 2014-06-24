package com.util.nbc_data_layer;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoMaster;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoMaster.DevOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * this class is the specific implementation of the db abstract iface.
 * it allows to vary different implementations of the ORM types of frameworks
 * as well as the raw android sql usage by providing a hook to the underlying DB...
 * this is the implementation class of the factory pattern.
 * 
 * author: J.Mena
 */
public class SqliteDBGreenDaoIface extends SqliteDBAbstractIface 
{
	private String MyTag = "SqliteDBGreenDaoIface";
	
	//these are the class members that are from green dao. 
    private DaoMaster daoMaster;//this holds the DB connection
    private DaoSession daoSession;//this holds caching for all the entities for a particular session.    
    private DevOpenHelper helper;//helper obj that creates the db connection and provides the db connection.

	public SqliteDBGreenDaoIface(Context context, String dbName, CursorFactory factory, T_Session_Type sessionType)
	{
		//call base constructor.
		super(context, dbName, factory, sessionType);
	}

	@Override
	/*
	 * this method will perform the initialization of the green dao iface
	 * and preserve the specific objs with its db connection.
	 */
	public void initializeDB() 
	{
		if(!initialized)
		{
			try
			{
				//create a new helper obj that does most of the DB initialization.
		        helper = new DaoMaster.DevOpenHelper(this.context, this.dbName, this.factory);
		        	        
		        //get reference to writable DB.
		        db = helper.getWritableDatabase();
		        	        
		        //get master obj as entry point for green dao.
		        daoMaster = new DaoMaster(db);
		        
		        //get session obj for schema created previously.
		        daoSession = daoMaster.newSession();
		        
		        //setup init flag to no longer initialize anymore.
		        initialized = true;
			}
			
			catch (Exception e)
			{
				initialized = false;
				Log.d(MyTag, "JM...error = "+e.getMessage());
			}			
		}
	}

	@Override
	/*
	 * return specific session object. caller will have to cast the returned instance to their
	 * specific implementation type.
	 * 
	 */
	public Object getDBSession()
	{	
		return daoSession;
	}
}
