package sfs2x;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class MyExtension extends SFSExtension
{
    @Override
    public void init()
    {
        trace("Hello, this is my first SFS2X Extension!");
      
        // Add a new Request Handler
        addRequestHandler("sum", SumReqHandler.class);
    }
    
    @Override
    public void destroy()
    {
        trace("Destroy is called!");
    }
 
    public static class SumReqHandler extends BaseClientRequestHandler
    {
        @Override
        public void handleClientRequest(User sender, ISFSObject params)
        {
           trace("INSIDE THE HANDLE CLIENT REQUEST HANDLER "+ params+", user info = "+sender);
           
            // Get the client parameters
            int n1 = params.getInt("n1");
            int n2 = params.getInt("n2");
          
            // Create a response object
            ISFSObject resObj = new SFSObject(); 
            resObj.putInt("res", n1 + n2);
          
            // Send it back
            send("sum", resObj, sender);
        }
    }
}