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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

import java.util.Random;


/**
 *
 * @author James B Stevenson <j.brandon.s@gmail.com>
 */
public class Render implements Renderer {

    private Square s;
    private Random r;
    private Texture t;
    private GUI gui;

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);

		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		Camera.Init();

		s = new Square();
		r = new Random();
		gui = new GUI(gl, CNA.getContext());
    }

    public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                gl.glLoadIdentity();

		gl.glLoadMatrixf(Camera.GetModelview(), 0);

		CameraTestRender01(gl);

		gui.Render(gl);


    }

    private void CameraTestRender01(GL10 gl){
		gl.glDisable(GL10.GL_TEXTURE_2D);
		/* left to right */
		r.setSeed(12);
		gl.glPushMatrix();
		for(int i = 0; i < 16; i++){
		    gl.glPushMatrix();
			gl.glColor4f(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
			gl.glRotatef(22.5f * i, 0, 1, 0);
			gl.glTranslatef(0, 0, -5f);
			s.draw(gl);
		    gl.glPopMatrix();
		}
		gl.glPopMatrix();

		/* top to bottom */
		r.setSeed(21);
		gl.glPushMatrix();
		for(int i = 0; i < 16; i++){
		    gl.glPushMatrix();
			gl.glColor4f(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
			gl.glRotatef(22.5f * i, 1, 0, 0);
			gl.glTranslatef(0, 0, -5f);
			s.draw(gl);
		    gl.glPopMatrix();
		}
		gl.glPopMatrix();

		gl.glEnable(GL10.GL_TEXTURE_2D);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {

		gl.glViewport(0, 0, width, height);
		Camera.Resize(width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		gl.glLoadMatrixf(Camera.GetProjection(), 0);

                gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
}
