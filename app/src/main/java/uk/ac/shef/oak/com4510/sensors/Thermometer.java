/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */


public class Thermometer {
    private static final String TAG = Thermometer.class.getSimpleName();
    private long mSamplingRateInMSecs;
    private long mSamplingRateNano;
    private SensorEventListener mTemperatureListener = null;
    private SensorManager mSensorManager;
    private Sensor mThermometerSensor;
    private long timePhoneWasLastRebooted = 0;
    private long Thermometer_READING_FREQUENCY= 30000;
    private long lastReportTime = 0;
    private boolean started;
    private Accelerometer accelerometer;
    private float current_temperature;
    /**
     * returns current temperature
     */
    public float getCurrentTemperature(){
        return this.current_temperature;
    }

    /**
     * this is used to stop the Thermometer if we have not seen any movement in the last 20 seconds
     */
    private static final long STOPPING_THRESHOLD = (long)20000;


    public Thermometer(Context context) {
        // http://androidforums.com/threads/how-to-get-time-of-last-system-boot.548661/
        timePhoneWasLastRebooted = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        current_temperature = 0;
        mSamplingRateNano = (long) (Thermometer_READING_FREQUENCY) * 1000000;
        mSamplingRateInMSecs = (long) Thermometer_READING_FREQUENCY;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mThermometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        initThermometerListener();
    }

    /**
     * it inits the listener and establishes the actions to take when a reading is available
     */
    private void initThermometerListener() {
        if (!standardTemperatureSensorAvailable()) {
            Log.d(TAG, "Standard Thermometer unavailable");
        } else {
            Log.d(TAG, "Using Thermometer");
            mTemperatureListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    long diff = event.timestamp - lastReportTime;
                    // time is in nanoseconds it represents the set reference times the first time we come here
                    // set event timestamp to current time in milliseconds
                    // see answer 2 at http://stackoverflow.com/questions/5500765/accelerometer-sensorevent-timestamp
                    // the following operation avoids reporting too many events too quickly - the sensor may always
                    // misbehave and start sending data very quickly
                    if (diff >= mSamplingRateNano) {
                        long actualTimeInMseconds = timePhoneWasLastRebooted + (long) (event.timestamp / 1000000.0);
                        current_temperature = event.values[0];
                        int accuracy = event.accuracy;

                        Log.i(TAG, Utilities.mSecsToString(actualTimeInMseconds) + ": current Temperature: " + current_temperature + "with accuract: " + accuracy);
                        lastReportTime = event.timestamp;
                        // if we have not see any movement on the side of the accelerometer, let's stop
                        long timeLag= actualTimeInMseconds-accelerometer.getLastReportTime();
                        if (timeLag> STOPPING_THRESHOLD)
                            stopThermometer();
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }

    }


    /**
     * it returns true if the sensor is available
     * @return
     */
    public boolean standardTemperatureSensorAvailable() {
        return (mThermometerSensor != null);
    }

    /**
     * it starts the Temperature monitoring
     * @param accelerometer
     */
    public void startSensingTemperature(Accelerometer accelerometer) {
        this.accelerometer= accelerometer;
        // if the sensor is null,then mSensorManager is null and we get a crash
        if (standardTemperatureSensorAvailable()) {
            Log.d("Standard Thermometer", "starting listener");
            // delay is in microseconds (1millisecond=1000 microseconds)
            // it does not seem to work though
            //stopThermometer();
            // otherwise we stop immediately because
            mSensorManager.registerListener(mTemperatureListener, mThermometerSensor, (int) (mSamplingRateInMSecs * 1000));
            setStarted(true);
        } else {
            Log.i(TAG, "Thermometer unavailable or already active");
        }
    }


    /**
     * this stops the Thermometer
     */
    public void stopThermometer() {
        if (standardTemperatureSensorAvailable()) {
            Log.d("Standard Thermometer", "Stopping listener");
            try {
                mSensorManager.unregisterListener(mTemperatureListener);
            } catch (Exception e) {
                // probably already unregistered
            }
        }
        setStarted(false);
    }

    /**
     * returns true if the Thermometer is currently working
     * @return
     */

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }


}
