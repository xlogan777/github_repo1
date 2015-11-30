package tech.jm.myappandroid2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.URL;

//http://www.androiddesignpatterns.com/2013/01/inner-class-handler-memory-leak.html
public class MainActivity extends ActionBarActivity {
    private int mProgressStatus = 0;
    private static ProgressBar mProgress;

    private static class MyHandler extends  android.os.Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity)
        {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            int aResponse = msg.getData().getInt("progressUpdate");
            Log.d("","Hey i am here prog = "+aResponse);
            mProgress.setProgress(aResponse);
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    public static void testRomeRss()
    {
        try
        {
            //rss 1.0
            URL feedUrl = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");

            //rss 2.0
            //URL feedUrl = new URL("http://rss.ireport.com/feeds/oncnn.rss");

            Log.d("MainActivity","testing****************");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            Log.d("Main activity",feed.toString());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d("Main Activity","ERROR: "+ex.getMessage());
        }
    }

    public static void testSpringRestWithGson()
    {
        try
        {
            String url = "http://rest-service.guides.spring.io/greeting";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            Greeting greeting = restTemplate.getForObject(url, Greeting.class);
            Log.d("MainActivity",greeting.toString());
            int x = 10;
            //return greeting;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }

    public void testAndroidUIL()
    {
        try{
            //this singleton was set up in the application class.
            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
            ImageSize size_img = new ImageSize(100, 100);//fit the downloaded image to this size.

            //load image synchronously, with size
            final Bitmap bit_map =
                    imageLoader.loadImageSync
                            ("http://javatechig.com/wp-content/uploads/2014/05/UniversalImageLoader-620x405.png", size_img);

            Log.d("MainActivity","display mage file.");
            MainActivity.this.runOnUiThread(new Runnable(){
                                                public void run(){

                                                    ImageView iv = (ImageView)findViewById(R.id.myImageView);
                                                    iv.setImageBitmap(bit_map);//set image map.

                                                }
                                            }
                                           );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //restore the saved state.
        super.onCreate(savedInstanceState);

        //set the content view
        setContentView(R.layout.activity_main);

        //test spring fw.
        //testSpringFw();

        //testRomeRss();

        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        //set the callbacks here for the threads
        final Button prg1 = (Button)this.findViewById(R.id.button4);
        prg1.setOnClickListener( new Button.OnClickListener()
            {
                public void onClick(View view)
                {
                    //adding a runnable onto the UI thread task queue to process
                    //since we cant call UI components from outside the UI thread.
                    Thread t1 = new Thread
                    (
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    //while (mProgressStatus < 100) {
                                        mProgressStatus += 10;

                                        try
                                        {
                                            testAndroidUIL();
                                            testSpringRestWithGson();
                                            testRomeRss();
                                            //do fake work by sleeping
                                            Thread.sleep(3000);

                                            //send a msg to the handler thread obj for the
                                            //UI thread to do stuff.

//                                            Message msgObj = mHandler.obtainMessage();
//                                            Bundle b = new Bundle();
//                                            b.putInt("progressUpdate",mProgressStatus);
//                                            msgObj.setData(b);
//                                            mHandler.sendMessage(msgObj);
                                        }
                                        catch(Exception e){}

                                        // Update the progress bar, by posting to the handler class.
                                        //i am using the send msg approach to bundle data back to handler
                                        //class
                                        mHandler.post(new Runnable() {
                                            public void run() {
                                                mProgress.setProgress(mProgressStatus);
                                            }
                                        });
                                    //}
                                }
                            }
                    );
                    t1.start();
                }
            }
        );

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

        //closed the cursor.
        //doing another test
        //final test
        managedCursor.close();

        ContentResolver cr2 = this.getContentResolver();
        //content:// => content provider protocol..similar to http:// concept
        //authority = tech.jm.myappandroid2.mycontentprovider
        //path-segment => "mapData"
        //id of record being requested after mapData, in this case item 100
        Uri my_content_uri = Uri.parse("content://tech.jm.myappandroid2.mycontentprovider/mapData/100");
        int cc = cr2.delete(my_content_uri,"id",new String[]{"1"});

        Uri my_content_uri2 = Uri.parse("content://tech.jm.myappandroid2.mycontentprovider/mapData/200");
        ContentValues cv = new ContentValues();
        cv.put("name","jimbo");
        Uri tmp1 = cr.insert(my_content_uri2, cv);

        Cursor tmp_cur = cr.query(my_content_uri2,null,null,null,null);

        if(tmp_cur.getCount() > 0)
        {
            while(tmp_cur.moveToNext())
            {
                String my_name = tmp_cur.getString(tmp_cur.getColumnIndex("name_col"));
                Log.d("","my name  = "+my_name);
            }
        }

        tmp_cur.close();

        String ext_storage_state = Environment.getExternalStorageState();
        File f = this.getExternalCacheDir();//represent dir name of tmp storage as a cache.
        File f2 = this.getCacheDir();//gets local cache to save files.
        File f3 = this.getFilesDir();
        Log.d("","int dir = "+f3+", ext storage state = "+ext_storage_state+" , ext cache dir = "+f+" , cache_dir"+f2);

        //this will show opening and internal files. these are private files..
        //use the api to create and read files.
        File file = new File(this.getFilesDir(), "my_file.txt");
        String fname = file.getAbsolutePath();
        try
        {

            PrintWriter pw =
                    new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            this.openFileOutput("my_file.txt", Activity.MODE_PRIVATE))));

            pw.println("this is a test");
            pw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(this.openFileInput("my_file.txt")));

            String line = "";
            while( (line = br.readLine()) != null)
            {
                Log.d("",line);
            }

            br.close();

            File file22 = new File("my_file.txt");
            Log.d("","path = "+file22.getAbsolutePath());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //save across multiple androind instances with shared prefs.
        //eithe at activity level or at application level.
        //this can just be a call to this.getPreferences..which does the same below.
        final SharedPreferences sp = this.getSharedPreferences(MainActivity.class.getName(),Activity.MODE_PRIVATE);

        int val = 100;
        //get the editor for shared prefs
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("myId", val);//save data to shared prefs
        editor.commit();//commit the change.

        final SharedPreferences sp2 = this.getSharedPreferences(MainActivity.class.getName(),Activity.MODE_PRIVATE);
        int val2 = sp2.getInt("myId",0);
        Log.d("","val 2 = "+val2);

        //u can also add preferences from a an xml file.

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

        //setup new button for mutlimedia activity handling.
        final Button activity_5 = (Button)findViewById(R.id.button5);
        activity_5.setOnClickListener( new Button.OnClickListener()
            {
                public void onClick(View view)
                {
                    try {
                        Intent intent = new Intent(MainActivity.this, MainActivity33Activity.class);
                        startActivity(intent);
                        Log.d("","Start MM demo activity.");
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        );

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
