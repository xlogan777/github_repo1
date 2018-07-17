/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs2x;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.SFSRoom;
import sfs2x.client.entities.variables.SFSRoomVariable;
import sfs2x.client.requests.CreateRoomRequest;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.requests.RoomSettings;
//import sfs2x.client.util.ConfigData;
import sfs2x.client.requests.SetRoomVariablesRequest;

/**
 * Basic SFS2X client, performing connection and login to a 'localhost' server
 */
public class JavaSimpleConnector implements IEventListener
{
    private SmartFox sfs;
//    private final ConfigData cfg;
    
    //private final Logger log = LoggerFactory.getLogger(JavaSimpleConnector.class.getSimpleName());
    private final static Logger log = Logger.getLogger(JavaSimpleConnector.class.getSimpleName());

    public JavaSimpleConnector() 
    {
       
    }
    
    public void connect()
    {
       /**
        * Setup the main API object and add all the events we
        * want to listen to.
        */
       sfs = new SmartFox();
       sfs.addEventListener(SFSEvent.CONNECTION, this);
       sfs.addEventListener(SFSEvent.CONNECTION_LOST, this);
       sfs.addEventListener(SFSEvent.LOGIN, this);
       sfs.addEventListener(SFSEvent.LOGIN_ERROR, this);
       
       //code in events for joining rooms.
       sfs.addEventListener(SFSEvent.ROOM_JOIN, this);
       sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
       
       //create a room dynamically
       sfs.addEventListener(SFSEvent.ROOM_ADD, this);
       sfs.addEventListener(SFSEvent.ROOM_CREATION_ERROR, this);
       
       //extension response.
       sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
       
       /**
        * Create a configuration for the connection passing
        * the basic parameters such as host, TCP port number and Zone name
        * that will be used for logging in.
        */
//       cfg = new ConfigData();
//       cfg.setHost("localhost");
//       cfg.setPort(9933);
//       cfg.setZone("BasicExamples");
//       sfs.connect(cfg);
       
       //this loads the config obj via a config file, xml that is passed in
       //the false doesnt auto connect to the sfs server.
       sfs.loadConfig(System.getProperty("confPath"), false);
       
       //this forces the connection to the server with the loaded xml file.
       sfs.connect();
    }
    
    public void close()
    {
       sfs.disconnect(); 
    }

