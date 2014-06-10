package com.util.nbc_common_framework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * this class will perform all the SQLlite interactions to the DB.
 * it will provide simple access to the CRUD DB operations.
 * 
 * Author J.Mena
 *
 */
public class NBCDataBaseHelper extends SQLiteOpenHelper 
{	
	//used for logging
	private String FILETAGNAME = "NBCDataBaseHelper";
	
	//keeps track of the DB version..when this is changed the onUpgrade function is called.
	private final static int DATABASE_VERSION = 1;
	
	//name of the DB.
	private final static String DATABASE_NAME = "NBC_Content_Data.db";
	
	//tables names
	private final String GALLERY_IMAGES_TABLE_NAME = "GALLERY_IMAGES_TABLE";
	private final String RELATED_ITEMS_TABLE_NAME = "RELATED_ITEMS_TABLE";
	private final String CONTENT_ITEMS_TABLE_NAME = "CONTENT_ITEMS_TABLE";
	
	//sql to create the tables in the DB.
	private final String sql_gallery_table = 
			" CREATE TABLE " + GALLERY_IMAGES_TABLE_NAME +
			" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			" gal_cid INTEGER NOT NULL UNIQUE, " +
			" gal_imageHeight TEXT NOT NULL, " +
			" gal_index TEXT NOT NULL, " +
			" gal_imagePath TEXT NOT NULL, " +
			" gal_imageCaption TEXT NOT NULL, " +
			" gal_imageCredit TEXT NOT NULL, " +
			" gal_imageWidth TEXT NOT NULL);";
	
	private final String sql_related_items_table = 
			" CREATE TABLE " + RELATED_ITEMS_TABLE_NAME +
			" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			" rel_parentCid INTEGER NOT NULL UNIQUE, " +
			" rel_typeName TEXT NOT NULL, " +
			" rel_cid TEXT NOT NULL, " +
			" rel_title TEXT NOT NULL, " +
			" rel_source TEXT NOT NULL, " +
			" rel_mobileThumbnailUrl TEXT NOT NULL, " +
			" rel_storyThumbnailUrl TEXT NOT NULL, " +
			" rel_sponsored TEXT NOT NULL, " +
			" rel_url TEXT NOT NULL, " +
			" rel_typeID TEXT NOT NULL);";
	
	//TODO: still need to define this table.
	private final String sql_content_items_table = "";
	
	//constructor
	public NBCDataBaseHelper(Context context) 
	{
		//invoke super class to setup most of the DB.
		//the null param value is if we are gonna use cursor factory. for now i am not.
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	/*
	 * this will invoke private function to perform the DB table creation.
	 * 
	 */
	public void onCreate(SQLiteDatabase db) 
	{
		Log.d(FILETAGNAME, "JM...creating the DB now!!!");
		
		//create tables.
		createTables(db);
	}

	@Override
	/*
	 * this will perform the update of the DB as needed. checks to see if the version
	 * has changed to perform this task.
	 * 
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		//check to see if new version more than old version..if so then
		//recreate tables..and drop old data.
		if(newVersion > oldVersion)
		{	
			Log.d(FILETAGNAME, "JM...newVer = "+newVersion+" , old ver = "+oldVersion+", do upgrade now!!!");
			
			//sql to drop tables with data in them.
			db.execSQL("DROP TABLE IF EXISTS " + GALLERY_IMAGES_TABLE_NAME + ";");
			db.execSQL("DROP TABLE IF EXISTS " + RELATED_ITEMS_TABLE_NAME + ";");
			//db.execSQL("DROP TABLE IF EXISTS " + CONTENT_ITEMS_TABLE_NAME + ";");
			
			//create tables.
			createTables(db);
		}
	}
	
	/*
	 * this is a private function that will perform all the DB operations to create tables.
	 */
	private void createTables(SQLiteDatabase db)
	{
		//run the gallery table creation.
		db.execSQL(sql_gallery_table);
		
		//run the related items table creation.
		db.execSQL(sql_related_items_table);
		
		//TODO: enable this code.
		//run the content item table creation.
		//db.execSQL(sql_content_items_table);
	}
}
