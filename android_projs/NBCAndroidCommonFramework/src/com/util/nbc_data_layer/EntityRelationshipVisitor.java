package com.util.nbc_data_layer;

import java.util.List;

import com.util.nbc_data_layer.NBCDataBaseHelper.T_UrlTypeToId;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.RelatedItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.RelatedItemsTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTable;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * this file is the standard visitor pattern from the GOF book. 
 * Each function allows for each object type to setup relationships as well as 
 * specific processing for the specific POJO type.
 * 
 * Author J.Mena
 **/
public class EntityRelationshipVisitor implements EntityVisitorIface
{	
	@Override
	public void visit
	(ContentItemLeadMediaTable cntItemleadMediaTable, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable) 
	{
		//case on the different types but only for the url type relationship setup needed
		switch(typeID)
		{
			case E_LEAD_MEDIA_THUMBNAIL_URL_TYPE:
				//make the association with cms id to url-img obj here.
				cntItemleadMediaTable.setLeadMediaThumbnailUrlImgTypeRowID(urlImgFileTable.getId());
				cntItemleadMediaTable.setUrlImgFileTable(urlImgFileTable);
				break;
				
			default:
				//TODO: this is a serious error...need to log and handle in some way.
				break;
		}
	}

	@Override
	public void visit(ContentItemMediaTable cntItemMediaTable, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable) 
	{
		//case on the different types but only for the url type relationship setup needed
		switch(typeID)
		{
			case E_MEDIA_URL_TYPE:
				//make associations here specific to defined entity obj type.
				cntItemMediaTable.setMediaUrlImgTypeRowID(urlImgFileTable.getId());
				cntItemMediaTable.setMediaUrlImgType(urlImgFileTable);
				break;
				
			case E_MEDIA_PHOTO_THUMBNAIL_URL_TYPE:
				//make associations here specific to defined entity obj type.
				cntItemMediaTable.setMediaPhotoThumbnailUrlImgTypeRowID(urlImgFileTable.getId());
				cntItemMediaTable.setMediaPhotoThumbnailUrlImgType(urlImgFileTable);
				break;
				
			case E_MEDIA_THUMBNAIL_URL_TYPE:
				//make associations here specific to defined entity obj type.
				cntItemMediaTable.setMediaThumbnailUrlImgTypeRowID(urlImgFileTable.getId());
				cntItemMediaTable.setMediaThumbnailUrlImgType(urlImgFileTable);
				break;
				
			default:
				//TODO: this is a serious error...need to log and handle in some way.
				break;
		}
	}
	
	@Override
	public void visit(RelatedItemsTable relItemsTable, T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable) 
	{	
		//case on the different types but only for the url type relationship setup needed
		switch(typeID)
		{
			case E_REL_ITEM_MOBILE_THUMBNAIL_URL_TYPE:
				//make the association with cms id to url-img obj here.
				relItemsTable.setRelItemMobileThumbnailUrlImgTypeRowID(urlImgFileTable.getId());
				relItemsTable.setRelItemMobileThumbnailUrlImgType(urlImgFileTable);
				break;
				
			case E_REL_ITEM_STORY_THUMBNAIL_URL_TYPE:
				//make the association with cms id to url-img obj here.
				relItemsTable.setRelItemStoryThumbnailUrlImgTypeRowID(urlImgFileTable.getId());
				relItemsTable.setRelItemStoryThumbnailUrlImgType(urlImgFileTable);
				break;
				
			default:
				//TODO: this is a serious error...need to log and handle in some way.
				break;
		}
	}
	
//	@Override
//	public void visit(GalleryContentTable galleryCntTable, T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable) 
//	{
//		//case on the different types but only for the url type relationship setup needed
//		switch(typeID)
//		{
//			case E_GAL_IMG_PATH_URL_TYPE:
//				//make the association with cms id to url-img obj here.
//				//galleryCntTable.setGalleryImgPathUrlImgTypeRowID(urlImgFileTable.getId());
//				//galleryCntTable.setUrlImgFileTable(urlImgFileTable);
//				break;
//				
//			default:
//				//TODO: this is a serious error...need to log and handle in some way.
//				break;
//		}
//	}

