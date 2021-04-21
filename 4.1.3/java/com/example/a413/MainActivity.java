package com.example.a413;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
/**
 * This program will enable the user to enter Email, Subject, Message, File.
 * The application will then open a selection of applications the user can choose from.
 * We can choose GMail for example. The file and the other attached information to the gmail will automatically fill out.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-26
 */
public class MainActivity extends AppCompatActivity{
    private static final int RESULT_FILE_CODE = 1;
    private Uri fullPath;

    private Button buttonSend;

    private EditText eMail;
    private EditText subject;
    private EditText message;

    private TextView textView;

    /**
     * This method gets called once we start the activity.
     * We get our buttons from the UI by id.
     * We add OnClickListeners to all of our buttons.
     * We disable the send button until a file has been chosen.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSend = this.findViewById(R.id.buttonSend);
        Button buttonFileChooser = this.findViewById(R.id.buttonFileChooser);
        textView = this.findViewById(R.id.textView);

        eMail = this.findViewById(R.id.editTextTextEmailAddress);
        subject = this.findViewById(R.id.editTextTextSubject);
        message = this.findViewById(R.id.editTextTextMessage);

        //send email
        buttonSend.setOnClickListener(v -> {
            if(eMail.getText().length() > 0 && subject.getText().length() > 0 && message.getText().length() > 0){
                sendEmail(eMail.getText().toString(), subject.getText().toString(), message.getText().toString());
            }
            else{
                Toast.makeText(MainActivity.this, "Make sure to fill in all values!", Toast.LENGTH_SHORT).show();
            }
        });

        //add file to email.
        buttonFileChooser.setOnClickListener(v -> {
            Intent pickFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            pickFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            pickFileIntent.setType("*/*");
            pickFileIntent = Intent.createChooser(pickFileIntent, "Select file for upload");
            startActivityForResult(pickFileIntent, RESULT_FILE_CODE);
        });

        buttonSend.setEnabled(false);
    }

    /**
     * When we get a activity result we reach this method.
     * We check if the parameter values match RESULT_OK and RESULT_FILE_CODE.
     * Get the Bundle from the data (Intent).
     * We use the intent to get the data, this holds the URI of our selected file from the FileChooser.
     * @param requestCode - The corresponding code we sent with the intent.
     * @param resultCode -  Status received.
     * @param data - Intent.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_FILE_CODE && resultCode == RESULT_OK){
            fullPath = data.getData();
            textView.setText(fullPath.getPath());
            buttonSend.setEnabled(true);
        }
    }

    /**
     * This method will create a Intent with ACTION_SEND.
     *
     * We add our attributes to our intent and finally call startActivity.
     *
     * When this activity starts the user will be prompt to select an app for this action.
     *
     * We will use GMail to send our email.
     * @param eMail email addresses
     * @param subject email subject
     * @param message email message
     */
    private void sendEmail(String eMail, String subject, String message)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{eMail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.putExtra(Intent.EXTRA_STREAM, fullPath);

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        buttonSend.setEnabled(false);
    }
}