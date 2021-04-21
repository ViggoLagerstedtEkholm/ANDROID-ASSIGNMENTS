package com.example.a310;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * This program will show the user 3 buttons. Each button leads to 3 different fragments.
 * Each fragment will hold a chart type created using AndroidPlot.
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-25
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}