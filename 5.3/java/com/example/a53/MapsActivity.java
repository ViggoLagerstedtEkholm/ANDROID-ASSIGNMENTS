package com.example.a53;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * This program shows your current position and if you look around the app will correspond this to real life direction.
 * The API key is located in google_maps_api.XML in the values folder. import ( 'com.google.android.gms:play-services-maps:17.0.0' );
 * I made it so that the camera is locked to the user position, if you use the emulator virtual sensor and rotate it will rotate around the position using the phone compass.
 * A provided API key is located in the google_maps_api.xml file!
 *
 * Wait until the map is ready, this can take 5-10 seconds!
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-02-02
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, SensorEventListener {

    private static final int PERMISSION_FOR_FINE_LOCATION = 100;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private SensorManager sensorManager;
    private Sensor magnetoMeter;
    private Sensor accelerator;
    private LatLng latLng;

    private static final double PI = 3.14159265359;

    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private final float[] orientation = new float [3];
    private final float[] rotationMatrix = new float[9]; // 3 x 3 matrix

    float azimuth = 0;

    /**
     * This method is called when we first start the activity.
     * We get our map fragment from the view by ID, we add a onMapReadyCallback on it.
     * We get all of our sensors needed.
     * We call the initializeAPI to prepare the Google Map API.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetoMeter = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerator = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        initializeAPI();
    }

    /**
     * When our application resumes we reach this method.
     * We add listeners to our sensors.
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetoMeter, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerator, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * When our application pauses we reach this method.
     * We remove all listeners to our sensors, it would drain the battery otherwise.
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * We check the user for permission, if permission is granted we request the locationUpdates from the user.
     */
    private void initializeAPI() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FOR_FINE_LOCATION);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    /**
     * If we get permission we can request the locationUpdates from the user.
     * Else we exit the application.
     * @param requestCode the code we passed when we sent the request.
     * @param permissions the permissions we wanted.
     * @param grantResults The result of our request to get the permission.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_FOR_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {
                Toast.makeText(this, "This app can't operate without your permission!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * If the user changes their latitude and longitude this method will be called.
     * We get the latitude and longitude from the new location.
     * We clear the map from all previous markers.
     * The received location can be used to get the address of the user.
     * We add a marker to this new location and move the camera to this marker.
     * @param location the updated location of the user's phone.
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        latLng = new LatLng(latitude, longitude);

        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addressesList = geocoder.getFromLocation(latitude, longitude, 1);
            String currentAddress = addressesList.get(0).getAddressLine(0);
            MarkerOptions startDest = new MarkerOptions().position(latLng).title(currentAddress);
            mMap.clear();
            mMap.addMarker(startDest);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called once the Google map is ready.
     * @param googleMap the googleMap object representing the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    /**
     * When any of our registered sensors get a input we reach this method.
     * We check the event type and depending on the type we get all the values from the event.
     * We calculate rotationMatrix, this is then used to calculate orientation.
     * We get the azimuth from our orientation and this will be used to point towards our position coordinates.
     * We call updateCamera with the calculated azimuth.
     * @param event the event from our sensor.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            gravity = event.values;
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            geomagnetic = event.values;
        }

        SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic);
        SensorManager.getOrientation(rotationMatrix, orientation);

        azimuth = (float) (orientation[0] * 180 / PI);

        updateCamera(azimuth);
    }

    /**
     * This method will point our camera towards the location we are currently at.
     * If we rotate our camera real life our application will translate this into the camera rotation in the app.
     * @param bearing the direction of our camera.
     */
    public void updateCamera(float bearing) {
        Log.d("Bearing: " , String.valueOf(bearing));
        if(latLng != null){
            CameraPosition currentPlace = new CameraPosition.Builder()
                    .target(latLng)
                    .bearing(bearing).tilt(65.5f).zoom(10f).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
        }
    }

    /**
     * Not used
     * @param provider .
     * @param status .
     * @param extras .
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Not used
     * @param provider .
     */
    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    /**
     * Not used
     * @param provider .
     */
    @Override
    public void onProviderDisabled(@NonNull String provider) {

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