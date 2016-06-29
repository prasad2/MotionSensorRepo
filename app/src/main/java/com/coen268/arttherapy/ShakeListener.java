package com.coen268.arttherapy;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import java.lang.UnsupportedOperationException;

@SuppressWarnings("ALL")
public class ShakeListener implements SensorListener {

    private static final int FORCE_THRESHOLD = 350;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 3;

    private SensorManager manager;
    private float mLastX = -1.0f,mLastY = -1.0f,mLastZ = -1.0f ;
    private long mLastTime;
    private OnShakeListener myShakeListener;
    private Context myContext;
    private int myShakeCount = 0;
    private long myLastShake;
    private long myLastForce;


    public interface OnShakeListener{
        public void onShake();
    }

 public ShakeListener(Context context){
     myContext =context;
     resume();
 }

    public void setOnShakeListener(OnShakeListener listener)
    {
        myShakeListener = listener;
    }
    public void resume(){
        manager = (SensorManager)myContext.getSystemService(Context.SENSOR_SERVICE);

        if(manager == null){
            throw  new UnsupportedOperationException("Sensors not supported");
        }

        boolean supported = manager.registerListener(this,SensorManager.SENSOR_ACCELEROMETER,SensorManager.SENSOR_DELAY_GAME);

        if(!supported){
            manager.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            throw new UnsupportedOperationException("Accelerometer not supported");
        }
    }

    public void pause(){
        if(manager != null){
            manager.unregisterListener(this,SensorManager.SENSOR_ACCELEROMETER);
            manager=null;
        }
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if(sensor != SensorManager.SENSOR_ACCELEROMETER) return;

        long now = System.currentTimeMillis();

        if((now - mLastTime)>SHAKE_TIMEOUT){
            myShakeCount = 0;
        }

        if((now - mLastTime)> TIME_THRESHOLD){
            long diff = now - mLastTime;

            float speed = Math.abs(values[SensorManager.DATA_X] + values[SensorManager.DATA_Y] + values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
            if (speed > FORCE_THRESHOLD) {
                if ((++myShakeCount >= SHAKE_COUNT) && (now - myLastShake > SHAKE_DURATION)) {
                    myLastShake = now;
                    myShakeCount = 0;
                    if (myShakeListener != null) {
                        myShakeListener.onShake();
                    }
                }
                myLastForce = now;
            }
            mLastTime = now;
            mLastX = values[SensorManager.DATA_X];
            mLastY = values[SensorManager.DATA_Y];
            mLastZ = values[SensorManager.DATA_Z];
        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
