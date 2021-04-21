package com.example.a22;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 *  This program loads/saves the most recent prime number into a SQLite database.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-20
 */

public class MainActivity extends AppCompatActivity {
    public static DatabaseHelper databaseHelper;

    /**
     * OnCreate is called once the Activity is first initiated.
     * Create an instance of DatabaseHelper which is the class used to load/save data into the database.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
    }
}