    /**
     * This handles the events coming from the server
     */
    @Override
    public void dispatch(BaseEvent evt) throws SFSException 
    {
        /**
         * Handle CONNECTIOn event
         */
        if (evt.getType().equals(SFSEvent.CONNECTION))
        {
            boolean success = (Boolean) evt.getArguments().get("success");

            if (!success)
            {
                log.warn("Connection failed!");
                return;
            }
            
            log.info("Connection success: " + sfs.getConnectionMode());
            
            /**
             * Send a guest login request (no name, no password)
             * The server will auto-assign a guest user name
             */
            //sfs.send(new LoginRequest("", "", sfs.getCurrentZone()));
            
            //since we have loaded the config file the zone name is going to be used.
            //since we are using the system controller and not custom controller.
            //no username/passwd is needed.
            //but i provided a username for now.
            //sfs.send(new LoginRequest("Administrator"));
            sfs.send(new LoginRequest("Kermit", "thefrog"));
            
            //sfs.send(new LoginRequest("JimboUser"));
            //sfs.send(new LoginRequest("", ""));
        }
        
        /**
         * Handle CONNECTION_LOST event
         */
        else if (evt.getType().equals(SFSEvent.CONNECTION_LOST))
        {
            log.info("Connection was closed");
        }
        
        /**
         * Handle LOGIN event
         */
        else if (evt.getType().equals(SFSEvent.LOGIN))
        {
            log.info("Logged in as: " + sfs.getMySelf().getName());
            
            //print the number of rooms i get after successful login.
            log.info(""+sfs.getRoomList());
            
//            //create new room and add settings to it.
//            RoomSettings rs = new RoomSettings("Jimbo_Chat_Room");
//            rs.setMaxUsers(3);
//            rs.setGroupId("default");
//
//            //send the create room request.
//            sfs.send(new CreateRoomRequest(rs));
//            log.info("finished login");
//            
//            //join another room.
//            sfs.send(new JoinRoomRequest("MyRoom1"));
//            //sfs.send(new JoinRoomRequest("MyRoom2"));
//            //sfs.send(new JoinRoomRequest("The Lobby"));
//            
//            //code for extension request
//            ISFSObject sfso = new SFSObject();
//            sfso.putInt("n1", 25);
//            sfso.putInt("n2", 17);
//         
//            sfs.send( new ExtensionRequest("sum", sfso) );            
        }
        
        /**
         * Handle LOGIN_ERROR event
         */
        else if (evt.getType().equals(SFSEvent.LOGIN_ERROR))
        {
            log.warn("Login error:  " + evt.getArguments().get("errorMessage"));
        }
        /**
         * handle ROOM_JOIN
         */
        else if (evt.getType().equals(SFSEvent.ROOM_JOIN))
        {
        	   SFSRoom tmp = (SFSRoom)evt.getArguments().get("room");
            log.info("Joined Room: " + tmp.getName());
            log.info("Last Joined Room : "+sfs.getLastJoinedRoom().getName());
            log.info("List of currently Joined Rooms : "+sfs.getJoinedRooms());
            
//            ArrayList listOfVars = new ArrayList();
//            
//            listOfVars.add( new SFSRoomVariable("bgImage", "coolBackground.jpg") );
//            listOfVars.add( new SFSRoomVariable("stars", 4) );
//          
//            // nested object
////            var chatSettings:SFSObject = new SFSObject()
////            chatSettings.putUtfStringArray("allowedFonts", ["Verdana", "Arial", "Times New Roman"])
////            chatSettings.putIntArray("allowedFontSizes", [10,11,12,13])
////            chatSettings.putBool("isBoldAllowed", true)
////            chatSettings.putBool("isItalicAllowed", true)
////            chatSettings.putBool("isUnderlineAllowed", false)
////             
////            listOfVars.push( new SFSRoomVariable("settings", chatSettings))
//             
//            // Set variables
//            sfs.send( new SetRoomVariablesRequest(listOfVars, tmp) );
//            
//            ISFSObject sfso = new SFSObject();
//            sfso.putByte("id", (byte)10);
//            sfso.putShort("health", (short)5000);
//            sfso.putIntArray("pos", Arrays.asList(120,150));
//            sfso.putUtfString("name", "Hurricane");
//            
//            log.info(sfso.getDump());
//            
//         // Send request to Zone level extension on server side
//            sfs.send( new ExtensionRequest("data", sfso) );
        }
        
        
        else if (evt.getType().equals(SFSEvent.ROOM_JOIN_ERROR))
        {
            log.warn("Join failed: " + evt.getArguments().get("errorMessage"));
        }
        
        else if (evt.getType().equals(SFSEvent.ROOM_ADD))
        {
           log.info("A new Room was added: " + evt.getArguments().get("room"));
        }
        
        else if (evt.getType().equals(SFSEvent.ROOM_CREATION_ERROR))
        {
        	  log.warn("An error occurred while attempting to create the Room: " + evt.getArguments().get("errorMessage"));
        }
        
        else if (evt.getType().equals(SFSEvent.EXTENSION_RESPONSE))
        {
           String val = (String)evt.getArguments().get("cmd");
           if("sum".equalsIgnoreCase(val))
           {              
              SFSObject res = (SFSObject)evt.getArguments().get("params");
              log.info("Result: "+res.getInt("res"));   
           }
           
        }
        
    }

    /**
     * @param args the command line arguments
    * @throws InterruptedException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException 
    {
    	Connection con = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	try
    	{
    		//driver class = com.mysql.jdbc.Driver
    		Class.forName("com.mysql.jdbc.Driver");
	    	//Class.forName("com.mysql.cj.jdbc.Driver");
	    	con=DriverManager.getConnection(  
	    	"jdbc:mysql://localhost:3306/jimbo_db1?useSSL=false","jimmy","jimmy123");  
	    	//here sonoo is database name, root is username and password  
	    	stmt=con.createStatement();  
	    	rs=stmt.executeQuery("select * from muppets");  
	    	while(rs.next())  
	    		log.info(rs.getString(2)+"  "+rs.getString(3));
	    	
    	}
    	catch(Exception e)
    	{
    		log.error(e,e);
    		if(rs !=null)
    			rs.close();
    		if(stmt !=null)
    			stmt.close();
    		if(con !=null)
    			con.close();
    	}
    	
       log.info("hello");
       JavaSimpleConnector jsc = new JavaSimpleConnector();
       jsc.connect();
//       Thread.sleep(2000);
//       jsc.close();
    }
}
