/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cherryyosh.androidlab.cna;

import android.opengl.Matrix;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * NOTE: Right now really is just a nice wrapper class.
 *
 * @author Admin
 */
public class Camera {

    private static float _Modelview[];
    private static float _Projection[];
    private static float _Orthographic[];
    private static float _X = 0;
    private static float _Y = 0;
    private static float _Z = 0;
    private static float _RotX = 0;
    private static float _RotY = 0;
    private static float _RotZ = 0;
    //Acceelerometer support
    private static float _YawnThreshhold = 0f;
    private static float _PitchThreshhold = 0.2f;
    private static Sensor _Sensor;
    private static SensorManager _SensorManager;
    private static Boolean _ASupported;
    private static boolean _AListening = false;

    public static void Init() {
	_Modelview = new float[16];
	_Projection = new float[16];
	_Orthographic = new float[16];

	Matrix.setIdentityM(_Modelview, 0);

	Matrix.setIdentityM(_Projection, 0);
    }

    public static void Resize(int width, int height){
	Projection(45.0f, (float)width/height, 1, 1000);

	Matrix.orthoM(_Orthographic, 0, 0, width, -height, 0, 1, 10);
    }

    public static void Projection(float fovy, float aspect, float zNear, float zFar){
	//Thanks to GLU, same code taken from them to prevent need of adding a library.
	float top = zNear * (float)Math.tan(fovy*(Math.PI / 360.0));
	float bottom = -top;
	float left = bottom * aspect;
	float right = top * aspect;


	Matrix.setIdentityM(_Projection, 0);
	Matrix.frustumM(_Projection, 0, left, right, bottom, top, zNear, zFar);
    }

    public static void Translate(float x, float y, float z) {
	_X += x;
	_Y += y;
	_Z += z;

	Matrix.translateM(_Modelview, 0, x, y, z);
    }

    /**
     *
     * controls the rotation of the camera, think of a head turning
     *
     * @param x The amount to rotate around the X axis
     * @param y The amount to rotate around the Y axis
     * @param z The amount to rotate around the Z axis
     */
    public static void Rotate(float x, float y, float z) {
	_RotX += x;
	_RotY += y;
	//_RotZ += z;

	if(_RotX < 0) _RotX += 360; if(_RotX > 360) _RotX -= 360;
	if(_RotY < 0) _RotY += 360; if(_RotY > 360) _RotY -= 360;
	//if(_RotZ < 0) _RotZ += 360; if(_RotZ > 360) _RotZ -= 360;

	Matrix.setIdentityM(_Modelview, 0);

	Matrix.rotateM(_Modelview, 0, _RotX, 1, 0, 0);
	Matrix.rotateM(_Modelview, 0, _RotY, 0, 1, 0);
	//Matrix.rotateM(_Modelview, 0, _RotZ, 0, 0, 1);

	Matrix.translateM(_Modelview, 0, _X, _Y, _Z);
    }

    public static float[] GetModelview() {
	return _Modelview;
    }

    public static float[] GetProjection() {
	return _Projection;
    }

    public static float[] GetOrthographic() {
	return _Orthographic;
    }

    public static boolean AIsListening() {
	return _AListening;
    }

    public static boolean AIsSupported() {
	if (_ASupported == null) {
	    if (CNA.getContext() != null) {
		_SensorManager = (SensorManager) CNA.getContext().
			getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = _SensorManager.getSensorList(
			Sensor.TYPE_ACCELEROMETER);
		_ASupported = (sensors.size() > 0);
	    } else {
		_ASupported = Boolean.FALSE;
	    }
	}
	CNA.Toast("Accelerometer: " + _ASupported);
	return _ASupported;
    }

    public static void ConfigureThreshholds(float yawn, float pitch) {
	Camera._YawnThreshhold = yawn;
	Camera._PitchThreshhold = pitch;
    }

    public static void StartA() {
	_SensorManager = (SensorManager) CNA.getContext().
		getSystemService(Context.SENSOR_SERVICE);
	List<Sensor> sensors = _SensorManager.getSensorList(
		Sensor.TYPE_ACCELEROMETER);
	if (sensors.size() > 0) {
	    _Sensor = sensors.get(0);
	    _AListening = _SensorManager.registerListener(
		    mSensorEventListener, _Sensor,
		    SensorManager.SENSOR_DELAY_GAME);
	}
    }

    public static void StopA() {
	_AListening = false;
	try {
	    if (_SensorManager != null && mSensorEventListener != null) {
		_SensorManager.unregisterListener(mSensorEventListener);
	    }
	} catch (Exception e) {
	}
    }
    /**
     * The listener that listen to events from the accelerometer listener
     */
    private static SensorEventListener mSensorEventListener = new SensorEventListener()  {

	private float _lastY = 0;
	private float _lastP = 0;

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
	    float vX = event.values[1];
	    float vY = event.values[2];


	    if (vX > (_lastY + Camera._YawnThreshhold)
		    || vX < (_lastY - Camera._YawnThreshhold)) {
			Camera.Rotate(0f, (float) (0.9*_lastY + (0.1)*vX), 0f);
		_lastY = vX;
	    }

	    if (vY > (_lastP + Camera._PitchThreshhold)
		    || vY < (_lastY - Camera._PitchThreshhold)) {
			Camera.Rotate((float) (0.9*_lastP + (0.1)*vY), 0f, 0f);
		_lastP = vY;
	    }
	}
    };
}
