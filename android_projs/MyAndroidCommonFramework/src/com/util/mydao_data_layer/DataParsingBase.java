package com.util.mydao_data_layer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.util.Log;

/**
 * this class will perform the consumption of data from an input stream.
 * it will delegate to an overriding class to perform the specific parsing scheme.  
 *  
 * Author J.Mena
 */
public abstract class DataParsingBase 
{
	//use for logging.
	private String FILETAGNAME = "DataParsingBase";
	
	//enum type that allows for the same error handling when parsing 
	//data from the input stream.
	public enum T_BasicContentTypes
	{
		E_CONTENT_ITEM_TYPE,
		E_RELATED_ITEM_TYPE,
		E_GALLERY_ITEM_TYPE
	}
	
	//constructor
	public DataParsingBase()
	{
		
	}
	
	/*
	 * this method will take in an input stream and read data from it at a fixed size 1kb for now.
	 * it will return a byte_array_output_stream obj, so the caller can do with it as they please.
	 * this will not close the input stream. the caller needs to do this!!!
	 * 
	 * return valid byte_array_outstream obj or NULL.
	 */
	public ByteArrayOutputStream readDataFromInputStream(InputStream inputStream)
	{
		ByteArrayOutputStream out_buff = null;
		
		try
		{
			//create a byte array buffer to read in data from the input stream
			int buff_len = 1024;//1 KB size buff limit
			byte [] buff = new byte[buff_len];
			
			//create final buffer that will hold read data from the input stream. 
			out_buff = new ByteArrayOutputStream();
			
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
		}
		catch(Exception e)
		{
			Log.d(FILETAGNAME, e.getMessage());
		}
		finally
		{
			try
			{
				//close the input stream and the output stream.
				if(inputStream != null)
					inputStream.close();
				
				if(out_buff != null)
					out_buff.close();
			}
			catch(Exception e)
			{
				Log.d(FILETAGNAME, "JM...failed to close the streams after parsing..."+e.getMessage());
			}
		}
		
		//return the json obj reader back to the caller.
		return out_buff;
	}
	
	/*
	 * this method invoke an internal method via the type and invoke the specific
	 * processing dependent on the type. the error handling is the same... 
	 * 
	 */
	public void parseAndStoreDataType(String inputString, ParsingInputParams parsingInputParams, SqliteDBAbstractIface dbIface)
	{
		try
		{
			//switch on the specific types to handle accordingly.
			switch(parsingInputParams.getContentType())
			{
				case E_CONTENT_ITEM_TYPE:
					parseAndStoreContentData(inputString, parsingInputParams, dbIface);
					break;
				
				case E_RELATED_ITEM_TYPE:
					parseAndStoreRelatedItemsData(inputString, parsingInputParams, dbIface);
					break;
					
				case E_GALLERY_ITEM_TYPE:					
					parseAndStoreGalleryContentData(inputString, parsingInputParams, dbIface);
					break;
			}
		}
		catch(Exception e)
		{
			Log.d(FILETAGNAME, "JM..common error handling for json errors...msg = "+e.getMessage());
		}
	}
	
	/*
	 * this will receive a url like this 
	 * and get the image dimensions and the filename from it. this method will return POJO to the caller
	 * that contains specifics about the image.
	 * 
	 */
    public ImgFileUrlSpecs parseUrlString(String urlInput, long defaultWidth, long defaultHeight)
	{    	
    	String fwd_slash = "/";
    	
    	//preset with and height with something valid.
    	long width = defaultWidth;
		long height = defaultHeight;
		String filename = null;
    	
    	//for now valid url...
		if(urlInput.indexOf("http://") > -1 )
		{
			//get last forward slash from the end of the string...
			//then get the filename from there.
			int end_fwd_slash_idx = urlInput.lastIndexOf(fwd_slash);
			
			if( end_fwd_slash_idx > -1)
			{
				//get the filename from the next char after the fwd slash.
				filename = urlInput.substring(end_fwd_slash_idx+1);
				
				//get index of the enclosing slash from the dimensions
				int next_fwd_slash = urlInput.lastIndexOf(fwd_slash, end_fwd_slash_idx-1);
				
				if(next_fwd_slash > -1)
				{
					//get the dimension string from url
					String dimension_of_img = urlInput.substring(next_fwd_slash+1,end_fwd_slash_idx);
					
					//split based on * to separate the width and height.
					//need to escape the * with \\* for regex to use the * as a split char.
					String [] img_dimen = dimension_of_img.split("\\*");
					
					if(img_dimen.length == 2)
					{
						try
						{
							width = Long.parseLong(img_dimen[0]);
							height = Long.parseLong(img_dimen[1]);
						}
						catch(Exception e)
						{
							Log.d(FILETAGNAME,"JM... use the defaults now...error = "+e.getMessage());
						}
					}
				}
			}
		}
		
		Log.d(FILETAGNAME,"fname = "+filename+", width = "+width+", height = "+height+",");
		
		return new ImgFileUrlSpecs(filename, width, height);
	}
	
	/*
	 * these methods are the entry point to define your own parsing scheme for data from the
	 * input string. each method handle a specific type of data from the input string. that can
	 * throw an exception if an error occurs.
	 */
	protected abstract void parseAndStoreContentData
	(String inputString, ParsingInputParams parsingInputParams, SqliteDBAbstractIface dbIface) throws Exception;
	
	protected abstract void parseAndStoreRelatedItemsData
	(String inputString, ParsingInputParams parsingInputParams, SqliteDBAbstractIface dbIface) throws Exception;
	
	protected abstract void parseAndStoreGalleryContentData
	(String inputString, ParsingInputParams parsingInputParams, SqliteDBAbstractIface dbIface) throws Exception;	
}
