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
import sys;#used for command line args

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
        
        os.chdir(idx);#add bin to path
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
def killTCProcess(logfile_name, configObj):

    #do a ps -ef for linux
    my_list_of_tc_processes = getListTCProcesses(configObj);
    for str_items in my_list_of_tc_processes:
                
        logfile_name.write("found tomcat process to kill...");
        mystr = str_items.split();#split string based on space
        
        #use the pid to kill the process here if it exists...
        if(mystr[1]):
            logfile_name.write("force kill tomcat pid = "+mystr[1]+"\n");
            #do kill -9 pid here
            pid_num = int(mystr[1]);
            os.kill(pid_num,9);
    
    #check if we have configured to send email and have atleast 1 item in the list.    
    if (configObj.sendEmail == "yes" and len(my_list_of_tc_processes) > 0):
        send_emailViaSendMail(my_list_of_tc_processes);#send email to notify that forced kill is required...

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

#this will list tc processes and return back the list.
def getListTCProcesses(configObj):
    
    #do a ps -ef for linux
    ps = subprocess.Popen(("ps", "-ef"), stdout=subprocess.PIPE);
    output = ps.communicate()[0];#execute command
    
    #this is an empty list and add as needed.
    tmp_list_of_tc_processes = [];
    
    #split all the lines from the output and iterate over each line
    #this is iterating over each line from the [ps -ef]
    for line in output.split('\n'):
        
        #this will interate over each dir name and check if the
        #this line contains that dir name, if it does then peform the kill.
        for dir_name in configObj.listOfTCDirs:
            
            #if we find tomcat instance, then kill it...
            if line.find(dir_name) > -1:
                tmp_list_of_tc_processes.append(line);
                break;
                
    #return back to caller the list of tc processes
    return tmp_list_of_tc_processes;

#main()
def main():
    print "restart_tc...";
    
    #get a config obj...
    MyConfigObj = parseRestart_TC_Config("restart_tc_config.xml");
    
    #open log file to provide it to other functions.
    myLogFile = open(MyConfigObj.logFileName,"a");
    myLogFile.write("-----Restart  Begin-----\n");
    
    #get command line args if any.
    input1 = sys.argv;
    
    #check to see if we have exactly 2 args, the python file and the
    #path for the tomcat instance.
    if len(input1) == 2 :
        myLogFile.write("command line args passed = "+ sys.argv[1]+"\n");
        
        #loop over list of dirs and remove data from it.
        while len(MyConfigObj.listOfTCDirs) > 0:
            MyConfigObj.listOfTCDirs.pop();
    
        #add new item from cmd line arg to list of dirs.
        MyConfigObj.listOfTCDirs.append(sys.argv[1]);
    
    #add the [/bin] to list of dirs to allow for full path to the tc instance.
    for index in range(len(MyConfigObj.listOfTCDirs)):
        MyConfigObj.listOfTCDirs[index] += "/bin";
      
    #get local time to put into log file for shutdown.
    localtime1 = time.asctime(time.localtime(time.time()));
    myLogFile.write("shutdown started at => "+localtime1+"\n");
    
    time.sleep(int(MyConfigObj.sleepTimeVal));#before getting the listing of tc processes.
    
    #log the list of processes that are going to get shutdown.
    list_of_tc_processes = getListTCProcesses(MyConfigObj);
    myLogFile.write("tc process_list size = "+str(len(list_of_tc_processes))+"\n");
    
    for str_items in list_of_tc_processes:
        myLogFile.write("before shutdown, tc process => "+str_items+" \n");

    time.sleep(int(MyConfigObj.sleepTimeVal));#before getting the listing of tc processes.
    
    #begin the shutdown process for tc...
    tcProcessing(MyConfigObj.listOfTCDirs, myLogFile,"shutdown.sh",MyConfigObj.sleepTimeVal);
       
    #perform kill processing if needed
    myLogFile.write("Check to see if there are TC processes still running and kill them if so.. \n");
	
	time.sleep(int(MyConfigObj.sleepTimeVal));
    myLogFile.write("finished sleeping before find rogue tomcat processes. \n");
	
    killTCProcess(myLogFile,MyConfigObj);
    time.sleep(int(MyConfigObj.sleepTimeVal));#sleep for config sleep time before restarting.
       
    #get local time to put into log file for startup.
    localtime1 = time.asctime(time.localtime(time.time()));
    myLogFile.write("startup started at => "+localtime1+"\n");
       
    #begin the startup process for tc...
    tcProcessing(MyConfigObj.listOfTCDirs, myLogFile,"startup.sh", MyConfigObj.sleepTimeVal);
    time.sleep(int(MyConfigObj.sleepTimeVal));#before checking to see what tc processes exist
    
    #log the list of processes that are currently running.
    list_of_tc_processes2 = getListTCProcesses(MyConfigObj);
    myLogFile.write("tc process_list size = "+str(len(list_of_tc_processes2))+"\n");
    
    for str_items in list_of_tc_processes2:
        myLogFile.write("after startup, tc process => "+str_items+" \n");
        
    time.sleep(int(MyConfigObj.sleepTimeVal));
        
    myLogFile.write("-----Restart End-----\n");
    myLogFile.write("\n");
    
    #close log file.
    myLogFile.close();

#invoke main program.
if __name__ == "__main__":
    main();

