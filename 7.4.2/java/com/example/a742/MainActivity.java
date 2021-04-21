package com.example.a742;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;

/**
 * This program will display all the environment sensors this includes:
 *  - pressure
 *  - humidity
 *  - light
 *  - device temperature
 *  - environment temperature
 *  @author  Viggo Lagerstedt Ekholm
 *  @date   2021-02-05
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;

    private Sensor pressure;
    private Sensor humidity;
    private Sensor light;
    private Sensor deviceTemperature;
    private Sensor environmentTemperature;

    private TextView valueEnvironmentTemperature;
    private TextView valueDeviceTemperature;
    private TextView valueHumidity;
    private TextView valueLight;
    private TextView valueAirPressure;

    /**
     * This method is called when we first start the activity.
     * We call our initialize method.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    /**
     * This method gets all the UI components by id.
     * We also create all of our sensors here.
     */
    private void initialize()
    {
        valueEnvironmentTemperature = this.findViewById(R.id.valueTemperature);
        valueDeviceTemperature = this.findViewById(R.id.valueDeviceTemperature);
        valueHumidity = this.findViewById(R.id.valueHumidity);
        valueLight = this.findViewById(R.id.valueLight);
        valueAirPressure = this.findViewById(R.id.valueAirPressure);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        deviceTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        environmentTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    /**
     * This method is called when we resume our application.
     * We register all of our sensors to or EventListener.
     */
    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();

        sensorManager.registerListener(this, environmentTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, humidity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, deviceTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * This method is called when we pause our application.
     * We unregister all of our sensors so we don't drain the battery.
     */
    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * If any sensor detect a change this method gets called.
     * We use the switch case to see what event was triggered.
     * For each of these sensors we have a textView that we print the values to so the user can see the values on screen.
     * @param event the event we triggered.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_TEMPERATURE:
                valueDeviceTemperature.setText(event.values[0] + " °C");
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                valueHumidity.setText(MessageFormat.format("{0}  %", event.values[0]));
                break;
            case Sensor.TYPE_PRESSURE:
                valueAirPressure.setText(MessageFormat.format("{0} hPa or mbar", event.values[0]));
                break;
            case Sensor.TYPE_LIGHT:
                valueLight.setText(MessageFormat.format("{0}  lx", event.values[0]));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                valueEnvironmentTemperature.setText(MessageFormat.format("{0} °C", event.values[0]));
                break;
        }
    }

    /**
     * Not used
     * @param sensor .
     * @param accuracy .
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}