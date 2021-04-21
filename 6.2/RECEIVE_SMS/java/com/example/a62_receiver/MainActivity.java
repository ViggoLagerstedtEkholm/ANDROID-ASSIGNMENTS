package com.example.a62_receiver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * This application will be able to receive a SMS.
 * The processing of the SMS is done in the SMSHandler class, this class extends the BroadcastReceiver and will
 * trigger if it receives a new SMS.
 * @author  Viggo Lagerstedt Ekholm
 * @date  2021-02-10
 */
public class MainActivity extends AppCompatActivity{
    private static final int RECEIVE_SMS = 101;

    /**
     * This method will ask the user for permission and it will also get the intent extras.
     * When we get a received sms this activity will be recreated and we will see a String with
     * the SMS message in the center of the screen.
     * @param savedInstanceState latest saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = this.findViewById(R.id.txfReceivedSMS);
        Log.d("Here is the receiver!", "Detected!");

        //Get the intent.
        Intent intent = getIntent();
        String message = intent.getStringExtra("Message");
        textView.setText(message);

        //Check if our textView is empty!
        if(textView.getText().equals("")){
            textView.setText(R.string.no_sms_received_yet);
        }

        //Check for permission.
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)){
                Toast.makeText(this, "You need to grant permission!", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS);
            }
        }
    }

    /**
     *  This method will be called if we have either accepted or rejected permission for SMS_RECEIVE.
     * @param requestCode the requestCode sent with the request.
     * @param permissions all asked permissions.
     * @param grantResults the array of granted permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == RECEIVE_SMS){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"The permission was granted.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"The permission was not granted.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}