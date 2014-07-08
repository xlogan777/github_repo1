package com.util.nbc_data_layer;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTable;

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
		//make the association with cms id to url-img obj here.
		cntItemleadMediaTable.setLeadMediaThumbnailUrlImgTypeRowID(urlImgFileTable.getId());
		cntItemleadMediaTable.setUrlImgFileTable(urlImgFileTable);
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
	public void visit(ContentItemsTable cntItemsTable, ContentItemLeadMediaTable cntLeadMediaTable, DaoSession daoSession) 
	{
	
		
	}

	@Override
	public void visit(ContentItemsTable cntItemsTable, ContentItemMediaTable cntItemMediaTable, DaoSession daoSession) 
	{
	
		
	}

	@Override
	public void visit(ContentItemsTable cntItemsTable, ContentItemDetailTable cntItemDetailTable, DaoSession daoSession) 
	{
	
		
	}
}
