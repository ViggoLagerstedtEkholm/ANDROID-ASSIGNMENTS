package com.example.a12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


//Creates a class called MainActivity and inherits from AppCompatActivity. This is a Activity class.
//We also use the interface OnClickListener to implement behavior for click events.
public class MainActivity extends AppCompatActivity implements OnClickListener {
    //We declare a static final String variable which will be our identifier when we pass the intent.
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    //OnCreate is called once the Activity is first initiated.
    //We pass a Bundle to this Activity so we can get the most recent saved instance.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState); calls the Activity base class method with the bundle.
        super.onCreate(savedInstanceState);
        //We specify what layout this Activity uses, and activity_main is a XML file.
        setContentView(R.layout.activity_main);

        //Get the button from the view using "findViewById". This will return a button corresponding to that ID.
        Button myButton = this.findViewById(R.id.button);
        //Add the event handler to this button.
        myButton.setOnClickListener(this);
    }

    //This is our event handler which is called once we click the button.
    @Override
    public void onClick(View v) {
        //Send our message!
        sendMessage();
    }

    //Send message to another activity.
    public void sendMessage(){
        //Create a intent object and pass our context and destination(DisplayMessageActivity).
        Intent myIntent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        String userText = editText.getText().toString();
        //Add data to our intent.
        myIntent.putExtra(EXTRA_MESSAGE, userText);
        startActivity(myIntent);
    }
}