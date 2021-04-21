package com.example.a721;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
/**
 * This class will create our 2 channels that we should send notifications to.
 * The reason we use Application as our superclass is because this class will be instantiated before
 * any other class when the process for your application/package is created.
 *
 */
public class MyApplication extends Application {
    public static final String CHANNEL_1 = "CHANNEL1";
    public static final String CHANNEL_2 = "CHANNEL2";

    /**
     * Called when we start our application.
     * We call the createNotificationChannels();
     */
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    /**
     * This method will create 2 notification channels:
     * Channel 1 - IMPORTANCE_HIGH (POPUP) get placed in the notification list.
     * Channel 2 - IMPORTANCE_LOW (NO POPUP) get placed in the silent notification list.
     */
    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_1, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("this is channel 1");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_2, "Channel 2", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("this is channel 2");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

