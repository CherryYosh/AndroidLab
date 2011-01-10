/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cherryyosh.androidlab.cna;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
    // Our vertices.

    private float[] vertices;
    private float texture[] = {
	0.0f, 0.0f,
	0.0f, 1.0f,
	1.0f, 1.0f,
	1.0f, 0.0f};

    // The order we like to connect them.
    private short[] indices = {0, 1, 2, 0, 2, 3};
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private ShortBuffer indexBuffer;

    public Square() {
	this(1);
    }

    public Square(int scale) {
	vertices = new float[]{
		    -1.0f * scale, 1.0f * scale, 0.0f,
		    -1.0f * scale, -1.0f * scale, 0.0f,
		    1.0f * scale, -1.0f * scale, 0.0f,
		    1.0f * scale, 1.0f * scale, 0.0f,};


	ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	vbb.order(ByteOrder.nativeOrder());
	vertexBuffer = vbb.asFloatBuffer();
	vertexBuffer.put(vertices);
	vertexBuffer.position(0);

	ByteBuffer byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	textureBuffer = byteBuf.asFloatBuffer();
	textureBuffer.put(texture);
	textureBuffer.position(0);

	ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
	ibb.order(ByteOrder.nativeOrder());
	indexBuffer = ibb.asShortBuffer();
	indexBuffer.put(indices);
	indexBuffer.position(0);
    }

    public void draw(GL10 gl) {
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

	gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
		GL10.GL_UNSIGNED_SHORT, indexBuffer);

	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    /**
     * This function draws our square on screen.
     * @param gl
     */
    public void draw(GL10 gl, int id) {
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

	gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
		GL10.GL_UNSIGNED_SHORT, indexBuffer);

	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}
