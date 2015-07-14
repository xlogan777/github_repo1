

/**
 * @author Menaj
 * 
 * this will perform the needed processing for 
 * 1. invoke sp to get heartbeat msgs from recv shadow table.
 * 2. deserialize these etl msgs.
 * 3. save them to the status table.
 * 4. release db resources.
 */

import java.sql.{DriverManager, Connection,PreparedStatement, Statement, ResultSet};

//this is a class with constructor args
class DbProcessing(val jdbcDriver: String,  val dbUrl: String, val dbUserName: String, val dbPasswd: String)
{
   private var driver: String = jdbcDriver;
   private var url: String = dbUrl;
   private var username: String = dbUserName;
   private var password: String = dbPasswd;

   //inner method for this class.
   private def getDbConnection() : Connection =
   {
      //return conn obj back to caller.
      var connection: Connection = null;

      try 
      {
         // make the connection
         Class.forName(driver);
         connection = DriverManager.getConnection(url, username, password);
      }
      catch 
      {
         case e : Exception => 
         {
            e.printStackTrace;
         }
      }
         
      return connection;
   }

   //this will make a connection and 
   def getHeartBeatData(): String =
   {
      var my_connection: Connection = this.getDbConnection();
      var my_data : String = "";//return empty string if not found.
      var statement: Statement = null;
      var resultSet: ResultSet = null;
      
      if(my_connection != null)
      {
         try
         {
            // create the statement, and run the select query
            statement = my_connection.createStatement();
            //resultSet = statement.executeQuery("SELECT * from employee");
            resultSet = statement.executeQuery("select * from sendshadow where id_transaction = 115070711313903051");
            
            while(resultSet.next()) 
            {
//               var fname: String = resultSet.getString("first_name");
//               var lname: String = resultSet.getString("last_name");
               
//               my_data += ("fname = " + fname + ", lname = " + lname);
//               println("fname = " + fname + ", lname = " + lname);
               
               var id_catalogtype : Int = resultSet.getInt("id_catalogtype");
               
               my_data += ("id_catalogtype = "+id_catalogtype);
               println(my_data);
            }
         }
         catch
         {
            case e: Exception =>
               e.printStackTrace();
         }
         finally
         {
            if(resultSet != null)
               resultSet.close();
            
            if(statement != null)
               statement.close();
            
            if(my_connection != null)
               my_connection.close();
         }
      }
      else
      {
         println("have a null connection, this is no good.");
      }
      
      return my_data;
   }
   
   def saveHeartBeatData(fname: String, lname: String, salary: Int) : Boolean = 
   {
      var status: Boolean = true;
      var my_connection: Connection = this.getDbConnection();
      var prep_stmt: PreparedStatement = null;
      
      if(my_connection != null)
      {
         try
         {
            // create the statement, and run the select query
            prep_stmt = my_connection.prepareStatement("insert into employee(first_name, last_name, salary) values (?,?,?)");
            prep_stmt.setString(1, fname);
            prep_stmt.setString(2, lname);
            prep_stmt.setInt(3, salary);
            prep_stmt.executeUpdate();
         }
         catch
         {
            case e: Exception =>
            {
               e.printStackTrace();
               status = false;
            }
         }
         finally
         {
            if(prep_stmt != null)
               prep_stmt.close();
            
            if(my_connection != null)
               my_connection.close();
         }
      }
      else
      {
         println("have a null connection, this is no good.");
      }
      
      return status;
   }
}