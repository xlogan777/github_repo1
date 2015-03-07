package tech.jm.myappandroid2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity33Activity extends ActionBarActivity implements SensorEventListener
{
    //use for the audio mgr.
    private float volume;
    private SoundPool soundPool;
    private int soundId;

    //for sensors
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    long mLastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity33);

        Log.d("", "MAIN MM");

        final TextView tv1 = (TextView) this.findViewById(R.id.textView6);

        //get the audio mgr from android
        final AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);

        //load the sound effects.
        audioManager.loadSoundEffects();

        //get the volume level from audio mgr.
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        tv1.setText(String.valueOf(volume));

        //setup 2 buttons to either play the audio sound via audio mgr or
        //play sound via the sound pool.
        final Button up_button = (Button) findViewById(R.id.button6);
        up_button.setOnClickListener(new Button.OnClickListener() {
                                         public void onClick(View view) {
                                             try {
                                                 Log.d("", "'");
                                                 audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     }
        );

        final Button play_button = (Button) findViewById(R.id.button7);
        play_button.setOnClickListener(new Button.OnClickListener() {
                                           public void onClick(View view) {
                                               try {
                                                   Log.d("", "'");
                                                   soundPool.play(soundId, volume, volume, 1, 0, 1.0f);
                                               } catch (Exception e) {
                                                   e.printStackTrace();
                                               }
                                           }
                                       }
        );

        //create a sound pool obj and provide the required params.
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //SoundPool(int maxStreams, int streamType, int srcQuality)

        //set a complete listener to enable the button for the sound pool.
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                                                    play_button.setEnabled(true);
                                                }
                                            }
        );

        //get a sound id for this raw sound obj.
        //the raw sound obj can be a file and u can get it via  a path and filename, asset file name..
        //file id type..etc..for now i am not loading anything since i dont have any sound file to load.
        //soundId = soundPool.load(this, R.raw.sound, 1);

        final Button ringtone_butt = (Button) findViewById(R.id.button8);
        ringtone_butt.setOnClickListener(new Button.OnClickListener() {
                                             public void onClick(View view) {
                                                 try {
                                                     //ring tone mgr
                                                     Ringtone ringtone =
                                                             (Ringtone) RingtoneManager.getRingtone
                                                                     (MainActivity33Activity.this, Settings.System.DEFAULT_RINGTONE_URI);

                                                     if (ringtone != null) {
                                                         Log.d("", "ring tone");
                                                         ringtone.play();
                                                     }
                                                 } catch (Exception e) {
                                                     e.printStackTrace();
                                                 }
                                             }
                                         }
        );

        //for media player see documentation for this stuff..has a state machine for its setup.
        //MediaPlayer.
        //video_view is used to allow for multiple sources to be used to display video.
        //simple widget for just video.

        //for media recorder for video/audio
        //https://developer.android.com/reference/android/media/MediaRecorder.html
        //the above link show how the simple state machine and steps are there for usage of this
        //android feature.

        //usage for the camera is this url with sample steps for camera and video capture
        //as well as setup steps.
        //http://developer.android.com/guide/topics/media/camera.html

        //do toast notifications
        String my_name = "this is my name";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, my_name, duration);
        toast.show();

        //status bar notifications and notification manager.
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_edit_text_material)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity32Activity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity32Activity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        int mId = 100;
        mNotificationManager.notify(mId, mBuilder.build());

        //set sound and vibration for a notification is done like so
        //notification.defaults |= Notification.DEFAULT_SOUND;
        /* custom vibrations to be set on the notification obj .
        long[] vibrate = {
                        0,
                        100,
                        200,
                        300
                        };
        notification.vibrate  =  vibrate;
        */

        //alarm notifications allow for
        //scheduled processing at either a fixed rate time, or one shot..
        //it used the Alarm Mgr..and u set it via intents.
        //alarms remain active even when app is sleep or not active.

        //networking in android
        Thread net_thead = new Thread() {
            public void run() {
                try {
                    URL url = new URL("http://api.geonames.org/earthquakesJSON?north=44.1&south=-%C2%AD%E2%80%909.9&east=-%C2%AD%E2%80%9022.4&west=55.2&username=demo");
                    HttpURLConnection http_conn = (HttpURLConnection) url.openConnection();
                    InputStream is = http_conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int numRead = 0;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    while ((numRead = is.read(buffer, 0, buffer.length)) > 0) {
                        bos.write(buffer, 0, numRead);
                    }

                    String json_str = bos.toString();
                    Log.d("",json_str);
                    bos.close();
                    is.close();
                    http_conn.disconnect();

                } catch (Exception e) {
                    Log.e("Abhan", "Error: " + e);
                }
            }
        };//class

        net_thead.start();

        //do code for blue tooth test
        blueToothTest();

        //sensor test
        sensorsTest();
    }

    public void blueToothTest()
    {
        //get the blue tooth adapter to check to see if we have bluetooth on this device.
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            Log.d("","blue tooth not available.");
        }
        else
        {
            Log.d("","blue tooth ON status = "+mBluetoothAdapter.isEnabled());

            mBluetoothAdapter.getBondedDevices();
            mBluetoothAdapter.startDiscovery();
            mBluetoothAdapter.cancelDiscovery();

            //to connect to devices, u need same UUID between devices to connect each other.

            //if we have bluetooth but its not enabled, send intent to system to enable it.
            if(!mBluetoothAdapter.isEnabled())
            {
                //use default action for enabling intent.
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBluetoothIntent);
                //startActivityForResult(enableBluetoothIntent,REQUEST_ENABLE_BT);
            }
        }
    }

    public void sensorsTest()
    {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //register listener for sensor test accelerometer
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("","setup listener for accelerometer");
    }

    @Override
    //listener to show the xyz coordinate for the accelerometer.
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            long actualTime = System.currentTimeMillis();
            if (actualTime - mLastUpdate > 500) {
                mLastUpdate = actualTime;
                float x = event.values[0], y = event.values[1], z = event.values[2];
                Log.d("", "x = " + x + ", y = " + y + ", z = " + z);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    protected void onPause()
    {
        //when the activity is paused, moved to the background,
        //need to release resources. by unloading the sound id item, and releasing the
        //sound pool.
        if(soundPool != null)
        {
            soundPool.unload(soundId);
            soundPool.release();
            soundPool = null;
        }

        //unregister for accelerometer test.
        mSensorManager.unregisterListener(this);

        super.onPause();
        //testing
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
