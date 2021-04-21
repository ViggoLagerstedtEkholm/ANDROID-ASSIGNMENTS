package com.example.a33;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 *  The class makes it possible for the user to record video and then see the recorded video.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-27
 */

public class MainActivity extends AppCompatActivity implements OnClickListener {
    static final int REQUEST_CAMERA_VIDEO_CAPTURE = 1;
    static final int VIDEO_DURATION = 10;

    /**
     * OnCreate is called once the Activity is first initiated.
     * The button is accessed using the activity layout file and specifying id of the button.
     * We add the OnClickListener to this button so it can react to click events.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recordBtn = this.findViewById(R.id.recordBtn);
        recordBtn.setOnClickListener(this);
    }

    /**
     * When the user clicks the button in the layout this method handles that event and calls createIntentForVideoCapture();
     * @param view This is the view object passed from the event.
     */
    @Override
    public void onClick(View view) {
        createIntentForVideoCapture();
    }

    /**
     * Create an intent to capture video.
     * Put more data in the intent such as the duration of the video (MediaStore.EXTRA_DURATION_LIMIT, VIDEO_DURATION)
     * We start the activity for the result and pass the intent and REQUEST_CAMERA_VIDEO_CAPTURE.
     */
    private void createIntentForVideoCapture(){
        Intent myVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        myVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, VIDEO_DURATION);
        startActivityForResult(myVideoIntent, REQUEST_CAMERA_VIDEO_CAPTURE);
    }

    /**
     * We check if the parameter values match RESULT_OK and REQUEST_IMAGE_CAPTURE.
     * Get the data from the intent and display this data with the help of VideoView.
     * @param requestCode the request code.
     * @param resultCode the result code.
     * @param data intent data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            VideoView videoView = new VideoView(this);
            assert data != null;
            videoView.setVideoURI(data.getData());
            videoView.start();
            builder.setView(videoView).show();
        }
    }
}