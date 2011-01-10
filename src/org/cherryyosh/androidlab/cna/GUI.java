/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cherryyosh.androidlab.cna;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

/**
 *
 * @author Admin
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
}
