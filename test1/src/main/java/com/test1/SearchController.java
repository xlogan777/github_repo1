package com.test1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

// import com.bbu.productmaintenancedao.ProductMaintenanceDao;
// import com.bbu.productmaintenancedao.ProductMaintenanceDaoOracleImpl;
// import com.bbu.productpojo.ProductType;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//https://docs.oracle.com/en/cloud/paas/atp-cloud/atpug/connect-preparing.html#GUID-EFAFA00E-54CC-47C7-8C71-E7868279EF3B

//https://www.oracle.com/database/technologies/appdev/dotnet/odtvscodequickstart.html
/*
   use this "Connect using a TNSNAMES.ORA Connection Alias"

   A connection dialog will open. In the Connection Type dropdown, select TNS Alias
   Make sure the TNS Admin Location field is set to the directory where your TNSNAMES.ORA 
   file is located. If not, change it. Select an alias from the TNS Alias dropdown list
   Select the database role from the Role drop down list
   Enter the username and password
   Provide a connection name to be used to reference this connection in Database Explorer 
   and elsewhere. Click the Create Connection button
*/

//https://marketplace.visualstudio.com/items?itemName=humao.rest-client
//https://www.oracle.com/database/technologies/appdev/dotnet/odtvscodequickstart.html
//https://code.visualstudio.com/docs/java/java-debugging
//http://www.masterspringboot.com/getting-started/spring-boot-introduction/how-to-set-jvm-settings-in-a-spring-boot-application
@RestController
public class SearchController
{

   private static Logger log = Logger.getLogger(SearchController.class.getName());
   // private DataSource dataSource = null;
   
   /**
    * default constructor to setup env type and data source based on env type.
    */
   public SearchController()
   {
      // this.dataSource = null;

      log.info("setting env once!!!!");

      // dataSource = (DataSource) DemoAppContext.getContext().getBean("c3p0DataSourceT2");
      // log.error("testing");
   }//controller

   /**
    * this method is to test if the micro svc is working.
    * 
    * @return stubbed string to show that MS is working.
    */
   @RequestMapping(value = "/myTest1", method = RequestMethod.GET, produces = "application/json")
   public String myTest1()
   {
      String info = "Running test1 MS";
	   log.error(info);
      return info;
   }

   @RequestMapping(value = "/test123", method = RequestMethod.GET, produces = "application/json")
   public String test123()
   {
      Connection dbConn = null;
      CallableStatement call = null;
      ResultSet rs = null;
      String db_url = "jdbc:oracle:thin:@cascbbd02.bxxi.com:1524:CENRT2";
      String username = "CDB";
      String passwd = "CDB";

      try
      {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         dbConn = DriverManager.getConnection(db_url, username, passwd);

         call = dbConn.prepareCall("{call TEST1.listUsers()}");
         // call.setString(2, pricingLookup.getIdCustomer());         
         // call.registerOutParameter(1, OracleTypes.CURSOR);
         
         call.execute();
         
         // rs = (ResultSet) call.getObject(1);
         // if (rs.next())
         // {
            
         //    // returnPricingLookup.setIdCustomer(rs.getString("CUSTOMERGROUP_ID"));
            
         // }
      }
      catch (Exception ex)
      {
         log.error(ex,ex);
      }
      finally
      {
         try 
         {
            if(rs != null)
               rs.close();
            
            if(call != null)
               call.close();
            
            if(dbConn != null)
               dbConn.close();

         } catch (Exception e) 
         {
            log.error(e,e);
         }
      }

      return "";
   }

   // @RequestMapping(value = "/getProductById", method = RequestMethod.GET, produces = "application/json")
   // @CrossOrigin
   // public ResponseEntity<ProductType> getProductById
   // (
   //    @RequestParam(value = "productId", defaultValue = "-1") String productId
   // )
   // {
   //    ProductType prod_type = null;
   //    long prod_id = -1;
   //    Connection conn = null;

   //    try
   //    {
   //       // check for valid id.
   //       try
   //       {
   //          prod_id = Long.parseLong(productId);
   //       }
   //       catch (Exception e)
   //       {
   //          log.error(e, e);
   //       }

   //       // only call Db if id is valid
   //       if (prod_id != -1)
   //       {
   //          // get the connection from the data src
   //          conn = dataSource.getConnection();

   //          // create the dao and set the connection for it to be used.
   //          ProductMaintenanceDao productMaintenanceDao = new ProductMaintenanceDaoOracleImpl();
   //          productMaintenanceDao.setConnection(conn);

   //          // get the prod id from dao.
   //          prod_type = productMaintenanceDao.getProductById(prod_id);
   //       }
   //    }
   //    catch (Exception e)
   //    {
   //       log.error(e, e);
   //    }
   //    finally
   //    {
   //       if (conn != null)
   //       {
   //          try
   //          {
   //             conn.close();
   //          }
   //          catch (SQLException e)
   //          {
   //             log.error(e, e);
   //          }
   //       }
   //    }

   //    // embed obj type back to caller with ok http status.
   //    ResponseEntity<ProductType> response_entity = 
   //          new ResponseEntity<ProductType>(prod_type, HttpStatus.OK);

   //    // return response entity to caller.
   //    return response_entity;
   // }
}
