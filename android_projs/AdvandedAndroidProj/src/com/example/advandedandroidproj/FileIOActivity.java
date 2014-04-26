package com.example.advandedandroidproj;

import java.io.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*
 * this will show how to do file io..and save and get data from the internal
 * storage.
 */
public class FileIOActivity extends Activity 
{
	//internal fields for this file io.
	private EditText et;
	private String data;
	private String file = "mydata.jimbo";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_io);
		et = (EditText)(findViewById(R.id.editText1));

	}
	
	public void save(View view)
	{
		//get the txt from the text field
		data = et.getText().toString();
		
		try 
		{
			//open file in file system
			PrintWriter pw = new PrintWriter(openFileOutput(file, 0/*MODE_WORLD_READABLE*/));
			pw.write(data);
			pw.close();
			
			//FileOutputStream fOut = openFileOutput(file,MODE_WORLD_READABLE);
			
			//write data and close file.
			//fOut.write(data.getBytes());
			//fOut.close();
			
			//display to use that all is ok..
			Toast.makeText(getBaseContext(),"file saved", Toast.LENGTH_SHORT).show();
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void read(View view)
	{
		try
		{
			//open file to read
			//FileInputStream fin = openFileInput(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(file)));
			
			//read data from file
//			int c;
//			String temp="";
//			while( (c = fin.read()) != -1)
//			{
//				temp = temp + Character.toString((char)c);
//			}
			String line = br.readLine();
						
		
			//set the txt field with the txt from the file.
			if(line != null)
				et.setText(line);
			else
				et.setText("no data in the file");
			
			//tell user that reading was fine.
			Toast.makeText(getBaseContext(),"file read", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){e.printStackTrace();}
	}
}
