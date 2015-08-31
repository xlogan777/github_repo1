package jmtechsvcs.myweatherapp.activitypkg;

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

        //TODO: need to add other text view items here.

        //return the row view for this inflated item.
        return rowView;
    }
}
