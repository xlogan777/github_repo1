package com.util.nbc_common_framework;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this class will have different parsing schemes for each type of content data
 * received from the caller. in this class we only handle JSON format.
 * 
 * Author J.Mena
 *
 */
public class NBCDataParsingAsJson extends NBCDataParsingBase
{	
	/*
	 * this method will take in a content json data obj and parse the data from this content item
	 * this is a generic content obj type. handles all the content objs that can be displayed..
	 * things like, Article, Video_Release, etc.
	 */
	@Override
	protected void parseContentData(String inputString) throws Exception
	{
		//root obj for the json tree.
		JSONObject obj = new JSONObject(inputString);
		
//1. metadata json obj.
		JSONObject metadata = obj.getJSONObject("metadata");
		
		//get fields from meta data obj.
		String metadata_shareURL = metadata.getString("shareURL");
		String metadata_leadMediaThumbnail = metadata.getString("leadMediaThumbnail");
		String metadata_leadMediaType = metadata.getString("leadMediaType");
		String metadata_videoLength = metadata.getString("videoLength");
		String metadata_slugKeyword = metadata.getString("slugKeyword");
		String metadata_leadEmbeddedVideo = metadata.getString("leadEmbeddedVideo");
		String metadata_contentType = metadata.getString("contentType");
		String metadata_sponsored = metadata.getString("sponsored");
		String metadata_leadMediaExtID = metadata.getString("leadMediaExtID");
		int metadata_contentId = metadata.getInt("contentId");
		
//2. detail content fields
		JSONObject detailContentFields = obj.getJSONObject("detailContentFields");
		
		//get fields from detail content fields
		String detailContentFields_contentTargetPath = detailContentFields.getString("contentTargetPath");
		//String detailContentFields_displayTimestamp = detailContentFields.getString("displayTimestamp");
		String detailContentFields_thumbnailUrl = detailContentFields.getString("thumbnailUrl");
		String detailContentFields_contentTypeCssName = detailContentFields.getString("contentTypeCssName");
		//String detailContentFields_contentSectionName = detailContentFields.getString("contentSectionName");
		String detailContentFields_contentSubsectionName = detailContentFields.getString("contentSubsectionName");
		//String detailContentFields_contentSubsectionCssName = detailContentFields.getString("contentSubsectionCssName");
		//String detailContentFields_thumbMarginLeft = detailContentFields.getString("thumbMarginLeft");
		String detailContentFields_imageCredit = detailContentFields.getString("imageCredit");
		String detailContentFields_flag = detailContentFields.getString("flag");
		//String detailContentFields_usingPlaceholderImg = detailContentFields.getString("usingPlaceholderImg");
		//String detailContentFields_usWorldTarget = detailContentFields.getString("usWorldTarget");
		
//3. media thumbnail
		JSONObject mediaThumbnail = obj.getJSONObject("mediaThumbnail");
		
		//get fields from media thumnail obj.
		String mediaThumbnail_url = mediaThumbnail.getString("url");
		String mediaThumbnail_width = mediaThumbnail.getString("width");
		String mediaThumbnail_height = mediaThumbnail.getString("height");
				
//4. media content
		JSONArray mediaContent = obj.getJSONArray("mediaContent");//get array for this item
		JSONObject obj_in_mediaContent = mediaContent.getJSONObject(0);//pull first object from json array.
		
		//get fields from media content obj.
		String obj_in_mediaContent_url = obj_in_mediaContent.getString("url");
		//String obj_in_mediaContent_medium = obj_in_mediaContent.getString("medium");
		//String obj_in_mediaContent_type = obj_in_mediaContent.getString("type");
		//String obj_in_mediaContent_mediaTitle = obj_in_mediaContent.getString("mediaTitle");
		
//5 overall obj
		String title = obj.getString("title");
		String fullTitle = obj.getString("fullTitle");
		String subTitle = obj.getString("subTitle");
		String link = obj.getString("link");
		//String guid = obj.getString("guid");
		String pubDate = obj.getString("pubDate");
		String pubDateDisplay = obj.getString("pubDateDisplay");		
		//JSONArray customCategory_array = obj.getJSONArray("customCategory");
		String description = obj.getString("description");
		String photoThumbnail = obj.getString("customCategory");
		
				
		//TODO: create obj that holds this data.
		//will need to be array list of (POJO-java_bean or hashmap objs).
	}
	
	/*
	 * this will take in a json string and parse out the related items of the
	 * string. this related item data generally belongs to article content.
	 * json structure is
	 * json_obj{ json_array[ json_obj{}, json_obj{}...etc ] }
	 */
	@Override
	protected void parseRelatedItemsData(String inputString) throws Exception
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
	protected void parseGalleryContentData(String inputString)throws Exception
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
