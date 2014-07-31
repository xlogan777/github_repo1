package com.util.mydao_data_layer;

import java.util.List;

import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemsTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.DaoMaster;
import com.util.mydao_data_layer.GreenDaoSrcGen.DaoSession;
import com.util.mydao_data_layer.GreenDaoSrcGen.GalleryContentTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.GalleryContentTableDao;
import com.util.mydao_data_layer.GreenDaoSrcGen.ImgFnameTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.ImgFnameTableDao;
import com.util.mydao_data_layer.GreenDaoSrcGen.RelatedItemsTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.RelatedItemsTableDao;
import com.util.mydao_data_layer.GreenDaoSrcGen.UrlImgFileTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.UrlImgFileTableDao;
import com.util.mydao_data_layer.dataTypes.*;

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
    private DataBaseHelper helper;//helper obj that creates the db connection and provides the db connection.
         
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
		        helper = new DataBaseHelper(this.context, this.dbName, this.factory);
		        	        
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
	public UrlImgTypeIface imgFileTableEntryAndAssociationProcessing
	(ImgFileUrlSpecs imgFileSepcs, ImgFileDetails imgFileDetails, long cmsID, long urlTypeID, String urlLocation)
	{
		//object to be returned back to caller.
		UrlImgFileTable rv = null;
		
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
			//do the processing here for the img_fname entity item. 
			ImgFnameTable img_fname_entity = 
					(ImgFnameTable)this.imgTableProcessing(imgFileSepcs, imgFileDetails, urlLocation);
			
			//create entity obj here and setup with specifics of the java bean.
			UrlImgFileTable url_img_entity = new UrlImgFileTable();
			url_img_entity.setCmsID(cmsID);
			url_img_entity.setUrlTypeID(urlTypeID);
			
			//association here to img-url table here.
			url_img_entity.setImgFnameID(img_fname_entity.getId());
			url_img_entity.setImgFnameTable(img_fname_entity);
			
			//create row here in the img-url table
			dao_session.getUrlImgFileTableDao().insert(url_img_entity);
									
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
	 * this will perform the processing for the saving of the img file table to the DB layer
	 * or just returning the found fname row item back to caller as an obj.
	 */
	@Override
	public ImgFnameTypeIface imgTableProcessing
	(ImgFileUrlSpecs imgFileSepcs, ImgFileDetails imgFileDetails, String urlLocation)
	{
		DaoSession dao_session = ((DaoSession)sessionObj);
		
		ImgFnameTable img_fname_entity = null;

		//find if we have this same fname in the table already.
		List <ImgFnameTable> fname_items = 
		    dao_session.getImgFnameTableDao().queryBuilder().where
		    (
		       ImgFnameTableDao.Properties.ImgFname.eq(imgFileSepcs.getFname()),
		       ImgFnameTableDao.Properties.ImgWidth.eq(imgFileSepcs.getWidth()),
		       ImgFnameTableDao.Properties.ImgHeight.eq(imgFileSepcs.getHeight())
			).list();
		
		if(fname_items.size() == 0)
		{
				//create entity obj here and setup with specifics of the java bean.
				img_fname_entity = new ImgFnameTable();
				img_fname_entity.setImgFname(imgFileSepcs.getFname());
				img_fname_entity.setImgWidth(imgFileSepcs.getWidth());
				img_fname_entity.setImgHeight(imgFileSepcs.getHeight());
				
				//check to see if we have img details.so then use it here.
				if(imgFileDetails != null)
				{	
					img_fname_entity.setImgCaption(imgFileDetails.getCaption());
					img_fname_entity.setImgCredit(imgFileDetails.getCredit());
				}
				else
				{
					img_fname_entity.setImgCaption("");
					img_fname_entity.setImgCredit("");
				}
				
				img_fname_entity.setImgUrlLocation(urlLocation);
							
				//create row in img-fname table
				dao_session.getImgFnameTableDao().insert(img_fname_entity);
		}
		else if(fname_items.size() == 1)
		{
			img_fname_entity = fname_items.get(0);
		}
		else
		{
			//TODO: this is a major error..handle it..
		}
		
		return img_fname_entity;
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
	 DataBaseHelper.T_UrlTypeToId typeID,
	 //Object entityObj,
	 UrlImgTypeIface entityObj,
	 DataParsingBase parsingObj
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
	}
	
	/*
	 * this will take in a set of entity tables and perform the necessary associations for them
	 * it will also do the db insertions as needed.
	 * these db insert/updates need to be done like this since a content id is unique..
	 */
	@Override
	public void contentItemTableAssociationProcessing
	(
	 CntTypeIface cntItemsTableBean, 
	 CntDetailTypeIface cntItemDetailTableBean, 
	 CntMediaTypeIface cntMediaTableBean, 
	 CntLeadMediaTypeIface cntLeadMediaTableBean
	)
	{
		//cast to green dao session obj from generic session obj type.
		DaoSession daoSession = (DaoSession)this.sessionObj;
		
		//cast to specific bean obj type here.
		ContentItemsTable content_items_table_bean = (ContentItemsTable)cntItemsTableBean;
		
	//perform the entity table association and db transaction for cnt_lead_media_table_bean	
		//cast the entity obj to the visitor iface for each entity obj.
		EntityItemIface entity_item = (EntityItemIface)cntLeadMediaTableBean;
		
		//use the visitor iface to rcv the visitor obj to allow for specific processing for
		//each entity obj.
		entity_item.accept(visitor,daoSession,content_items_table_bean);
	//perform the entity table association and db transaction for cnt_lead_media_table_bean
		
	//perform the entity table association and db transaction for cnt_media_table_bean	
		//cast the entity obj to the visitor iface for each entity obj.
		entity_item = (EntityItemIface)cntMediaTableBean;
		
		//use the visitor iface to rcv the visitor obj to allow for specific processing for
		//each entity obj.
		entity_item.accept(visitor,daoSession,content_items_table_bean);
	//perform the entity table association and db transaction for cnt_media_table_bean
		
	//perform the entity table association and db transaction for cnt_item_detail_table_bean	
		//cast the entity obj to the visitor iface for each entity obj.
		entity_item = (EntityItemIface)cntItemDetailTableBean;
		
		//use the visitor iface to rcv the visitor obj to allow for specific processing for
		//each entity obj.
		entity_item.accept(visitor,daoSession,content_items_table_bean);
	//perform the entity table association and db transaction for cnt_item_detail_table_bean
		
	//perform the entity table association and db transaction for cnt_items_table_bean	
		//cast the entity obj to the visitor iface for each entity obj.
		entity_item = (EntityItemIface)cntItemsTableBean;
		
		//use the visitor iface to rcv the visitor obj to allow for specific processing for
		//each entity obj.
		entity_item.accept(visitor,daoSession);
	//perform the entity table association and db transaction for cnt_items_table_bean
	}
	
	/*
	 * this will perform the data insertion from the related items bean to the db layer.
	 * 
	 */
	@Override
	public void relatedItemsTableAssociationProcessing(RelCntItemTypeIface relatedItemsTableBean)
	{
		//cast to green dao session obj from generic session obj type.
		DaoSession daoSession = (DaoSession)this.sessionObj;
		
		//perform the entity table association and db transaction for related_items_table_bean	
		//cast the entity obj to the visitor iface for each entity obj.
		EntityItemIface entity_item = (EntityItemIface)relatedItemsTableBean;
		
		//use the visitor iface to rcv the visitor obj to allow for specific processing for
		//each entity obj.
		entity_item.accept(visitor,daoSession);
		//perform the entity table association and db transaction for related_items_table_bean
	}
	
	/*
	 * this will perform the data insertion from the gallery items bean to the db layer.
	 * 
	 */
	@Override
	public void galleryTableAssociationProcessing(GalleryCntTypeIface galleryItemsTableBean)
	{
		//cast to green dao session obj from generic session obj type.
		DaoSession daoSession = (DaoSession)this.sessionObj;
		
		//perform the entity table association and db transaction for gallery_items_table_bean	
		//cast the entity obj to the visitor iface for each entity obj.
		EntityItemIface entity_item = (EntityItemIface)galleryItemsTableBean;
		
		//use the visitor iface to rcv the visitor obj to allow for specific processing for
		//each entity obj.
		entity_item.accept(visitor,daoSession);
		//perform the entity table association and db transaction for gallery_items_table_bean
	}
	
	/*
	 * 
	 * this will return back to caller as param what we got from the db layer
	 * using this cnt id.
	 */
	@Override
	public CntTypeIface getContentData(long cmsId)
	{		
		//cast to green dao session obj from generic session obj type.
		DaoSession daoSession = (DaoSession)this.sessionObj;
		
		//load the specific entity type using the id.
		ContentItemsTable contentData = daoSession.getContentItemsTableDao().load(cmsId);
		
		//return specific obj.
		return contentData;
	}
	
	/*
	 * 
	 * this will return back to caller as param what we got from the db layer
	 * using this cnt id the entire related items list.
	 * 
	 * NOTE: added the [SuppressWarnings annotation to remove the cast warning of list types.]
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<RelCntItemTypeIface> getRelatedContentDataAsList(long cmsId)
	{
		//cast to green dao session obj from generic session obj type.
		DaoSession daoSession = (DaoSession)this.sessionObj;
	
		//get the list of related items using the cms id.
		List<RelatedItemsTable> rel_items = 
			daoSession.getRelatedItemsTableDao().queryBuilder().where
				(RelatedItemsTableDao.Properties.ParentCmsID.eq(cmsId)).list();
		
		//cast through an intermediate wildcard type to cast to list of object types
		List<RelCntItemTypeIface> relatedCntList = (List<RelCntItemTypeIface>)(List<?>)rel_items;
		
		//return specific list.
		return relatedCntList;
	}
	
	/*
	 * 
	 * this will return back to caller as param what we got from the db layer
	 * using this cnt id the entire gallery items list.
	 * 
	 * NOTE: added the [SuppressWarnings annotation to remove the cast warning of list types.]
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<GalleryCntTypeIface> getGalleryContentDataAsList(long cmsId)
	{
		//cast to green dao session obj from generic session obj type.
		DaoSession daoSession = (DaoSession)this.sessionObj;
		
		List<GalleryContentTable> gal_items = 
			daoSession.getGalleryContentTableDao().queryBuilder().where
				(GalleryContentTableDao.Properties.GalCmsID.eq(cmsId)).list();
		
		//cast through an intermediate wildcard type to cast to list of object types
		List<GalleryCntTypeIface> galleryList = (List<GalleryCntTypeIface>)(List<?>)gal_items;
		
		//return specific list.
		return galleryList;
	}
}
