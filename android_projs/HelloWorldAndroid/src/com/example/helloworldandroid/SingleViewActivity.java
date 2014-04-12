package com.example.helloworldandroid;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * 
 * this class will handle the expanding of an image when clicked from the 
 * grid view activity window.
 *
 */
public class SingleViewActivity extends ActionBarActivity 
{
	private static String MyTag = "SingleViewActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_single_view);
		
		// Get intent data
		Intent i = getIntent();
		
		// Selected image id, get it from intent.
		int position = i.getExtras().getInt("id");
		
		//create image adapter with application context.
		ImageAdapter imageAdapter = new ImageAdapter(this);
		
		//find the view Image view obj to view this thumbnail if full scree from this activity layout file 
		ImageView imageView = (ImageView) findViewById(R.id.SingleView);
		
		//set the image resource to the thumbnail in the ImageAdapter.
		//get the position from the intent and get the id for the image to display in the ImageAdapter class
		//and set that image view obj with that thumbnail but for this activity, not the one that
		//shows all the thumbnails.
		imageView.setImageResource(imageAdapter.mThumbIds[position]);
		
		Log.d(MyTag, "completed the finding of the image from the adapter and setting up for the full display.");
	}
}
