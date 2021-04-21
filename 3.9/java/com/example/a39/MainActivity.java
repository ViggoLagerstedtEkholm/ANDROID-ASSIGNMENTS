package com.example.a39;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * This program draws a 3D pyramid rotating around the Y axis indefinitely.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-29
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method gets called once we start the activity.
     *
     * We remove the title of the screen.
     * We make the app full-screen.
     * We create the OpenGl surface View.
     * We add the renderer to this surface View.
     * We finally set the content view of this new surface View instance.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new CubeRenderer());
        setContentView(view);
    }
}