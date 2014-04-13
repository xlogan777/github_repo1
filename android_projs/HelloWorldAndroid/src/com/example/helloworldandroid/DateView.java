package com.example.helloworldandroid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class DateView extends TextView
{
	private static final String MyTag = "DateView";
	
	//used to set attribs here.
	public String delimiter;
	public boolean fancyText;
	
	public DateView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		
		//call internal function
		this.setDate();
	}

	/**
	 * this will take the attrib obj and get a typed array to match the
	 * list of attrib in the /res/values/attrs.xml file.
	 */
	public DateView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		
		//get the type array that from the context for the dateview object in the xml file.
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateView );
		
		//gets the number of custom fields in the type array.
		int N = a.getIndexCount();
		
		for (int i = 0; i < N; ++i)
		{
			//get the first attrib
			int attr = a.getIndex(i);
			
			switch (attr)
			{
				//check to see if it is a delimiter type
				case R.styleable.DateView_delimiter:
					//set val of delimiter to class vars
					delimiter = a.getString(attr);
					//call date processing 
					setDate();
					Log.d(MyTag, "Found delimiter for custom view obj.");
					break;
				
				//check to see if it is a fancy text type
				case R.styleable.DateView_fancyText:
					//set val of delimiter to class vars
					fancyText = a.getBoolean(attr, false);
					//perfrom fancy text processing.
					fancyText();
					Log.d(MyTag, "Found delimiter for custom view obj.");
					break;
			}
		}
		
		//this returns the typeArray back to be used later.
		a.recycle();
	}

	public DateView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		//call internal function
		this.setDate();
	}
	
	private void setDate() 
	{
		SimpleDateFormat dateFormat;
		
		//create date obj with use of delimiter.
		if(delimiter != null)
			dateFormat = new SimpleDateFormat("yyyy" + delimiter + "MM" + delimiter + "dd",Locale.US);
		else
			dateFormat = new SimpleDateFormat("yyyy:MM:dd",Locale.US);
		
		//get time
		String today = dateFormat.format(Calendar.getInstance().getTime());
		
		//update text.
		setText(today+" my own custom view obj."); // self = DateView = subclass of TextView
	}
	
	private void fancyText() 
	{
		//set the shadow of the text here. for the date obj. if vars set.
		if( this.fancyText)
		{
			setShadowLayer(9, 1, 1, Color.rgb(44, 44, 40));
		}
	}

}
