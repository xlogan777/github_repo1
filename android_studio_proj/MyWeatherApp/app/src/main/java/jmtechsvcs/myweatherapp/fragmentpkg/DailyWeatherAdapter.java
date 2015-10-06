package jmtechsvcs.myweatherapp.fragmentpkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DailyWeatherInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.MathUtils;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * Created by jimmy on 8/30/2015.
 */
public class DailyWeatherAdapter extends ArrayAdapter<DailyWeatherInfoTable>
{
    private static final String LOGTAG = "DailyWeatherAdapter";

    //these are used to setup the row view for
    //this adapter.
    private final Context context;
    private final List<DailyWeatherInfoTable> values;

    public DailyWeatherAdapter(Context context, List<DailyWeatherInfoTable> values)
    {
        //call the super class to setup the xml layout with the
        //input values.
        super(context, R.layout.daily_weather_row_layout, values);

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
        View rowView = inflater.inflate(R.layout.daily_weather_row_layout, parent, false);

        //get the item row from the values list.
        DailyWeatherInfoTable item_row = values.get(position);

        //any else is optional to display..so treat it that way.
        String result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getDaily_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_temp_val)).setText(
                    MathUtils.getTempString(item_row.getDaily_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_temp_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getDaily_min_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_min_val)).setText(
                    MathUtils.getTempString(item_row.getDaily_min_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_min_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getDaily_max_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_max_val)).setText(
                    MathUtils.getTempString(item_row.getDaily_max_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_max_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getDaily_morning_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_morning_val)).setText(
                    MathUtils.getTempString(item_row.getDaily_morning_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_morning_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getDaily_evening_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_evening_val)).setText(
                    MathUtils.getTempString(item_row.getDaily_evening_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_evening_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getDaily_night_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_night_val)).setText(
                    MathUtils.getTempString(item_row.getDaily_night_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_night_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getHumidity_val());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_humidity_val)).setText(
                    MathUtils.getPercentString(item_row.getHumidity_val()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_humidity_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getClouds_all());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_cloud_pect_val)).setText(
                    MathUtils.getPercentString(item_row.getClouds_all()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_cloud_pect_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getPressure_value());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_pressure_val)).setText(
                    MathUtils.getPressureString((long)item_row.getPressure_value()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_pressure_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getPressure_value());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_precipitation_val)).setText(
                    MathUtils.getDistanceString(
                            (long)item_row.getPressure_value(), 'I') + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_precipitation_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getWind_dirr_deg());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_wind_deg_val)).setText(
                            MathUtils.getDegreeString(item_row.getWind_dirr_deg()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_wind_deg_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getWind_speed_mps());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_wind_speed_val)).setText(
                    MathUtils.getVelocityString(item_row.getWind_speed_mps()) + "");
        else
            ((TextView)rowView.findViewById(R.id.daily_wind_speed_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getDaily_weather_date());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.weather_date_val)).setText(
                    WeatherAppUtils.getUtcFromUtcSeconds(item_row.getDaily_weather_date(), WeatherAppUtils.UTC_DATE_FORMAT) + "");
        else
            ((TextView)rowView.findViewById(R.id.weather_date_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getDaily_symbol_name());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_sym_name_val)).setText(item_row.getDaily_symbol_name());
        else
            ((TextView)rowView.findViewById(R.id.daily_sym_name_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getDaily_precip_type());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_precip_type_val)).setText(item_row.getDaily_precip_type());
        else
            ((TextView)rowView.findViewById(R.id.daily_precip_type_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getDaily_precip_type());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_wind_dirr_code_val)).setText(item_row.getDaily_precip_type());
        else
            ((TextView)rowView.findViewById(R.id.daily_wind_dirr_code_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getWind_dirr_name());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_wind_name_val)).setText(item_row.getWind_dirr_name());
        else
            ((TextView)rowView.findViewById(R.id.daily_wind_name_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getWind_speed_name());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_wind_speed_name_val)).setText(item_row.getWind_speed_name());
        else
            ((TextView)rowView.findViewById(R.id.daily_wind_speed_name_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getClouds_val());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.daily_clouds_val)).setText(item_row.getClouds_val());
        else
            ((TextView)rowView.findViewById(R.id.daily_clouds_val)).setText(result);

        //TODO: need to setup the image view item here.

        //return the row view for this inflated item.
        return rowView;
    }
}
