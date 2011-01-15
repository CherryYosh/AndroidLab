/*
 * Copyright (C) 2011 James B Stevenson <j.brandon.s@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * @author James B Stevenson <j.brandon.s@gmail.com>
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
