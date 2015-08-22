package jmtechsvcs.myweatherapp.fragmentpkg;

import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;

//class to show the weather station data here for a city data set.
public class WeatherStationFragment extends ListFragment
{
    private static String LOGTAG = "WeatherStationFragment";
    private List<WeatherStationInfoTable> weatherStationList;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeatherStationFragment()
    {

    }

    //set data list here.
    public void setWeatherStationList(List<WeatherStationInfoTable> weatherStationList)
    {
        this.weatherStationList = weatherStationList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(LOGTAG,"size of weather stations = "+weatherStationList.size());

        //create the simple array adapter
        WeatherStationSimpleArrayAdapter simpleArrayAdapter =
                new WeatherStationSimpleArrayAdapter(getActivity(), weatherStationList);

        //set the custom adapter to the list adapter here for this frag list.
        setListAdapter(simpleArrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        //call super class.
        super.onActivityCreated(savedInstanceState);

        //allow for scrolling inside the list fragment container.
        this.getListView().setScrollContainer(true);
    }
}
