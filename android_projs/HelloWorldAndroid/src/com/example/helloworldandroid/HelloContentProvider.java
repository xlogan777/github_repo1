package com.example.helloworldandroid;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class HelloContentProvider extends ContentProvider 
{
	private static final String MyTag = "HelloContentProvider";
	
	static final String PROVIDER_NAME = "com.example.helloworldandroid.College";
	
	//this is the url at which the content provider is accessible for.
	static final String URL = "content://" + PROVIDER_NAME + "/students";
	
	//create a Uri obj to use for this ojects access.
	static final Uri CONTENT_URI = Uri.parse(URL);
	
	//these vars are for the field in the db table
	static final String _ID = "_id";
	static final String NAME = "name";
	static final String GRADE = "grade";
	
	//create new hash map.
	private static HashMap<String, String> STUDENTS_PROJECTION_MAP = new HashMap<String,String>();
	
	static final int STUDENTS = 1;
	static final int STUDENT_ID = 2;
	
	//this class helps in matching uri passed in to insert, delete, query..with the ones we expect.
	static final UriMatcher uriMatcher;
	
	/*static block to initialize uri*/
	static
	{
		//create empty uri matcher class with no matching tree.
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		//add the url based on the provider name and students objec type and 
		//make the association to the [STUDENTS] static id so that it can be used
		//as a switch statement for specific db calls.
		//this is adding authority name, specific path, and value to return if a match occured.
		uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
		
		uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
	}
	
	/**
	* Database specific constant declarations
	*/
	
	//db type for sql_lite
	private SQLiteDatabase db;
	
	//name of the db...
	static final String DATABASE_NAME = "College";
	
	//table name in the db
	static final String STUDENTS_TABLE_NAME = "students";
	
	//version of the db.
	static final int DATABASE_VERSION = 1;
	
	//vars that creates a table in the db.
	static final String CREATE_DB_TABLE =
	" CREATE TABLE " + STUDENTS_TABLE_NAME +
	" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	" name TEXT NOT NULL, " +
	" grade TEXT NOT NULL);";
	
	public HelloContentProvider() 
	{
		
	}
	
	public void closeDB()
	{
		db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME);
		db.close();
	}
	
	public void dropAndCreate()
	{
		//these will drop and create the table again..rather than deleting all the rows.
		db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME);
		db.execSQL(CREATE_DB_TABLE);
		Log.d(MyTag,"dropped and created");
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{
		int count = 0;
		switch (uriMatcher.match(uri))
		{
			case STUDENTS:
				
					//this will delete the row from the selection.
					count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
				
				break;
				
			case STUDENT_ID:
				String id = uri.getPathSegments().get(1);
				count = db.delete( STUDENTS_TABLE_NAME, _ID + " = " + id +
				(!TextUtils.isEmpty(selection) ? " AND (" +
				selection + ')' : ""), selectionArgs);
				break;
				
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return count;
	}

	@Override
	public String getType(Uri uri) 
	{
		switch (uriMatcher.match(uri))
		{
			/**
			* Get all student records
			*/
			case STUDENTS:
				return "vnd.android.cursor.dir/vnd.example.students";
				
			/**
			* Get a particular student
			*/
			case STUDENT_ID:
				return "vnd.android.cursor.item/vnd.example.students";
				
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{
		if(db != null && !db.isOpen())
		{
			this.onCreate();
			Log.d(MyTag,"do onCreate() from insert...");
		}
		
		
		/**
		* Add a new student record
		*/
		//this will add to the student table, the values provided.
		//returns the row id of this add..
		long rowID = db.insert( STUDENTS_TABLE_NAME, "", values);
		
		/**
		* If record is added successfully
		*/
		if (rowID > 0)
		{
			//this will return a full path with the rowId of the data element added..to the uri path.
			//content://com.example.helloworldandroid.College/students/rowId
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			
			//this will notify by default to the CursorAdapter object of the change.
			getContext().getContentResolver().notifyChange(_uri, null);
			
			return _uri;
		}
		
		//throw exception if not allowed to add data to db.
		throw new SQLException("Failed to add a record into " + uri);
	}
	
	@Override
	public boolean onCreate() 
	{
		//get the context for this app.
		Context context = getContext();
		
		//create the helper and provide it the context..
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		
		//save content provider to db helper.
		dbHelper.setContentProvider(this);
		
		/**
		* Create a writeable database which will trigger its
		* creation if it doesn't already exist.
		*/
		//this will actually create the db and call the onCreate function if the first time.
		db = dbHelper.getWritableDatabase();//this should call the db onCreate function.
		dbHelper.onCreate(db);//call the onCreate  here when we close the db connection.
		
		boolean status = (db == null)? false:true;
		
		Log.d(MyTag, "onCreate() from ...provider, "+status);
				
		//if we failed the return false, otherwise true.
		return status;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) 
	{
		if(db != null && !db.isOpen())
		{
			this.onCreate();
			Log.d(MyTag,"do onCreate() from query...");
		}
		
		//helper class that is used to build queries to be sent to sql_lite db.
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		//this is setting up the table that we want to query. u can use this for db joins..
		qb.setTables(STUDENTS_TABLE_NAME);
		
		//this will match the uri passed in to the ones that we expect, and return
		//the associated int value..to enter specific switch case statement.
		switch (uriMatcher.match(uri))
		{
			//if the query uri is for students they provide the map from the db. 
			case STUDENTS:
				qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
				Log.d(MyTag,"student_map_proj = "+STUDENTS_PROJECTION_MAP.toString());
				break;
			
			case STUDENT_ID:
				qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
				break;
				
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		//check the sorting type..and default to name type.
		if (sortOrder == null || sortOrder == "")
		{
			/**
			* By default sort on student names
			*/
			sortOrder = HelloContentProvider.NAME;
		}
		
		//this is doing the actual db query..providing the db table and the sort order.
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		
		/**
		* register to watch a content URI for changes
		*/
		c.setNotificationUri(getContext().getContentResolver(), uri);
		
		//return cursor.
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) 
	{
		int count = 0;
		switch (uriMatcher.match(uri))
		{
			case STUDENTS:
				count = db.update(STUDENTS_TABLE_NAME, values,
				selection, selectionArgs);
				break;
				
			case STUDENT_ID:
				count = db.update(STUDENTS_TABLE_NAME, values, _ID +
				" = " + uri.getPathSegments().get(1) +
				(!TextUtils.isEmpty(selection) ? " AND (" +
				selection + ')' : ""), selectionArgs);
				break;
				
			default:
				throw new IllegalArgumentException("Unknown URI " + uri );
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return count;
	}
	
	/**
	* Helper class that actually creates and manages
	* the provider's underlying data repository.
	*/
	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		private static String MyTag = "DatabaseHelper";
		
		private HelloContentProvider provider;
		
		DatabaseHelper(Context context)
		{
			//this will setup the name, version for this new db instance tied to
			//the context obj for this app. the db is not created here.
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void setContentProvider(HelloContentProvider provider)
		{
			this.provider = provider;
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			Log.d(DatabaseHelper.MyTag,"onCreate()");
			
			//utility function.
			provider.dropAndCreate();
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{			
			//this will create a new one..just so happens to create it with same version.
			onCreate(db);
		}
	}	
	
}
