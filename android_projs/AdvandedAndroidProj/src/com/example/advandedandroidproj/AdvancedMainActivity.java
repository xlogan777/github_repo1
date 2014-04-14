package com.example.advandedandroidproj;

import android.support.v7.app.ActionBarActivity;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.content.ClipDescription;

public class AdvancedMainActivity extends ActionBarActivity 
{
	//logging field that allows for tag name display in the logcat.
	private static String LogTagClassName = "AdvancedMainActivity";
	
	//this will hold the imageView object from the layout file
	private ImageView ima;
		
	//vars to hold the layout when being dragged.
	private android.widget.RelativeLayout.LayoutParams layoutParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_main);
		
		Log.d(LogTagClassName, "In the Main activity for the new project.");
		
		this.performDragAndDropProcessing();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advanced_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_settings) 
		{
			Log.d(LogTagClassName, "Clicked the action settings menu item.");
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void performDragAndDropProcessing()
	{
		//get the id of the image.
		ima = (ImageView)findViewById(R.id.iv_logo);
		
		// Sets the tag for this image view obj.
		ima.setTag("Android Logo");
		
		//this will setup the longclickListener for the image, when the user 
		//presses the image and holds it for a bit.
		//called via anonymous class to be tied to specific view obj.
		//this will also do the shadow portion of that drag and drop that is needed.
		ima.setOnLongClickListener
		( new View.OnLongClickListener() 
			{
				@Override
				//implement this function to for the long click.
				public boolean onLongClick(View v)
				{
					//for drag, need to create clip item, and clip data.
					
					//create new clip data item with tag name from imageview obj preloaded before
					//this call to anonymous class.
					ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
					
					//setup the mime type to be plain text for the description
					String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
					
					//create clip data that has the label, mime type and the item for the clip data.
					ClipData dragData = new ClipData(v.getTag().toString(),	mimeTypes, item);
					
					// Instantiates the drag shadow builder.
					//this creates the shadow of the drag..with the imageview obj..gotten from the layout.
					View.DragShadowBuilder myShadow = new DragShadowBuilder(ima);
					
					// Starts the drag, for this image..
					//provide it the data to be dragged, the shadow obj..
					v.startDrag
					( dragData, // the data to be dragged
					  myShadow, // the drag shadow builder
					  null, // no need to use local data
					  0 // flags (not currently used, set to 0)
					);
					
					//return true so that you can be notified of all the long click events.
					return true;
				}
			}
		);
		
		// Create and set the drag event listener for the View		
		ima.setOnDragListener
		(
		 new OnDragListener()
			{
				@Override
				//implement this function for the dragging of the event.
				public boolean onDrag(View v, DragEvent event)
				{
					int x_cord = 0;
					int y_cord = 0;
					
					//switch on the different events seen from this onDragListener
					switch(event.getAction())
					{
						//started the dragging.
						case DragEvent.ACTION_DRAG_STARTED:
							//get layout params of the view. in this case the image view.
							layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
							
							Log.d(LogTagClassName, "Action is DragEvent.ACTION_DRAG_STARTED");
							// Do nothing
							break;
						
						//entered another view obj with this view obj.
						case DragEvent.ACTION_DRAG_ENTERED:
							Log.d(LogTagClassName, "Action is DragEvent.ACTION_DRAG_ENTERED");
							x_cord = (int) event.getX();
							y_cord = (int) event.getY();
							break;
						
						//left the view that we entered...with this view ob being dragged out.
						case DragEvent.ACTION_DRAG_EXITED :
							Log.d(LogTagClassName, "Action is DragEvent.ACTION_DRAG_EXITED");
							x_cord = (int) event.getX();
							y_cord = (int) event.getY();
							layoutParams.leftMargin = x_cord;
							layoutParams.topMargin = y_cord;
							v.setLayoutParams(layoutParams);
							break;
						
						//get the location of the item being dragged.
						case DragEvent.ACTION_DRAG_LOCATION :
							Log.d(LogTagClassName, "Action is DragEvent.ACTION_DRAG_LOCATION");
							x_cord = (int) event.getX();
							y_cord = (int) event.getY();
							break;
												
						//dropped the dragged item to the location to be done with the dragging.
						case DragEvent.ACTION_DROP:
							Log.d(LogTagClassName, "ACTION_DROP event");							
							// Do nothing
							break;
							
						//ended the dragging process.
						case DragEvent.ACTION_DRAG_ENDED :
							Log.d(LogTagClassName, "Action is DragEvent.ACTION_DRAG_ENDED");
							// Do nothing
							break;
						
						default: 
							Log.d(LogTagClassName, "Nothing seen...???");
							break;
					}//switch
					
					//return true so that ur notified of each event, not just the end event.
					return true;
				}//onDrag
			}//listener
		);
	}
}
