package com.util.nbc_data_layer;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;

/**
 * this class will have different parsing schemes for each type of content data
 * received from the caller. in this class we only handle JSON format.
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
	protected void parseAndStoreContentData(String inputString, SqliteDBAbstractIface dbIface) throws Exception
	{
		//root obj for the json tree.
		JSONObject obj = new JSONObject(inputString);
		
//1. metadata json obj.
		JSONObject metadata = obj.getJSONObject("metadata");
		
		//get fields from meta data obj.
		String metadata_shareURL = metadata.getString("shareURL");//DONE
		String metadata_leadMediaThumbnail = metadata.getString("leadMediaThumbnail");//DONE
		String metadata_leadMediaType = metadata.getString("leadMediaType");//DONE
		
		int metadata_videoLength = metadata.optInt("videoLength");//DONE
		
		String metadata_slugKeyword = metadata.getString("slugKeyword");//DONE
		String metadata_leadEmbeddedVideo = metadata.getString("leadEmbeddedVideo");//DONE
		String metadata_contentType = metadata.getString("contentType");//DONE
		boolean metadata_sponsored = metadata.optBoolean("sponsored");//DONE
		String metadata_leadMediaExtID = metadata.getString("leadMediaExtID");//DONE
		int metadata_contentId = metadata.optInt("contentId");//DONE
		
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
		int mediaThumbnail_width = mediaThumbnail.optInt("width");//DONE
		int mediaThumbnail_height = mediaThumbnail.optInt("height");//DONE
				
//4. media content
		JSONArray mediaContent = obj.getJSONArray("mediaContent");//get array for this item
		JSONObject obj_in_mediaContent = mediaContent.getJSONObject(0);//pull first object from json array.
		
		//get fields from media content obj.
		String obj_in_mediaContent_url = obj_in_mediaContent.getString("url");
		//String obj_in_mediaContent_medium = obj_in_mediaContent.getString("medium");
		//String obj_in_mediaContent_type = obj_in_mediaContent.getString("type");
		//String obj_in_mediaContent_mediaTitle = obj_in_mediaContent.getString("mediaTitle");
		
//5 overall obj
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
				
		//create pojo for cnt lead media table
		ContentItemLeadMediaTable cnt_lead_media_table_bean = new ContentItemLeadMediaTable();
		cnt_lead_media_table_bean.setCmsID(metadata_contentId);
		cnt_lead_media_table_bean.setLeadEmbeddedVideo(metadata_leadEmbeddedVideo);
		cnt_lead_media_table_bean.setLeadMediaContentType(metadata_leadMediaType);
		cnt_lead_media_table_bean.setLeadMediaExtID(metadata_leadMediaExtID);
		cnt_lead_media_table_bean.setLeadMediaThumbnail(metadata_leadMediaThumbnail);
		
		//create pojo for cnt media table
		ContentItemMediaTable cnt_media_table_bean = new ContentItemMediaTable();
		cnt_media_table_bean.setCmsID(metadata_contentId);
		cnt_media_table_bean.setHeight(mediaThumbnail_height);
		cnt_media_table_bean.setImageCredit(detailContentFields_imageCredit);
		cnt_media_table_bean.setPhotoThumbnail(photoThumbnail);
		cnt_media_table_bean.setThumbnail(detailContentFields_thumbnailUrl);
		cnt_media_table_bean.setUrl(mediaThumbnail_url);
		cnt_media_table_bean.setWidth(mediaThumbnail_width);
		
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
				
		//create pojo for cnt items table
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

		//check what type the session obj if and cast accordingly.
		if(dbIface.getSessionType() == SqliteDBAbstractIface.T_Session_Type.E_GREEN_DAO)
		{
			//cast to green dao session obj.
			DaoSession daoSession = (DaoSession)dbIface.getDBSession();
			
			//update the foreign key associations for the content item table entry.
			//save the update via there respective dao. update the content item obj new sub bean obj.
			daoSession.getContentItemLeadMediaTableDao().insertOrReplace(cnt_lead_media_table_bean);
			content_items_table_bean.setContentItemLeadMediaTable(cnt_lead_media_table_bean);
			
			daoSession.getContentItemMediaTableDao().insertOrReplace(cnt_media_table_bean);
			content_items_table_bean.setContentItemMediaTable(cnt_media_table_bean);
			
			daoSession.getContentItemDetailTableDao().insertOrReplace(cnt_item_detail_table_bean);
			content_items_table_bean.setContentItemDetailTable(cnt_item_detail_table_bean);		
			
			//update content item obj.
			daoSession.getContentItemsTableDao().insertOrReplace(content_items_table_bean);
			
			//TODO: still need to find the correct filename location based on the id of the url..
			//need to keep track of the actual filename.			
		}
	}
	
	/*
	 * this will take in a json string and parse out the related items of the
	 * string. this related item data generally belongs to article content.
	 * json structure is
	 * json_obj{ json_array[ json_obj{}, json_obj{}...etc ] }
	 */
	@Override
	protected void parseAndStoreRelatedItemsData(String inputString, SqliteDBAbstractIface dbIface) throws Exception
	{
		//create json obj from input string. this is the root of the json data tree.
		JSONObject obj = new JSONObject(inputString);
		
		//get the content id associated to this json obj.
		//this id is most likely an artcle content type.
		int contentId = obj.getInt("contentId"); 
		
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
			String typeName = json_obj.getString("typeName");
			String id = json_obj.getString("id");
			String title = json_obj.getString("title");
			String source = json_obj.getString("source");
			String mobileThumbnailUrl = json_obj.getString("mobileThumbnailUrl");
			String storyThumbnailUrl = json_obj.getString("storyThumbnailUrl");
			boolean sponsored = json_obj.getBoolean("sponsored");
			String url = json_obj.getString("url");
			String typeID = json_obj.getString("typeID");
			
			//TODO: create obj that holds this data.
			//will need to be array list of (POJO-java_bean or hashmap objs).
		}
	}
	
	/*
	 * this will take in a json string and parse the needed fields for the gallery data as a content item.
	 * the data layout of this content items is
	 * json array[ json_objs{},..etc]
	 *    
	 */
	@Override
	protected void parseAndStoreGalleryContentData(String inputString, SqliteDBAbstractIface dbIface)throws Exception
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
			String imageHeight = obj.getString("imageHeight");
			String index = obj.getString("index");
			String imagePath = obj.getString("imagePath");
			String imageCaption = obj.getString("imageCaption");
			String imageCredit = obj.getString("imageCredit");
			String imageWidth = obj.getString("imageWidth");
			
			//TODO: create obj that holds this data.
			//will need to be array list of (POJO-java_bean or hashmap objs).
		}
	}
}
