package com.util.nbc_data_layer;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgFnameTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.RelatedItemsTable;

/**
 * this class will have different parsing schemes for each type of content data
 * received from the caller. in this class we only handle JSON format.
 * this class is specific to parsing the json data streams for content data, related items
 * gallery types...as well as the data insertion of the data to the data layer via green dao
 * by using the generic iface feature.
 * 
 * Author J.Mena
 *
 */
public class NBCDataParsingAsJson extends NBCDataParsingBase
{	
	private final String NBCDataParsingAsJsonTAG = "NBCDataParsingAsJson";

	/*
	 * this method will take in a content json data obj and parse the data from this content item
	 * this is a generic content obj type. handles all the content objs that can be displayed..
	 * things like, Article, Video_Release, etc.
	 */
	@Override
	protected void parseAndStoreContentData
	(String inputString, ParsingInputParams parsingInputParams, SqliteDBAbstractIface dbIface) throws Exception
	{
		//root obj for the json tree.
		JSONObject obj = new JSONObject(inputString);
		
//1. metadata json obj.
		JSONObject metadata = obj.getJSONObject("metadata");
		
		//get fields from meta data obj.
		String metadata_shareURL = metadata.getString("shareURL");//DONE
		String metadata_leadMediaThumbnail = metadata.getString("leadMediaThumbnail");//DONE
		String metadata_leadMediaType = metadata.getString("leadMediaType");//DONE
		
		long metadata_videoLength = metadata.optLong("videoLength");//DONE
		
		String metadata_slugKeyword = metadata.getString("slugKeyword");//DONE
		String metadata_leadEmbeddedVideo = metadata.getString("leadEmbeddedVideo");//DONE
		String metadata_contentType = metadata.getString("contentType");//DONE
		boolean metadata_sponsored = metadata.optBoolean("sponsored");//DONE
		String metadata_leadMediaExtID = metadata.getString("leadMediaExtID");//DONE
		long metadata_contentId = metadata.optLong("contentId");//DONE		
		
//2. detail content fields
		JSONObject detailContentFields = obj.getJSONObject("detailContentFields");
		
		//get fields from detail content fields
		String detailContentFields_contentTargetPath = detailContentFields.getString("contentTargetPath");//DONE
		String detailContentFields_displayTimestamp = detailContentFields.getString("displayTimestamp");//DONE
		String detailContentFields_thumbnailUrl = detailContentFields.getString("thumbnailUrl");//DONE
		String detailContentFields_contentTypeCssName = detailContentFields.getString("contentTypeCssName");//DONE
		String detailContentFields_contentSectionName = detailContentFields.getString("contentSectionName");//DONE
		String detailContentFields_contentSubsectionName = detailContentFields.getString("contentSubsectionName");//DONE
		String detailContentFields_contentSubsectionCssName = detailContentFields.getString("contentSubsectionCssName");//DONE
		//String detailContentFields_thumbMarginLeft = detailContentFields.getString("thumbMarginLeft");
		String detailContentFields_imageCredit = detailContentFields.getString("imageCredit");//DONE
		boolean detailContentFields_flag = detailContentFields.optBoolean("flag");//DONE
		boolean detailContentFields_usingPlaceholderImg = detailContentFields.optBoolean("usingPlaceholderImg");//DONE
		boolean detailContentFields_usWorldTarget = detailContentFields.optBoolean("usWorldTarget");//DONE
		
//3. media thumbnail
		JSONObject mediaThumbnail = obj.getJSONObject("mediaThumbnail");
		
		//get fields from media thumnail obj.
		String mediaThumbnail_url = mediaThumbnail.getString("url");//DONE
		long mediaThumbnail_width = mediaThumbnail.optLong("width");//DONE
		long mediaThumbnail_height = mediaThumbnail.optLong("height");//DONE
				
//4. media content
		JSONArray mediaContent = obj.getJSONArray("mediaContent");//get array for this item
		JSONObject obj_in_mediaContent = mediaContent.getJSONObject(0);//pull first object from json array.
		
		//get fields from media content obj.
		String obj_in_mediaContent_url = obj_in_mediaContent.getString("url");
		//String obj_in_mediaContent_medium = obj_in_mediaContent.getString("medium");
		//String obj_in_mediaContent_type = obj_in_mediaContent.getString("type");
		//String obj_in_mediaContent_mediaTitle = obj_in_mediaContent.getString("mediaTitle");
		
//5. overall obj
		String title = obj.getString("title");//DONE
		String fullTitle = obj.getString("fullTitle");//DONE
		String subTitle = obj.getString("subTitle");//DONE
		String link = obj.getString("link");//DONE
		String guid = obj.getString("guid");//abs path to the content item. //DONE
		String pubDate = obj.getString("pubDate");//DONE
		String pubDateDisplay = obj.getString("pubDateDisplay");//DONE		
		//JSONArray customCategory_array = obj.getJSONArray("customCategory");
		String description = obj.getString("description");//DONE
		String photoThumbnail = obj.getString("photoThumbnail");//DONE
		
		Log.d(NBCDataParsingAsJsonTAG, "JM...finished parsing nbc content item data.");

//create pojo for cnt lead media table
		ContentItemLeadMediaTable cnt_lead_media_table_bean = new ContentItemLeadMediaTable();
		cnt_lead_media_table_bean.setCmsID(metadata_contentId);
		cnt_lead_media_table_bean.setLeadMediaContentType(metadata_leadMediaType);
		cnt_lead_media_table_bean.setLeadMediaExtID(metadata_leadMediaExtID);
		cnt_lead_media_table_bean.setLeadEmbeddedVideo(metadata_leadEmbeddedVideo);
		cnt_lead_media_table_bean.setLeadMediaThumbnailType
			(NBCDataBaseHelper.T_UrlTypeToId.E_LEAD_MEDIA_THUMBNAIL_URL_TYPE.getUrlTypeID());
		
		//perform the urlstring to table associations. this all gets saved in the entity obj type.
		dbIface.peformUrlStringToTableAssociations
		(
		 metadata_leadMediaThumbnail,
		 null,
		 metadata_contentId, 
		 NBCDataBaseHelper.T_UrlTypeToId.E_LEAD_MEDIA_THUMBNAIL_URL_TYPE, 
		 cnt_lead_media_table_bean, 
		 this
		);
//create pojo for cnt lead media table		

//create pojo for cnt media table
		ContentItemMediaTable cnt_media_table_bean = new ContentItemMediaTable();
		cnt_media_table_bean.setCmsID(metadata_contentId);
		
		cnt_media_table_bean.setMediaUrlType(NBCDataBaseHelper.T_UrlTypeToId.E_MEDIA_URL_TYPE.getUrlTypeID());		
		//TODO: finish this here also.
		//wont need the height and width since i get that from the url of the image file.
		//cnt_media_table_bean.setUrl(mediaThumbnail_url);
		//cnt_media_table_bean.setHeight(mediaThumbnail_height);
		//cnt_media_table_bean.setWidth(mediaThumbnail_width);
		dbIface.peformUrlStringToTableAssociations
		(
		 mediaThumbnail_url,
		 null,
		 metadata_contentId, 
		 NBCDataBaseHelper.T_UrlTypeToId.E_MEDIA_URL_TYPE, 
		 cnt_media_table_bean, 
		 this
		);
				
		cnt_media_table_bean.setMediaPhotoThumbnailUrlType(NBCDataBaseHelper.T_UrlTypeToId.E_MEDIA_PHOTO_THUMBNAIL_URL_TYPE.getUrlTypeID());		
		//these were all replaced with url types
		//cnt_media_table_bean.setPhotoThumbnail(photoThumbnail);
		dbIface.peformUrlStringToTableAssociations
		(
		 photoThumbnail,
		 null,
		 metadata_contentId, 
		 NBCDataBaseHelper.T_UrlTypeToId.E_MEDIA_PHOTO_THUMBNAIL_URL_TYPE,
		 cnt_media_table_bean, 
		 this
		);

		//cnt_media_table_bean.setImageCredit(detailContentFields_imageCredit);
		//cnt_media_table_bean.setThumbnail(detailContentFields_thumbnailUrl);
		//create img details obj since we have some from the main json content obj.
		ImgFileDetails img_details = new ImgFileDetails(detailContentFields_imageCredit,"");
		cnt_media_table_bean.setMediaThumbnailUrlType(NBCDataBaseHelper.T_UrlTypeToId.E_MEDIA_THUMBNAIL_URL_TYPE.getUrlTypeID());		
		dbIface.peformUrlStringToTableAssociations
		(
		 detailContentFields_thumbnailUrl,
		 img_details,
		 metadata_contentId, 
		 NBCDataBaseHelper.T_UrlTypeToId.E_MEDIA_THUMBNAIL_URL_TYPE, 
		 cnt_media_table_bean, 
		 this
		);
//create pojo for cnt media table		

//create pojo for cnt detail table
		ContentItemDetailTable cnt_item_detail_table_bean = new ContentItemDetailTable();
		cnt_item_detail_table_bean.setCmsID(metadata_contentId);
		cnt_item_detail_table_bean.setContentSectionName(detailContentFields_contentSectionName);
		cnt_item_detail_table_bean.setContentSectionNameCss(detailContentFields_contentTypeCssName);
		cnt_item_detail_table_bean.setContentSubSectionName(detailContentFields_contentSubsectionName);
		cnt_item_detail_table_bean.setContentSubSectionNameCss(detailContentFields_contentSubsectionCssName);
		cnt_item_detail_table_bean.setDescription(description);
		cnt_item_detail_table_bean.setDisplayTimeStamp(detailContentFields_displayTimestamp);
		cnt_item_detail_table_bean.setFlag(detailContentFields_flag);
		cnt_item_detail_table_bean.setFullTitle(fullTitle);
		cnt_item_detail_table_bean.setSubTitle(subTitle);
		cnt_item_detail_table_bean.setTitle(title);
		cnt_item_detail_table_bean.setUsingPlaceholderImg(detailContentFields_usingPlaceholderImg);
		cnt_item_detail_table_bean.setUSWorldTarget(detailContentFields_usWorldTarget);
		cnt_item_detail_table_bean.setVideoLength(metadata_videoLength);
//create pojo for cnt detail table		
				
//create pojo for cnt item table
		ContentItemsTable content_items_table_bean = new ContentItemsTable();
		content_items_table_bean.setCmsID(metadata_contentId);
		content_items_table_bean.setContentTargetPath(detailContentFields_contentTargetPath);
		content_items_table_bean.setContentType(metadata_contentType);
		content_items_table_bean.setGuid(guid);
		content_items_table_bean.setLink(link);
		content_items_table_bean.setPubDate(pubDate);
		content_items_table_bean.setPubDisplayDate(pubDateDisplay);
		content_items_table_bean.setShareUrl(metadata_shareURL);
		content_items_table_bean.setSlugKeyword(metadata_slugKeyword);
		content_items_table_bean.setSponsored(metadata_sponsored);
//create pojo for cnt items table

		//perform the necessary table associations and updated for the beans
		//that are for the content item and its specific detail types.
		dbIface.contentItemTableAssociationProcessing
		(content_items_table_bean, cnt_item_detail_table_bean, cnt_media_table_bean, cnt_lead_media_table_bean);
	}
	
