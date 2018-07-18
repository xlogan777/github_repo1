package sfs2x;

import com.smartfoxserver.v2.components.login.LoginAssistantComponent;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class MuppetsExtension extends SFSExtension
{
    private LoginAssistantComponent lac;
     
    @Override
    public void init()
    {
        lac = new LoginAssistantComponent(this);
         
        // Configure the component
        lac.getConfig().loginTable = "muppets";
        lac.getConfig().userNameField = "name";
        lac.getConfig().passwordField = "pword";
        
        trace("muppets login assistant configured");
    }
     
    public void destroy()
    {
        lac.destroy();
    }
}
