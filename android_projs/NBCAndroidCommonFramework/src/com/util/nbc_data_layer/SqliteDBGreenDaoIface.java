package com.util.nbc_data_layer;

import java.util.List;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoMaster;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgFnameTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTableDao.Properties;

import de.greenrobot.dao.query.QueryBuilder;
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
    private NBCDataBaseHelper helper;//helper obj that creates the db connection and provides the db connection.

	public SqliteDBGreenDaoIface(Context context, String dbName, CursorFactory factory, T_Session_Type sessionType)
	{
		//call base constructor.
		super(context, dbName, factory, sessionType);
	}

	/*
	 * this method will perform the initialization of the green dao iface
	 * and preserve the specific objs with its db connection.
	 */
	@Override
	public void initializeDB()
	{
		if(!initialized)
		{
			try
			{
				//create a new helper obj that does most of the DB initialization.
		        helper = new NBCDataBaseHelper(this.context, this.dbName, this.factory);
		        	        
		        //get reference to writable DB.
		        db = helper.getWritableDatabase();
		        	        
		        //get master obj as entry point for green dao.
		        daoMaster = new DaoMaster(db);
		        
		        //get session obj from the schema and save the obj.		        
		        sessionObj = daoMaster.newSession();
		        
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
	
	/*
	 * this function will create the the img file table entry and associate it that to the url_img table
	 * it may also create the img_details table is necessary. 
	 */
	@Override
	public Object imgFileTableEntryAndAssociationProcessing
	(ImgFileUrlSpecs imgFileSepcs, ImgFileDetails imgFileDetails, long cmsID, long urlTypeID, String urlLocation)
	{
		//object to be returned back to caller.
		Object rv = null;
		
		//use the session dao here.
		DaoSession dao_session = ((DaoSession)sessionObj);
		
		//get dao for specific table. in this case the content item lead media table.
		ContentItemLeadMediaTableDao dao = dao_session.getContentItemLeadMediaTableDao();
		
		//get a list of items for the leadMediaContent table. should be no more than one.
		//if we find an item in the list then use it...otherwise create new item.
		List <ContentItemLeadMediaTable> items = 
		   dao.queryBuilder().where(Properties.CmsID.eq(cmsID), Properties.LeadMediaThumbnailType.eq(urlTypeID)).list();
		
		//add logging features to see what we get.
		QueryBuilder.LOG_SQL = true;
		QueryBuilder.LOG_VALUES = true;
		
		Log.d(MyTag, "JM...size of list for the query is "+items.size());
		
		if(items.size() == 1)
		{
			rv = items.get(0);
			Log.d(MyTag, "JM...found one entry, need to return this back.");
		}
		else
		{
			if(imgFileDetails != null)
			{
				//TODO: create the img details table entry here and provide link to img file table entity.
				//create row in img_details table if have info for that stuff. save row id. make association if needed to img-fname table.
				//not needed for now.
			}
			
			//create row in the img-fname table with the specs from the img-url obj, save row id.
			ImgFnameTable img_fname = new ImgFnameTable();
			img_fname.setImageFname(imgFileSepcs.getFname());
			img_fname.setImgHeight(imgFileSepcs.getHeight());
			img_fname.setImgWidth(imgFileSepcs.getWidth());
						
			//association here.
			img_fname.setImgDetailsID(0);//for now set to zero..
			//img_fname.setImgDetailsTable(null);//not needed for now.
			
	//TODO: need to create the actual image file here...!!!
			
			//create row in img-fname table
			long row_id = dao_session.getImgFnameTableDao().insertOrReplace(img_fname);			
			
			//create url-img row in this table.
			UrlImgFileTable url_img = new UrlImgFileTable();
			url_img.setCmsID(cmsID);
			url_img.setUrlTypeID(urlTypeID);
			url_img.setUrlLocation(urlLocation);
			
			//association here
			url_img.setImgFnameID(row_id);
			url_img.setImgFnameTable(img_fname);
			
			//create row here.
			long row_id_2 = dao_session.getUrlImgFileTableDao().insertOrReplace(url_img);
			
			Log.d(MyTag, "JM...created img file entry and url_img file entry...return user obj back.");
			
			rv = url_img;
		}
		
		return rv;
	}
}
