package com.weatherappdao.greendao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyWeatherDaoGenerator 
{
	//test
	public static void main(String[] args)
	{
		//create green dao schema obj with version number, and pkg for the generated files.
		int db_version_number = 1;
		String pkg_name = "jmtechsvcs.myweatherapp.greendaosrcgenpkg";
		
		//create schema obj.
      Schema schema = new Schema(db_version_number, pkg_name);
     
      //this will allow for keep sections to be generated for all entities under this schema.
      //you can place your code in here...to keep after each generation of the code.
      schema.enableKeepSectionsByDefault();

		System.out.println("create city id info table");
		Entity city_info_table = createCityInfoTable(schema);
		
		System.out.println("create city weather cond table");
		Entity city_weather_cond_table = createCityWeatherCurrCondTable(schema);
		
		System.out.println("create weather icon table");
		Entity weather_icon_table = createWeatherIconTable(schema);
		
		System.out.println("create weather station info table");
		Entity weather_station_entity =  createWeatherStationInfoTable(schema);
		
		System.out.println("create daily weather info table");
      Entity daily_weather_entity = createDailyWeatherInfoTable(schema);
      
      System.out.println("create hourly weather info table");
      Entity hourly_weather_entity = createHourlyWeatherInfoTable(schema);
		
		//generate the source code
		try
		{
			//create dao generator obj.
			DaoGenerator dao_generator = new DaoGenerator();
			
			//generate code based on schema and place in outdir.			
			//dao_generator.generateAll(schema, "c:/users/jimmy/desktop");
			dao_generator.generateAll(schema, "c:/users/menaj/desktop");
			
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
      city_weather_cond_entity.addLongProperty("curr_weather_id").notNull();
      city_weather_cond_entity.addStringProperty("curr_weather_main").notNull();
      city_weather_cond_entity.addStringProperty("curr_weather_desc").notNull();
      city_weather_cond_entity.addStringProperty("curr_weather_icon").notNull();
      
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
      
      city_weather_cond_entity.addDoubleProperty("curr_main_temp").notNull();
      city_weather_cond_entity.addLongProperty("curr_main_pressure").notNull();
      city_weather_cond_entity.addLongProperty("curr_main_humidity").notNull();
      city_weather_cond_entity.addDoubleProperty("curr_main_temp_min").notNull();
      city_weather_cond_entity.addDoubleProperty("curr_main_temp_max").notNull();
      city_weather_cond_entity.addLongProperty("curr_main_sea_level").notNull();
      city_weather_cond_entity.addLongProperty("curr_main_grnd_level").notNull();
      
      /*
       "wind": {
        "speed": 4.1,//meters/sec
        "deg": 160//degrees
    },
       */
      
      city_weather_cond_entity.addDoubleProperty("curr_wind_speed").notNull();
      city_weather_cond_entity.addLongProperty("curr_wind_degs").notNull();
      
      
      /*
         <wind>
          <speed value="7.78" name="Moderate breeze"/>
          <direction value="140" code="SE" name="SouthEast"/>
         </wind>
       */
      city_weather_cond_entity.addStringProperty("curr_wind_speed_name").notNull();//from xml
      city_weather_cond_entity.addStringProperty("curr_wind_dirr_code").notNull();//from xml
      
      /*
       "clouds": {
        "all": 75 //cloudiness %
    }
       */
      
      city_weather_cond_entity.addLongProperty("curr_clouds_all").notNull();
      
      //<visibility value="10000"/>
      city_weather_cond_entity.addLongProperty("curr_visibility").notNull();//from xml
      
      /*
       <precipitation mode="no" value = "100"/>
       */
      city_weather_cond_entity.addStringProperty("precipitation_mode").notNull();//from xml
      city_weather_cond_entity.addDoubleProperty("precipitation_value").notNull();//from xml
      
      /*
       "rain":{"3h":3},//rain volume for the last 3 hrs
       */
      city_weather_cond_entity.addDoubleProperty("curr_rain_last3hrs").notNull();
      
      /*
       "snow":{"3h":3},//snow volume for the last 3 hrs
       */
      
      city_weather_cond_entity.addDoubleProperty("curr_snow_last3hrs").notNull();
      
      /*
       "dt": 1437667656//unix utc
       */
      
      city_weather_cond_entity.addLongProperty("curr_data_calc_time").notNull();
      
      /*
       "sys": {        
        "sunrise": 1437597912,//unix utc
        "sunset": 1437638505//unix utc
    }
       */
      
      city_weather_cond_entity.addLongProperty("curr_sys_sunrise_time").notNull();
      city_weather_cond_entity.addLongProperty("curr_sys_sunset_time").notNull();
      
      return city_weather_cond_entity;
	}
	
	public static Entity createWeatherIconTable(Schema schema)
	{
	   //table name
      Entity weather_icon_entity = schema.addEntity("WeatherIconTable");
      
      //add pk
      weather_icon_entity.addStringProperty("icon_id").primaryKey().notNull();
      
      //add non pk fields
      weather_icon_entity.addStringProperty("icon_url").notNull();
      weather_icon_entity.addStringProperty("image_path").notNull();
      
      //this is kept here in case we want to store the raw image as a blob
      weather_icon_entity.addByteArrayProperty("image_raw").notNull();
      
      return weather_icon_entity;
	}
	
	public static Entity createWeatherStationInfoTable(Schema schema)
	{
	   //table name
      Entity weather_station_entity = schema.addEntity("WeatherStationInfoTable");
      
      //add pk
      weather_station_entity.addLongProperty("station_id").primaryKey().notNull();
      
      //add non pk fields
      weather_station_entity.addLongProperty("city_id").notNull();
      
      weather_station_entity.addDoubleProperty("station_lat").notNull();
      weather_station_entity.addDoubleProperty("station_lon").notNull();
      weather_station_entity.addStringProperty("station_name").notNull();
      weather_station_entity.addDoubleProperty("station_temp").notNull();
      weather_station_entity.addLongProperty("station_pressure").notNull();
      weather_station_entity.addLongProperty("station_humidity").notNull();
      weather_station_entity.addDoubleProperty("station_wind_speed").notNull();
      weather_station_entity.addLongProperty("station_wind_deg").notNull();
      weather_station_entity.addDoubleProperty("station_wind_gust").notNull();
      weather_station_entity.addLongProperty("station_visibility_dist").notNull();
      weather_station_entity.addDoubleProperty("station_calc_dewpt").notNull();
      weather_station_entity.addDoubleProperty("station_calc_humidex").notNull();
      weather_station_entity.addLongProperty("station_clouds_dist").notNull();
      weather_station_entity.addStringProperty("station_clouds_cond").notNull();
      weather_station_entity.addDoubleProperty("station_rain_1h").notNull();
      weather_station_entity.addDoubleProperty("station_rain_24h").notNull();
      weather_station_entity.addDoubleProperty("station_rain_today").notNull();
      weather_station_entity.addLongProperty("last_update_time").notNull();
      
      return weather_station_entity;
	}
	
	public static Entity createDailyWeatherInfoTable(Schema schema)
	{
	   /*
	    <time day="2015-10-08">
         <symbol number="600" name="light snow" var="13d"/>
         <precipitation value="0.91" type="rain"/>
         <windDirection deg="306" code="NW" name="Northwest"/>
         <windSpeed mps="15.68" name="High wind, near gale"/>
         <temperature day="37.78" min="27.99" max="39.45" night="33.04" eve="37" morn="27.99"/>
         <pressure unit="hPa" value="1008.27"/>
         <humidity value="92" unit="%"/>
         <clouds value="broken clouds" all="64" unit="%"/>
       </time>
	    */
	   
	   //table name
      Entity daily_weather_entity = schema.addEntity("DailyWeatherInfoTable");
      
      //add pk, this will be a auto inc pk since we will have the same items repeated here
      //and will be updated accordingly.
      daily_weather_entity.addIdProperty().primaryKey().autoincrement();
      
      //add non pk fields
      daily_weather_entity.addLongProperty("city_id").notNull();
      
      //<symbol number="600" name="light snow" var="13d"/>
      daily_weather_entity.addLongProperty("daily_symbol_number").notNull();
      daily_weather_entity.addStringProperty("daily_symbol_name").notNull();//done
      daily_weather_entity.addStringProperty("daily_symbol_var").notNull();
      
      //<precipitation value="0.91" type="rain"/>
      daily_weather_entity.addDoubleProperty("daily_precip_value").notNull();//done
      daily_weather_entity.addStringProperty("daily_precip_type").notNull();//done
      daily_weather_entity.addStringProperty("daily_precip_unit").notNull();//done
      
      //<windDirection deg="306" code="NW" name="Northwest"/>
      daily_weather_entity.addLongProperty("daily_wind_dirr_deg").notNull();//done
      daily_weather_entity.addStringProperty("daily_wind_dirr_code").notNull();//done
      daily_weather_entity.addStringProperty("daily_wind_dirr_name").notNull();//done
      
      //<windSpeed mps="15.68" name="High wind, near gale"/>
      daily_weather_entity.addDoubleProperty("daily_wind_speed_mps").notNull();//done
      daily_weather_entity.addStringProperty("daily_wind_speed_name").notNull();//done
      
      //<temperature day="37.78" min="27.99" max="39.45" night="33.04" eve="37" morn="27.99"/>
      daily_weather_entity.addDoubleProperty("daily_temp").notNull();//done
      daily_weather_entity.addDoubleProperty("daily_min_temp").notNull();//done
      daily_weather_entity.addDoubleProperty("daily_max_temp").notNull();//done
      daily_weather_entity.addDoubleProperty("daily_night_temp").notNull();//done
      daily_weather_entity.addDoubleProperty("daily_evening_temp").notNull();//done
      daily_weather_entity.addDoubleProperty("daily_morning_temp").notNull();//done
      
      //<pressure unit="hPa" value="1008.27"/>
      daily_weather_entity.addStringProperty("daily_pressure_unit").notNull();//not needed
      daily_weather_entity.addDoubleProperty("daily_pressure_value").notNull();//done
      
      //<humidity value="92" unit="%"/>
      daily_weather_entity.addLongProperty("daily_humidity_val").notNull();//done
      daily_weather_entity.addStringProperty("daily_humidity_unit").notNull();//not needed
      
      //<clouds value="broken clouds" all="64" unit="%"/>
      daily_weather_entity.addStringProperty("daily_clouds_val").notNull();
      daily_weather_entity.addDoubleProperty("daily_clouds_all").notNull();//done
      daily_weather_entity.addStringProperty("daily_clouds_unit").notNull();//not needed
      
      //<time day="2015-10-08">
      daily_weather_entity.addLongProperty("daily_weather_date").notNull();//done
      
      return daily_weather_entity;
	}
	
	  public static Entity createHourlyWeatherInfoTable(Schema schema)
	  {
	     /*
	     <time from="2015-10-08T00:00:00" to="2015-10-08T03:00:00">
            <symbol number="500" name="light rain" var="10n"/>
            <precipitation unit="3h" value="0.015" type="rain"/>
            <windDirection deg="146.004" code="SE" name="SouthEast"/>
            <windSpeed mps="1.34" name="Calm"/>
            <temperature unit="imperial" value="42.17" min="42.17" max="47.11"/>
            <pressure unit="hPa" value="711.83"/>
            <humidity value="96" unit="%"/>
            <clouds value="broken clouds" all="56" unit="%"/>
        </time>
	      */
	     
	      //table name
	      Entity hourly_weather_entity = schema.addEntity("HourlyWeatherInfoTable");
	      
	      //add pk, this will be a auto inc pk since we will have the same items repeated here
	      //and will be updated accordingly.
	      hourly_weather_entity.addIdProperty().primaryKey().autoincrement();
	      
	      //add non pk fields
	      hourly_weather_entity.addLongProperty("city_id").notNull();
	      
	      //<time from="2015-10-08T00:00:00" to="2015-10-08T03:00:00">
	      hourly_weather_entity.addLongProperty("hourly_from_weather_date").notNull();//done
	      hourly_weather_entity.addLongProperty("hourly_to_weather_date").notNull();//done
	      
	      //<symbol number="500" name="light rain" var="10n"/>
	      hourly_weather_entity.addLongProperty("hourly_symbol_number").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_symbol_name").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_symbol_var").notNull();//done
	      
	      //<precipitation unit="3h" value="0.015" type="rain"/>
	      hourly_weather_entity.addStringProperty("hourly_precip_unit").notNull();//done
	      hourly_weather_entity.addDoubleProperty("hourly_precip_value").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_precip_type").notNull();//done
	      
	      //<windDirection deg="146.004" code="SE" name="SouthEast"/>
	      hourly_weather_entity.addDoubleProperty("hourly_wind_dirr_deg").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_wind_dirr_code").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_wind_dirr_name").notNull();//done
	      
	      //<windSpeed mps="1.34" name="Calm"/>
	      hourly_weather_entity.addDoubleProperty("hourly_wind_speed_mps").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_wind_speed_name").notNull();//done
	      
	      //<temperature unit="imperial" value="42.17" min="42.17" max="47.11"/>
	      hourly_weather_entity.addStringProperty("hourly_unit").notNull();//done
	      hourly_weather_entity.addDoubleProperty("hourly_temp_value").notNull();//done
	      hourly_weather_entity.addDoubleProperty("hourly_min_temp").notNull();//done
	      hourly_weather_entity.addDoubleProperty("hourly_max_temp").notNull();//done
	      
	      //<pressure unit="hPa" value="711.83"/>
	      hourly_weather_entity.addStringProperty("hourly_pressure_unit").notNull();//done
	      hourly_weather_entity.addDoubleProperty("hourly_pressure_value").notNull();//done
	      
	      //<humidity value="96" unit="%"/>
	      hourly_weather_entity.addLongProperty("hourly_humidity_val").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_humidity_unit").notNull();//done
	      
	      //<clouds value="broken clouds" all="56" unit="%"/>
	      hourly_weather_entity.addStringProperty("hourly_clouds_val").notNull();//done
	      hourly_weather_entity.addDoubleProperty("hourly_clouds_all").notNull();//done
	      hourly_weather_entity.addStringProperty("hourly_clouds_unit").notNull();//done
	      
	      return hourly_weather_entity;
	  }
}
