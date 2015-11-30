package tech.jm.myappandroid2;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by jimmy on 11/29/2015.
 */
public class MyApplication extends Application
{
    @Override
    /**
     * this is loading the config objs and setups up the image loader for use in the
     * entire app.
     */
    public void onCreate()
    {
        super.onCreate();

        // UNIVERSAL IMAGE LOADER SETUP
        //default display options.
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        //image loader config obj which uses default image display objs.
        ImageLoaderConfiguration config = new
                ImageLoaderConfiguration.Builder(getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .discCacheSize(100 * 1024 * 1024).build();

        //init singleton with image loader config obj.
        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        Log.d("MyApplication","setup config here for app for image loader.");
    }
}
