package com.test1;

// import com.etl.config.EtlConfig;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class DemoApplication 
{
	private static Logger log = Logger.getLogger(DemoApplication.class.getName());
	// public static void initLogger()
	// {
	//    boolean status = EtlConfig.log4JConfigExists();
 
	//    if (status == false)
	//    {
	// 	  throw new IllegalArgumentException("didnt correctly set this vm args ROOT_CONFIG_DIRECTORY..end now");
	//    }
 
	//    // log a statement.
	//    log.info("Logger initalized");
	// }   
 

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// //init logger
		// initLogger();
	}

}
