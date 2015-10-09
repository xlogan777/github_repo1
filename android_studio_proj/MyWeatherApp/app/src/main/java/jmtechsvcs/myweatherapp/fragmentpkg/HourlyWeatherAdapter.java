package jmtechsvcs.myweatherapp.fragmentpkg;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTable;
import jmtechsvcs.myweatherapp.utilspkg.MathUtils;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * Created by Menaj on 9/23/2015.
 */
public class HourlyWeatherAdapter extends ArrayAdapter<HourlyWeatherInfoTable>
{
    private static final String LOGTAG = "HourlyWeatherAdapter";

    //these are used to setup the row view for
    //this adapter.
    private final Context context;
    private final List<HourlyWeatherInfoTable> values;

    public HourlyWeatherAdapter(Context context, List<HourlyWeatherInfoTable> values)
    {
        //call the super class to setup the xml layout with the
        //input values.
        super(context, R.layout.hourly_weather_row_layout, values);

        //save the ctx and data from the const params.
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //this will use the inflater service to be used to inflate the
        //row layout.
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);

        //inflate the row view here for the xml layout file and attach to the parent
        //view group.
        View rowView = inflater.inflate(R.layout.hourly_weather_row_layout, parent, false);

        //get the item row from the values list.
        HourlyWeatherInfoTable item_row = values.get(position);

        //any else is optional to display..so treat it that way.
        String result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getHourly_from_weather_date());

        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HrFromDate_val)).setText(
                    WeatherAppUtils.getUtcFromUtcSeconds(item_row.getHourly_from_weather_date(),WeatherAppUtils.UTC_DATE_FORMAT_hms) + "");
        else
            ((TextView)rowView.findViewById(R.id.HrFromDate_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getHourly_to_weather_date());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HrToDate_val)).setText(
                    WeatherAppUtils.getUtcFromUtcSeconds(item_row.getHourly_to_weather_date(), WeatherAppUtils.UTC_DATE_FORMAT_hms) + "");
        else
            ((TextView)rowView.findViewById(R.id.HrToDate_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getHourly_symbol_number());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HrSymNum_Val)).setText(item_row.getHourly_symbol_number() + "");
        else
            ((TextView)rowView.findViewById(R.id.HrSymNum_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getHourly_symbol_name());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlySymName_val)).setText(item_row.getHourly_symbol_name());
        else
            ((TextView)rowView.findViewById(R.id.HourlySymName_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_precip_value());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyPrecipValue_val)).setText(
                    MathUtils.getDistanceString(item_row.getHourly_precip_value(), 'I') + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyPrecipValue_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getHourly_precip_type());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourPrecipType_val)).setText(item_row.getHourly_precip_type());
        else
            ((TextView)rowView.findViewById(R.id.HourPrecipType_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_wind_dirr_deg());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HrWindDeg_val)).setText(
                    MathUtils.getDegreeString(item_row.getHourly_wind_dirr_deg()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HrWindDeg_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getHourly_wind_dirr_code());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HrWindCode_val)).setText(item_row.getHourly_wind_dirr_code());
        else
            ((TextView)rowView.findViewById(R.id.HrWindCode_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getHourly_wind_dirr_name());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyWindName_val)).setText(item_row.getHourly_wind_dirr_name());
        else
            ((TextView)rowView.findViewById(R.id.HourlyWindName_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_wind_speed_mps());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyWindSpeed_Val)).setText(
                    MathUtils.getVelocityString(item_row.getHourly_wind_speed_mps()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyWindSpeed_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getHourly_wind_speed_name());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyWindSpeedName_val)).setText(item_row.getHourly_wind_speed_name());
        else
            ((TextView)rowView.findViewById(R.id.HourlyWindSpeedName_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_temp_value());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyTemp_Val)).setText(
                    MathUtils.getTempString(item_row.getHourly_temp_value()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyTemp_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_min_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyTempMin_val)).setText(
                    MathUtils.getTempString(item_row.getHourly_min_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyTempMin_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_max_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyTempMax_val)).setText(
                    MathUtils.getTempString(item_row.getHourly_max_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyTempMax_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_pressure_value());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyPressure_Val)).setText(
                    MathUtils.getPressureString((long) item_row.getHourly_pressure_value()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyPressure_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getHourly_humidity_val());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyHumidity_Val)).setText(
                    MathUtils.getPercentString(item_row.getHourly_humidity_val()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyHumidity_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getHourly_clouds_val());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyClouds_val)).setText(item_row.getHourly_clouds_val());
        else
            ((TextView)rowView.findViewById(R.id.HourlyClouds_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_clouds_all());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyCloudsAll_val)).setText(
                    MathUtils.getPercentString(item_row.getHourly_clouds_all()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyCloudsAll_val)).setText(result);

        //get the weather icon table with the var id.
        WeatherIconTable weatherIconTable =
                WeatherAppUtils.getWeatherIconTable
                        (item_row.getHourly_symbol_var(), context);

        //load the weather icon.
        if(weatherIconTable != null)
        {
            //load the bitmap.
            Bitmap bitmap = WeatherAppUtils.loadCityWeatherIcon(weatherIconTable);

            //if not null, then display it.
            if(bitmap != null)
            {
                //set the image bit map here.
                ((ImageView)rowView.findViewById(R.id.imageViewHourly)).setImageBitmap(bitmap);
                Log.d(LOGTAG, "loaded icon weather data for hourly weather.");
            }
        }

        //return the row view for this inflated item.
        return rowView;
    }
}

