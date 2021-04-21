package com.example.a12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

//Creates a class called DisplayMessageActivity and inherits from AppCompatActivity. This is a Activity class.
public class DisplayMessageActivity extends AppCompatActivity {

    //OnCreate is called once the Activity is first initiated.
    //We pass a Bundle to this Activity so we can get the most recent saved instance.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.onCreate(savedInstanceState); calls the Activity base class method with the bundle.
        //We specify what layout this Activity uses, and activity_display_message is a XML file.
        setContentView(R.layout.activity_display_message);

        //Here we initiate a intent of type Intent. We assign it the value of the Intent with getIntent();
        Intent intent = getIntent();
        //What we receive is the passed string from the previous Activity and assign this to the message variable.
        //The ainActivity.EXTRA_MESSAGE acts as the identifier of this passed string.
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Get the textView from the layout XML file.
        TextView textView = findViewById(R.id.textView);
        //Use the reference to the TextView to set the text value of this object.
        textView.setText(message);
    }
}