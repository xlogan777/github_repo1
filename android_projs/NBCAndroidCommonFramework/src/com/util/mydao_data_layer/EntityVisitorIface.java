package com.util.mydao_data_layer;

/**
 * 
 * this is the iface that contains all the different POJO types from
 * the green dao generator. This is part of the visitor pattern from the GOF.
 *
 */
import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemDetailTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemMediaTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemsTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.DaoSession;
import com.util.mydao_data_layer.GreenDaoSrcGen.GalleryContentTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.RelatedItemsTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.UrlImgFileTable;

public interface EntityVisitorIface 
{
	/*
	 * visitor section of the img-url file processing
	 */
	public void visit
	(ContentItemLeadMediaTable cntItemleadMediaTable, DataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);	
	public void visit
	(ContentItemMediaTable cntItemMediaTable, DataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
	public void visit
	(RelatedItemsTable relItemsTable, DataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
	
//	public void visit
//	(GalleryContentTable galleryCntTable, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
	
	
	/*
	 * visitor section of the content items with specific associations.
	 */
	public void visit(ContentItemLeadMediaTable cntLeadMediaTable, DaoSession daoSession, ContentItemsTable cntItemsTable);
	public void visit(ContentItemMediaTable cntItemMediaTable, DaoSession daoSession, ContentItemsTable cntItemsTable);
	public void visit(ContentItemDetailTable cntItemDetailTable, DaoSession daoSession, ContentItemsTable cntItemsTable);
	
	//area for single items
	public void visit(RelatedItemsTable relatedItemsTable, DaoSession daoSession);
	public void visit(GalleryContentTable galleryContentTable, DaoSession daoSession);
	public void visit(ContentItemsTable contentItemsTable, DaoSession daoSession);
	
	//TODO: add the content items table entry here for the visitor pattern...
}
