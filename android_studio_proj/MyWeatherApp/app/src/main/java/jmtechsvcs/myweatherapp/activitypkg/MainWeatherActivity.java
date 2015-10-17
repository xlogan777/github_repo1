package jmtechsvcs.myweatherapp.activitypkg;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.utilspkg.AnalyticsTracking;

//test
public class MainWeatherActivity extends Activity {

    private static String LOGTAG = "MainWeatherActivity";

    //webview for loading html.
    private WebView webView;

//http://stackoverflow.com/questions/14744496/extract-database-of-an-application-from-android-device-through-adb

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        //send the tracking ofthe viewing of this screen.
        AnalyticsTracking.sendScreenViewEvents(MainWeatherActivity.class.getSimpleName());

        //add the webview here.
        webView = (WebView)findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://openweathermap.org/maps");
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main_weather, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
