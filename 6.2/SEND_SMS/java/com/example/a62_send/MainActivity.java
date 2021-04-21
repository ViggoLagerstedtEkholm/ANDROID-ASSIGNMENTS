package com.example.a62_send;

import android.Manifest;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * This class is responsible for sending a SMS message to a number with a message.
 * @author  Viggo Lagerstedt Ekholm
 * @date  2021-02-10
 */
public class MainActivity extends AppCompatActivity implements OnClickListener{

    private EditText message;
    private EditText number;
    private TextView textView;

    /**
     * This method will get the UI components and ask the user for permission to SEND_SMS.
     * @param savedInstanceState the last saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = this.findViewById(R.id.buttonSend);

        message = this.findViewById(R.id.inputText);
        number = this.findViewById(R.id.editTextPhone);
        textView =this.findViewById(R.id.textView);

        sendButton.setOnClickListener(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
    }

    /**
     * This method will be triggered if we click our "SEND SMS" button. Here we call sendSMS();
     * @param view the view.
     */
    @Override
    public void onClick(View view)
    {
        sendSMS();
    }

    /**
     * This method will try to send a message with the help of SmsManager. If we can send a message
     * we will see "SMS Sent Successfully" else "SMS Could Not Send". We attach the message EditText.getText()
     * which will fetch the text currently in the EditText.
     *
     * We then also print the phone number we sent the SMS to onto the screen.
     */
    private void sendSMS(){
        try{
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(number.getText().toString(), null, message.getText().toString(), null,null);

            Toast.makeText(MainActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
            textView.setText(number.getText().toString());
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "SMS Could Not Send", Toast.LENGTH_SHORT).show();
            textView.setText(number.getText().toString());
        }
    }
}
