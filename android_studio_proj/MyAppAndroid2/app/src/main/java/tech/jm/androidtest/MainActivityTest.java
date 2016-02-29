package tech.jm.androidtest;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.http.ParseHttpResponse;
import com.parse.http.ParseNetworkInterceptor;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import tech.jm.myappandroid2.Greeting;
import tech.jm.myappandroid2.MainActivity;
import tech.jm.myappandroid2.UTBlankActivity;

/**
 * Created by jimmy on 11/29/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<UTBlankActivity>
{
    private UTBlankActivity utBlankActivity;
    private Context mContext = null;

    public MainActivityTest()
    {
        super(UTBlankActivity.class);
    }

//    public MainActivityTest(Class<UTBlankActivity> activityClass)
//    {
//        super(activityClass);
//    }

    public byte[] getByteArrayFromStream(InputStream inputStream)
    {
        byte [] data = null;

        try
        {
            //create byte array obj.
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            //create buff to hold bytes read.
            byte [] buff = new byte[1024];//1kb buff
            int bytes_read = 0;

            //read raw bytes to buff, and track bytes read
            while( (bytes_read = inputStream.read(buff,0,buff.length)) >0 )
            {
                //save bytes reads to output stream
                bos.write(buff,0,bytes_read);
            }

            //get byte array from outstream
            data = bos.toByteArray();

            //close the output stream.
            bos.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return data;
    }

    public String urlTesting()
    {
        String rv_data = null;

        final int TIMEOUT = 60000;//timeout for http call.
        //return the input stream.
        InputStream rv = null;

        //http connection.
        HttpURLConnection conn = null;
        try
        {
            //create url obj
            URL url_get = new URL("http://10.0.2.2:1337");

            //URL url_get = new URL("http://10.150.30.174:1337/parse");

            //cast to http conn type
            conn = (HttpURLConnection)url_get.openConnection();

            //set the timeouts to be for this url connection before we make
            //a new connection.
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);

            //setup as a Get request.
            conn.setRequestMethod("GET");

            //connect to the url.
            conn.connect();

            //this will access the url, and get back a status code.
            int status_code = conn.getResponseCode();

            //if we have a valid status get the data.
            if(status_code == HttpURLConnection.HTTP_OK)
            {
                //get the input stream.
                rv = conn.getInputStream();

                byte [] data = getByteArrayFromStream(rv);
                String my_data = new String(data);
                Log.d("MainActivity","data = "+my_data);

                rv_data = my_data;
            }

            Log.d("MainActivity","http status code = "+status_code);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            //close http conn here
            if(conn != null)
            {
                conn.disconnect();
            }

            //close the stream.
            if(rv != null)
            {
                try
                {
                    rv.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return rv_data;
    }

    public void myParseTesting()
    {
        //set the logging level.
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        Log.d("MainActivity", "level = " + Parse.getLogLevel() + ", set to level = " + Parse.LOG_LEVEL_VERBOSE);

        Parse.initialize(new Parse.Configuration.Builder(mContext)
                .applicationId("myAppId")
                .clientKey("myClientKey")
                .server("http://10.0.2.2:1337/parse/").build());

        Log.d("MainActivity", "setup parser client calls.");

        try
        {
            ParseObject gameScore = new ParseObject("GameScore");
            gameScore.put("score", 1337);
            gameScore.put("playerName", "Sean Plott");
            gameScore.put("cheatMode", false);

            gameScore.save();//save data in the foreground.
            //gameScore.saveInBackground();//saves data in the background.

            int x = 100;

            ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");

            List<ParseObject> parseObjectList = query.find();

            for(ParseObject parseObject : parseObjectList)
            {
                int score = parseObject.getInt("score");
                String name = parseObject.getString("playerName");
                boolean cm = parseObject.getBoolean("cheatMode");

                Log.d("MainActivity", "score = "+score+", name = "+name+", cheatMode = "+cm);
                Log.d("MainActivity",parseObject.toString());
            }

            Bitmap bitmap;
            ByteArrayOutputStream blob = new ByteArrayOutputStream();

            try
            {
                InputStream is =
                        getInstrumentation().getTargetContext().getResources().getAssets().open("ic_launcher-web.png");

                bitmap = BitmapFactory.decodeStream(is);
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 ,blob);
            }
            catch(Exception e)
            {
                Log.d("MainActivity","error = "+e.getMessage());
                e.printStackTrace();
            }

            ParseFile parseImagefile = new ParseFile("test1_pic.png",blob.toByteArray());
            parseImagefile.save();
            String url = parseImagefile.getUrl();
            Log.d("MainActivity","url = "+url);

//            query = ParseQuery.getQuery("96d5b00ec2021f1e348dbbd5e8549d4c_test1_pic.png");
//            query.setLimit(10);
//            parseObjectList = query.find();
//            for(ParseObject obj1 : parseObjectList)
//            {
//                int xx = 1;
//            }

//            query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {
//                public void done(ParseObject gameScore, ParseException e) {
//                    if (e == null) {
//                        // object will be your game score
//                        int score = gameScore.getInt("score");
//                        String playerName = gameScore.getString("playerName");
//                        boolean cheatMode = gameScore.getBoolean("cheatMode");
//
//                        Log.d("MainActivity","score = "+score+", playerName = "+
//                                playerName+", cheatMode = "+cheatMode);
//
//                    } else {
//                        // something went wrong
//                        Log.d("MainActivity","got an exception = "+e.getMessage());
//                    }
//                }
//            });

            x++;
        }
        catch(ParseException e)
        {
            Log.d("MainActivity","error code = "+e.getCode()+", msg = "+e.getMessage());
            e.printStackTrace();
        }

        int y = 100;
    }

    public static void myTestRomeRss()
    {
        try
        {
            //rss 1.0
            URL feedUrl = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");

            //rss 2.0
            //URL feedUrl = new URL("http://rss.ireport.com/feeds/oncnn.rss");

            Log.d("Jimbo","testing****************");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            Log.d("Jimbo",feed.toString());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d("Jimbo","ERROR: "+ex.getMessage());
        }
    }

    public static void myTestSpringRestWithGson()
    {
        try
        {
            String url = "http://rest-service.guides.spring.io/greeting";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            Greeting greeting = restTemplate.getForObject(url, Greeting.class);
            Log.d("Jimbo",greeting.toString());
            int x = 10;
            //return greeting;
        } catch (Exception e) {
            Log.e("Jimbo", e.getMessage(), e);
        }
    }

    public static void myTestAndroidUIL()
    {
        try{
            //this singleton was set up in the application class.
            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
            ImageSize size_img = new ImageSize(100, 100);//fit the downloaded image to this size.

            //load image synchronously, with size
            final Bitmap bit_map =
                    imageLoader.loadImageSync
                            ("http://javatechig.com/wp-content/uploads/2014/05/UniversalImageLoader-620x405.png", size_img);

            Log.d("Jimbo","display image file.");
//            MainActivity.this.runOnUiThread(new Runnable(){
//                                                public void run(){
//
//                                                    ImageView iv = (ImageView)findViewById(R.id.myImageView);
//                                                    iv.setImageBitmap(bit_map);//set image map.
//
//                                                }
//                                            }
//            );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //here we setup the class members for the UT
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        Log.d("Jimbo","setup");

        //get the activity here.
        utBlankActivity = getActivity();

        //save the activity as the context
        mContext = utBlankActivity;
    }

    //here we close the obj with stuff that sets the test case back to start
    //and release any resources obtained during the test.
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        Log.d("Jimbo", "tear down activity test");

        //set class members to null
        utBlankActivity = null;
        mContext = null;
    }

    public void testFeatures() throws Exception
    {
        myParseTesting();
        //urlTesting();
        //myTestRomeRss();
        //myTestSpringRestWithGson();
        //myTestAndroidUIL();
       // myParseTest();
    }
}
