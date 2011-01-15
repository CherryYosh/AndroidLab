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

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;

import android.opengl.GLSurfaceView;
import android.view.WindowManager;
import android.view.Window;
import android.widget.Toast;

import android.util.Log;
import android.view.MotionEvent; //touch support

/**
 *
 * @author James B Stevenson <j.brandon.s@gmail.com>
 */
public class CNA extends Activity {

    private static Context CONTEXT;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
	super.onCreate(icicle);



	getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
		WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	requestWindowFeature(Window.FEATURE_NO_TITLE);

	CNASurfaceView view = new CNASurfaceView(this);
	setContentView(view);

	CONTEXT = this;

	if (Camera.AIsSupported()) {
	    Camera.StartA();
	}
    }

    public static Context getContext() {
	return CONTEXT;
    }

    public static void Toast(final String s){
        //runOnUIThread fixes a crashing issue when called from a diffrent thread.
       ((Activity)CONTEXT).runOnUiThread(new Runnable(){public void run() {Toast.makeText(CONTEXT, s, Toast.LENGTH_SHORT).show();}});
    }

    public static void Logi(final String str){
        Log.i("CNA", str);
    }
}

class CNASurfaceView extends GLSurfaceView {
    private float _OldX;
    private float _OldY;

    public CNASurfaceView(Context context){
        super(context);
        setRenderer(new Render());
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                 _OldX = ev.getX(0);
                 _OldY = ev.getY(0);
                 break;
            case MotionEvent.ACTION_MOVE:
                Camera.HandelTouch(ev.getX(0) - _OldX, ev.getY(0) - _OldY);
                 _OldX = ev.getX(0);
                 _OldY = ev.getY(0);
                break;
            default:
                CNA.Logi("MotionEvent "+ev.getAction()+" raised but not handeled.");
                break;
        }

     return true;
    }
}
