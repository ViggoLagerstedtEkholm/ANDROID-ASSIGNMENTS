package com.example.a39;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * This class represent our cube model.
 * To store the 3D coordinates and color values we use FloatBuffers and ByteBuffers.
 * This particular model is a 3D pyramid.
 */
public class CubeModel {
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer colorBuffer;
    private final ByteBuffer indexBuffer;

    //Indices is used to tell the renderer how we want to arrange the vertices.
    //This saves a lot of memory because we can represent our 3D model with less bytes.
    private final byte[] indices = {
            2, 4, 3,   // front face
            1, 4, 2,   // right face
            0, 4, 1,   // back face
            4, 0, 3    // left face
    };

    /**
     * This constructor prepares our model to memory.
     *
     * We put our vertices into the FloatBuffer.
     *
     * We put our color values into the ByteBuffer.
     *
     * We put our indices into the FloatBuffer
     *
     */
    public CubeModel() {
        //Triangle coordinates.
        // 5 vertices of the pyramid in (x,y,z)
        // 0. left-bottom-back
        // 1. right-bottom-back
        // 2. right-bottom-front
        // 3. left-bottom-front
        // 4. top
        float[] vertices = {
                // 5 vertices of the pyramid in (x,y,z)
                -1.0f, -1.0f, -1.0f,  // 0. left-bottom-back
                1.0f, -1.0f, -1.0f,  // 1. right-bottom-back
                1.0f, -1.0f, 1.0f,  // 2. right-bottom-front
                -1.0f, -1.0f, 1.0f,  // 3. left-bottom-front
                0.0f, 1.0f, 0.0f   // 4. top
        };
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        //RGBA
        // 0. blue
        // 1. green
        // 2. blue
        // 3. green
        // 4. red
        float[] colors = {
                0.0f, 0.0f, 1.0f, 1.0f,  // 0. blue
                0.0f, 1.0f, 0.0f, 1.0f,  // 1. green
                0.0f, 0.0f, 1.0f, 1.0f,  // 2. blue
                0.0f, 1.0f, 0.0f, 1.0f,  // 3. green
                1.0f, 0.0f, 0.0f, 1.0f   // 4. red
        };
        byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        colorBuffer = byteBuf.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        indexBuffer = ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    /**
     * We use the vertexPointer and colorPointer to point to where we store our vertices and colors.
     * We also specify the size of each data type. Ex,
     * The vertices will use 3 coordinates to represent a triangle.
     * The colors will use 4 values to represent a RGBA value.
     *
     * We use glEnableClientState to enable vertex and color arrays.
     *
     * We use glDrawElements to finally draw our model passing in the indices.
     *
     * We use glDisableClientState to disable the vertex and color array when we're done.
     * @param gl the OpenGL rendering tools.
     */
    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE,
                indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
