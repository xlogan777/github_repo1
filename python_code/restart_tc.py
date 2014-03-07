#!/usr/bin/python

#this file is for restarting tomcat instances gracefullu
#and will kill the processes by id if they are still lingering around...
#it will also send an email if a forced kill was done..

#author Jimbo...

#python file for restarting tc instances
import xml.dom.minidom;#used for parsing xml files
import os;#used for directory stuff
import time;#used for sleep processing
import subprocess;#used for linux command invocation
import smtplib;#email module for python

#Class ConfigObj
#this will store the config file content into this obj...
class ConfigObj:
    "ConfigObj for parsing restart_tc confile file";
    
    def __init__(self,sendEmail, logFilename, listOfTCDirs,sleepTimeVal):
        self.sendEmail = sendEmail;
        self.logFileName = logFilename;
        self.listOfTCDirs = listOfTCDirs;
        self.sleepTimeVal = sleepTimeVal;
                
    def printInternalData(self):
        #print internal data
        print "send_email = ", self.sendEmail, ", log_filename = ",self.logFileName+" sleep_time_val = "+self.sleepTimeVal;
        
        #print list of directories to restart tc...
        for idx in self.listOfTCDirs:
            print "tc_dir = ", idx;
            
#Class ConfigObj
    

#function will parse config file and save it to an config obj to be later used.
def parseRestart_TC_Config(config_filename):
        
    #invoke the dom parser from minidom and provide it the xml file.
    MyDomTree = xml.dom.minidom.parse(config_filename);
    root_element = MyDomTree.documentElement;#this is the root element
    
    #get element by tagname and get the value for send email
    send_email = root_element.getElementsByTagName("Send_Email_Notice")[0];
    send_email_val = send_email.getAttribute("emailVal");
    
    #get element by tagname and get the value for log filename
    log_file = root_element.getElementsByTagName("LogFile")[0];
    log_file_val = log_file.getAttribute("fname");

    sleep_time = root_element.getElementsByTagName("SleepTime")[0];
    sleep_time_val = sleep_time.getAttribute("sleepTimeVal");
    
    #get element by tagname and get the childnodes associated with the list of dirs tag name
    dir_list = root_element.getElementsByTagName("List_Of_TC_Dirs")[0].childNodes;
    
    #iterate over list of dirs
    list_of_tc_dirs = []; #create empty list to contain string of dirs for tomcat
    for cnode_data in dir_list:
        if cnode_data.nodeName == "dir_path":
            #from node of dirs u need to get new node of data elements first child and
            #then get its data.
            list_of_tc_dirs.append(cnode_data.firstChild.data);#append to list

    #this returns a new config obj when called.    
    return ConfigObj(send_email_val, log_file_val, list_of_tc_dirs, sleep_time_val);

#this will perform startup/shutdown for tomcat instances
def tcProcessing(list_of_dirs,logfile_name,tc_Command, sleepTimeVal):
    
    for idx in list_of_dirs:
        logfile_name.write("enter folder path "+ idx +", to perform "+ tc_Command + " of TC\n");
        
        #enter dir
        os.chdir(idx);
        logfile_name.write("curr dir in = "+os.getcwd()+"\n");
        
        #perform graceful shutdown of tc...
        #invoke tc shutdown script.
        logfile_name.write("perform "+ tc_Command +" of tc now...for path = "+idx+"\n");
        command_to_exe = "./"+tc_Command;
        os.system(command_to_exe);
               
        #sleep for time passed in.
        sval = int(sleepTimeVal);
        time.sleep(sval);

