#!/usr/bin/python

#this file is used to deploy new tomcat instances with new nbc-feedloader.jar file
#need to have the jar file at the same level as where the this script file is.

#import specific functions from restart_tc.py python script.
from restart_tc import parseRestart_TC_Config;
from restart_tc import tcProcessing;
from restart_tc import killTCProcess;

import os;
import datetime;
import shutil;
import time;
 
#main()
def main():

    #save current directory...
    curr_dir = os.getcwd() + "/";
    
    #provide the parsing file to return a config obj.
    MyConfigObj = parseRestart_TC_Config("restart_tc_config.xml");
    
    #rename the log file since we want out own log file.
    MyConfigObj.logFileName = "deploy_nbc_fl_jar.log";
    
    #open log file.
    myLogFile = open(MyConfigObj.logFileName,"a");
    
    localtime1 = time.asctime(time.localtime(time.time()));
    myLogFile.write("------starting deployment------- at => "+localtime1+"\n");
        
    myLogFile.write("This is the curr dir = "+ curr_dir+"\n");
    
    #create empty list
    save_config_dirs = [];
    
    #add bin path...
    for idx in range(len(MyConfigObj.listOfTCDirs)):
        
        #save dir path before to new list
        save_config_dirs.append(MyConfigObj.listOfTCDirs[idx]);
        
        #append /bin to path
        MyConfigObj.listOfTCDirs[idx] += "/bin";
            
    #shutdown tc.
    tcProcessing(MyConfigObj.listOfTCDirs, myLogFile,"shutdown.sh",MyConfigObj.sleepTimeVal);
    
    #do a for kill if process hasn't died gracefully.
    killTCProcess(myLogFile, MyConfigObj);
    
    #loop over all the dirs
    for dir_path in save_config_dirs:
        
        #this is the path to jar files for feedloader app.
        path_to_jars = "/webapps/FeedLoaderWebApp/WEB-INF/lib/";
        concat_path = dir_path+path_to_jars;
        save_file_exits = False;
        nbc_fl_jar_exits = False;
        
        #name of nbc fl jar file
        #assume nbc-jar is in current dir.
        nbc_jar_file = "nbc-feed-loader.jar";
        
        #create file name for today's save file to be search for.
        today_date = datetime.date.today();#get todays date    
        nbc_jar_save_file = "nbc-feed-loader.jar.SAVE_"+str(today_date);   
    
        save_file_path = concat_path + nbc_jar_save_file;
        nbc_file_path = concat_path + nbc_jar_file;
        
        myLogFile.write(save_file_path + "\n");
        myLogFile.write(nbc_file_path + "\n");
        
        save_file_exits = os.path.exists(save_file_path);
        nbc_fl_jar_exits = os.path.exists(nbc_file_path);
        
        myLogFile.write("save_file_exits = "+
                         str(save_file_exits) +
                         ", nbc_fl_jar_exits = " +
                         str(nbc_fl_jar_exits)+
                         "\n");
        
        #if save file exits, then just copy new nbc file into dest dir.
        #it doesnt matter if nbc-fl.jar exits in dest dir or not.
        if save_file_exits == True:        
            myLogFile.write("create new jar file for nbc-feedloader.jar, jar.save file already exists.\n");
    
        elif nbc_fl_jar_exits == True:
            myLogFile.write("back up current jar file as jar.save, and copy over new jar.\n");
            
            #this is an mv command to back up the current nbc.jar file to nbc.jar.save
            os.rename(nbc_file_path, save_file_path);
    
        else:
            myLogFile.write("copy new jar file and don't do jar.save since its first time its run.\n");
        
        #source is nbc.jar file in the current location, dest is where to place it to.
        #always do a copy regardless...
        shutil.copy((curr_dir+nbc_jar_file), concat_path);
        
    #startup tc.
    tcProcessing(MyConfigObj.listOfTCDirs, myLogFile,"startup.sh",MyConfigObj.sleepTimeVal);
    
    localtime1 = time.asctime(time.localtime(time.time()));
    myLogFile.write("------ending deployment------- at => "+localtime1+"\n");
    myLogFile.write("\n");
    
    #close the log file.
    myLogFile.close();

#invoke main program.
if __name__ == "__main__":
    
    #main program
    print "deploying the jars for FL...";
    
    main();

