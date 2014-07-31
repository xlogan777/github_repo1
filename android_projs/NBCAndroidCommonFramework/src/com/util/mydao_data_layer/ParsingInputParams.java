package com.util.mydao_data_layer;

import com.util.mydao_data_layer.DataParsingBase.T_BasicContentTypes;

/**
 * this is an immutable obj that is used to pass params to the parsing code from the caller.
 * this object may grow...for now not needed to be a hashmap type of input since
 * we dont need to have the expense of a hashmap for param list.
 * 
 * Author J.Mena
 *
 */
public class ParsingInputParams 
{
	private final long cmsId;
	private final T_BasicContentTypes type;
	
	public ParsingInputParams(long cmsId, T_BasicContentTypes type)
	{
		this.cmsId = cmsId;
		this.type = type;
	}
	
	/*
	 * return the cms id
	 */
	public long getCmsId()
	{
		return this.cmsId;
	}
	
	/*
	 * return the content type
	 */
	public T_BasicContentTypes getContentType()
	{
		return this.type;
	}
}
