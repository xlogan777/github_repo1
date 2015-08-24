package jmtechsvcs.myweatherapp.fragmentpkg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;

/**
 * Created by jimmy on 8/22/2015.
 */
public class WeatherStationSimpleArrayAdapter extends ArrayAdapter<WeatherStationInfoTable>
{
    private static final String LOGTAG = "WthrStaSimpArrayAdapter";

    //these are used to setup the row view for
    //this adapter.
    private final Context context;
    private final List<WeatherStationInfoTable> values;

    public WeatherStationSimpleArrayAdapter(Context context, List<WeatherStationInfoTable> values)
    {
        //call the super class to setup the xml layout with the
        //input values.
        super( context, R.layout.weather_station_info_row_layout, values);

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
        View rowView = inflater.inflate(R.layout.weather_station_info_row_layout, parent, false);

        Log.d(LOGTAG,"size of list = "+values.size()+", item provided = "+position);

        //get the row item from the list container to use to set the view accordingly.
        WeatherStationInfoTable item_row = values.get(position);

        //TODO: add the correct conversions here for the items that need to be converted.

        //add the items to the view here for a row item.
        TextView textView = (TextView)rowView.findViewById(R.id.station_name_val);
        textView.setText(item_row.getStation_name());

        textView = (TextView)rowView.findViewById(R.id.station_id_val);
        textView.setText(item_row.getStation_id()+"");

        textView = (TextView)rowView.findViewById(R.id.station_time_val);
        textView.setText(item_row.getLast_update_time()+"");

        textView = (TextView)rowView.findViewById(R.id.temp_val);
        textView.setText(item_row.getStation_temp()+"");

        textView = (TextView)rowView.findViewById(R.id.pressure_val);
        textView.setText(item_row.getStation_pressure()+"");

        textView = (TextView)rowView.findViewById(R.id.humidity_val);
        textView.setText(item_row.getStation_humidity()+"");

        textView = (TextView)rowView.findViewById(R.id.wind_speed_val);
        textView.setText(item_row.getStation_wind_speed()+"");

        textView = (TextView)rowView.findViewById(R.id.wind_deg_val);
        textView.setText(item_row.getStation_wind_deg()+"");

        textView = (TextView)rowView.findViewById(R.id.wind_gust_val);
        textView.setText(item_row.getStation_wind_gust()+"");

        textView = (TextView)rowView.findViewById(R.id.visibility_val);
        textView.setText(item_row.getStation_visibility_dist()+"");

        textView = (TextView)rowView.findViewById(R.id.dewpoint_val);
        textView.setText(item_row.getStation_calc_dewpt()+"");

        textView = (TextView)rowView.findViewById(R.id.humidex_val);
        textView.setText(item_row.getStation_calc_humidex()+"");

        textView = (TextView)rowView.findViewById(R.id.cloud_cond_val);
        textView.setText(item_row.getStation_clouds_cond());

        textView = (TextView)rowView.findViewById(R.id.rain_today_val);
        textView.setText(item_row.getStation_rain_today()+"");

        //return the row view for this inflated item.
        return rowView;
    }
}
