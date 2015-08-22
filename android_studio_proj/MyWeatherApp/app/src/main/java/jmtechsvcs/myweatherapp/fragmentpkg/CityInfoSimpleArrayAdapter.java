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
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * Created by jimmy on 8/20/2015.
 */
//this class is the custom array adapter for use in the city list fragment.
public class CityInfoSimpleArrayAdapter extends ArrayAdapter<String>
{
    private static final String LOGTAG = "CityInf[]Adapter";

    //these are used to setup the row view for
    //this adapter.
    private final Context context;
    private final List<String> values;

    public CityInfoSimpleArrayAdapter(Context context, List<String> values)
    {
        //call the super class to setup the xml layout with the
        //input values.
        super( context, R.layout.city_info_row_layout, values);

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
        View rowView = inflater.inflate(R.layout.city_info_row_layout, parent, false);

        //get the data from the list container position and parse the string here to display it.
        String [] items = values.get(position).split(",");
        Log.d(LOGTAG, values.get(position));

        //get the cid from the data string to use as the city id.
        String cid = items[0];
        String city_id = WeatherAppUtils.getDataFromInput(cid);
        TextView textView = (TextView)rowView.findViewById(R.id.city_id_vals);
        textView.setText(city_id);

        String cn = items[1];//city name
        cn = WeatherAppUtils.getDataFromInput(cn);

        String cc = items[2];//country code
        cc = WeatherAppUtils.getDataFromInput(cc);

        textView = (TextView)rowView.findViewById(R.id.cn_cc_vals);
        textView.setText(cn+", "+cc);

        String lat_s = items[3];//lat
        String lat = WeatherAppUtils.getDataFromInput(lat_s);
        textView = (TextView)rowView.findViewById(R.id.lat_vals);
        textView.setText(lat);

        String lon_s = items[4];//lon
        String lon = WeatherAppUtils.getDataFromInput(lon_s);
        textView = (TextView)rowView.findViewById(R.id.lon_vals);
        textView.setText(lon);

        //return the row view for this inflated item.
        return rowView;
    }
}
