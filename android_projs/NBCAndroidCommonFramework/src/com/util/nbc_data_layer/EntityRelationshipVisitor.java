package com.util.nbc_data_layer;

import java.util.List;

import com.util.nbc_data_layer.NBCDataBaseHelper.T_UrlTypeToId;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTable;
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
	
	@Override
	public void visit(GalleryContentTable galleryCntTable, T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable) 
	{
		//case on the different types but only for the url type relationship setup needed
		switch(typeID)
		{
			case E_GAL_IMG_PATH_URL_TYPE:
				//make the association with cms id to url-img obj here.
				galleryCntTable.setGalleryImgPathUrlImgTypeRowID(urlImgFileTable.getId());
				galleryCntTable.setUrlImgFileTable(urlImgFileTable);
				break;
				
			default:
				//TODO: this is a serious error...need to log and handle in some way.
				break;
		}
	}

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
		//get parent_cms_id and rel_cms_id to find the correct row fo the item if it exits.
		//both of these ids is primary key.
		long cms_id = relatedItemsTable.getParentCmsID();
		long rel_cms_id = relatedItemsTable.getRelCmsID();
		
		//get the dao of this item.
		RelatedItemsTableDao dao = daoSession.getRelatedItemsTableDao();
		QueryBuilder<RelatedItemsTable> tmp2 = dao.queryBuilder();
		tmp2.where(RelatedItemsTableDao.Properties.ParentCmsID.eq(cms_id), RelatedItemsTableDao.Properties.RelCmsID.eq(rel_cms_id));
		
		List<RelatedItemsTable> rel_items = dao.queryBuilder().where
		(RelatedItemsTableDao.Properties.ParentCmsID.eq(cms_id), RelatedItemsTableDao.Properties.RelCmsID.eq(rel_cms_id)).list();
		
		if(rel_items.size() == 0)
		{
			daoSession.getRelatedItemsTableDao().insert(relatedItemsTable);
		}
		else if(rel_items.size() == 1)
		{
			RelatedItemsTable tmp = rel_items.get(0);
			long pk_id = tmp.getId();
			relatedItemsTable.setId(pk_id);
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
		//check first if we have the item in the db before we insert..
		daoSession.getGalleryContentTableDao().insert(galleryContentTable);
	}

	@Override
	public void visit(ContentItemsTable contentItemsTable, DaoSession daoSession) 
	{
		daoSession.getContentItemsTableDao().insertOrReplace(contentItemsTable);
	}
}
