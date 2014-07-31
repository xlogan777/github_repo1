package com.util.mydao_data_layer;

import com.util.mydao_data_layer.SqliteDBAbstractIface.T_Session_Type;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * 
 * this will do the db connection and do the setup for theDB...
 * it will also allow for ref retrieval of the db connection.
 * 
 * author j.mena
 *
 */
public class CommonUtils 
{
	//class member for db iface with green dao.
	private static SqliteDBAbstractIface dbIface = null;
	
	private static String LOGTAG = "CommonUtils";

	/*
	 * this is a static factory method that follows the factory pattern from the GOF book 
	 * to provide common implementation for db iface interactions without exposing the specific
	 * implementations. allows for synchronization of code when creating the single db connection.
	 */
	public synchronized static void createSqliteIface
	(Context context, String dbName, CursorFactory factory,T_Session_Type sessionType)
	{
		//init if the obj is null.
		if(dbIface == null)
		{
			//switch on the type and create appropriate iface implementation obj.
			switch(sessionType)
			{
				case E_NO_SESSION_TYPE:
					//TODO: throw an exception here!!!
					break;
					
				case E_GREEN_DAO:
					dbIface = new SqliteDBGreenDaoIface(context, dbName, factory, sessionType);
					break;
			}
			
			//check to make sure we have a valid obj before any methods on the ref.
			if(dbIface != null)
			{
				//perform common initialization routines for db iface.		
				dbIface.initializeDB();
			}
			
			Log.d(LOGTAG, "JM...the status for the DB iface initialization is = "+dbIface.isInitialized());
		}
	}
	
	/*
	 * return the static reference to the db iface that is contained in this class.
	 */
	public static SqliteDBAbstractIface getDBIface()
	{
		return dbIface;
	}
}
