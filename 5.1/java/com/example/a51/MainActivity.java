package com.example.a51;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

/**
 *  This program shows all the information about the user's position, this includes:
 *  -  Latitude
 *  -  Longitude
 *  -  Amplitude
 *  -  Accuracy
 *  -  Address
 *
 *  The user can also disable tracking.
 *  The user can select high and low accuracy, high accuracy means higher power usage due to costly triangulation.
 *
 * The app uses the Google Play API "com.google.android.gms:play-services-location:18.0.0"
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-02-01
 */
public class MainActivity extends AppCompatActivity implements OnSuccessListener {

    private static final int PERMISSION_FOR_FINE_LOCATION = 100;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallBack;

    private TextView valueLatitude;
    private TextView valueLongitude;
    private TextView valueAltitude;
    private TextView valueAccuracy;
    private TextView valueSpeed;
    private TextView valueUpdateStatus;
    private TextView valueTypeOfLocationAccuracy;
    private TextView valueAddress;

    private SwitchCompat slideLocation;
    private SwitchCompat slideLocationMode;

    /**
     * OnCreate is called once the Activity is first initiated.
     * We call all methods that enable GPS capability here.
     * @param savedInstanceState This is the first paramter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFields();
        initializeLocationAPI();
        initializeGPSCapabilities();
    }

    /**
     * This method gets all UI components and adds EventListeners to all buttons.
     */
    private void initializeFields() {
        valueLatitude =  this.findViewById(R.id.valueLatitude);
        valueLongitude = this.findViewById(R.id.valueLongitude);
        valueAltitude = this.findViewById(R.id.valueAltitude);
        valueAccuracy = this.findViewById(R.id.valueAccuracy);
        valueSpeed = this.findViewById(R.id.valueSpeed);
        valueUpdateStatus = this.findViewById(R.id.valueUpdateStatus);
        valueTypeOfLocationAccuracy = this.findViewById(R.id.valueTypeOfLocationAccuracy);
        valueAddress =  this.findViewById(R.id.valueAdress);

        slideLocation = this.findViewById(R.id.slideLocation);
        slideLocationMode = this.findViewById(R.id.slideLocationMode);

        slideLocation.setOnClickListener(v -> {
            if (slideLocation.isChecked()) {
                startLocationUpdate();
            } else {
                stopLocationUpdate();
            }
        });
        slideLocationMode.setOnClickListener(v -> {
            if (slideLocationMode.isChecked()) {
                locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
                valueTypeOfLocationAccuracy.setText(R.string.lowest_location_accuracy);
            } else {
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                valueTypeOfLocationAccuracy.setText(R.string.highest_location_accuracy);
            }
        });
    }

    /**
     * Here we create the locationRequest, we specify the updating interval and the minimal updating interval.
     * We set the Priority to HIGH_ACCURACY, this enables triangulation of the user's position using nearby base stations.
     *
     * We also add a LocationCallback which implements onLocationResult, this will get the current location and update all fields.
     */
    private void initializeLocationAPI() {
        int UPDATING_INTERVAL_SPEED = 2000;
        int UPDATING_INTERVAL_MIN_SPEED = 1000;
        locationRequest = new LocationRequest()
                .setInterval(UPDATING_INTERVAL_SPEED)
                .setFastestInterval(UPDATING_INTERVAL_MIN_SPEED)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location location = locationResult.getLastLocation();
                updateAllFields(location);
            }
        };

    }

    /**
     * This method will query the user for permission, if we get permission we can provide a onSuccessListener to the Location.
     */
    private void initializeGPSCapabilities() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FOR_FINE_LOCATION);
            }
        }
    }

    /**
     * This method handles the request for a specific permission.
     * We check if the requestCode is the one we sent (PERMISSION_FOR_FINE_LOCATION)
     * If our permission we asked the user for is granted we can initialize the GPS capabilities.
     * @param requestCode - The corresponding code we sent with the intent.
     * @param permissions -  The array of permissions we want to query the user for.
     * @param grantResults - The array containing the status of our permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_FOR_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeGPSCapabilities();
            } else {
                Toast.makeText(this, "This app can't operate without your permission!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * If we can successfully get the current location we reach this method. We update all the fields with the received location.
     *  -  Latitude
     *  -  Longitude
     *  -  Amplitude
     *  -  Accuracy
     *  -  Address
     *
     *  We use the hasAltitude / hasAccuracy/ hasSpeed, these might be empty!
     *
     *  We use GeoCoder to match the latitude and longitude to an address.
     *
     * @param location the Location object containing the information about the user's position.
     */
    @Override
    public void onSuccess(Object location) {
        updateAllFields((Location) location);
    }

    /**
     * This method receives a location that represents the location of the user.
     * We get all the values from this location object
     * @param location the updated location.
     */
    private void updateAllFields(Location location) {
        if(location != null){
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());

            valueLatitude.setText(latitude);
            valueLongitude.setText(longitude);

            if (location.hasAltitude()) {
                String altitude = String.valueOf(location.getAltitude());
                valueAltitude.setText(altitude);
            } else{
                valueAltitude.setText(R.string.no_altitude_detected);
            }

            if (location.hasAccuracy()) {
                String Accuracy = String.valueOf(location.getAccuracy());
                valueAccuracy.setText(Accuracy);
            }
            else{
                valueAccuracy.setText(R.string.no_accuracy_detected);
            }

            if (location.hasSpeed()) {
                String speed = String.valueOf(location.getSpeed());
                valueSpeed.setText(speed);
            }else{
                valueSpeed.setText(R.string.no_speed_detected);
            }

            Geocoder geocoder = new Geocoder(this);

            try{
                List<Address> locations = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                valueAddress.setText(locations.get(0).getAddressLine(0) );
            }catch(IOException e){
                valueAddress.setText(R.string.address_failed_to_fetch);
            }
        }
    }

    /**
     * We check if we have permission, if permission is granted we activate the tracking of the user location.
     */
    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            valueUpdateStatus.setText(R.string.location_is_being_updated);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FOR_FINE_LOCATION);
            }
        }
    }

    /**
     * This is a reset method, we just set all fields to "Updates off" because we call this once we want to stop tracking of the user position.
     */
    private void stopLocationUpdate(){
        valueUpdateStatus.setText(R.string.location_is_not_being_updated);
        valueLatitude.setText(R.string.updates_off);
        valueLongitude.setText(R.string.updates_off);
        valueSpeed.setText(R.string.updates_off);
        valueAccuracy.setText(R.string.updates_off);
        valueAddress.setText(R.string.updates_off);
        valueAltitude.setText(R.string.updates_off);
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }
}