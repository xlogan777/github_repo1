package com.example.helloworldandroid;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
 * this file will handle the image setup to an adapter to then tie it back
 * to the activity.
 *
 */

public class ImageAdapter extends BaseAdapter 
{
	private Context mContext;
	
	private static final String MyTag = "ImageAdapter";
	
	//Keep all Images in array
	//these are all the images in the res/drawable-hdpi folder for now..
	//these values in the array are in the R.java file to be used to get the
	//actual image thumbnail.
	public Integer[] mThumbIds = 
	{
		R.drawable.images1, R.drawable.images2,
		R.drawable.images3, R.drawable.images4,
		R.drawable.images5, R.drawable.images6,
		R.drawable.images7, R.drawable.images1,
		R.drawable.images2, R.drawable.images3,
		R.drawable.images4, R.drawable.images5,
		R.drawable.images6, R.drawable.images7,
		R.drawable.images1, R.drawable.images2,
		R.drawable.images3, R.drawable.images4
	};
	
	// Constructor
	public ImageAdapter(Context c) 
	{
		mContext = c;
	}
	
	public int getCount() 
	{
		Log.d(MyTag, "Len of array is "+mThumbIds.length);
		return mThumbIds.length;
	}
	
	public Object getItem(int position) 
	{
		return null;
	}
	
	public long getItemId(int position) 
	{
		return 0;
	}
	
	//create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView imageView;

		//this part of the if statement created the Image view object for each thumbnail image
		//and provides the scaling of it to be shown on the screen.
		if (convertView == null) 
		{
			//create new image view if convert view is null, with application context.
		    imageView = new ImageView(mContext);
		    
		    //setup the layout params, scaliung of the image, and the padding.
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
			
			Log.d(MyTag, "created new image view obj, since view passed in was null..");
		} 
		else 
		{
			//just cast the view passed in as a ImageView to show the view
			imageView = (ImageView) convertView;
			Log.d(MyTag, "reused view and changed it to ImageView to allow to set thumbnail to view.");
		}
		
		//this will get the image specific to the image id in the array and set the imageview obj with that
		//specific one.
		//picks a specific image to be set as the drawable back to the context...
		imageView.setImageResource(mThumbIds[position]);
		
		//return the view with the image(either new image view or cast view from one passed in.)
		return imageView;
	}
}
