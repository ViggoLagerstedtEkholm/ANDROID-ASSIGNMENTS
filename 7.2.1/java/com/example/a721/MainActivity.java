package com.example.a721;

import android.app.Notification;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.a721.MyApplication.CHANNEL_1;
import static com.example.a721.MyApplication.CHANNEL_2;

/**
 * This program will show the user 4 buttons in total these are:
 * - Dialog - Displays a custom dialog window with 2 fields, when the user presses ok these values will be printed under the Dialog button.
 * - Send on channel 1 - This will send a notification to our phone in high priority (we will see it pop up)
 * - Send on channel 2 - This will send a notification to our phone in low priority (we will not see a popup)
 * - Toast - Will display a small toast in the bottom of the screen saying "This is a toast!".
 *
 *  @author  Viggo Lagerstedt Ekholm
 *  @date   2021-02-05
 */
public class MainActivity extends AppCompatActivity implements MyDialogListener {
    private NotificationManagerCompat notificationManagerCompat;

    private final int CHANNEL_VALUE_1 = 1;
    private final int CHANNEL_VALUE_2 = 2;

    private TextView title;
    private TextView message;

    private TextView input1;
    private TextView input2;

    /**
     * This method is called when we first start the activity.
     * We fetch the buttons/textViews from our UI using the id.
     * We add a OnClickListeners to our buttons.
     *
     * - buttonDialog - show the dialog.
     * - buttonNotificationChannel1 - Check if the title and message is not empty, if so let the user know. Otherwise call the createNotification(CHANNEL_VALUE_1).
     * - buttonNotificationChannel2 - Check if the title and message is not empty, if so let the user know. Otherwise call the createNotification(CHANNEL_VALUE_2)
     * - buttonToast - Call the method createToast();
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        Button dialogButton = this.findViewById(R.id.buttonDialog);
        Button notificationButtonChannel1 = this.findViewById(R.id.buttonNotificationChannel1);
        Button notificationButtonChannel2 = this.findViewById(R.id.buttonNotificationChannel2);
        Button buttonToast = this.findViewById(R.id.buttonToast);

        input1 = this.findViewById(R.id.textView1);
        input2 = this.findViewById(R.id.textView2);

        title =  this.findViewById(R.id.inputTitle);
        message = this.findViewById(R.id.inputMessage);

        buttonToast.setOnClickListener(v -> createToast());

        notificationButtonChannel1.setOnClickListener(v -> {
            if(message.getText().length() > 0 && title.getText().length() > 0){
                createNotification(CHANNEL_VALUE_1);
            }
            else
            {
                Toast.makeText(MainActivity.this, "Enter a title and a message to notification!", Toast.LENGTH_SHORT).show();
            }
        });

        notificationButtonChannel2.setOnClickListener(v -> {
            if(message.getText().length() > 0 && title.getText().length() > 0) {
                createNotification(CHANNEL_VALUE_2);
            }
            else{
                Toast.makeText(MainActivity.this, "Enter a title and a message to notification!", Toast.LENGTH_SHORT).show();
            }
        });

        dialogButton.setOnClickListener(v -> showDialog());
    }

    /**
     * This method shows our custom dialog.
     */
    private void showDialog(){
        ExampleDialogFragment dialogFragment = new ExampleDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "Example dialog");
    }

    /**
     * This method is our eventListener, we print the values to the screen.
     * @param field1 first field passed from event.
     * @param field2 second field passed from event.
     */
    @Override
    public void inputData(String field1, String field2) {
        input1.setText(field1);
        input2.setText(field2);
    }

    /**
     * This method uses a switch case to see if we want to use channel1 or channel2.
     * Depending on what case is chosen we create a notification for that channel.
     * Our channel1 will create a popup notification.
     * Our channel2 will not create a popup.
     * @param channel the notification channel we going to use.
     */
    private void createNotification(int channel){
        String notificationTitle = title.getText().toString();
        String notificationMessage = message.getText().toString();

        switch(channel){
            case CHANNEL_VALUE_1:
                    Notification notificationChannel1 = new NotificationCompat.Builder(this, CHANNEL_1)
                            .setSmallIcon(R.drawable.ic_android_black_24dp)
                            .setContentTitle(notificationTitle)
                            .setContentText(notificationMessage)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .build();

                    notificationManagerCompat.notify(CHANNEL_VALUE_1, notificationChannel1);
                break;
            case CHANNEL_VALUE_2:
                    Notification notificationChannel2 = new NotificationCompat.Builder(this, CHANNEL_2)
                            .setSmallIcon(R.drawable.ic_android_black_24dp)
                            .setContentTitle(notificationTitle)
                            .setContentText(notificationMessage)
                            .setPriority(NotificationCompat.PRIORITY_LOW)
                            .build();

                    notificationManagerCompat.notify(CHANNEL_VALUE_2, notificationChannel2);
                break;
        }

    }

    /**
     * This method creates a Toast with the message "This is a toast!".
     */
    private void createToast(){
        Toast.makeText(this, "This is a toast!", Toast.LENGTH_SHORT).show();
    }
}