	/*
	 * this will take in a json string and parse out the related items of the
	 * string. this related item data generally belongs to article content.
	 * json structure is
	 * json_obj{ json_array[ json_obj{}, json_obj{}...etc ] }
	 */
	@Override
	protected void parseAndStoreRelatedItemsData
	(String inputString, ParsingInputParams parsingInputParams, SqliteDBAbstractIface dbIface) throws Exception
	{
		//create json obj from input string. this is the root of the json data tree.
		JSONObject obj = new JSONObject(inputString);
		
		//get meta data portion of the input
		JSONObject meta_data = obj.getJSONObject("metadata");
		
		//get the content id associated to this json obj.
		//this id is most likely an article content type.
		long parentCmsId = meta_data.optLong("contentId"); 
		
		//get json array from json obj.
		JSONArray json_array = obj.getJSONArray("relatedContent");
		
		//get the len of the json array
		int len = json_array.length();
		
		//loop over all the item in the json array
		for(int i = 0; i < len; i++)
		{
			//get the json obj at the index in the json array. 
			JSONObject json_obj = json_array.getJSONObject(i);
			
			//get the data for each json obj at the specific json array index.
			String contentType = json_obj.getString("typeName");
			long relCmsId = json_obj.optLong("id");
			String title = json_obj.getString("title");
			String source = json_obj.getString("source");
			boolean sponsored = json_obj.optBoolean("sponsored");
			String url = json_obj.getString("url");
			long typeID = json_obj.optLong("typeID");
			
			//handle with img url table management.
			String mobileThumbnailUrl = json_obj.getString("mobileThumbnailUrl");
			String storyThumbnailUrl = json_obj.getString("storyThumbnailUrl");
			//end of the parsing of the json string.
						
		//create pojo for related items table
			RelatedItemsTable related_items_table_bean = new RelatedItemsTable();
			related_items_table_bean.setParentCmsID(parentCmsId);
			related_items_table_bean.setContentType(contentType);
			related_items_table_bean.setRelCmsID(relCmsId);
			related_items_table_bean.setTitle(title);
			related_items_table_bean.setSource(source);
			related_items_table_bean.setSponsored(sponsored);
			related_items_table_bean.setSharingUrl(url);
			related_items_table_bean.setTypeID(typeID);
			
			related_items_table_bean.setRelItemMobileThumbnailUrlType
			(NBCDataBaseHelper.T_UrlTypeToId.E_REL_ITEM_MOBILE_THUMBNAIL_URL_TYPE.getUrlTypeID());
			
			//perform the urlstring to table associations. this all gets saved in the entity obj type.
			//dont have any details about any of the images.
			dbIface.peformUrlStringToTableAssociations
			(
			 mobileThumbnailUrl,
			 null,
			 relCmsId, 
			 NBCDataBaseHelper.T_UrlTypeToId.E_REL_ITEM_MOBILE_THUMBNAIL_URL_TYPE, 
			 related_items_table_bean, 
			 this
			);
			
			related_items_table_bean.setRelItemMobileThumbnailUrlType
			(NBCDataBaseHelper.T_UrlTypeToId.E_REL_ITEM_STORY_THUMBNAIL_URL_TYPE.getUrlTypeID());
			
			//perform the urlstring to table associations. this all gets saved in the entity obj type.
			//dont have any details about any of the images.
			dbIface.peformUrlStringToTableAssociations
			(
			 storyThumbnailUrl,
			 null,
			 relCmsId, 
			 NBCDataBaseHelper.T_UrlTypeToId.E_REL_ITEM_STORY_THUMBNAIL_URL_TYPE, 
			 related_items_table_bean, 
			 this
			);
			
			//add the related content to the DB layer here.
			dbIface.relatedItemsTableAssociationProcessing(related_items_table_bean);
		//create pojo for related items table
		}//for loop
	}
	
