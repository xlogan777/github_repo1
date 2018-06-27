/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfs2x;

import com.smartfoxserver.v2.exceptions.SFSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.SFSRoom;
import sfs2x.client.requests.CreateRoomRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.requests.RoomSettings;
//import sfs2x.client.util.ConfigData;

/**
 * Basic SFS2X client, performing connection and login to a 'localhost' server
 */
public class JavaSimpleConnector implements IEventListener
{
    private final SmartFox sfs;
//    private final ConfigData cfg;
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JavaSimpleConnector() 
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
        
        /**
         * Create a configuration for the connection passing
         * the basic parameters such as host, TCP port number and Zone name
         * that will be used for logging in.
         */
//        cfg = new ConfigData();
//        cfg.setHost("localhost");
//        cfg.setPort(9933);
//        cfg.setZone("BasicExamples");
//        sfs.connect(cfg);
        
        //this loads the config obj via a config file, xml that is passed in
        //the false doesnt auto connect to the sfs server.
        sfs.loadConfig(System.getProperty("confPath"), false);
        
        //this forces the connection to the server with the loaded xml file.
        sfs.connect();
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
            sfs.send(new LoginRequest("JimboUser"));
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
            
            //join another room.
            //sfs.send(new JoinRoomRequest("MyRoom1"));
            //sfs.send(new JoinRoomRequest("MyRoom2"));
            //sfs.send(new JoinRoomRequest("The Lobby"));
            
            //create new room and add settings to it.
            RoomSettings rs = new RoomSettings("Piggy Chat Room");
            rs.setMaxUsers(3);
            rs.setGroupId("ChatGroup");

            //send the create room request.
            sfs.send(new CreateRoomRequest(rs));
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
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new JavaSimpleConnector();
    }
}
