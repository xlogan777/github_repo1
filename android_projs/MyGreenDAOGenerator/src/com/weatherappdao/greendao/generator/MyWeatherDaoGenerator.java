package com.weatherappdao.greendao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyWeatherDaoGenerator 
{
   //test 3
	public static void main(String[] args)
	{
		//create green dao schema obj with version number, and pkg for the generated files.
		int db_version_number = 1;
		String pkg_name = "jmtechsvcs.myweatherapp.GreenDaoSrcGen";
		
		//create schema obj.
      Schema schema = new Schema(db_version_number, pkg_name);
     
      //this will allow for keep sections to be generated for all entities under this schema.
      //you can place your code in here...to keep after each generation of the code.
      schema.enableKeepSectionsByDefault();

		System.out.println("create city id info table");
		Entity city_info_table = createCityInfoTable(schema);
		
		//generate the source code.
		try
		{
			//create dao generator obj.
			DaoGenerator dao_generator = new DaoGenerator();
			
			//generate code based on schema and place in outdir.			
			dao_generator.generateAll(schema, "c:/users/jimmy/desktop");
			
			System.out.println("JM...generated the tables from green dao.");
		}
		catch(Exception e)
		{
			System.out.println("JM...exception = "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Entity createCityInfoTable(Schema schema)
	{
	  //table name
      Entity city_info_table_entity = schema.addEntity("CityInfoTable");
      
      //add pk
      city_info_table_entity.addLongProperty("city_id").primaryKey().notNull();
      
      //add non pk fields.
      city_info_table_entity.addStringProperty("name").notNull();
      city_info_table_entity.addStringProperty("country").notNull();
      city_info_table_entity.addDoubleProperty("lon").notNull();
      city_info_table_entity.addDoubleProperty("lat").notNull();
      
      //return entity
      return city_info_table_entity;
	}
}
