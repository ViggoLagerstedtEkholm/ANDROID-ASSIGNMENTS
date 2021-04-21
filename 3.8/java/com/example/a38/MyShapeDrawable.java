package com.example.a38;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class handles all the 2D drawing.
 * The class inherits from Drawable and must therefore implement draw, setAlpha, setColorFilter, getOpacity.
 * We draw a oval shape drawable 'shapeDrawableCircle' and draw a circle using the canvas.drawCircle.
 * The background will have a android jpg image that is a bitmap.
 */
public class MyShapeDrawable extends Drawable {
    private final Circle circle;
    private final ShapeDrawable shapeDrawableCircle;
    private final Paint paint;
    private final Bitmap logo;

    /**
     * Create a paint object that will be RED.
     */
    public MyShapeDrawable(Context context){
        paint = new Paint();
        circle = new Circle();

        paint.setARGB(255, 255, 0, 0);

        Resources res = context.getResources();
        logo = BitmapFactory.decodeResource(res, R.drawable.android);
        shapeDrawableCircle = new ShapeDrawable(new OvalShape());
        shapeDrawableCircle.getPaint().setColor(Color.BLACK);
        setBoundaries();
    }

    /**
     * This method returns the circle instance.
     * @return Circle
     */
    public Circle getCircle(){
        return circle;
    }

    /**
     * We draw our circles in this draw method using canvas.
     * @param canvas This is our canvas we will draw 2D graphics on.
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        setBoundaries();
        canvas.drawBitmap(logo, 0, 0,paint);
        shapeDrawableCircle.draw(canvas);
        canvas.drawCircle((float) getBounds().width() / 2, (float) getBounds().height() / 2, circle.getRadius(), paint);
    }

    /**
     * This method sets the boundaries for our drawables circle.
     */
    private void setBoundaries(){
        int shapeWidth = (int) circle.getRadius() / 2 + 100;
        int shapeHeight = (int) circle.getRadius() / 2 + 100;
        int x = getBounds().width() / 2;
        int y = getBounds().height() / 2;
        shapeDrawableCircle.setBounds(x, y, x + shapeWidth, y + shapeHeight);
    }

    /**
     * Not used
     * @param alpha .
     */
    @Override
    public void setAlpha(int alpha) {

    }

    /**
     * Not used
     * @param colorFilter .
     */
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }
    /**
     * Not used
     * @return int
     */
    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
