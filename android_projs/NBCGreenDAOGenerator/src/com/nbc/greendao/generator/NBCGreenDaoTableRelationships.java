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
		Property fk_imgDetailsID = imgFnameTable.addLongProperty("imgDetailsID").notNull().getProperty();		
		imgFnameTable.addToOne(imgDetailsTable, fk_imgDetailsID);
		
		//create fk relationship between url_img table to img_fname table.
		Property fk_imgFnameID = urlImgFileTable.addLongProperty("imgFnameID").notNull().getProperty();
		urlImgFileTable.addToOne(imgFnameTable, fk_imgFnameID);
	}
	
	/*
	 * this will establish the relationships between the related items and the gallery entities to the
	 * img-url table. 
	 */
	public static void createRelationshipsRelatedItemsAndGalleryToImgDetails(Entity relatedItemsTable, Entity galleryTable, Entity urlImgFileTable)	
	{
		//setup related items table for the img media urls
		//setup 1:1 relationships
		Property fk_relItemMobileThumbnailUrlImgTypeRowID = relatedItemsTable.addLongProperty("relItemMobileThumbnailUrlImgTypeRowID").notNull().getProperty();
		relatedItemsTable.addToOne(urlImgFileTable, fk_relItemMobileThumbnailUrlImgTypeRowID, "relItemMobileThumbnailUrlImgType");
		
		Property fk_relItemStoryThumbnailUrlImgTypeRowID = relatedItemsTable.addLongProperty("relItemStoryThumbnailUrlImgTypeRowID").notNull().getProperty();
		relatedItemsTable.addToOne(urlImgFileTable, fk_relItemStoryThumbnailUrlImgTypeRowID, "relItemStoryThumbnailUrlImgType");
		//setup related items table for the img media urls
		
		//setup the gallery item table for the img media urls.
		//setup 1:1 relationships
		Property fk_GalleryImgPathUrlTypeRowID = galleryTable.addLongProperty("galleryImgPathUrlImgTypeRowID").notNull().getProperty();
		galleryTable.addToOne(urlImgFileTable, fk_GalleryImgPathUrlTypeRowID);
		//setup the gallery item table for the img media urls.
	}
	
	/*
	 * this will setup all the relationships for the content item and its
	 * internal details and create a fk relationship with the url-img file table.
	 */
	public static void createRelationshipContentItemsToDetails
	(Entity cntItemTable, Entity cntLeadMediaTable, Entity cntMediaTable, Entity cntItemDetailTable, Entity urlImgFileTable)
	{
		//setup the lead media table to have a fk with url-img file table.
		Property fk_leadMediaThumbnailUrlImgTypeRowID = cntLeadMediaTable.addLongProperty("leadMediaThumbnailUrlImgTypeRowID").notNull().getProperty();
		cntLeadMediaTable.addToOne(urlImgFileTable, fk_leadMediaThumbnailUrlImgTypeRowID);
		
		//setup the media table to have a fk with url-img file table.
		Property fk_mediaUrlImgTypeRowID = cntMediaTable.addLongProperty("mediaUrlImgTypeRowID").notNull().getProperty();
		cntMediaTable.addToOne(urlImgFileTable, fk_mediaUrlImgTypeRowID,"mediaUrlImgType");
		
		Property fk_mediaPhotoThumbnailUrlImgTypeRowID = cntMediaTable.addLongProperty("mediaPhotoThumbnailUrlImgTypeRowID").notNull().getProperty();
		cntMediaTable.addToOne(urlImgFileTable, fk_mediaPhotoThumbnailUrlImgTypeRowID,"mediaPhotoThumbnailUrlImgType");
		
		Property fk_mediaThumbnailUrlImgTypeRowID = cntMediaTable.addLongProperty("mediaThumbnailUrlImgTypeRowID").notNull().getProperty();
		cntMediaTable.addToOne(urlImgFileTable, fk_mediaThumbnailUrlImgTypeRowID,"mediaThumbnailUrlImgType");
		
		
		//setup the media table to have a fk with url-img file table.
		
//simulate the 1:1 table relationship
		//for production will create the 1:empty_relationship since we wont be accessing the 
		//specific items details of the content item outside of the content item table.
				
		//create FK relationship with lead_media table
		Property fk_cntLeadMediaID = cntItemTable.addLongProperty("cntLeadMediaCmsID").notNull().getProperty();
		cntItemTable.addToOne(cntLeadMediaTable, fk_cntLeadMediaID);
		
		//create FK relationship with content media table
		Property fk_cntMediaID = cntItemTable.addLongProperty("cntMediaCmsID").notNull().getProperty();
		cntItemTable.addToOne(cntMediaTable, fk_cntMediaID);
		
		//create FK relationship with content detail table
		Property fk_cntItemDetailID = cntItemTable.addLongProperty("cntItemDetailCmsID").notNull().getProperty();
		cntItemTable.addToOne(cntItemDetailTable, fk_cntItemDetailID);
//simulate the 1:1 table relationship
		 
	}
	
}
