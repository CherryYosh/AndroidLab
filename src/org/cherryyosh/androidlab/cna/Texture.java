/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cherryyosh.androidlab.cna;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Admin
 */
public class Texture {

    private int[] _glid = new int[1];

    public Texture(GL10 gl, Context context, int id){
	LoadTexture(gl, context, id);
    }

    public void LoadTexture(GL10 gl, Context c, int id) {
	InputStream input = c.getResources().openRawResource(id);
	Bitmap bitmap = null;

	try {
	    //BitmapFactory is an Android graphics utility for images
	    bitmap = BitmapFactory.decodeStream(input);

	} finally {
	    //Always clear and close
	    try {
		input.close();
		input = null;
	    } catch (IOException e) {
	    }
	}

	//Generate one texture pointer...
	gl.glGenTextures(1, _glid, 0);
	//...and bind it to our array
	gl.glBindTexture(GL10.GL_TEXTURE_2D, _glid[0]);

	//Create Nearest Filtered Texture
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

	//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

	//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
	GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

	//Clean up
	bitmap.recycle();
    }

    public int getID(){ return _glid[0]; }
}
