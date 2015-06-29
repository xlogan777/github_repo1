package tech.jm.myappandroid2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity2 extends ActionBarActivity {

    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        Log.d("MainActivity2","started activity with fragments.");

        final TilesFragment tf = new TilesFragment();
        final DetailsFragment df = new DetailsFragment();

        final FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //this is adding the frags to a frame layout obj in the layout of this activity.

        ft.add(R.id.titleFrame, tf);
        ft.add(R.id.detailFrame,df);

        //or you can add it to the main layout for this activity..by getting the layout id
        //and attaching to the root of the layout these fragments.
        //ft.add(R.id.my_linear_layout, df);

        ft.commit();

        final Button hide_show_tiles = (Button)findViewById(R.id.hide_show_tiles);

        hide_show_tiles.setOnClickListener( new Button.OnClickListener()
           {
               public void onClick(View view)
               {
                    FragmentTransaction ft = fm.beginTransaction();
                    if(df.isHidden())
                    {
                        ft.show(df);
                    }
                    else
                    {
                        ft.hide(df);
                    }
                   ft.commit();
               }
           }
        );

        final Button hide_show_details = (Button)findViewById(R.id.hide_show_details);

        hide_show_details.setOnClickListener( new Button.OnClickListener()
            {
                public void onClick(View view)
                {
                    FragmentTransaction ft = fm.beginTransaction();
                    if(tf.isHidden())
                    {
                        ft.show(tf);
                    }
                    else
                    {
                        ft.hide(tf);
                    }
                    ft.commit();
                }
            }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("MainActivity2","hahahah");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
