package com.example.a23;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 *  This program loads all contacts from the contacts table. It displays the name of the contact and the phone number.
 *  If the contact does not have a number it will simply display "NO NUMBER".
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-25
 */
public class MainActivity extends AppCompatActivity {

    /**
     * OnCreate is called once the Activity is first initiated.
     * We use setSoftInputMode so our ListView fragment wont get pushed up if we start typing.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}