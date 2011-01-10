/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Admin
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
}

class CNASurfaceView extends GLSurfaceView {
    public CNASurfaceView(Context context){
        super(context);
        setRenderer(new Render());
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
     final int historySize = ev.getHistorySize();
     final int pointerCount = ev.getPointerCount();
     for (int h = 0; h < historySize; h++) {
         Log.i("CNA", "At historical time "+ev.getHistoricalEventTime(h) + ":");
         for (int p = 0; p < pointerCount; p++) {
             Log.i("CNA", "pointer "+ev.getPointerId(p)+": ("+ev.getHistoricalX(p, h)+","+ev.getHistoricalY(p, h)+")");
         }
     }
     /*
     Log.i("CNA", "At time "+ev.getEventTime()+":");
     for (int p = 0; p < pointerCount; p++) {
         Log.i("CNA",   "pointer "+ev.getPointerId(p)+": ("+ev.getX(p)+","+ev.getY(p)+")");
     }*/
     return true;
    }
}
