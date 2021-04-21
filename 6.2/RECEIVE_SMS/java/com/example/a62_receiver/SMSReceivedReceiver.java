package com.example.a62_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * This class is responsible for receiving broadcasts. We will be interested in the SMS_RECEIVED
 * intent. If this specific broadcast is received we will go through the intent extras and extract
 * the message. We will then create a intent that starts the MainActivity with the sms message displayed.
 * @author  Viggo Lagerstedt Ekholm
 * @date  2021-02-10
 */
public class SMSReceivedReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    /**
     *  This method will be called if we get a received broadcast.
     *  We will get the SMS from the intent and loop through all the messages.
     *  When we have looped over all the messages we compose a message String that we then
     *  send to the MainActivity with the help of a intent.
     * @param context application context.
     * @param intent the intent.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //Check if the received intent is the "android.provider.Telephony.SMS_RECEIVED"".
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras(); //Get the sms contents.
            if (bundle != null) {

                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }

                SmsMessage[] messages = new SmsMessage[pdus.length];

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    //Get all the sms messages.
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody()); //Add the sms segment to the whole sms message.
                }

                String message = sb.toString();

                //Show the message with toast!
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                //Send intent.
                Intent messageIntent = new Intent(context, MainActivity.class);
                messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                messageIntent.putExtra("Message", message);
                context.startActivity(messageIntent);

                this.abortBroadcast();
            }
        }
    }
}
