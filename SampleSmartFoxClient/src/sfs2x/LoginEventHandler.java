package sfs2x;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class LoginEventHandler extends BaseServerEventHandler
{
   @Override
   public void handleServerEvent(ISFSEvent event) throws SFSException
   {
      // Grab parameters from client request
      String userName = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
      String cryptedPass = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
      ISession session = (ISession) event.getParameter(SFSEventParam.SESSION);

      // Get password from DB
      // TODO
      IDBManager dbManager = getParentExtension().getParentZone().getDBManager();
      Connection connection = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try
      {
         // Grab a connection from the DBManager connection pool
         // TODO
          connection = dbManager.getConnection();
/*
         Class.forName("com.mysql.cj.jdbc.Driver");
         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jimbo_db1?useSSL=false", "jimmy", "jimmy123");
*/
         // Build a prepared statement
         stmt = connection.prepareStatement("SELECT pword,id FROM muppets WHERE name=?");
         stmt.setString(1, userName);

         // Execute query
         rs = stmt.executeQuery();

         // Verify that one record was found
         if (!rs.first())
         {
            // This is the part that goes to the client
            SFSErrorData errData = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
            errData.addParameter(userName);

            // This is logged on the server side
            throw new SFSLoginException("Bad user name: " + userName, errData);
         }

         String dbPword = rs.getString("pword");
         int dbId = rs.getInt("id");

         // Verify the secure password
         if (!getApi().checkSecurePassword(session, dbPword, cryptedPass))
         {
            SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
            data.addParameter(userName);

            throw new SFSLoginException("Login failed for user: " + userName, data);
         }

         // Store the client dbId in the session
         session.setProperty(DBLogin.DATABASE_ID, dbId);
      }

      // User name was not found
      catch (SQLException /*| ClassNotFoundException*/ e)
      {
         SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
         errData.addParameter("SQL Error: " + e.getMessage());

         throw new SFSLoginException("A SQL Error occurred: " + e.getMessage(), errData);
      }

      finally
      {
         // Return connection to the DBManager connection pool
         try
         {
            if (rs != null)
               rs.close();

            if (stmt != null)
               stmt.close();

            if (connection != null)
               connection.close();
         }
         catch (SQLException e)
         {
            e.printStackTrace();
         }
      }
   }
}
