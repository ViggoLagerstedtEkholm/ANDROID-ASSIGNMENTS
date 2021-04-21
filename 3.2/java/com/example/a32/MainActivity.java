package com.example.a32;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

/**
 *  The class makes it possible for the user to record and play audio.
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-25
 */

public class MainActivity extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private Button startBtn;
    private Button stopBtn;

    private Button playBtn;
    private Button cancelBtn;

    private String fullPath;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private boolean permissionToRecordAccepted = false;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};
    /**
     * OnCreate is called once the Activity is first initiated.
     * We request permission from the user to use the microphone.
     * We get all buttons from the layout.
     * We add events to all of our buttons.
     * Creates the full path for our audio file.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        startBtn = this.findViewById(R.id.btnStart);
        stopBtn = this.findViewById(R.id.btnStop);

        playBtn = this.findViewById(R.id.btnPlay);
        cancelBtn = this.findViewById(R.id.btnCancel);

        cancelBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        playBtn.setEnabled(false);

        startBtn.setOnClickListener(v -> Start());
        stopBtn.setOnClickListener(v -> Stop());
        playBtn.setOnClickListener(v -> Play());
        cancelBtn.setOnClickListener(v -> Cancel());

        fullPath = getExternalCacheDir().getAbsolutePath();
        fullPath += "/audio.3gp";
    }

    /**
     * Creates a new MediaPlayer and try to play the sound from given file path.
     * Handles the UI logic so buttons that should not be pressed get disabled.
     */
    private void Play(){
         mediaPlayer = new MediaPlayer();
        try {
            Log.e("PLAY_PATH", fullPath);
            mediaPlayer.setDataSource(fullPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            startBtn.setEnabled(false);
            playBtn.setEnabled(false);
            cancelBtn.setEnabled(true);
        } catch (IOException e) {
            Log.e("PREPARE_STATUS", "prepare() failed");
            Log.e("trace", e.getMessage());

        }
    }

    /**
     * Stops the sound from playing.
     * Handles the UI logic so buttons that should not be pressed get disabled.
     */
    private void Cancel(){
        mediaPlayer.release();
        mediaPlayer = null;
        cancelBtn.setEnabled(false);
        playBtn.setEnabled(true);
        startBtn.setEnabled(true);
    }

    /**
     * Creates a new MediaRecorder and try to play the sound from given file path.
     * setAudioSource(MediaRecorder.AudioSource.MIC) - This will pick up sound from the microphone.
     * setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) - The file created will be .3gp
     * setOutputFile(fullPath) - This is the path of the output.
     * setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) - This is the encoding of the file.
     * Handles the UI logic so buttons that should not be pressed get disabled.
     */
    private void Start(){
        Log.d("STARTED_RECORDING", fullPath);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fullPath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            mediaRecorder.prepare();
        }catch(IOException e){
            e.printStackTrace();
        }
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        playBtn.setEnabled(false);
        mediaRecorder.start();
    }

    /**
     * Stops the recording.
     * Handle the UI logic so buttons that should not be pressed get disabled.
     */
    private void Stop(){
        Log.d("STOPPED_RECORDING", fullPath);
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        playBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        startBtn.setEnabled(true);
    }

    /**
     * Creates a popup window that queries the user for permission. If it's not accepted to use the microphone end this activity.
     * @param requestCode This is the request code.
     * @param permissions array of user permissions.
     * @param grantResults array of granted results.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted ) {
            finish();
        }
    }
}