#this will get the pid and perform a kill on the process if we 
#find a process for tomcat.
def killTCProcess(logfile_name):
    
    #do a ps -ef for linux
    ps = subprocess.Popen(("ps", "-ef"), stdout=subprocess.PIPE);
    output = ps.communicate()[0];#execute command
    
    #used to help in deciding if to send an email..
    bool_sendEmail = False;
    
    #this is an empty list and add as needed.
    list_of_tc_processes = [];
    
    #split all the lines from the output and iterate over each line
    for line in output.split('\n'):
        
        #if we find tomcat instance, then kill it...
        if line.find("tomcat") > -1:
            
            #check to see if an email hasnt been sent yet..and if not
            #send one, and dont send any more.
            bool_sendEmail = True;
            
            list_of_tc_processes.append(line);
            
            logfile_name.write("found tomcat process to kill...");
            mystr = line.split();#split string based on space
            
            #use the pid to kill the process here if it exists...
            if(mystr[1]):
                logfile_name.write("force kill tomcat pid = "+mystr[1]+"\n");
                #do kill -9 pid here
                pid_num = int(mystr[1]);
                os.kill(pid_num,9);
    
    #check to see if we need to send email...if so then send it with
    #list of processes that were killed.
    global MyConfigObj;    
    
    if (bool_sendEmail == True and MyConfigObj.sendEmail == "yes"):
        send_emailViaSendMail(list_of_tc_processes);#send email to notify that forced kill is required...

#this works when you are not behind a proxy or firewall...
#takes in a list of process info.
def send_emailViaGmail(listOfTCProcesses):
    gmail_user = ""; #use a valid gmail address
    gmail_pwd = "XXX";#add password fix..., put the valid password
    FROM = ""; #put real email address
    TO = [""]; #must be a list, put real email address
    SUBJECT = "List of Tomcat processes that didn't shutdown gracefully";    
    TEXT = "Full list of TC processes \n\n";
    
    #append to the email body all the list of processes that were killed
    for idx in listOfTCProcesses:
        TEXT += (idx + "\n");

    message = "From: "+FROM+ "\n"+"To: "+TO[0]+"\n"+"Subject: "+SUBJECT+"\n"+TEXT;
    print message;
    
    try:        
        server = smtplib.SMTP("smtp.gmail.com", 587); #or port 465 doesn't seem to work!
        server.ehlo();
        server.starttls();
        server.login(gmail_user, gmail_pwd);
        server.sendmail(FROM, TO, message);
        server.close();
        print "successfully sent the mail";
    except Exception, Argument:
        print "failed to send mail = ",Argument;

def send_emailViaSendMail(listOfTCProcesses):
    SENDMAIL = "/usr/sbin/sendmail"; # sendmail location
    
    FROM = "";#use valid email address
    TO = [""];#use valid email address
    
    SUBJECT = "List of Tomcat processes that didn't shutdown gracefully";
    TEXT = "Full list of TC processes \n\n";
    
    #append to the email body all the list of processes that were killed
    for idx in listOfTCProcesses:
        TEXT += (idx + "\n");

    message = "From: "+FROM+ "\n"+"To: "+TO[0]+"\n"+"Subject: "+SUBJECT+"\n"+TEXT;
    
    #use linux sendmail to send this email.
    p = os.popen("%s -t -i" % SENDMAIL, "w");
    p.write(message);
    p.close();

# Main program
print "restart_tc...";

#get a config obj...
MyConfigObj = parseRestart_TC_Config("restart_tc_config.xml");
 
#open log file to provide it to other functions.
myLogFile = open(MyConfigObj.logFileName,"a");
myLogFile.write("-----Restart  Begin-----\n");
 
#get local time to put into log file for shutdown.
localtime1 = time.asctime(time.localtime(time.time()));
myLogFile.write("shutdown started at => "+localtime1+"\n");
 
#begin the shutdown process for tc...
tcProcessing(MyConfigObj.listOfTCDirs, myLogFile,"shutdown.sh",MyConfigObj.sleepTimeVal);
 
#perform kill processing if needed
myLogFile.write("Check to see if there are TC processes still running and kill them if so.. \n");
killTCProcess(myLogFile);
 
#get local time to put into log file for startup.
localtime1 = time.asctime(time.localtime(time.time()));
myLogFile.write("startup started at => "+localtime1+"\n");
 
#begin the startup process for tc...
tcProcessing(MyConfigObj.listOfTCDirs, myLogFile,"startup.sh", MyConfigObj.sleepTimeVal);
myLogFile.write("-----Restart End-----\n");
myLogFile.write("\n");
