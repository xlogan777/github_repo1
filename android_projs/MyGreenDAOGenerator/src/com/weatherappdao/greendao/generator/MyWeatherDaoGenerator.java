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
		
		System.out.println("create city weather cond table");	
		Entity city_weather_cond_table = createCityWeatherCurrCondTable(schema);
		
		//generate the source code
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
	
	public static Entity createCityWeatherCurrCondTable(Schema schema)
	{
	   //table name
      Entity city_weather_cond_entity = schema.addEntity("CityWeatherCurrCondTable");
      
      //add pk
      city_weather_cond_entity.addLongProperty("city_id").primaryKey().notNull();
      
      //add non pk fields
      /*
       "id": 803,//weather condition id
            "main": "Clouds",//group of weather params.(can be "Rain, Snow"..)
            "description": "broken clouds",//weather desc in the group
            "icon": "04n"//weather icon id, this shows the image icon
       */
      city_weather_cond_entity.addLongProperty("curr_weather_id");
      city_weather_cond_entity.addStringProperty("curr_weather_main");
      city_weather_cond_entity.addStringProperty("curr_weather_desc");
      city_weather_cond_entity.addStringProperty("curr_weather_icon");
      
      /*
       "main": {
        "temp": 296.03,//kelvin
        "pressure": 1019,//atmospheric pressure, hPa units
        "humidity": 83,//humidity %
        "temp_min": 295.93,//kelvin
        "temp_max": 296.15,//kelvin
      "sea_level": 1020//atmospheric pressure, hPa units
      "grnd_level":1010//atmospheric pressure, hPa units
    }
       */
      
      city_weather_cond_entity.addDoubleProperty("curr_main_temp");
      city_weather_cond_entity.addLongProperty("curr_main_pressure");
      city_weather_cond_entity.addLongProperty("curr_main_humidity");
      city_weather_cond_entity.addDoubleProperty("curr_main_temp_min");
      city_weather_cond_entity.addDoubleProperty("curr_main_temp_max");
      city_weather_cond_entity.addLongProperty("curr_main_sea_level");
      city_weather_cond_entity.addLongProperty("curr_main_grnd_level");
      
      /*
       "wind": {
        "speed": 4.1,//meters/sec
        "deg": 160//degrees
    },
       */
      
      city_weather_cond_entity.addDoubleProperty("curr_wind_speed");
      city_weather_cond_entity.addLongProperty("curr_wind_degs");
      
      /*
       "clouds": {
        "all": 75 //cloudiness %
    }
       */
      
      city_weather_cond_entity.addLongProperty("curr_clouds_all");
      
      /*
       "rain":{"3h":3},//rain volume for the last 3 hrs
       */
      city_weather_cond_entity.addLongProperty("curr_rain_last3hrs");
      
      /*
       "snow":{"3h":3},//snow volume for the last 3 hrs
       */
      
      city_weather_cond_entity.addLongProperty("curr_snow_last3hrs");
      
      /*
       "dt": 1437667656//unix utc
       */
      
      city_weather_cond_entity.addLongProperty("curr_data_calc_time");
      
      /*
       "sys": {        
        "sunrise": 1437597912,//unix utc
        "sunset": 1437638505//unix utc
    }
       */
      
      city_weather_cond_entity.addLongProperty("curr_sys_sunrise_time");
      city_weather_cond_entity.addLongProperty("curr_sys_sunset_time");
      
      return city_weather_cond_entity;
	}
}
