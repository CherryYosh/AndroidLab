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

/**
 *
 * @author James B Stevenson <j.brandon.s@gmail.com>
 */
public class GUI {

    private Square s;
    private Texture t;

    public GUI(GL10 gl, Context context) {
	s = new Square(20);
	t = new Texture(gl, context, R.drawable.calibratebutton);
    }

    public void Render(GL10 gl) {
	gl.glPushMatrix();
	gl.glMatrixMode(gl.GL_PROJECTION);
	gl.glPushMatrix();
	gl.glLoadMatrixf(Camera.GetOrthographic(), 0);
	gl.glMatrixMode(gl.GL_MODELVIEW);
	gl.glLoadIdentity();

	gl.glTranslatef(20, -300, -5);
	s.draw(gl, t.getID());

	gl.glMatrixMode(gl.GL_PROJECTION);
	gl.glPopMatrix();
	gl.glMatrixMode(gl.GL_MODELVIEW);
	gl.glPopMatrix();
    }

    /**
     * Test if this gui has a object which contains the provited point.
     *
     * @param x the x coord (screen space) to test
     * @param y the y coord (screen sapce) to test
     */
    public boolean containsPoint(float x, float y){
        return false;
    }
}
