package com.util.nbc_data_layer;

/**
 * 
 * this is a POJO class that keep the img file details of the img file.
 * this is an immutable obj.
 * 
 * author J.Mena
 *
 */
public class ImgFileDetails 
{
	private String credit = null;
	private String caption = null;
	
	//constructor
	public ImgFileDetails(String credit, String caption)
	{
		this.credit = credit;
		this.caption = caption;
	}
	
	/*
	 * return the credit text of this obj
	 */
	public String getCredit()
	{
		return this.credit;
	}
	
	/*
	 * return the caption text of this obj
	 */
	public String getCaption()
	{
		return this.caption;
	}
}
