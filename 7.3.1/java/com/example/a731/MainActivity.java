package com.example.a731;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This program will show the user a button, when the user clicks this button a Toast will write either:
 * 1. "Device is connected to the internet!" - If we have internet.
 * 2. "No connection to internet!" - If we don't have internet.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-02-04
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * This method is called when we first start the activity.
     * We get all the UI components by id.
     * We add onClickListeners on our button.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCheckConnection = this.findViewById(R.id.buttonCheckInternet);

        buttonCheckConnection.setOnClickListener(this);
    }

    /**
     * When we click the button we will call this method.
     * We will create a ConnectivityManager that will handle the connection checking.
     * We call getActiveNetworkInfo(), this returns a NetworkInfo (the status of the network)
     * We create a boolean isConnected and get the state using the NetworkInfo object.
     * We call showStatus with our isConnected boolean value.
     * @param view This is the view object passed from the event.
     */
    @Override
    public void onClick(View view) {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        showStatus(isConnected);
    }

    /**
     * This method will print either:
     * 1. "Device is connected to the internet!" - if isConnected = true;
     * 2. "No connection to internet!" - if isConnected = false;
     * @param isConnected boolean
     */
    private void showStatus(boolean isConnected){
        if(isConnected){
            Toast.makeText(this, "Device is connected to the internet!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No connection to internet!", Toast.LENGTH_SHORT).show();
        }
    }
}