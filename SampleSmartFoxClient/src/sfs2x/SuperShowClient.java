/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs2x;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.SFSRoom;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.LoginRequest;

/**
 * Basic SFS2X client, performing connection and login to a 'localhost' server
 */
public class SuperShowClient implements IEventListener
{
    private SmartFox sfs;
    private final static Logger log = Logger.getLogger(SuperShowClient.class.getSimpleName());

    public SuperShowClient() 
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
            //sfs.send(new LoginRequest("kermit", "thefrog"));
            //sfs.send(new LoginRequest("JimboUser"));
            sfs.send(new LoginRequest("", ""));
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
           //get the command from the response.
           String val = (String)evt.getArguments().get("cmd");
           
           if("game.practice.lobby.success".equalsIgnoreCase(val))
           {              
              log.info("processing the [game.practice.lobby.success]");
              log.info(""+sfs.getRoomList());//print rooms i have joined.
           }
           else if("game.practice.competitorlist".equalsIgnoreCase(val))
           {
              SFSObject res = (SFSObject)evt.getArguments().get("params");
              log.info("Result competitor int array: "+res.getIntArray("competitor_id_array"));
           }
           else if("game.practice.ready".equalsIgnoreCase(val))
           {
              SFSObject res = (SFSObject)evt.getArguments().get("params");
              log.info("Result room id: "+res.getInt("room_id"));
           }
        }
    }
    
    public void sendGamePracticeStartCmd()
    {
       //code for extension request
       ISFSObject sfso = new SFSObject();
       sfs.send( new ExtensionRequest("game.practice.start", sfso));
    }
    
    public void sendGamePracticeCompetitorListCmd()
    {
       //code for extension request
       ISFSObject sfso = new SFSObject();
       sfs.send( new ExtensionRequest("game.practice.competitorlist", sfso));
    }
    
    public void sendGamePracticeCompetitorCmd()
    {
       //code for extension request
       ISFSObject sfso = new SFSObject();
       
       sfso.putInt("player_id", 1);
       sfso.putInt("competitor_id", 2);
       
       sfs.send( new ExtensionRequest("game.practice.competitor", sfso));
    }

    /**
     * @param args the command line arguments
    * @throws InterruptedException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
    * @throws IOException 
     */
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, IOException 
    {    
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       SuperShowClient jsc = new SuperShowClient();
       
       //this will connect to smartfox and login as a guest to a zone 
       //specific by the sfs-config.xml file. 
       jsc.connect();
       Thread.sleep(3000);
       
       String cmd = "";
       while(true)
       {
          System.out.println("0 = end this client");
          System.out.println("1 = game.practice");
          System.out.println("2 = game.competitorlist");
          System.out.println("3 = game.competitor");
          System.out.println("enter command");
          cmd = br.readLine();
          
          if(cmd.equals("1"))
          {
             //sends the game practice cmd to sfs dev zone1 extension.
             jsc.sendGamePracticeStartCmd();             
          }
          else if(cmd.equals("2"))
          {
             //send the game practice competitor list cmd.
             jsc.sendGamePracticeCompetitorListCmd();             
          }
          else if(cmd.equals("3"))
          {
             //send the game practice competitor cmd.
             jsc.sendGamePracticeCompetitorCmd();             
          }
          else
          {
             log.info("end client");
             jsc.close();
             break;
          }
          
          cmd = "";
       }
    }
}
