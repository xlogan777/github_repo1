package tech.jm.myfirstandroidapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;


public class MainActivity1 extends ActionBarActivity {

    private static final String myMainActivity1 = "MainActivity1";
    private String FRAG1_TAG = "Frag_Tag_1";
    private String FRAG2_TAG = "Frag_Tag_2";

    //method that is invoked when the button is clicked from the UI.
    //this is the callback method for the button.
    //this method will replace the current
    //fragments. The call to commit at the end of the method causes both of the new fragments
    //to become visible simultaneously. The times in the top and bottom views will always
    //be in sync.
    public void update()
    {
        //get current date and time.
        Date time = new Date();

        //begin a frag transaction from the activity.
        FragmentTransaction xact = getFragmentManager().beginTransaction();

        //replace the older fragments named FRAG_TAG1/2, with the new fragments created here
        //via the fragment static method and it being provided a new input Date obj.
        xact.replace( R.id.date_time,  DateTime.newInstance(time), FRAG1_TAG);
        xact.replace( R.id.date_time2, DateTime.newInstance(time), FRAG2_TAG);

        //dont have a back stack.
        xact.addToBackStack(null);

        //allow for the fragments to be updated immediately.
        xact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        //commit these changes, making them available now.
        xact.commit();
        Log.d(myMainActivity1,"finished with the update of the fragments via the callback.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //render the view here.
        setContentView(R.layout.activity_main_activity1);

        //adding an event handler for the button UI item that calls this
        //activities update method.
        ((Button) findViewById(R.id.new_fragments)).setOnClickListener(
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v) { update(); }
                });

        //get the current date.
        Date time = new Date();

        //fragment transaction mgr to allow for creation of a fragment.
        FragmentManager fragMgr = getFragmentManager();
        FragmentTransaction xact = fragMgr.beginTransaction();

        //check to see if a fragment already exists in the mgr, if not create it
        //if so,then dont do anything.
        if(null == fragMgr.findFragmentByTag(FRAG1_TAG))
        {
            //create fragment with layout and assign it the frag_tag name to get it later.
            //it is also creating the fragment obj here and adding it to the frag mgr.
            //this is passing the fragment obj here by calling its default constructor.
            //xact.add(R.id.date_time, new DateTime(), FRAG1_TAG);

            //this will use the static factory from the fragment class and
            //provide it data as if it were a constructor based creation of the fragment class.
            //this is the preferred method to create fragments with static factories.
            xact.add(R.id.date_time, DateTime.newInstance(time), FRAG1_TAG);
        }

        //use tagging and the frag trans mgr to either create a new fragment and add it to the
        //trans mgr, or do nothing since its already there in the trans mgr.
        //this is similar code to FRAG_TAG_1, but adding another fragment to the display.
        if (null == fragMgr.findFragmentByTag(FRAG2_TAG))
        {
            xact.add(R.id.date_time2, DateTime.newInstance(time), FRAG2_TAG);
        }

        xact.commit();
        //fragment transaction mgr to allow for creation of a fragment.

        //add callback features to the view,  get edit txt view items first.
        //make these vars final to be accessible in anonymous class for event handler.
        final EditText t1 = (EditText)this.findViewById(R.id.text1);
        final EditText t2 = (EditText)this.findViewById(R.id.text2);

        //find the button to attach the event handler for.
        //provide the listener to the button to do some action.
        //it will update the text in the text fields.
        Button b1 = (Button)this.findViewById(R.id.button1);
        b1.setOnClickListener
        (
          new Button.OnClickListener()
          {
              @Override
              public void onClick(View arg1)
              {
                t1.setText("testing "+Math.random());
              }
          }
        );

        Button b2 = (Button)this.findViewById(R.id.button2);
        b2.setOnClickListener
        (
                new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View arg1)
                    {
                        t2.setText("testing 123, = "+ (int)(Math.random()*100 + 1) );
                    }
                }
        );

        //create an intent that has this activity and the class to call..
        //as a final vars to the callback.
        final Intent intent1 = new Intent(this,FinchLifecycle.class);

        String tt = "create table Test_Table_1 (_id integer primary key autoincrement, pid integer, fname text, lname text, age integer);";
        Log.d(myMainActivity1,tt);

        Button b6 = (Button)this.findViewById(R.id.button6);
        b6.setOnClickListener
                (
                        new Button.OnClickListener()
                        {
                            @Override
                            public void onClick(View arg1)
                            {
                                //start new activity
                                Log.d(myMainActivity1,"start new activity, finch life_cycle");
                                startActivity(intent1);
                            }
                        }
                );

        Log.d(myMainActivity1,"test");
        Log.d(myMainActivity1,android.provider.ContactsContract.Data.CONTENT_URI.toString());
        Log.d(myMainActivity1,android.provider.ContactsContract.Data._ID.toString());
        Log.d(myMainActivity1, android.provider.ContactsContract.Data.DISPLAY_NAME.toString());

        String data_stuff[] = new String[]
                {android.provider.ContactsContract.Data._ID,
                        android.provider.ContactsContract.Data.DISPLAY_NAME};

        Cursor cursor = getContentResolver().
                query
                (
                    //android.provider.ContactsContract.Data.CONTENT_URI,
                        ContactsContract.Contacts.CONTENT_URI,
                        //data_stuff,
                        null,
                    null,
                    null,
                    null
                );

        if (cursor.moveToFirst())
        {
            int idx = cursor.getColumnIndex(Contacts.People.DISPLAY_NAME);
            do
            {
                String name = cursor.getString(idx);
                Log.d(myMainActivity1,name);
            }
            while (cursor.moveToNext());
        }

        //close the cursor.
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity1, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
