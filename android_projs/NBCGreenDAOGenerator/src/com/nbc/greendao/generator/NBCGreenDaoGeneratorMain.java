package com.nbc.greendao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * this will generate the java bean files for each entity class mapped to the
 * DB table. It also generates the Entity DAO java file that does the java-sql mapping.
 * Define the schema here with all the table relationships...1:1, 1:n..etc here.
 * and the setup will have the associations in the java bean files and the DAO interface
 * will contain these associations also.
 * 
 * Author J.Mena
 *
 */
public class NBCGreenDaoGeneratorMain 
{
	public static void main(String[] args) 
	{
		//create green dao schema obj with version number, and pkg for the generated files.
		int db_version_number = 1;
		String pkg_name = "com.util.nbc_data_layer.nbcGreenDaoSrcGen";
		
		//create schema obj.
        Schema schema = new Schema(db_version_number, pkg_name);
        
        //this will allow for keep sections to be generated for all entities under this schema.
        //you can place your code in here...to keep after each generation of the code.
        schema.enableKeepSectionsByDefault();
        
        //create the image association table
        //System.out.println("create createImgFileAccessTable");
        //createImgFileAccessTable(schema);
		
		//create the image table that holds the location of the actual image file name
		//on the file system.
		System.out.println("create createImgFnameTable");
		Entity entity_img_file_table = createImgFnameTable(schema);
				
		// create the gallery table
		System.out.println("create createGalleryContentTable");
		Entity entity_gallery_table = createGalleryContentTable(schema, entity_img_file_table);
		
		//create the related items table.
		System.out.println("create createRelatedItemsTable");
		Entity related_item_table = createRelatedItemsTable(schema, entity_img_file_table, entity_gallery_table);
		
		System.out.println("create createContentItemLeadMediaTable");
		Entity content_item_lead_media_table = createContentItemLeadMediaTable(schema, entity_img_file_table);
		
		System.out.println("create createContentItemMediaTable");
		Entity content_item_media_table = createContentItemMediaTable(schema, entity_img_file_table);
		
		System.out.println("create createContentItemDetailTable");
		Entity content_item_detail_table = createContentItemDetailTable(schema);
		
		System.out.println("create createContentItemsTable");
		/*
	  	  Schema schema
		  Entity contentItemMediaLead,
		  Entity contentItemMedia, 
		  Entity contentItemDetail, 
		  Entity galleryTable,
		  Entity relatedItems	  
		 */
		createContentItemsTable
		( 
		  schema,
		  content_item_lead_media_table,
		  content_item_media_table,
		  content_item_detail_table,
		  entity_gallery_table,
		  related_item_table 
		);
		
		//generate the source code.
		try
		{
			//create dao generator obj.
			DaoGenerator dao_generator = new DaoGenerator();
			
			//generate code based on schema and place in outdir.			
			dao_generator.generateAll(schema, "../NBCAndroidCommonFramework/src");
			
			System.out.println("JM...generated the tables from green dao.");
		}
		catch(Exception e)
		{
			System.out.println("JM...exception = "+e.getMessage());
			e.printStackTrace();
		}
	}
	
/**
 * this will use the schema to create entity and contain the appropriate 
 * properties for the entity. the entity will also contain the DB relationships as needed.
 */
	/*
	 * add the file access table that does the tracking of the image files
	 * on the disk. this is a relationship table.
	 */
	/*public static void createImgFileAccessTable(Schema schema)
	{
		//table name
		Entity ImgFileAccessTable = schema.addEntity("ImgFileAccessTable");
		
		//this is pk		
		ImgFileAccessTable.addIntProperty("ImgTableFnameID").primaryKey().notNull();
		
		//non pk columns.
		ImgFileAccessTable.addIntProperty("ImgFileRefCount").notNull();
	}*/
	
	/*
	 * this will take in a schema obj and create the entity for the image filename table
	 * this will keep track of the image file name on the file system.
	 */
	public static Entity createImgFnameTable(Schema schema)
	{
		//create the entity obj and tied it to the schema.
		//this is the table in the DB. the string passed in will be the 
		//table name.
		Entity ImgFnameTable = schema.addEntity("ImgFnameTable");
		
		//this is the PK, which cant be null.
		ImgFnameTable.addIntProperty("ImgFnameID").primaryKey().notNull();
		
		//add the non-PK columns.
		ImgFnameTable.addStringProperty("ImageFname").notNull();
		ImgFnameTable.addIntProperty("ImgFileRefCount").notNull();
		
		return ImgFnameTable;
	}
	
