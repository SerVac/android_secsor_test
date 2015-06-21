package my.test.vs.acceltest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.List;


public class MainActivity extends Activity implements SensorEventListener {
    private static final String TAG = "MyLog";
    private static final int START_BTN_TAG = R.id.start_btn;
    private static final int STOP_BTN_TAG = R.id.stop_btn;

    private Button startBtn;
    private Button stopBtn;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private BufferedInputStream bufferedInputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        startBtn = (Button) findViewById(START_BTN_TAG);
        stopBtn = (Button) findViewById(STOP_BTN_TAG);
        stopBtn.setEnabled(false);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            List<Sensor> gravSensors = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
            for (int i = 0; i < gravSensors.size(); i++) {
                if ((gravSensors.get(i).getVendor().contains("Google Inc.")) &&
                        (gravSensors.get(i).getVersion() == 3)) {
                    // Use the version 3 gravity sensor.
                    mSensor = gravSensors.get(i);
                }
            }
        } else {
            // Use the accelerometer.
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Log.d(TAG, "Accelerometer is OK!");
            } else {
                Log.d(TAG, "there are no accelerometers on your device!");
            }
        }

        onPause();
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        String str = arrayToString(event.values);
//        Log.d(TAG, "acceler: " + arrayToString(event.values));
    }

    private String arrayToString(float[] values) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < values.length; i++) {
            stringBuffer.append(values[i] + "; ");
        }
        stringBuffer.setLength(stringBuffer.length()-2);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClick(View v) {
//        Log.d(TAG, "Clicked 2: " + view.getId());
        switch (v.getId()) {
            case START_BTN_TAG:
                onResume();
                FileUtil.write("TEST TEST TEST!");

                stopBtn.setEnabled(true);
                break;
            case STOP_BTN_TAG:
                onPause();
                stopBtn.setEnabled(false);
                break;

        }
    }

    public static Context getAppContext() {
        return MainActivity.getAppContext();
    }
}
