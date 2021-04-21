package com.example.a722;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * This program will create a vibration on the user's phone if they press the "Vibrate" button.
 *
 *  @author  Viggo Lagerstedt Ekholm
 *  @date   2021-02-05
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * This method is called when we first start the activity.
     * We fetch the button from our UI using the id.
     * We add a OnClickListener to this button.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonVibrate = this.findViewById(R.id.buttonVibrate);
        buttonVibrate.setOnClickListener(this);
    }

    /**
     * This method will be triggered if we click the "Vibrate" button.
     * We will create a new Vibrator object.
     * We will specify the duration of the vibration (1000ms)
     * We will make a Toast to display that we created the vibration.
     * @param view This is the view object passed from the event.
     */
    @Override
    public void onClick(View view) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        Toast.makeText(this, "Started vibrating!", Toast.LENGTH_SHORT).show();
    }
}