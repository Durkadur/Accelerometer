package se.mah.bagge.accelerometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class Main extends Activity {
    SensorService sservice = new SensorService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), SensorService.class );
        startService(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast toast = Toast.makeText(this, "shake counter: " + sservice.getCounter(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.show();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

}