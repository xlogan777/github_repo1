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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.Room;
import sfs2x.client.entities.SFSRoom;
import sfs2x.client.entities.variables.RoomVariable;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.LoginRequest;

/**
 * Basic SFS2X client, performing connection and login to a 'localhost' server
 */
public class SuperShowClient implements IEventListener
{
    private SmartFox sfs;
    private final static Logger log = Logger.getLogger(SuperShowClient.class.getSimpleName());
    
    private String practiceRoomName = "";

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
              log.info("Result competitor int array: "+res.getIntArray("competitor_list_id"));
           }
           else if("game.practice.ready".equalsIgnoreCase(val))
           {
              SFSObject res = (SFSObject)evt.getArguments().get("params");
              log.info("Result room id: "+res.getUtfString("room_id"));
              
              //get the room via the id
              Room room = sfs.getRoomById(Integer.parseInt(res.getUtfString("room_id")));
              
              log.info("user_vars = "+sfs.getMySelf().getVariables());
              
              //get the room vars for this room
              List<RoomVariable> room_vars = room.getVariables();
              
              //print the room vars.
              log.info(room_vars);
              
              //print current room list.
              log.info(""+sfs.getRoomList());//print rooms i have joined.
           }
           else if("match.game.setactive".equalsIgnoreCase(val))
           {
              SFSObject res = (SFSObject)evt.getArguments().get("params");
              log.info("active player: "+res.getInt("player_id"));
              
              //get the room via the id
              Room room = sfs.getLastJoinedRoom();
              
              log.info("user_vars = "+sfs.getMySelf().getVariables());
              
              //get the room vars for this room
              List<RoomVariable> room_vars = room.getVariables();
              
              //print the room vars.
              log.info(room_vars);
              
              //print current room list.
              log.info(""+sfs.getRoomList());//print rooms i have joined.
           }
           else if("match.deck.shuffle".equalsIgnoreCase(val))
           {
              SFSObject res = (SFSObject)evt.getArguments().get("params");
              log.info("player_id: "+res.getInt("player_id"));
              log.info("player_deck_array: "+res.getUtfStringArray("player_deck_array"));
           }
        }
    }
    
    public void sendGamePracticeStartCmd()
    {
       //code for extension request
       ISFSObject sfso = new SFSObject();
       sfso.putInt("competitor_id", 0);
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
       
       sfso.putInt("opponent_competitor_id", 1);
       
       sfs.send( new ExtensionRequest("game.practice.competitor", sfso));
    }
    
    public void sendAiCmd()
    {
       log.info(""+sfs.getRoomList());
       
       //get the room with the name needed for the extension call.
       Room room = sfs.getLastJoinedRoom();
       
       //code for extension request
       ISFSObject sfso = new SFSObject();
       
       sfso.putUtfString("ai", "ai");
       
       sfs.send( new ExtensionRequest("ai.cmds.start", sfso, room));
    }
    
    public void sendMatchPracticeReady()
    {
       //code for extension request
       ISFSObject sfso = new SFSObject();
       
       sfs.send( new ExtensionRequest("match.practice.ready", sfso));
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
          Thread.sleep(3000);
          System.out.println("0 = end this client");
          System.out.println("1 = game.practice");
          System.out.println("2 = game.competitorlist");
          System.out.println("3 = game.competitor");
          System.out.println("4 = ai.cmd");
          System.out.println("5 = match.practice.ready");
          
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
          else if(cmd.equals("4"))
          {
             //send the game practice competitor cmd.
             jsc.sendAiCmd();
          }
          else if(cmd.equals("5"))
          {
             jsc.sendMatchPracticeReady();
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
