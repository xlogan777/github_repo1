package jmtechsvcs.myweatherapp.activitypkg;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.fragmentpkg.CityListFragment;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityInfoTable;
import jmtechsvcs.myweatherapp.utils.BeanQueryParams;
import jmtechsvcs.myweatherapp.utils.WeatherDbProcessing;

public class CitySearchActivity extends ActionBarActivity implements CityListFragment.OnFragmentInteractionListener
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

        final Button search_button = (Button)findViewById(R.id.performsearch);

        //add the event handler for the button.
        search_button.setOnClickListener(new Button.OnClickListener(){
             public void onClick(View view){
                 Log.d(LOGTAG, "clicked the search button.");

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

                     //if their is a fragment in this view destory it..
                     if(fragment != null)
                     {
                         // remove the existing fragment from the frag tx.
                         ft.remove(fragment);

                         //commit this change.
                         ft.commit();
                     }
                 }

                 //get the text from both the cc and the city edit boxes.
                 String city_name = ((EditText)findViewById(R.id.city_name_input)).getText().toString();
                 String cc = ((EditText)findViewById(R.id.cc_input)).getText().toString();

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

                 if(list_city_info != null && list_city_info.size() > 0)
                 {
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
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id)
    {
        Log.d(LOGTAG,"activity_callback = "+id);
    }
}
