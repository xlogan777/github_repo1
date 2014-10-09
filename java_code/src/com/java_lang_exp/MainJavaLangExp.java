package com.java_lang_exp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainJavaLangExp 
{
	static final Logger logger = 
			LogManager.getLogger(MainJavaLangExp.class.getName());
	  
	public static void test0()
	{
		try
		{
			logger.debug("Hello this is an debug message");
			logger.info("Hello this is an info message");
			logger.error("Hello this is an error message");
		    
			//JsonP_Example json_example1 = new JsonP_Example();
			//json_example1.parser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		//test 0
		test0();
	}

}