	@Override
	public void visit(ContentItemLeadMediaTable cntLeadMediaTable, DaoSession daoSession, ContentItemsTable cntItemsTable) 
	{
		daoSession.getContentItemLeadMediaTableDao().insertOrReplace(cntLeadMediaTable);
		cntItemsTable.setContentItemLeadMediaTable(cntLeadMediaTable);
	}

	@Override
	public void visit(ContentItemMediaTable cntItemMediaTable, DaoSession daoSession, ContentItemsTable cntItemsTable) 
	{
		daoSession.getContentItemMediaTableDao().insertOrReplace(cntItemMediaTable);
		cntItemsTable.setContentItemMediaTable(cntItemMediaTable);
	}

	@Override
	public void visit(ContentItemDetailTable cntItemDetailTable, DaoSession daoSession, ContentItemsTable cntItemsTable) 
	{
		daoSession.getContentItemDetailTableDao().insertOrReplace(cntItemDetailTable);
		cntItemsTable.setContentItemDetailTable(cntItemDetailTable);
	}

	@Override
	public void visit(RelatedItemsTable relatedItemsTable, DaoSession daoSession)
	{
		//get parent_cms_id and rel_cms_id to find the correct row for the item if it exits.
		//both of these ids are primary keys
		long parent_cms_id = relatedItemsTable.getParentCmsID();
		long rel_cms_id = relatedItemsTable.getRelCmsID();
		
		//get the dao of this item.
		RelatedItemsTableDao dao = daoSession.getRelatedItemsTableDao();
		
		//get create the query and get the results by executing the query using the builder.
		List<RelatedItemsTable> rel_items = dao.queryBuilder().where
		(RelatedItemsTableDao.Properties.ParentCmsID.eq(parent_cms_id), RelatedItemsTableDao.Properties.RelCmsID.eq(rel_cms_id)).list();
		
		//no items found then, just do regular insert.
		if(rel_items.size() == 0)
		{
			daoSession.getRelatedItemsTableDao().insert(relatedItemsTable);
		}
		//found a single item..update that same row with the content passed in.
		else if(rel_items.size() == 1)
		{
			//get the item from the query list
			RelatedItemsTable tmp = rel_items.get(0);
			
			//get the row id of this item.
			long pk_id = tmp.getId();
			
			//update the current entity bean with the row id of the previous one.
			relatedItemsTable.setId(pk_id);
			
			//replace the previous entity in DB with the one passed.
			daoSession.getRelatedItemsTableDao().insertOrReplace(relatedItemsTable);
		}
		else
		{
			//TODO: should not happen...but log it if so...
		}
	}

	@Override
	public void visit(GalleryContentTable galleryContentTable, DaoSession daoSession)
	{
		//get parent_cms_id and rel_cms_id to find the correct row for the item if it exits.
		//both of these ids are primary keys
		long gal_cms_id = galleryContentTable.getGalCmsID();
		long img_idx = galleryContentTable.getImgIndex();
		
		//get the dao of this item.
		GalleryContentTableDao dao = daoSession.getGalleryContentTableDao();
		
		//get create the query and get the results by executing the query using the builder.
		List<GalleryContentTable> gal_items = dao.queryBuilder().where
		(GalleryContentTableDao.Properties.GalCmsID.eq(gal_cms_id), GalleryContentTableDao.Properties.ImgIndex.eq(img_idx)).list();
		
		//no items found then, just do regular insert.
		if(gal_items.size() == 0)
		{
			daoSession.getGalleryContentTableDao().insert(galleryContentTable);
		}
		//found a single item..update that same row with the content passed in.
		else if(gal_items.size() == 1)
		{
			//get the item from the query list
			GalleryContentTable tmp = gal_items.get(0);
			
			//get the row id of this item.
			long pk_id = tmp.getId();
			
			//update the current entity bean with the row id of the previous one.
			galleryContentTable.setId(pk_id);
			
			//replace the previous entity in DB with the one passed.
			daoSession.getGalleryContentTableDao().insertOrReplace(galleryContentTable);
		}
		else
		{
			//TODO: should not happen...but log it if so...
		}
	}

	@Override
	public void visit(ContentItemsTable contentItemsTable, DaoSession daoSession) 
	{
		daoSession.getContentItemsTableDao().insertOrReplace(contentItemsTable);
	}
}
