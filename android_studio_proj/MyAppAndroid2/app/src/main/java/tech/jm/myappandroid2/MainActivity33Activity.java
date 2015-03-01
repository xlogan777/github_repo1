package tech.jm.myappandroid2;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity33Activity extends ActionBarActivity {

    //use for the audio mgr.
    private float volume;
    private SoundPool soundPool;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity33);

        Log.d("","MAIN MM");

        final TextView tv1 = (TextView)this.findViewById(R.id.textView6);

        //get the audio mgr from android
        final AudioManager audioManager = (AudioManager)this.getSystemService(AUDIO_SERVICE);

        //load the sound effects.
        audioManager.loadSoundEffects();

        //get the volume level from audio mgr.
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        tv1.setText(String.valueOf(volume));

        //setup 2 buttons to either play the audio sound via audio mgr or
        //play sound via the sound pool.
        final Button up_button = (Button)findViewById(R.id.button6);
        up_button.setOnClickListener( new Button.OnClickListener()
          {
              public void onClick(View view)
              {
                  try
                  {
                      audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
                  }
                  catch(Exception e){e.printStackTrace();}
              }
          }
        );

        final Button play_button = (Button)findViewById(R.id.button7);
        play_button.setOnClickListener( new Button.OnClickListener()
          {
              public void onClick(View view)
              {
                  try
                  {
                      soundPool.play(soundId, volume, volume, 1, 0, 1.0f);
                  }
                  catch(Exception e){e.printStackTrace();}
              }
          }
        );

        //create a sound pool obj and provide the required params.
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //SoundPool(int maxStreams, int streamType, int srcQuality)

        //set a complete listener to enable the button for the sound pool.
        soundPool.setOnLoadCompleteListener( new SoundPool.OnLoadCompleteListener()
             {
                 public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
                 {
                     play_button.setEnabled(true);
                 }
             }
        );

        //get a sound id for this raw sound obj.
        //soundId = soundPool.load(this, R.raw.sound, 1);
        //test
    }

    @Override
    protected void onPause()
    {
        if(soundPool != null)
        {
            soundPool.unload(soundId);
            soundPool.release();
            soundPool = null;
        }
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity33, menu);
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
