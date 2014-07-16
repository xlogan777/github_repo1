package com.util.nbc_data_layer;

/**
 * 
 * this is the iface that contains all the different POJO types from
 * the green dao generator. This is part of the visitor pattern from the GOF.
 *
 */
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.RelatedItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTable;

public interface EntityVisitorIface 
{
	/*
	 * visitor section of the img-url file processing
	 */
	public void visit
	(ContentItemLeadMediaTable cntItemleadMediaTable, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);	
	public void visit
	(ContentItemMediaTable cntItemMediaTable, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
	public void visit
	(RelatedItemsTable relItemsTable, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
	
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
