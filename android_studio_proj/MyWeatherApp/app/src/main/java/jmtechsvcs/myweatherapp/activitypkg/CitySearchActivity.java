package jmtechsvcs.myweatherapp.activitypkg;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.fragmentpkg.CityListFragment;
import jmtechsvcs.myweatherapp.fragmentpkg.SpinnerDialog;
import jmtechsvcs.myweatherapp.fragmentpkg.WeatherOptionsFragment;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

public class CitySearchActivity extends Activity implements CityListFragment.OnFragmentInteractionListener
{
    private static String LOGTAG = "CitySearchActivity";
    private List<CityInfoTable> cityList;

    //acces the city list of data here.
    public List<CityInfoTable> getCityList(){
        return cityList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        //NOTE: do this code in the application context..at the application level.
        //for $$$ purposes.
        //add the google admob view for add display.
        //https://developers.google.com/admob/android/quick-start
//        AdView mAdView = (AdView)findViewById(R.id.adView);
//        AdRequest request = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
//                .addTestDevice("CA21EF673EA1B4FDE2DBBC38FFA4DFE")  // LG G2 phone md5 hash id.
//                .build();
//        mAdView.loadAd(request);

        final Button search_button = (Button)findViewById(R.id.performsearch);
        final Button clear_button = (Button)findViewById(R.id.cleardata);

        //add the event handler for clearing the fragment
        clear_button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){

                        //clear the inputs from user entered data.
                        ((EditText)findViewById(R.id.city_name_input)).setText("");
                        ((EditText)findViewById(R.id.cc_input)).setText("");

                        //remove fragment.
                        removeFragment();
                    }
                }
        );

        //add the event handler for search event.
        search_button.setOnClickListener(new Button.OnClickListener(){
             public void onClick(View view) {
                 Log.d(LOGTAG, "clicked the search button.");

                 //remove fragment.
                 removeFragment();

                 //get the text from both the cc and the city edit boxes.
                 String city_name = ((EditText) findViewById(R.id.city_name_input)).getText().toString();
                 String cc = ((EditText) findViewById(R.id.cc_input)).getText().toString();

                 //hide the keyboard input here.
                 hideKeyboardInput(view);

                 //if we dont have any data then do nothing.
                 if (city_name.length() == 0 && cc.length() == 0) {
                     Log.d(LOGTAG, "no data to use for search..do nothing.");
                 }
                 else
                 {
                     //use these 2 params to get the list of cities this matches.
                     Log.d(LOGTAG, "cityname = " + city_name + ", cc = " + cc);

                     //set the query params for the db processing api
                     BeanQueryParams bqp = new BeanQueryParams();
                     bqp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_CITY_INFO_TABLE_LIST_TYPE);
                     bqp.setCityName(city_name);
                     bqp.setCountryCode(cc);

                     //get the list of items here to set for the
                     List<CityInfoTable> list_city_info =
                             WeatherDbProcessing.getBeanByQueryParamsList(bqp, getApplicationContext(), new CityInfoTable());

                     if (list_city_info != null && list_city_info.size() > 0) {
                         //assign the city list to the list used by the fragment.
                         cityList = list_city_info;

                         //get the frag mgr and create a tx to add frags.
                         FragmentManager fm = getFragmentManager();
                         FragmentTransaction ft = fm.beginTransaction();

                         //create the fragment.
                         CityListFragment frag = new CityListFragment();

                         //add fragment to main activity layout
                         ft.add(R.id.frame_frag_layout, frag);

                         //commit this activity.
                         ft.commit();
                     }
                     else
                     {
                         Log.d(LOGTAG, "no data to display for fragments.");
                     }
                }
             }
         }
        );
    }

    private void hideKeyboardInput(View view)
    {
        //get the input method service from the app context.
        InputMethodManager im = (InputMethodManager)
                getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        //using the view, get the window and hide the soft input method, the keyboard in this case
        im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //getCurrentFocus().getWindowToken(),
    }

    protected void onResume()
    {
        super.onResume();

        //intent filter to allow for listening to this set of actions.
        IntentFilter iff = new IntentFilter("CallBackAction");
        iff.addAction(WeatherAppUtils.STOP_SPINNER_ACTION);
        iff.addAction(WeatherAppUtils.START_CURRENT_WEATHER_ACTIVITY_ACTION);
        iff.addAction(WeatherAppUtils.START_DAILY_WEATHER_ACTIVITY_ACTION);
        iff.addAction(WeatherAppUtils.START_WEATHER_STATION_ACTIVITY_ACTION);
        iff.addAction(WeatherAppUtils.START_CURRENT_HOURLY_FORECAST_ACTIVITY_ACTION);

        //register with local rcv
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, iff);
    }

    protected void onPause()
    {
        super.onPause();

        //unregister with rcv
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    public void removeFragment()
    {
        //use a non empty list to do clean up of fragment.
        if(cityList != null)
        {
            cityList.clear();

            //get frag mgr to clear frag item from view.
            FragmentManager fm = getFragmentManager();

            //start a frag tx.
            FragmentTransaction ft = fm.beginTransaction();

            //find the fragment tied to this view item.
            Fragment fragment = fm.findFragmentById(R.id.frame_frag_layout);

            //if their is a fragment in this view destroy it..
            if(fragment != null)
            {
                // remove the existing fragment from the frag tx.
                ft.remove(fragment);

                //commit this change.
                ft.commit();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_city_search, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if(id == R.id.action_settings){
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onFragmentInteraction(CityInfoTable data)
    {
        //call local method to use this item.
        this.showWeatherOptions(data);
    }

    private void removeDialogFragmentViaTag(String tag)
    {
        //get a frag tx and check to see if we have another frag.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(tag);

        //remove frag if it exists.
        if(prev != null)
        {
            ft.remove(prev);
            ft.commit();
        }
    }

    private void showWeatherOptions(CityInfoTable data)
    {
        //remove the frag if it exists.
        removeDialogFragmentViaTag("WeatherOptionsFragment");

        //create the dialog fragment and show the fragment with hooks for it to the
        //fragment mgr.
        WeatherOptionsFragment optionsFragment = new WeatherOptionsFragment();
        optionsFragment.setData(data);

        //show the fragment and also tied to the frag mgr
        optionsFragment.show(getFragmentManager(), "WeatherOptionsFragment");
    }

    //local broadcast receiver.
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            // intent can contain anydata
            Log.d(LOGTAG, "onReceive called for usage.");

            Intent local_intent = null;

            if(intent.getAction().equals(WeatherAppUtils.STOP_SPINNER_ACTION))
            {
                //stop the loading spinner.
                stopLoadingDialog();
            }
            else if(intent.getAction().equals(WeatherAppUtils.START_CURRENT_WEATHER_ACTIVITY_ACTION))
            {
                //add this to the android back stack for here to the next activity
                //being activated.
                local_intent = new Intent(CitySearchActivity.this, CurrentWeatherActivity.class);
            }
            else if(intent.getAction().equals(WeatherAppUtils.START_DAILY_WEATHER_ACTIVITY_ACTION))
            {
                //add this to the android back stack for here to the next activity
                //being activated.
                local_intent = new Intent(CitySearchActivity.this, DailyWeatherForecastActivity.class);
            }
            else if(intent.getAction().equals(WeatherAppUtils.START_WEATHER_STATION_ACTIVITY_ACTION))
            {
                //add this to the android back stack for here to the next activity
                //being activated.
                local_intent = new Intent(CitySearchActivity.this, WeatherStationDisplayActivity.class);
            }
            else if(intent.getAction().equals(WeatherAppUtils.START_CURRENT_HOURLY_FORECAST_ACTIVITY_ACTION))
            {
                //add this to the android back stack for here to the next activity
                //being activated.
                local_intent = new Intent(CitySearchActivity.this, HourlyWeatherActivity.class);
            }

            //check to see if we have a valid intent.
            if(local_intent != null)
            {
                //add the bundle to the intent.
                local_intent.putExtras(intent.getExtras());

                //start the activity with info on the city id to be used there.
                startActivity(local_intent);
            }
        }
    };

    public void showLoadingDialog()
    {
        //remove fragment
        removeDialogFragmentViaTag("SpinnerDialog");

        //create the loading spinner here
        SpinnerDialog my_frag = new SpinnerDialog();

        //load the fragment spinner here.
        my_frag.show(getFragmentManager(), "SpinnerDialog");
    }

    public void stopLoadingDialog()
    {
        //remove fragment.
        removeDialogFragmentViaTag("SpinnerDialog");
    }
}
