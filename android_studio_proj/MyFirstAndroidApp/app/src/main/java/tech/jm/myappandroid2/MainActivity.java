package tech.jm.myappandroid2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Browser;
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
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //testing
        //testing 2

        //restore the saved state.
        super.onCreate(savedInstanceState);

        //set the content view
        setContentView(R.layout.activity_main);

        //setup columns to return back in the cursor obj.
        String columns[] = new String[]
        {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.STARRED
        };

        String sql_params [] = new String[]{"0"};

        ContentResolver cr =  getContentResolver();
        Cursor cursor = cr.query
          (
            ContactsContract.Contacts.CONTENT_URI,//uri to request
            columns, //data items to return in cursor
            ContactsContract.Contacts.STARRED+"=?",//where clause
            sql_params,//sql params, similar to prepared statements.
            null//sort order..doesnt matter
          );

        //this cursor example is like a select (return results from table...no where clause here.)
//        Cursor cursor = cr.query
//                (
//                        ContactsContract.Contacts.CONTENT_URI,//uri to request
//                        columns, //data items to return in cursor
//                        null,
//                        null,
//                        null//sort order..doesnt matter
//                );

        //check to see if we have data..
        if(cursor.moveToFirst())
        {
            do
            {
                //access the cursor data here.
                String disp_name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //Log.d("","showing contacts display name = "+disp_name);
            }
            while (cursor.moveToNext());
        }

        //end the cursor to not leak memory.
        cursor.close();


        Cursor managedCursor = Browser.getAllBookmarks(this.getContentResolver());
        //Browser.saveBookmark(this, "hahaha","www.jimbo.com");//save a book mark.
        Browser.deleteFromHistory(this.getContentResolver(),"www.jimbo.com");


//        //bookmarks lab example
//        Uri bookmarks =  Browser.BOOKMARKS_URI;
//
//        String[] cols = new String[] {
//                Browser.BookmarkColumns.URL
//        };
//
//        Cursor managedCursor = getContentResolver().query(
//                bookmarks, // URI of the resource
//                columns,   // Which columns to return
//                null,      // Which rows to return (all rows)
//                null,      // Selection arguments (none)
//                null);

        if(managedCursor.getCount() > 0)
        {
            while(managedCursor.moveToNext())
            {
                String url = managedCursor.getString(managedCursor.getColumnIndex(Browser.BookmarkColumns.URL));
                Log.d("","url  = "+url);
            }
        }

        managedCursor.close();

        Intent intent = new Intent(this, MyService.class);
        Intent intent2 = new Intent(this, MyIntentService.class);
        intent.putExtra("hahaha","use data");
        intent2.putExtra("hahaha","use data");
        startService(intent);
        stopService(intent);
        startService(intent2);

        Intent broadcast_intent = new Intent();
        broadcast_intent.setAction("tech.jm.BR_ACTION_1");
        broadcast_intent.putExtra("data_to_consume","my_data");
        sendBroadcast(broadcast_intent);

        final Button list_button = (Button)findViewById(R.id.button_list_view);
        list_button.setOnClickListener( new Button.OnClickListener()
           {
               public void onClick(View view)
               {
                   try {
                      Intent list_demo = new Intent(MainActivity.this, MainActivity3Activity.class);
                      startActivity(list_demo);

                   }
                   catch(Exception e){e.printStackTrace();}
               }
           }
        );

        //initialize UI elements
        final EditText addressText = (EditText)findViewById(R.id.location);
        final Button button = (Button)findViewById(R.id.mapButton);

        button.setOnClickListener( new Button.OnClickListener()
        {
            public void onClick(View view)
            {
                try {
                    String address = addressText.getText().toString();
                    address = address.replace(' ', '+');
                    Intent geoIntent = new Intent
                            (android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
                    startActivity(geoIntent);
                    //startActivityForResult(geoIntent, 200);//command type for the other activity.
                }
                catch(Exception e){e.printStackTrace();}
            }
        }
        );

       final Button frag_button = (Button)findViewById(R.id.frag_button);
        frag_button.setOnClickListener( new Button.OnClickListener()
       {
           public void onClick(View view)
           {
               try {
                   Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                   startActivity(intent);
                   Log.d("MainActivity","calling MainActivity2");
               }
               catch(Exception e){e.printStackTrace();}
           }
       }
        );

        //create and intent to invoke the phone call activity
        Intent my_intent = new Intent(Intent.ACTION_CALL);

        //u need to set the mime type otherwise android will infer the mime type for u
        //example =>“image/*”
        //Intent.setType(String type)

        //create an intent and set the data associated with it, but it is set based on a
        //URI format.
        //this is creating an intent to invoke another activity from this activity.
        //this is implicit activity activation
        Intent newInt = new Intent(MainActivity.this, ReconfigActivity.class);

        newInt.setAction(Intent.ACTION_CALL);//set the action for this intent.

        //this is setting data for the intent to use with the action
        newInt.setData(Uri.parse("tel:+17183122934"));

        //this is adding more info to the intent data structure which is kept as name/value pairs.
        //user will be able to get either data from intent.
        newInt.putExtra(android.content.Intent.EXTRA_EMAIL,new String[]{"jimmy.mena@gmail.com"});

        //specify how intent should be handled.
        //dont put activity in the history back stack.
        newInt.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        //intent can be used to start an activity.
        //startActivity(newInt);

        //we can also do intent resolution. where the android system tries to find activities
        //that can handle the intent. intent resolution is specified in the manifest file
        //and it limited to action, data(uri), category

        //to check intents for an activity you need to use the package mgr android api call.
        //with the action you are using for this intent.

        //get the wifi state of the device and print the data to the screen.
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        final TextView wifi_state = (TextView)findViewById(R.id.wifi_state_id);

        int status_wifi = -1;
        if(wifiManager.isWifiEnabled())
        {
            wifi_state.setText("Wifi Enabled");
            Log.d("MainActivity","wifi enabled");
            status_wifi = 100;
        }
        else
        {
            wifi_state.setText("Wifi Not Enabled");
            Log.d("MainActivity","wifi disabled");
            status_wifi = 200;
        }

        //make explicit call to intent with this activity context and the next activity to call
        //add data to the extra of the intent.
        //start the new activity.
        //this will not explicitly state which actitivy to launch, the android runtime
        //will determine how to launch it. i made a intent filter inside the reconfig activity
        //in the android manifest that takes this action.
        Intent wifi_intent = new Intent(/*this, ReconfigActivity.class*/);
        wifi_intent.putExtra("wifi_state",status_wifi);
        wifi_intent.setAction("jm.tech.WIFI_STATE");
        //startActivity(wifi_intent);
    }

    @Override
    protected void onActivityResult(int requestCode,  int resultCode, Intent data)
    {
        //the result code in an android  way of returning status back to the mother activity.
        if(resultCode == Activity.RESULT_OK &&
                requestCode == 200)//this request code would be my own code type..
        {
            Log.d("","entered here since i got a status back from an activity.");
        }
    }

    //this is called when a configuration has changed.
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        Log.d("","new config chg = "+newConfig.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
