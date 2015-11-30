package tech.jm.androidtest;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ImageView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import tech.jm.myappandroid2.Greeting;
import tech.jm.myappandroid2.MainActivity;
import tech.jm.myappandroid2.R;

/**
 * Created by jimmy on 11/29/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    //private MainActivity mainActivity;

    public MainActivityTest()
    {
        super(MainActivity.class);
    }

    public MainActivityTest(Class<MainActivity> activityClass)
    {
        super(activityClass);
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
        //mainActivity = this.getActivity();
    }

    //here we close the obj with stuff that sets the test case back to start
    //and release any resources obtained during the test.
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        Log.d("Jimbo","tear down activity test");
    }

    public void testFeatures() throws Exception
    {
        myTestRomeRss();
        myTestSpringRestWithGson();
        myTestAndroidUIL();
    }
}
