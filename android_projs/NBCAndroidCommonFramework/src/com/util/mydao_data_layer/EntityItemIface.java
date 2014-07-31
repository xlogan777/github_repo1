package com.util.mydao_data_layer;

import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemsTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.DaoSession;
import com.util.mydao_data_layer.GreenDaoSrcGen.UrlImgFileTable;

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
	(EntityVisitorIface entityVisitorIface, DataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
	
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
