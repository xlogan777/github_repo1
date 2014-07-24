package com.util.nbc_data_layer;

import java.util.List;

import com.util.nbc_data_layer.dataTypes.*;

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
		
	//override these functions since they will be implementation dependent.
	public abstract void initializeDB();
	
	public abstract UrlImgTypeIface imgFileTableEntryAndAssociationProcessing
	(ImgFileUrlSpecs imgFileSepcs, ImgFileDetails imgFileDetails, long cmsID, long urlTypeID, String urlLocation);
	
	public abstract void peformUrlStringToTableAssociations
	(
	 String urlInput,
	 ImgFileDetails imgDetails,
	 long cmsID,
	 NBCDataBaseHelper.T_UrlTypeToId typeID,
	 UrlImgTypeIface entityObj,
	 NBCDataParsingBase parsingObj
	);
		
	public abstract void contentItemTableAssociationProcessing
	(
	 CntTypeIface cntItemsTableBean, 
	 CntDetailTypeIface cntItemDetailTableBean, 
	 CntMediaTypeIface cntMediaTableBean, 
	 CntLeadMediaTypeIface cntLeadMediaTableBean
	);
	
	public abstract void relatedItemsTableAssociationProcessing(RelCntItemTypeIface relatedItemsTableBean);
	
	public abstract void galleryTableAssociationProcessing(GalleryCntTypeIface galleryItemsTableBean);
	
	public abstract ImgFnameTypeIface imgTableProcessing(ImgFileUrlSpecs imgFileSepcs, ImgFileDetails imgFileDetails, String urlLocation);
	
	/*
	 * these functions are used as retrieval of data from the db layer.
	 */
	public abstract CntTypeIface getContentData(long cmsId);
	public abstract List<RelCntItemTypeIface> getRelatedContentDataAsList(long cmsId);
	public abstract List<GalleryCntTypeIface> getGalleryContentDataAsList(long cmsId);
}
