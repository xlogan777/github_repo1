package com.test1;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * this class will save the spring application context as a static reference
 * to be invoked by any client in this micro service.
 * this class was tagged with a @Component annotation to single that it is a 
 * candidate for auto detection classpath scanning. this class is implementing the
 * ApplicationContextAware interface to allow to save the app context.
 * 
 * @author Menaj
 * @version 1.0
 */
@Component
public class DemoAppContext implements ApplicationContextAware
{
   private static Logger log = Logger.getLogger(DemoAppContext.class.getName());
   
   //static reference for app context to get beans dynamically.
   private static ApplicationContext context;
   
   /**
    * constructor
    */
   public DemoAppContext()
   {
      
   }

   /* (non-Javadoc)
    * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
    */
   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
   {
      log.info("save app context into static vars for this class = "+applicationContext);
      context = applicationContext;
   }

   /**
    * returns the app context obj.
    * 
    * @return app context obj
    */
   public static ApplicationContext getContext()
   {
      return context;
   }
}