	/*
	 * this will take in a json string and parse the needed fields for the gallery data as a content item.
	 * the data layout of this content items is
	 * json array[ json_objs{},..etc]
	 *    
	 */
	@Override
	protected void parseAndStoreGalleryContentData
	(String inputString, ParsingInputParams parsingInputParams, SqliteDBAbstractIface dbIface) throws Exception
	{
		//create json array from json string. this the root for gallery content item.
		JSONArray json_array_gallery = new JSONArray(inputString);
		
		//get len of json array. 
		int len = json_array_gallery.length();
		
		//loop over the entire json array and get the data from each json obj in the array
		for(int i = 0; i < len; i++)
		{
			//get the json obj from the json array via the index.
			JSONObject obj = json_array_gallery.getJSONObject(i);
			
			//gather data from json obj at index "i".
			long imageHeight = obj.optLong("imageHeight");//not needed, will get from url
			long index = obj.optLong("index");
			//assume width,height is in the url, of not then take from here...???
			String imagePath = obj.getString("imagePath");
			
			//this is the details of an image file.
			String imageCaption = obj.getString("imageCaption");
			String imageCredit = obj.getString("imageCredit");
			
			long imageWidth = obj.optLong("imageWidth");//not needed, will get from url
			//this is the end of the parsing code...
			
		//create pojo for gallery content table.
			GalleryContentTable gallery_content_table = new GalleryContentTable();
			
			long cms_id = parsingInputParams.getCmsId();
			gallery_content_table.setGalCmsID(cms_id);
			gallery_content_table.setImgIndex(index);
			
			//img file details obj.
			ImgFileDetails img_details = new ImgFileDetails(imageCredit,imageCaption);
			
			//parse the url specs via the img path.
			ImgFileUrlSpecs img_file_specs = this.parseUrlString(imagePath, imageWidth, imageHeight);
			
			//perform the img file processing for the input data from the json str for this item. 
			ImgFnameTable img_fname_entity = 
					(ImgFnameTable)dbIface.imgTableProcessing(img_file_specs, img_details, imagePath);
			
			//make the table relationships now...
			gallery_content_table.setImgFnameID(img_fname_entity.getId());
			gallery_content_table.setImgFnameTable(img_fname_entity);
			
			//add the gallery content to the DB layer here.
			dbIface.galleryTableAssociationProcessing(gallery_content_table);
		//create pojo for gallery content table.
		}//for loop
	}
}
