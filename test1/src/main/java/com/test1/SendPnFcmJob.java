package com.test1;

import org.apache.log4j.Logger;

import java.util.Date;


public class SendPnFcmJob
{
   private static Logger log = Logger.getLogger(SendPnFcmJob.class.getName());
   
   //default constructor
   public SendPnFcmJob()
   {
      
   }
   
   public void processPushNotificationsToFcm()
   {
      log.error("SendPnFcmJob -> processPushNotificationsToFcm()"+new Date());
   }
}
