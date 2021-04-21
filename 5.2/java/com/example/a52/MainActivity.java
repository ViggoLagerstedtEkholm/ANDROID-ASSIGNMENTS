package com.example.a52;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.MessageFormat;

/**
 * This program shows a compass and you can rotate the phone to see the compass change!
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-02-02
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView valueRotation;
    private TextView valueRoll;
    private TextView valuePitch;
    private TextView valueDegrees;

    private TextView valueX1;
    private TextView valueX2;
    private TextView valueX3;

    private TextView valueY1;
    private TextView valueY2;
    private TextView valueY3;

    private TextView valueZ1;
    private TextView valueZ2;
    private TextView valueZ3;

    private ImageView compassImage;

    private SensorManager sensorManager;
    private Sensor magnetoMeter;
    private Sensor accelerator;
    private Sensor gyroscope;

    private final DecimalFormat df = new DecimalFormat();

    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private final float[] orientation = new float [3];
    private final float[] rotationMatrix = new float[9]; // 3 x 3 matrix

    float azimuth = 0;
    float pitch = 0;
    float roll = 0;

    public MainActivity() { }

    /**
     * OnCreate is called once the Activity is first initiated.
     * We pass a Bundle to this Activity so we can get the most recent saved instance.
     * We call the initialize method which gets all UI components loaded into our fields.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initialize();
    }

    /**
     * We get all of our components by ID and load them into our fields.
     * We also create our sensors for:
     * TYPE_MAGNETIC_FIELD
     * TYPE_ACCELEROMETER
     * TYPE_GYROSCOPE
     * All of these will sense different inputs from different sensors.
     */
    private void initialize()
    {
        compassImage =  this.findViewById(R.id.imageView);
        valueRotation = this.findViewById(R.id.valuerRotation);
        valueRoll = this.findViewById(R.id.valueRoll);
        valuePitch = this.findViewById(R.id.valuePitch);
        valueDegrees = this.findViewById(R.id.valueDegrees);

        valueX1 = this.findViewById(R.id.valueX1);
        valueX2 = this.findViewById(R.id.valueX2);
        valueX3 = this.findViewById(R.id.valueX3);

        valueY1 = this.findViewById(R.id.valueY1);
        valueY2 = this.findViewById(R.id.valueY2);
        valueY3 = this.findViewById(R.id.valueY3);

        valueZ1 = this.findViewById(R.id.valueZ1);
        valueZ2 = this.findViewById(R.id.valueZ2);
        valueZ3 = this.findViewById(R.id.valueZ3);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetoMeter = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerator = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    /**
     * Once our lifecycle reach this method we add EventListeners to all of our sensors.
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerator, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetoMeter, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * When we pause we don't need to read the inputs from our sensors, this would drain the battery.
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * When any of our registered sensors get a input we reach this method.
     * We check the event type and depending on the type we get all the values from the event.
     * We set the text of our UI text elements to the values.
     * To get the orientation we need to first calculate our 3 x 3 matrix, so we call getRotationMatrix.
     * We then use the rotation matrix to calculate the orientation of the phone.
     * The orientation has 3 values, azimuth, pitch, roll.
     * We can use the azimuth to determine the degrees to the north pole, this rotation is then applied to our imageView.
     *
     * We print all values we can get from the events onto the app.
     * @param event this is the event which was triggered.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //We create arrays to hold all of our values.
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            gravity = event.values;

            valueY1.setText(String.valueOf(Math.round(event.values[0])));
            valueY2.setText(String.valueOf(Math.round(event.values[1])));
            valueY3.setText(String.valueOf(Math.round(event.values[2])));
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            geomagnetic = event.values;

            valueX1.setText(String.valueOf(Math.round(event.values[0])));
            valueX2.setText(String.valueOf(Math.round(event.values[1])));
            valueX3.setText(String.valueOf(Math.round(event.values[2])));
        }
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){

            df.setMaximumFractionDigits(2);

            valueZ1.setText(df.format(event.values[0]));
            valueZ2.setText(df.format(event.values[1]));
            valueZ3.setText(df.format(event.values[2]));
        }
        else if(event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR){

            df.setMaximumFractionDigits(2);

            valueZ1.setText(df.format(event.values[0]));
            valueZ2.setText(df.format(event.values[1]));
            valueZ3.setText(df.format(event.values[2]));
        }

        SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic);
        SensorManager.getOrientation(rotationMatrix, orientation);

        azimuth = orientation[0];
        pitch = orientation[1];
        roll = orientation[2];

        float degree = (float)(Math.toDegrees(orientation[0])+360)%360;

        compassImage.setRotation(degree);

        valuePitch.setText(MessageFormat.format("{0}°", pitch));
        valueRoll.setText(MessageFormat.format("{0}°", roll));
        valueRotation.setText(MessageFormat.format("{0}°", orientation[0]));
        valueDegrees.setText(MessageFormat.format("{0}°", degree));
    }

    /**
     * Not used.
     * @param sensor sensor where accuracy changed.
     * @param accuracy the accuracy score.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}