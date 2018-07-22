package sfs2x;

import java.util.Arrays;

import com.smartfoxserver.v2.components.login.ILoginAssistantPlugin;
import com.smartfoxserver.v2.components.login.LoginAssistantComponent;
import com.smartfoxserver.v2.components.login.LoginData;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.security.DefaultPermissionProfile;

public class MuppetsExtension extends SFSExtension
{
    private LoginAssistantComponent lac;
     
    @Override
    public void init()
    {
    	lac = new LoginAssistantComponent(this);
        
        // Configure the component
        lac.getConfig().loginTable = "muppets";
//        lac.getConfig().userNameField = "name";
        
        lac.getConfig().userNameField = "email";//use email to verify user
        lac.getConfig().passwordField = "pword";
        lac.getConfig().nickNameField = "name";
        lac.getConfig().useCaseSensitiveNameChecks = true;//verify that name is case sensitive
     
        //setting this to get values on post processor plugin.
        lac.getConfig().extraFields = Arrays.asList("avatar", "isMod");
         
        //loading up post processor.
        lac.getConfig().postProcessPlugin = new ILoginAssistantPlugin()
        {
            public void execute(LoginData loginData)
            {
            	//pulling the extra fields.
                ISFSObject fields = loginData.extraFields;
                 
                //pulling the 2 extra fields added to get the data from DB
                String avatarPic = fields.getUtfString("avatar");
                boolean isMod = fields.getUtfString("isMod").equalsIgnoreCase("Y");
                 
                // Store avatar in session object
                loginData.session.setProperty("avatar", avatarPic);
                
                trace("setup user avatar for "+avatarPic);
                 
                // Set client as Moderator
                if (isMod)
                {
                    loginData.session.setProperty("$permission", DefaultPermissionProfile.MODERATOR);
                    trace("setup moderator for user = "+loginData.userName);
                }
            }
        };
        
        trace("muppets login assistant configured");
    }
     
    public void destroy()
    {
        lac.destroy();
    }
}
