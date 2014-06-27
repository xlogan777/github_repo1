package com.util.nbc_data_layer;

import java.util.EnumSet;
import java.util.HashMap;

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
	/*
	 * this is a enum class type that allows the different types of enums literals
	 * to contain a long type value predefined. it also contians an internal map that 
	 * does a reverse lookup from long id to enum type returned. 
	 */
	public enum T_UrlTypeToId
	{
		//this is a default condition that is returned when
		//invalid id - reverse loop up is done
		E_NOT_VALID_MEDIA_URL_TYPE(0),
		
		//taken from lead media table types
		E_LEAD_MEDIA_THUMBNAIL_URL_TYPE(1),
		
		//taken from media table types
		E_MEDIA_URL_TYPE(2),
		E_MEDIA_PHOTO_THUMBNAIL_URL_TYPE(3),
		E_MEDIA_THUMBNAIL_URL_TYPE(4),
		
		//taken from related item table types
		E_REL_ITEM_MOBILE_THUMBNAIL_URL_TYPE(5),
		E_REL_ITEM_STORY_THUMBNAIL_URL_TYPE(6),
		
		//taken from gallery img table types
		E_GAL_IMG_PATH_URL_TYPE(7);

		//constructor to map enums to long values.
		T_UrlTypeToId(long urlTypeVal_ID)
		{
			this.urlTypeVal_ID = urlTypeVal_ID;
		}
				
		//value of the id representation of the enum.
		private long urlTypeVal_ID;
		
		//create hashmap lookup table reference to be used across all enum types		
		private static final HashMap<Long, T_UrlTypeToId> lookupEnumTable;
		
		static
		{
			//create hash map
			lookupEnumTable = new HashMap<Long, T_UrlTypeToId>();
			
			//go over all the enums in the enum type
			for(T_UrlTypeToId url_type : EnumSet.allOf(T_UrlTypeToId.class))
			{
				//save in the lookup map the key being the id value of the enum 
				//and the enum type associated with that value.
				lookupEnumTable.put(url_type.getUrlTypeID(),url_type);
			}
			
			//remove the E_NOT_VALID_MEDIA_URL_TYPE from the map..
			lookupEnumTable.remove(E_NOT_VALID_MEDIA_URL_TYPE.getUrlTypeID());
		}
		
		/*
		 * this will return the long value associated with this enum.
		 */
		public long getUrlTypeID()
		{
			return this.urlTypeVal_ID;
		}
		
		/*
		 * this method will take an id and return the enum value associated with that "long" key.
		 * if an invalid key is passed , null may be returned. 
		 */
		public static T_UrlTypeToId getEnumFromID(long id)
		{
			//tmp return value.
			T_UrlTypeToId rv;
			
			//get the item using the key.
			rv = lookupEnumTable.get(id);
			
			//if no value enum type returned. return default to
			//indicate that u passed in bad key.
			if(rv == null)
			{
				rv = E_NOT_VALID_MEDIA_URL_TYPE;
			}
			
			return rv;
		}
	}
	
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
