package com.util.nbc_common_framework;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.util.Log;

/**
 * this class will perform the consumption of data from an input stream.
 * it will delegate to an overriding class to perform the specific parsing scheme.  
 *  
 * Author J.Mena
 */
public abstract class NBCDataParsingBase 
{
	//use for logging.
	private String FILETAGNAME = "NBCDataParsingBase";
	
	//enum type that allows for the same error handling when parsing 
	//data from the input stream.
	public enum BasicContentTypes
	{
		CONTENT_ITEM_TYPE,
		RELATED_ITEM_TYPE,
		GALLERY_ITEM_TYPE
	}
	
	//constructor
	public NBCDataParsingBase()
	{
		
	}
	
	/*
	 * this method will take in an input stream and read data from it.
	 * it will return a string as a byte stream to be parsed later.
	 * this will not close the input stream. the caller needs to do this!!!
	 * 
	 * return valid string obj or NULL.
	 */
	public String readDataFromInputStream(InputStream inputStream)
	{
		String outputString = null;
		
		try
		{
			//create a byte array buffer to read in data from the input stream
			int buff_len = 1024;//1 KB size buff limit
			byte [] buff = new byte[buff_len];
			
			//create final buffer that will hold read data from the input stream. 
			ByteArrayOutputStream out_buff = new ByteArrayOutputStream();
			
			//num bytes read.
			int data_read = 0;
			
			//loop to iterate over the input stream and save the data read from buffer 
			//to the out_put buffer using the temp array of buff to save the buff 
			//limit of data requested to be read.
			while( (data_read = inputStream.read(buff, 0, buff.length)) > 0)
			{
				//save from tmp buff to final output buffer the data read
				//and use data read to write that much only
				out_buff.write(buff, 0, data_read);
			}
			
			//create a string obj using the output buffer to return an
			//array of bytes to be converted to a string
			outputString = new String(out_buff.toByteArray());

			//set output buff to null
			out_buff =  null;
		}
		catch(Exception e)
		{
			Log.d(FILETAGNAME, e.getMessage());
		}
		
		//return the json obj reader back to the caller.
		return outputString;
	}
	
	/*
	 * this method invoke an internal method via the type and invoke the specific
	 * processing dependent on the type. the error handling is the same... 
	 * 
	 */
	public void parseDataType(String inputString, BasicContentTypes type)
	{
		try
		{
			//switch on the specific types to handle accordingly.
			switch(type)
			{
				case CONTENT_ITEM_TYPE:
					parseContentData(inputString);
					break;
				
				case RELATED_ITEM_TYPE:
					parseRelatedItemsData(inputString);
					break;
					
				case GALLERY_ITEM_TYPE:					
					parseGalleryContentData(inputString);
					break;
			}
		}
		catch(Exception e)
		{
			Log.d(FILETAGNAME, "JM..common error handling for json errors...msg = "+e.getMessage());
		}
	}
	
	/*
	 * these methods are the entry point to define your own parsing scheme for data from the
	 * input string. each method handle a specific type of data from the input string. that can
	 * throw an exception if an error occurs.
	 */
	protected abstract void parseContentData(String inputString) throws Exception;
	protected abstract void parseRelatedItemsData(String inputString) throws Exception;
	protected abstract void parseGalleryContentData(String inputString)throws Exception;
}
