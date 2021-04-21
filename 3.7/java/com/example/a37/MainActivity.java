package com.example.a37;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *  This program makes it possible to detect faces in images.
 *  The user can provide any images they want.
 *  The face detection uses the Google Services face detection API (com.google.android.gms:play-services-vision:20.1.3)
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-26
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FaceDetectionView faceDetection;
    private static final int DETECT_CODE_REQUEST = 100;


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
        Button buttonDetect = this.findViewById(R.id.buttonDetect);
        faceDetection = this.findViewById(R.id.imageView);
        buttonDetect.setOnClickListener(this);
    }

    /**
     * We create a intent that opens the files to the device, the user can pick any images!
     * @param view This is the view object passed from the event.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, DETECT_CODE_REQUEST);
    }

    /**
     * We create a Bitmap using the provided URI.
     * @param uri This is the adress on the device of the selected image.
     * @return Bitmap
     */
    private Bitmap createBitMap(Uri uri) throws FileNotFoundException {
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, null);
    }

    /**
     * We check if the parameter values match RESULT_OK and DETECT_CODE_REQUEST.
     * We get the data from the intent.
     * We use the FaceDetectionView class to set the Bitmap that should be used to detect faces.
     * @param requestCode the request code.
     * @param resultCode the result code.
     * @param data intent data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == DETECT_CODE_REQUEST && resultCode == RESULT_OK){
            Uri uri;
            try{
                uri = data.getData();
                Bitmap sourceImage = createBitMap(uri);
                faceDetection.setSourceImage(sourceImage);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}