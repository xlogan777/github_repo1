package com.util.nbc_data_layer;

import java.util.List;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoMaster;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgDetailsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgDetailsTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgFnameTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTableDao;

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
    
    //these variables will be used to temporarily set in the img details table
    //its a way of pointing to null details until we get details to override it with.
    private final String tmpCredit = "JM...NO CREDIT YET";
    private final String tmpCaption = "JM...NO CAPTION YET";
     
	//create the visitor obj to be used by each entity obj.
	private final EntityVisitorIface visitor = new EntityRelationshipVisitor();

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
	 * this method will use the dao of the url-img table to find a cmsID & url_type match
	 * if it doest find it, then we return this info back to the caller. if not then we 
	 * need to make the entries in the img_fname table, url-img table, and potentially the 
	 * img-details table if the obj for it is not null...
	 */
	@Override
	public Object imgFileTableEntryAndAssociationProcessing
	(ImgFileUrlSpecs imgFileSepcs, ImgFileDetails imgFileDetails, long cmsID, long urlTypeID, String urlLocation)
	{
		//object to be returned back to caller.
		Object rv = null;
		
		//use the session dao here.
		DaoSession dao_session = ((DaoSession)sessionObj);
		
		//get dao for specific table, in the case the url-img table.
		UrlImgFileTableDao url_img_dao = dao_session.getUrlImgFileTableDao();
		
		//get the list of items from the table matching with the cms_id&url_type by querying the dao.
		List <UrlImgFileTable> items = 
		   url_img_dao.queryBuilder().where
		   (
			   UrlImgFileTableDao.Properties.CmsID.eq(cmsID), 
			   UrlImgFileTableDao.Properties.UrlTypeID.eq(urlTypeID)
			).list();		
		
		//add logging features to see what we get. this is for debugging purposes only.
		//QueryBuilder.LOG_SQL = true;
		//QueryBuilder.LOG_VALUES = true;
		
		Log.d(MyTag, "JM...size of list for the query is "+items.size());
		
		//need to add the url-img table and img_fname table entries accordingly.
		if(items.size() == 0)
		{
			ImgDetailsTable img_details_entity_val = null;
			long row_num = 0;
			
			if(imgFileDetails != null)
			{
				//create entity obj here and setup with specific java bean  here.
				ImgDetailsTable img_details_entity = new ImgDetailsTable();
				img_details_entity.setImgCredit(imgFileDetails.getCredit());
				img_details_entity.setImgCaption(imgFileDetails.getCaption());

				//get dao for img details table.
				ImgDetailsTableDao img_details_dao = dao_session.getImgDetailsTableDao();
				
				//make a query to see if we have a img details row that macthes this potentially new
				//item 
				List <ImgDetailsTable> item_list = 
				   img_details_dao.queryBuilder().where
				   (
					ImgDetailsTableDao.Properties.ImgCredit.eq(imgFileDetails.getCredit()),
					ImgDetailsTableDao.Properties.ImgCaption.eq(imgFileDetails.getCaption())
				   ).list();
				
				//add new entry to this table.
				if(item_list.size() == 0)
				{
					row_num = img_details_dao.insert(img_details_entity);
					img_details_entity_val = img_details_entity;
				}
				//get  the obj for this item..and tie it to the img-fname table.
				else if(item_list.size() == 1)
				{
					img_details_entity_val = item_list.get(0);
					row_num = img_details_entity_val.getId();
				}
				else
				{
					Log.d(MyTag, "JM...hit error with finding more items in the item details table. handle this situation.");
				}
			}
			//handle the null img details special.
			else
			{
				//make emtpy association now if needed.
				//check to see if we did this already..if so use that row id as the id..
				//if not then make new entry now...
				img_details_entity_val = new ImgDetailsTable();
				img_details_entity_val.setImgCredit(tmpCredit);
				img_details_entity_val.setImgCaption(tmpCaption);
				
				//get dao of img details table.
				ImgDetailsTableDao img_details_dao = dao_session.getImgDetailsTableDao();
				
				//make a query to see if we have a img details row that matches this potentially new
				//item 
				List <ImgDetailsTable> item_list = 
				   img_details_dao.queryBuilder().where
				   (
					ImgDetailsTableDao.Properties.ImgCredit.eq(img_details_entity_val.getImgCredit()),
					ImgDetailsTableDao.Properties.ImgCaption.eq(img_details_entity_val.getImgCaption())
				   ).list();
				
				//make the first null emtpy row location for details.
				if(item_list.size() == 0)
				{					
					row_num = img_details_dao.insert(img_details_entity_val);
				}
				else
				{
					row_num = item_list.get(0).getId();
					img_details_entity_val = item_list.get(0);
				}
			}
			
			//create entity obj here and setup with specifics of the java bean.
			ImgFnameTable img_fname_entity = new ImgFnameTable();
			img_fname_entity.setImageFname(imgFileSepcs.getFname());
			img_fname_entity.setImgHeight(imgFileSepcs.getHeight());
			img_fname_entity.setImgWidth(imgFileSepcs.getWidth());
			
			//association here to img-details table.
			img_fname_entity.setImgDetailsID(row_num);
			img_fname_entity.setImgDetailsTable(img_details_entity_val);//not needed for now.
			
			//create row in img-fname table
			long row_id = dao_session.getImgFnameTableDao().insert(img_fname_entity);
			
			//create entity obj here and setup with specifics of the java bean.
			UrlImgFileTable url_img_entity = new UrlImgFileTable();
			url_img_entity.setCmsID(cmsID);
			url_img_entity.setUrlTypeID(urlTypeID);
			url_img_entity.setUrlLocation(urlLocation);
			
			//association here to img-url table here.
			url_img_entity.setImgFnameID(row_id);
			url_img_entity.setImgFnameTable(img_fname_entity);
			
			//create row here in the img-url table
			long row_id_2 = dao_session.getUrlImgFileTableDao().insert(url_img_entity);
									
			rv = url_img_entity;
			
			//TODO: need to create the actual image file here...!!!
			Log.d(MyTag, "JM...table entries for img-fname, img-url tables...create actual img-file on file system now.");
		}
		else if(items.size() == 1)
		{
			//return the first entry of the list to user.
			rv = items.get(0);
			Log.d(MyTag, "JM...found one entry, need to return this back.");
		}
		else
		{
			//TODO.. handle in a common way this error...should never happen.
			Log.d(MyTag, "JM...	MAJOR ERROR, NEED TO HANDLE THIS ACCORDINGLY.");
		}
		
		return rv;
	}
	
	/*
	 * this is an internal function will do most of the common processing for 
	 * 1.parsing the url path
	 * 2.getting the url-img entity obj from DB.
	 * 3.Do the table associations accordingly based on type.
	 */
	@Override
	public void peformUrlStringToTableAssociations
	(
	 String urlInput, 
	 ImgFileDetails imgDetails,
	 long cmsID, 
	 NBCDataBaseHelper.T_UrlTypeToId typeID, 
	 Object entityObj, 
	 NBCDataParsingBase parsingObj
	)
	{
		//setup default width and height for images specs.
		final long defaultWidth = 100;
		final long defaultHeight = 100;
		
		//parse the url here and get the meta data needed.
		//converted to url type. setup defaults for width and height.
		ImgFileUrlSpecs tmp_img_file = parsingObj.parseUrlString(urlInput, defaultWidth, defaultHeight);
						
		// this will be the entity obj for the img-url table. need to be cast since
		//return value is an object type to keep interface generic.
		UrlImgFileTable url_img_entity = 
			(UrlImgFileTable)this.imgFileTableEntryAndAssociationProcessing
				(
				 tmp_img_file,
				 imgDetails,
				 cmsID,
				 typeID.getUrlTypeID(),
				 urlInput
				);
		
		//cast the entity obj to the visitor iface for each entity obj.
		EntityItemIface entity_item = (EntityItemIface) entityObj;
		
		//use the visitor iface to rcv the visitor obj to allow for specific processing for
		//each entity obj.
		entity_item.accept(visitor, typeID, url_img_entity);
		
//		//provide switch to appropriately provide the correct type of associations here.
//		switch(typeID)
//		{
//			case E_NOT_VALID_MEDIA_URL_TYPE:
//				break;
//			
//			//taken from lead media table types
//			case E_LEAD_MEDIA_THUMBNAIL_URL_TYPE:
//				
//				//cast entity obj to specific java bean type to make appropriate associations.
//				ContentItemLeadMediaTable cnt_lead_media_table_bean = (ContentItemLeadMediaTable)entityObj;
//				
//				//make the association with cms id to url-img obj here.
//				cnt_lead_media_table_bean.setLeadMediaThumbnailUrlImgTypeRowID(url_img_entity.getId());
//				cnt_lead_media_table_bean.setUrlImgFileTable(url_img_entity);
//				
//				break;
//			
//			//taken from media table types
//			case E_MEDIA_URL_TYPE:
//				
//				//cast entity obj to be specific to defined entity obj type.
//				ContentItemMediaTable tmp1_cnt_media_table_bean = (ContentItemMediaTable)entityObj;
//				
//				//make associations here specific to defined entity obj type.
//				tmp1_cnt_media_table_bean.setMediaUrlImgTypeRowID(url_img_entity.getId());
//				tmp1_cnt_media_table_bean.setMediaUrlImgType(url_img_entity);
//				
//				break;
//				
//			case E_MEDIA_PHOTO_THUMBNAIL_URL_TYPE:
//				
//				//cast entity obj to be specific to defined entity obj type.
//				ContentItemMediaTable tmp2_cnt_media_table_bean = (ContentItemMediaTable)entityObj;
//				
//				//make associations here specific to defined entity obj type.
//				tmp2_cnt_media_table_bean.setMediaPhotoThumbnailUrlImgTypeRowID(url_img_entity.getId());
//				tmp2_cnt_media_table_bean.setMediaPhotoThumbnailUrlImgType(url_img_entity);
//
//				break;
//				
//			case E_MEDIA_THUMBNAIL_URL_TYPE:
//				
//				//cast entity obj to be specific to defined entity obj type.
//				ContentItemMediaTable tmp3_cnt_media_table_bean = (ContentItemMediaTable)entityObj;
//				
//				//make associations here specific to defined entity obj type.
//				tmp3_cnt_media_table_bean.setMediaThumbnailUrlImgTypeRowID(url_img_entity.getId());
//				tmp3_cnt_media_table_bean.setMediaThumbnailUrlImgType(url_img_entity);
//				
//				break;
//			
//			//taken from related item table types
//			case E_REL_ITEM_MOBILE_THUMBNAIL_URL_TYPE:
//				//TODO
//				break;
//				
//			case E_REL_ITEM_STORY_THUMBNAIL_URL_TYPE:
//				//TODO
//				break;
//			
//			//taken from gallery img table types
//			case E_GAL_IMG_PATH_URL_TYPE:
//				//TODO
//				break;
//		}
	}
	
	/*
	 * this will take in a set of entity tables and perform the necessary associations for them
	 * it will also do the db insertions as needed.
	 * these db insert/updates need to be done like this since a content id is unique..
	 */
	@Override
	public void contentItemTableAssociationProcessing
	(Object cntItemsTableBean, Object cntItemDetailTableBean, Object cntMediaTableBean, Object cntLeadMediaTableBean)
	{
		//cast to green dao session obj from generic session obj type.
		DaoSession daoSession = (DaoSession)this.sessionObj;
		
		//cast to specific bean obj type here.
		ContentItemsTable content_items_table_bean = (ContentItemsTable)cntItemsTableBean;
		
		//update the foreign key associations for the content item table entry.
		//save the update via there respective dao. update the content item obj new sub bean obj.
		ContentItemLeadMediaTable cnt_lead_media_table_bean = (ContentItemLeadMediaTable)cntLeadMediaTableBean;
		daoSession.getContentItemLeadMediaTableDao().insertOrReplace(cnt_lead_media_table_bean);
		content_items_table_bean.setContentItemLeadMediaTable(cnt_lead_media_table_bean);
				
		ContentItemMediaTable cnt_media_table_bean = (ContentItemMediaTable)cntMediaTableBean;
		daoSession.getContentItemMediaTableDao().insertOrReplace(cnt_media_table_bean);
		content_items_table_bean.setContentItemMediaTable(cnt_media_table_bean);
		
		ContentItemDetailTable cnt_item_detail_table_bean = (ContentItemDetailTable)cntItemDetailTableBean;
		daoSession.getContentItemDetailTableDao().insertOrReplace(cnt_item_detail_table_bean);
		content_items_table_bean.setContentItemDetailTable(cnt_item_detail_table_bean);
		
		//update content item obj.
		daoSession.getContentItemsTableDao().insertOrReplace(content_items_table_bean);
	}
}
