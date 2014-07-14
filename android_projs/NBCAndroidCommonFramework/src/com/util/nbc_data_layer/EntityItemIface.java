package com.util.nbc_data_layer;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTable;

/**
 * this will be implemented by each POJO from the green dao generated code.
 * This is part of the visitor pattern from the GOF.
 */
public interface EntityItemIface 
{
	/*
	 * this is used to associate the image table entity types to content item entity tables
	 */
	public void accept
	(EntityVisitorIface entityVisitorIface, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
	
	/*
	 * this is used to associate the different content types to the main content entity type.
	 */
	public void accept
	(EntityVisitorIface entityVisitorIface, DaoSession daoSession, ContentItemsTable cntItemsTable);
	
	/*
	 * this is used for either related items/gallery/content items entity tables.
	 */
	public void accept
	(EntityVisitorIface entityVisitorIface, DaoSession daoSession);
}
