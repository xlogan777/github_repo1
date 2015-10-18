package jmtechsvcs.myweatherapp.activitypkg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.GoogleAnalyticsTracking;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//https://github.com/jjoe64/GraphView
//https://github.com/jjoe64/GraphView-Demos
//http://jjoe64.github.io/GraphView/javadoc/
public class HourlyGraphActivity extends Activity
{
    private static final String LOGTAG = "HourlyGraphActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hourly_graph);

        //send the tracking ofthe viewing of this screen.
        GoogleAnalyticsTracking.sendScreenViewEvents(HourlyGraphActivity.class.getSimpleName());

        //get the bundle data from the intent
        Bundle bundle = getIntent().getExtras();
        long city_id = bundle.getLong("city_id");

        //create an empty list to be used for population by apu call
        List<HourlyWeatherInfoTable> current_date_list = new ArrayList<HourlyWeatherInfoTable>();

        //fill in the data to the array list if conditions are good.
        WeatherAppUtils.getHourlyWeatherData
                (city_id, current_date_list, WeatherAppUtils.HOURLY_FILL_LIST_SIZE, getApplicationContext());

        //get the graph view obj
        GraphView graph = (GraphView)findViewById(R.id.graphViewId);

        if(current_date_list.size() > 0)
        {
            //save the datapoints as a list.
            List<DataPoint> dataPoints = new ArrayList<DataPoint>();

            for(HourlyWeatherInfoTable hourly_data_item :current_date_list)
            {
                //this will represent the x-axis
                long from_date = hourly_data_item.getHourly_from_weather_date();
                DateTime date1 = new DateTime(from_date);//make date obj for datapoint.

                //this will represent the y-axis
                double temp_val = hourly_data_item.getHourly_temp_value();

                //date is x here, y is temp.
                DataPoint dp = new DataPoint(date1.toDate(), temp_val);
                dataPoints.add(dp);
            }

            //get the size of the data pts list.
            int dp_size = dataPoints.size();

            if(dp_size > 0)
            {
                //provide data type, and since size isnt enough, then this call will
                //return new array with correct size.
                DataPoint [] dp_array = dataPoints.toArray(new DataPoint[0]);

                // you can directly pass Date objects to DataPoint-Constructor
                // this will convert the Date to double via Date#getTime()
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dp_array);
                graph.addSeries(series);

                // set date label formatter
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            //this is the date field as x, need to make string to show it
                            //create the date format
                            SimpleDateFormat sdf = new SimpleDateFormat("HH");
                            String time_format = sdf.format(new Date((long) value));
                            return time_format+"H";
                        } else {
                            //this the temp field as y, need to make string
                            return super.formatLabel(value, isValueX) + "F";
                        }
                    }
                });

                //set based on the number u have of data points.
                graph.getGridLabelRenderer().setNumHorizontalLabels(dataPoints.size());
                graph.getViewport().setXAxisBoundsManual(true);

                // legend code
//                series.setTitle("temp");
//                graph.getLegendRenderer().setVisible(true);
//                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
            }
            else
            {
                Log.d(LOGTAG,"dp list size is less than 0..issue");
            }
        }
        else
        {
            Log.d(LOGTAG,"didnt get back any data to show in the graph..issues.");
        }
    }
}
