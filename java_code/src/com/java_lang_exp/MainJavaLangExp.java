package com.java_lang_exp;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class MainJavaLangExp 
{
	//get instance of logger class for this obj.
	static final Logger logger = 
			LogManager.getLogger(MainJavaLangExp.class.getName());
	  
	public static void test0()
	{
		try
		{
			//allows u to programtically update the log level for root logger.
			/*LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
			Configuration config = ctx.getConfiguration();
			LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
			loggerConfig.setLevel(Level.ALL);
			ctx.updateLoggers();
			*/
			
			logger.debug("Hello this is an debug message");
			logger.info("Hello this is an info message");
			logger.error("Hello this is an error message");
			logger.info("Hello info msg");
			//many other logging levels.
		    
			JsonP_Example json_example1 = new JsonP_Example();
			json_example1.parser();
			
			logger.trace("Trace Message!");
		    logger.debug("Debug Message!");
		    logger.info("Info Message!");
		    logger.warn("Warn Message!");
		    logger.error("Error Message!");
		    logger.fatal("Fatal Message!");
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
