package com.util.nbc_data_layer;

/**
 * this class will save away information from the url for an image file in 
 * simple to use sections. like width, height..etc.
 * this class will be an immutable obj. This is a POJO.
 * 
 * Author: J. Mena
 *
 */
public class ImgFileUrlSpecs 
{
	private final String fname;
	private final long width;
	private final long height;
	
	/*
	 * constructor
	 */
	public ImgFileUrlSpecs(String fname, long width, long height)
	{
		this.fname = fname;
		this.width = width;
		this.height = height;
	}
	
	/*
	 * return the image filename.
	 */	
	public String getFname()
	{
		return this.fname;
	}
	
	/*
	 * return the width dimension of the image
	 */
	public long getWidth()
	{
		return this.width;
	}
	
	/*
	 * return the height dimension of the image
	 */
	public long getHeight()
	{
		return this.height;
	}
}
