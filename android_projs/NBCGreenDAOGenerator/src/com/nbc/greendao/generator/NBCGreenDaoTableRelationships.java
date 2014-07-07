package com.nbc.greendao.generator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

/**
 * this class will handler all the table relationships 
 * needed for the data model.
 * 
 * author J.Mena
 *
 */
public class NBCGreenDaoTableRelationships 
{
	/*
	 * this will perform the relationships between the image file table and the image details
	 * table.
	 */
	public static void createRelationshipsImgFnameToImgDetails(Entity urlImgFileTable, Entity imgFnameTable, Entity imgDetailsTable)
	{
		//create fk relationship between image fname table an img details table.
		Property fk_imgDetailsID = imgFnameTable.addLongProperty("ImgDetailsID").notNull().getProperty();		
		imgFnameTable.addToOne(imgDetailsTable, fk_imgDetailsID);
		
		//create fk relationship between url_img table to img_fname table.
		Property fk_imgFnameID = urlImgFileTable.addLongProperty("ImgFnameID").notNull().getProperty();
		urlImgFileTable.addToOne(imgFnameTable, fk_imgFnameID);
	}
	
	/*
	 * this will setup all the relationships for the content item and its
	 * internal details and create a fk relationship with the url-img file table.
	 */
	public static void createRelationshipContentItemsToDetails
	(Entity cntItem, Entity cntLeadMedia, Entity cntMedia, Entity cntItemDetail, Entity UrlImgFileTable)
	{
		//setup the lead media table to have a fk with url-img file table.
		Property fk_leadMediaThumbnailUrlImgTypeRowID = cntLeadMedia.addLongProperty("leadMediaThumbnailUrlImgTypeRowID").notNull().getProperty();
		cntLeadMedia.addToOne(UrlImgFileTable, fk_leadMediaThumbnailUrlImgTypeRowID);
		
		//setup the media table to have a fk with url-img file table.
		Property fk_mediaUrlImgTypeRowID = cntMedia.addLongProperty("mediaUrlImgTypeRowID").notNull().getProperty();
		cntMedia.addToOne(UrlImgFileTable, fk_mediaUrlImgTypeRowID,"mediaUrlImgType");
		
		Property fk_mediaPhotoThumbnailUrlImgTypeRowID = cntMedia.addLongProperty("mediaPhotoThumbnailUrlImgTypeRowID").notNull().getProperty();
		cntMedia.addToOne(UrlImgFileTable, fk_mediaPhotoThumbnailUrlImgTypeRowID,"mediaPhotoThumbnailUrlImgType");
		
		Property fk_mediaThumbnailUrlImgTypeRowID = cntMedia.addLongProperty("mediaThumbnailUrlImgTypeRowID").notNull().getProperty();
		cntMedia.addToOne(UrlImgFileTable, fk_mediaThumbnailUrlImgTypeRowID,"mediaThumbnailUrlImgType");
		
		
		//setup the media table to have a fk with url-img file table.
		
//simulate the 1:1 table relationship
		//for production will create the 1:empty_relationship since we wont be accessing the 
		//specific items details of the content item outside of the content item table.
				
		//create FK relationship with lead_media table
		Property fk_cntLeadMediaID = cntItem.addLongProperty("cntLeadMediaCmsID").notNull().getProperty();
		cntItem.addToOne(cntLeadMedia, fk_cntLeadMediaID);
		
		//create FK relationship with content media table
		Property fk_cntMediaID = cntItem.addLongProperty("cntMediaCmsID").notNull().getProperty();
		cntItem.addToOne(cntMedia, fk_cntMediaID);
		
		//create FK relationship with content detail table
		Property fk_cntItemDetailID = cntItem.addLongProperty("cntItemDetailCmsID").notNull().getProperty();
		cntItem.addToOne(cntItemDetail, fk_cntItemDetailID);
//simulate the 1:1 table relationship
		 
	}
	
}
