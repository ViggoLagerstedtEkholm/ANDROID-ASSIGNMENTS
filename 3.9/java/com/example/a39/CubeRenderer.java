package com.example.a39;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * This method handles all the rendering.
 */
public class CubeRenderer implements GLSurfaceView.Renderer {
    private float rotationSpeed;
    private final CubeModel cubeModel = new CubeModel();

    /**
     * This method prepares rendering and uses a bunch of OpenGl capabilities to do so.
     * @param gl our OpenGL rendering tools
     * @param config our config.
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    /**
     * We use a viewport to tell OpenGl the bounds of the window.
     * We use a projection, model, view (MVP) to correctly draw 3D objects onto the screen.
     * If we want we can do transformations to the model such as scaling and change 3D coordinates.
     * @param gl our OpenGL rendering tools
     * @param width width of our screen.
     * @param height height of our screen.
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * For each frame, we must clear the GL_COLOR_BUFFER_BIT & GL_DEPTH_BUFFER_BIT.
     * We reset our Matrix with glLoadIdentity.
     * We call the draw function in our model.
     * We can use glTranslatef & glRotatef to apply transformations and rotation to our model.
     * @param gl our OpenGL rendering tools
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -10.f);
        gl.glRotatef(rotationSpeed, 0f, 1.0f, 0f);

        cubeModel.draw(gl);

        gl.glLoadIdentity();

        rotationSpeed -= 0.15f;
    }
}