	/*
	 * add the specific gallery content table type.
	 * will have 1:1 relationship to image file table.
	 */
	public static Entity createGalleryContentTable(Schema schema, Entity ImageFileTable)
	{
		//table name.
		Entity GalleryContentTable = schema.addEntity("GalleryContentTable");
		
		//this is pk.
		GalleryContentTable.addIntProperty("GalCmsID").primaryKey().notNull();
		
		//add non pk columns.
		GalleryContentTable.addIntProperty("ImgHeight").notNull();
		GalleryContentTable.addIntProperty("ImgWidth").notNull();
		GalleryContentTable.addIntProperty("ImgIndex").notNull();		
		GalleryContentTable.addStringProperty("ImgPath").notNull();
		GalleryContentTable.addStringProperty("ImgCaption").notNull();
		GalleryContentTable.addStringProperty("ImgCredit").notNull();
		
		//add table relationships here. this should be a 1:1 relationship
		//get the pk for the image table.
		Property img_table_id = ImageFileTable.getPkProperty();
		
		//make the 1:1 association from the gallery table to the image table.
		GalleryContentTable.addToOne(ImageFileTable, img_table_id);
		
		//return entity item.
		return GalleryContentTable;
	}
	
	/*
	 * this is responsible for the creation of the related item table.
	 * will have relationship to image file table. 1:1
	 * will have a relationship to the gallery table. 1:n
	 */
	public static Entity createRelatedItemsTable(Schema schema, Entity ImageFileTable, Entity GalleryTable)
	{
		//table name
		Entity RelatedItemsTable = schema.addEntity("RelatedItemsTable");
		
		//this is the pk.
		RelatedItemsTable.addIntProperty("ParentCmsID").primaryKey().notNull();
		
		//add non pk fields here.
		RelatedItemsTable.addIntProperty("RelCmsID").notNull();
		RelatedItemsTable.addStringProperty("ContentType").notNull();
		RelatedItemsTable.addStringProperty("Title").notNull();
		RelatedItemsTable.addBooleanProperty("Sponsored").notNull();
		RelatedItemsTable.addStringProperty("Source").notNull();
		RelatedItemsTable.addStringProperty("MobileThumbnailUrl").notNull();
		RelatedItemsTable.addStringProperty("StoryThumbnailUrl").notNull();
		RelatedItemsTable.addStringProperty("SharingUrl").notNull();
		RelatedItemsTable.addIntProperty("TypeID").notNull();
		
		//add table relationships here. this should be a 1:1 relationship
		//get the pk for the image table.
		Property img_table_id = ImageFileTable.getPkProperty();
		
		//make the 1:1 association from the related items table to the image table.
		RelatedItemsTable.addToOne(ImageFileTable, img_table_id);
		
		//make relationship with gallery table.
		//get the pk for this item.
		Property gallery_table_id = GalleryTable.getPkProperty();
		
		//make the 1:n relationship between the related items table and the gallery table.
		RelatedItemsTable.addToMany(GalleryTable, gallery_table_id);
		
		//return entity.
		return RelatedItemsTable;
	}
	
	/*
	 * this is the lead media table for a content item.
	 * will have a relationship to image table. 1:1.
	 */
	public static Entity createContentItemLeadMediaTable(Schema schema, Entity ImageFileTable)
	{
		//table name
		Entity ContentItemLeadMediaTable = schema.addEntity("ContentItemLeadMediaTable");
		
		//add pk
		ContentItemLeadMediaTable.addIntProperty("CmsID").primaryKey().notNull();
		
		//add non pk fields.
		ContentItemLeadMediaTable.addStringProperty("LeadMediaContentType").notNull();
		ContentItemLeadMediaTable.addStringProperty("LeadMediaThumbnail").notNull();
		ContentItemLeadMediaTable.addStringProperty("LeadMediaExtID").notNull();
		ContentItemLeadMediaTable.addStringProperty("LeadEmbeddedVideo").notNull();
		
		//add table relationships here. this should be a 1:1 relationship
		//get the pk for the image table.
		Property img_table_id = ImageFileTable.getPkProperty();
		
		//make the 1:1 association from the content items lead media table to the image table.
		ContentItemLeadMediaTable.addToOne(ImageFileTable, img_table_id);
		
		//return entity
		return ContentItemLeadMediaTable;
	}
	
