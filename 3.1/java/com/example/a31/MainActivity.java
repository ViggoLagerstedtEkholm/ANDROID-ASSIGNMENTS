package com.example.a31;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 *  The class makes it possible to use the Camera with the help of the Intent class.
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-21
 */
public class MainActivity extends AppCompatActivity implements OnClickListener
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;

    /**
     * OnCreate is called once the Activity is first initiated.
     * We pass a Bundle to this Activity so we can get the most recent saved instance.
     * Get the Button and ImageView with the help of their ID.
     * Add event handler to this button.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = this.findViewById(R.id.button);
        imageView = this.findViewById(R.id.imageView);
        myButton.setOnClickListener(this);
    }

    /**
     * When the user clicks the button in the layout this method handles that event and calls dispatchTakePictureIntent();
     * @param view This is the view object passed from the event.
     */
    @Override
    public void onClick(View view) {
        dispatchTakePictureIntent();
    }

    /**
     * Create an intent to capture image.
     * Try to start the activity with the help of the intent and the requestCode (REQUEST_IMAGE_CAPTURE)
     * If we fail show error message to the user.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Failed to capture image!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * When we get a activity result we reach this method.
     * We check if the parameter values match RESULT_OK and REQUEST_IMAGE_CAPTURE.
     * Get the Bundle from the data (Intent).
     * Use imageView to set the bitmap of the taken photo.
     * @param requestCode - The corresponding code we sent with the intent.
     * @param resultCode -  Status received.
     * @param data - Intent.
     */
    protected void onActivityResult (int requestCode,
                                     int resultCode,
                                     Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}