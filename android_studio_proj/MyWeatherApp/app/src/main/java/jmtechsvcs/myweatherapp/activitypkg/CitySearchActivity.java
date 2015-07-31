package jmtechsvcs.myweatherapp.activitypkg;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.fragmentpkg.CityListFragment;

public class CitySearchActivity extends ActionBarActivity implements CityListFragment.OnFragmentInteractionListener
{
    private static String LOGTAG = "CitySearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        //get the frag mgr and create a tx to add frags.
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //add fragment to main activity layout
        ft.add(R.id.frame_frag_layout, new CityListFragment());

        //commit this activity.
        ft.commit();

        final Button search_button = (Button)findViewById(R.id.performsearch);

        //add the event handler for the button.
        search_button.setOnClickListener( new Button.OnClickListener()
            {
                public void onClick(View view)
                {
                    Log.d(LOGTAG,"clicked the search button.");
                }
            }
        );

        //do the search here..if items found, then need to show list
        //and implement the on click for the current weather conditions.
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
