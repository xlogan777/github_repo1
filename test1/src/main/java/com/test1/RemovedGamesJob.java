package com.test1;

import java.util.Date;

import org.apache.log4j.Logger;

public class RemovedGamesJob
{
   private static Logger log = Logger.getLogger(RemovedGamesJob.class.getName());
   
   //default constructor
   public RemovedGamesJob()
   {
      
   }

   public void removeCharadesGames() 
   {
      log.error("RemovedGamesJob -> removeCharadesGames()"+new Date());
   }
}
