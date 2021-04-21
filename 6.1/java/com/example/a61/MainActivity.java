package com.example.a61;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * This program will show the user their call history.
 * The user can call anyone from this call history by clicking "Call".
 * At any point the user can refresh the page by scrolling up.
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-02-03
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<HistoryModel> callHistoryModels;
    private HistoryLogAdapter callHistoryAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    //The permissions we need to make the app work
    String[] appPermissions = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
    };

    private final int PERMISSION_REQUEST_CODE = 101;

    /**
     * This method is called when we first start the activity.
     * We start by setting the title to History.
     * We call the method instantiate, this will get all the UI components and add the adapter to our screen.
     * The swipeRefreshLayout is the refresh function we can see happening when we scroll up.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("History");
        instantiate();
        if(checkForHistoryPermission()){
            populateCallhistory();
        }
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * This method gets all UI components and creates the adapter.
     */
    private void instantiate(){
        swipeRefreshLayout = findViewById(R.id.activity_main_swipe_refresh_layout);
        RecyclerView recyclerView = findViewById(R.id.activity_main_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        callHistoryModels = new ArrayList<>();
        callHistoryAdapter = new HistoryLogAdapter(this, callHistoryModels);
        recyclerView.setAdapter(callHistoryAdapter);
    }

    /**
     * This method is called if we refresh the screen.
     * When this event happens we will call populateCallhistory() method which fetches all of the recent calls.
     */
    @Override
    public void onRefresh() {
        if(checkForHistoryPermission())
        {
            Log.d("REFRESH", "...");
            populateCallhistory();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * We go through all of the permissions requested, if any of them is denied we will not populate the screen with call history.
     * @param requestCode the code we passed when we sent the request.
     * @param permissions the permissions we wanted.
     * @param grantResults The result of our request to get the permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasFoundDenied = false;
        if(requestCode == PERMISSION_REQUEST_CODE){
            for(int i = 0; i < permissions.length; i++){
                if(grantResults[i]==PackageManager.PERMISSION_DENIED){
                    hasFoundDenied = true;
                    break;
                }
            }
            if(!hasFoundDenied){
                populateCallhistory();
            }
        }
    }

    /**
     * This method checks what permissions we need, if one is not granted we will add this to our list of needed permissions.
     * If the list is not empty we will ask for all permissions in the array.
     * @return boolean
     */
    private boolean checkForHistoryPermission(){
        ArrayList<String> listOfPermissionsNeeded = new ArrayList<>();
        for(String aPermission : appPermissions){
            if(ContextCompat.checkSelfPermission(this, aPermission) != PackageManager.PERMISSION_GRANTED){
                listOfPermissionsNeeded.add(aPermission);
            }
        }

        if(!listOfPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listOfPermissionsNeeded.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    /**
     * This method will fetch all the calls in our local database. We will add all of these to our adapter and refresh it once it's done.
     * We should also see what type the call is, what time/date, phone number.
     * After each iteration we create a new HistoryModel object that holds the fetched columns.
     */
    private void populateCallhistory() {
        String sortingOrder = android.provider.CallLog.Calls.DATE + " DESC";

        Cursor cursor;
        cursor = this.getContentResolver().query(
                CallLog.Calls.CONTENT_URI, null, null, null, sortingOrder);

        callHistoryModels.clear();

        while (cursor.moveToNext()) {
            String fullDate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
            String telephoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            String TYPE_OF_CALL = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
            String date = dateFormat.format(new Date(Long.parseLong(fullDate)));

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            String time =timeFormat.format(new Date(Long.parseLong(fullDate)));

            switch (Integer.parseInt(TYPE_OF_CALL)) {
                case CallLog.Calls.INCOMING_TYPE:
                    TYPE_OF_CALL = "Incoming";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    TYPE_OF_CALL = "Outgoing";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    TYPE_OF_CALL = "Missed";
                    break;
                case CallLog.Calls.VOICEMAIL_TYPE:
                    TYPE_OF_CALL = "Voicemail";
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                    TYPE_OF_CALL = "Rejected";
                    break;
                case CallLog.Calls.BLOCKED_TYPE:
                    TYPE_OF_CALL = "Blocked";
                    break;
                case CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                    TYPE_OF_CALL = "Externally Answered";
                    break;
                default:
                    TYPE_OF_CALL = "NA";
            }

            HistoryModel historyModel = new HistoryModel(telephoneNumber, date, time, TYPE_OF_CALL);
            callHistoryModels.add(historyModel);
            Log.d("COUNT: ", String.valueOf(callHistoryModels.size()));
        }
        cursor.close();
        callHistoryAdapter.notifyDataSetChanged();
    }
}