package com.util.nbc_data_layer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * 
 * this class will handle base interface for sql interactions
 * it will keep the db instance and the session obj if using and ORM framework.
 * this is the base class for the factory pattern.
 * 
 * Author: J.Mena
 */
public abstract class SqliteDBAbstractIface 
{
	//enum type that is used to track the different types of 
	//db iface types to be created via static factory.
	public enum T_Session_Type
	{
		E_NO_SESSION_TYPE,
		E_GREEN_DAO
	}

	protected SQLiteDatabase db;//db connection obj
	protected Context context;//context
	protected String dbName;//name of db
	protected CursorFactory factory;//cursor factory.
	protected T_Session_Type sessionType;//session type.
    protected boolean initialized = false;//tracks if successfully initialized or not.
    protected Object sessionObj = null;//obj to track the session obj.
	
	//constructor to setup class members
	public SqliteDBAbstractIface(Context context, String dbName, CursorFactory factory, T_Session_Type sessionType)
	{
		//init class members.
		db = null;
		this.context = context;
		this.dbName = dbName;
		this.factory = factory;
		this.sessionType = sessionType;
	}
	
	/*
	 * return local instance of db.
	 */
	public SQLiteDatabase getDBInstance()
	{
		return this.db;
	}
	
	/*
	 * return the session type of this obj.
	 */
	public T_Session_Type getSessionType()
	{
		return this.sessionType;
	}
	
	/*
	 * this will return the current session obj. caller needs to check for null before using the obj.
	 */
	public Object getDBSession()
	{	
		return sessionObj;
	}
	
	/*
	 * this will return the current status of the db iface obj.
	 * true if correctly initialized, false otherwise.
	 */
	public boolean isInitialized()
	{
		return this.initialized;
	}
	
	/*
	 * this is a static factory method that follows the factory pattern from the GOF book 
	 * to provide common implementation for db iface interactions without exposing the specific
	 * implementations.
	 */
	public static SqliteDBAbstractIface createSqliteIface
	(Context context, String dbName, CursorFactory factory,T_Session_Type sessionType)
	{
		SqliteDBAbstractIface tmp_db_iface = null;
		
		//switch on the type and create appropriate iface implementation obj.
		switch(sessionType)
		{
			case E_NO_SESSION_TYPE:
				//TODO: throw an exception here!!!
				break;
				
			case E_GREEN_DAO:
				tmp_db_iface = new SqliteDBGreenDaoIface(context, dbName, factory, sessionType);
				break;
		}
		
		//check to make sure we have a valid obj before any methods on the ref.
		if(tmp_db_iface != null)
		{
			//perform common initialization routines for db iface.		
			tmp_db_iface.initializeDB();
		}
		
		//return ref obj of iface.
		return tmp_db_iface;
	}
	
	//override these functions since they will be implementation dependent.
	public abstract void initializeDB();
	
	public abstract Object imgFileTableEntryAndAssociationProcessing
	(ImgFileUrlSpecs imgFileSepcs, ImgFileDetails imgFileDetails, long cmsID, long urlTypeID, String urlLocation);
	
	public abstract void peformUrlStringToTableAssociations
	(String urlInput, long cmsID, NBCDataBaseHelper.T_UrlTypeToId typeID, Object entityObj, NBCDataParsingBase parsingObj);
	
}
