package com.java_lang_exp;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/*
 * this class deals with JSONP api for java.
 */
public class JsonP_Example 
{
	static final Logger logger = 
			LogManager.getLogger(MainJavaLangExp.class.getName());
	
	/*
	 * function to parse a json file and print it to std out.
	 */
	public void parser() throws Exception
	{
		logger.debug("read json");
		 
		 JsonReader rdr = Json.createReader(new FileReader("test1.json"));
		 JsonObject obj = rdr.readObject();
		 String fname = obj.getString("fname");
		 String lname = obj.getString("lname");
		 String id = obj.getString("id");
		 
		 logger.debug(fname+" "+lname+" "+id);
		 		 
		 JsonArray json_array = obj.getJsonArray("friendlist");
		 		 
		 //for (JsonObject result : results.getValuesAs(JsonObject.class))
		 for(int i = 0; i < json_array.size(); i++ )
		 {
			 String names = json_array.getJsonObject(i).getString("name"); 
			 logger.debug(names);
		 }
	}
	
	public int adding(int x, int y)
	{
		return (x+y);
	}
}
