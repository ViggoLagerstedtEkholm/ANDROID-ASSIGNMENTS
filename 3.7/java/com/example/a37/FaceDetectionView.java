package com.example.a37;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * This class is a subclass of View and we use this class to detect faces in images.
 * The API used is the 'com.google.android.gms:play-services-vision:20.1.3'.
 * @author  Viggo Lagerstedt Ekholm
 */
public class FaceDetectionView extends View {
    private Bitmap currentBitMap;
    private SparseArray<Face> faceSparseArray;
    private final Paint paint;

    /**
     * Since this class is a subclass of View we must pass both Context and AttributeSet to the superclass constructor.
     * We create a instance of paint that should be drawing our outlines of the faces.
     * @param context the context
     * @param attrs attributes.
     */
    public FaceDetectionView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    /**
     * This method sets the image we should detect faces on. We then call the detectFaces method.
     * @param image the bitmap that should be used to detect faces on.
     */
    public void setSourceImage(Bitmap image){
        currentBitMap = image;
        detectFaces();
    }

    /**
     * This method gets called every time we want to draw something on screen.
     * We make sure we have a selected image and faces in that image before we try to draw these faces in the image.
     * drawBitmap(canvas); - calls the method that determines the scale and bounds of the Bitmap.
     * drawFaceCircle(canvas, scale); - calls the method that handles the positioning of the circles around the faces in the image.
     * @param canvas the canvas which we will draw different 2d graphics on.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((currentBitMap != null) && (faceSparseArray != null)) {
            double scale = drawBitmap(canvas);
            drawFaceCircle(canvas, scale);
        }
    }

    /**
     * This method creates a new FaceDetector.
     * We create a frame to hold our bitmap of our selected image.
     * We use detector.detect(frame); to get an array of faces in the image.
     * invalidate calls the onDraw to refresh the View.
     */
    public void detectFaces()
    {
        Log.d("DETECTING...", "detecting faces.");

        FaceDetector detector = new FaceDetector.Builder(getContext())
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        Frame frame = new Frame.Builder().setBitmap(currentBitMap).build();
        faceSparseArray = detector.detect(frame);
        detector.release();

        invalidate();
    }

    /**
     * This method gets all the information from both the canvas and the Bitmap (height, width)
     * We use the height and width information from the canvas and Bitmap to calculate the scale of the Bitmap.
     * We draw the Bitmap to the canvas with the bounds and the Bitmap.
     * @param canvas the canvas we draw on.
     * @return double
     */
    private double drawBitmap( Canvas canvas ) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double imageWidth = currentBitMap.getWidth();
        double imageHeight = currentBitMap.getHeight();

        double scale = Math.min( canvasWidth / imageWidth, canvasHeight / imageHeight );

        Rect bounds = new Rect( 0, 0, (int) ( imageWidth * scale ), (int) ( imageHeight * scale ) );
        canvas.drawBitmap( currentBitMap, null, bounds, null );
        return scale;
    }

    /**
     * This method goes through all the detected faces in the faces array.
     * To position the circle around the face we need to calculate the center point of each face.
     * If we use the provided scale and the face positions we can calculate left, right, top , right. This is the x1, x2, y1, y2 coordinates of a rectangle.
     * To then calculate the center point of this rectangle we need to do:
     * (left + right) / 2 -  (X coordinate of circle)
     * (top + bottom) / 2 -  (Y coordinate of circle)
     * This is the center point of our rectangle.
     * To calculate a decent radius we need to do:
     * (face.getWidth() + face.getHeight() ) / 2
     * We then use drawCircle to draw the circle around the face.
     * @param canvas the canvas we draw on.
     * @param scale the scale of the Bitmap.
     */
    private void drawFaceCircle(Canvas canvas, double scale) {
        float left;
        float top;
        float right;
        float bottom;

        for( int i = 0; i < faceSparseArray.size(); i++ ) {
            Face face = faceSparseArray.valueAt(i);

            left = (float) ( face.getPosition().x * scale );
            top = (float) ( face.getPosition().y * scale );
            right = (float) scale * ( face.getPosition().x + face.getWidth() );
            bottom = (float) scale * ( face.getPosition().y + face.getHeight() );

            canvas.drawCircle((left + right) / 2, (bottom + top) / 2, (face.getWidth() + face.getHeight() )/ 2, paint);
        }
    }
}
