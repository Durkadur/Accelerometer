package se.mah.bagge.Fanmode;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class SensorService extends Service implements SensorEventListener {
    private static final String DEBUG_TAG = "SensorService";
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 500;
    private static int counter = 0;
    private SensorManager sensorManager = null;
    private Sensor sensor = null;
    private int interval = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // do nothing
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Runs every time a sensor detects a change
        Log.i("Time", Long.toString(System.currentTimeMillis()));
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                Log.i("Status", "INNE" + Integer.toString(interval));
                // check if speed is above threshold
                if (speed > SHAKE_THRESHOLD) {
                    Log.i("Status", "JÄTTEINNE");
                    // only allow update to cheer counter every three times
                    if (interval >= 3) {
                        Log.i("Status", "ENORMTJÄTTEINNE");
                        counter++;
                        interval = 0;
                        Log.i("counter", Integer.toString(counter));
                    }
                    interval++;
                }

                Log.i("values", "X: " + Float.toString(x));
                Log.i("values", "Y: " + Float.toString(y));
                Log.i("values", "Z: " + Float.toString(z));

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }
    // get method for number of cheers
    public static int getCounter() {
        return counter;
    }

    // get method for number of cheers
    public static void resetCounter() {
        counter = 0;
    }
}