	/*
	 * this is the content item media table for a content item.
	 * will have a relationship to the image file table. 1:1. 
	 */
	public static Entity createContentItemMediaTable(Schema schema, Entity ImageFileTable)
	{
		//table name
		Entity ContentItemMediaTable = schema.addEntity("ContentItemMediaTable");
		
		//add pk.
		ContentItemMediaTable.addIntProperty("CmsID").primaryKey().notNull();
		
		//add non pk fields.
		ContentItemMediaTable.addStringProperty("Url").notNull();
		ContentItemMediaTable.addIntProperty("Width").notNull();
		ContentItemMediaTable.addIntProperty("Height").notNull();
		ContentItemMediaTable.addStringProperty("ImageCredit").notNull();
		ContentItemMediaTable.addStringProperty("PhotoThumbnail").notNull();
		ContentItemMediaTable.addStringProperty("Thumbnail").notNull();
		
		//add table relationships here. this should be a 1:1 relationship
		//get the pk for the image table.
		Property img_table_id = ImageFileTable.getPkProperty();
		
		//make the 1:1 association from the content item media table to the image table.
		ContentItemMediaTable.addToOne(ImageFileTable, img_table_id);
		
		//return entity
		return ContentItemMediaTable;
	}
	
	/*
	 * this is the content item detail table for a content item.
	 * 
	 */
	public static Entity createContentItemDetailTable(Schema schema)
	{
		//table name
		Entity ContentItemDetailTable = schema.addEntity("ContentItemDetailTable");
		
		//add pk.
		ContentItemDetailTable.addIntProperty("CmsID").primaryKey().notNull();
		
		//add non pk fields.
		ContentItemDetailTable.addStringProperty("DisplayTimeStamp").notNull();
		ContentItemDetailTable.addBooleanProperty("Flag").notNull();
		ContentItemDetailTable.addStringProperty("Title").notNull();
		ContentItemDetailTable.addStringProperty("FullTitle").notNull();
		ContentItemDetailTable.addStringProperty("SubTitle").notNull();
		ContentItemDetailTable.addStringProperty("Description").notNull();
		ContentItemDetailTable.addIntProperty("VideoLength").notNull();
		ContentItemDetailTable.addBooleanProperty("UsingPlaceholderImg").notNull();
		ContentItemDetailTable.addBooleanProperty("USWorldTarget").notNull();
		ContentItemDetailTable.addStringProperty("ContentSectionName").notNull();
		ContentItemDetailTable.addStringProperty("ContentSectionNameCss").notNull();
		ContentItemDetailTable.addStringProperty("ContentSubSectionName").notNull();
		ContentItemDetailTable.addStringProperty("ContentSubSectionNameCss").notNull();

		//return entity
		return ContentItemDetailTable;
	}
	
	/*
	 * this is the main content item that will contain most of the relevant data.
	 * will have relationship with content item lead media table. 1:1
	 * will have relationship with content item media table. 1:1
	 * will have relationship with content item detail table. 1:1 
	 * will have relationship with related items table. 1:n
	 * will have relationship with gallery content table. 1:1
	 * 
	 */
	public static void createContentItemsTable
	(
	  Schema schema,
	  Entity contentItemMediaLead,
	  Entity contentItemMedia, 
	  Entity contentItemDetail, 
	  Entity galleryTable,
	  Entity relatedItems
	)
	{
		//table name
		Entity ContentItemsTable = schema.addEntity("ContentItemsTable");
		
		//add pk
		ContentItemsTable.addIntProperty("CmsID").primaryKey().notNull();
		
		//add non pk fields.
		ContentItemsTable.addStringProperty("ContentType").notNull();
		ContentItemsTable.addBooleanProperty("Sponsored").notNull();
		ContentItemsTable.addStringProperty("ShareUrl").notNull();
		ContentItemsTable.addStringProperty("Link").notNull();
		ContentItemsTable.addStringProperty("Guid").notNull();
		ContentItemsTable.addStringProperty("PubDate").notNull();
		ContentItemsTable.addStringProperty("PubDisplayDate").notNull();
		ContentItemsTable.addStringProperty("SlugKeyword").notNull();
		ContentItemsTable.addStringProperty("ContentTargetPath").notNull();
		
		//TODO: make all the table relationships here...
		
	}
	
/**
 * this will use the schema to create entity and contain the appropriate 
 * properties for the entity. the entity will also contain the DB relationships as needed.
 */
	
}
