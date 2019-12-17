package uk.ac.shef.oak.com4510;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.sensors.Accelerometer;
import uk.ac.shef.oak.com4510.sensors.Barometer;
import uk.ac.shef.oak.com4510.sensors.Thermometer;
import uk.ac.shef.oak.com4510.utilities.Notification;

public class LocationService extends IntentService {
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private Accelerometer accelerometer;
    private Barometer barometer;
    private Thermometer thermometer;
    private Float mCurrentTemp, mCurrentPress;


    protected static final int NOTIFICATION_ID = 1001;
    private static String TAG = "LocationService";
    private static Service mCurrentService;

    public LocationService(String name) {
        super(name);
    }

    public LocationService() {
        super("Location Intent");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        barometer = new Barometer(this);
        thermometer = new Thermometer(this);
        accelerometer = new Accelerometer(this, barometer, thermometer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            restartForeground();
        }
        mCurrentService = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "restarting Service !!");

        // it has been killed by Android and now it is restarted. We must make sure to have reinitialised everything
        if (intent == null) {
            ProcessMainClass bck = new ProcessMainClass();
            bck.launchService(this);
        }


        accelerometer.startAccelerometerRecording();
        barometer.startSensingPressure(accelerometer);
        thermometer.startSensingTemperature(accelerometer);

        // make sure you call the startForeground on onStartCommand because otherwise
        // when we hide the notification on onScreen it will not restart in Android 6 and 7
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            restartForeground();
//        }

        return START_STICKY;
    }

    /**
     * called when a location is recognised
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (LocationResult.hasResult(intent)) {
            LocationResult locResults = LocationResult.extractResult(intent);
            if (locResults != null) {
                for (Location location : locResults.getLocations()) {
                    if (location == null) continue;
                    //do something with the location
                    Log.i("New Location", "Current location: " + location);
                    mCurrentLocation = location;
                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                    mCurrentPress = barometer.getCurrentPressure();
                    mCurrentTemp = thermometer.getCurrentTemperature();
                    // Log to make sure that the service is working
                    Log.i("MAP", "new location " + mCurrentLocation.toString());
                    Log.i("Pressure", "new pressure" + mCurrentPress.toString());
                    Log.i("Temperature", "new temperature " + mCurrentTemp.toString());

                    // check if the activity has not been closed in the meantime
                    if (MapsActivity.getActivity()!=null)
                        // any modification of the user interface must be done on the UI Thread. The Intent Service is running
                        // in its own thread, so it cannot communicate with the UI.
                        MapsActivity.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    if (MapsActivity.getMap() != null) {
                                        Polyline route = MapsActivity.getPolyline();
                                        List<LatLng> points = route.getPoints();
                                        points.add(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
                                        route.setPoints(points);
                                        // set starting marker
                                        if (MapsActivity.isStartPoint()){
                                            MapsActivity.setMarker(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()),
                                                    MapsActivity.getMap(), "Start of Trip", true, 14.0f);
                                            MapsActivity.stopStartPoint();
                                        }
                                        /*MapsActivity.getMap().addMarker(new MarkerOptions().position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
                                                .title(mLastUpdateTime));*/
                                    }
                                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                                    // it centres the camera around the new location
                                    MapsActivity.getMap().moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())));
                                    // it moves the camera to the selected zoom
                                    MapsActivity.getMap().animateCamera(zoom);
                                } catch (Exception e ){
                                    Log.e("LocationService", "Error cannot write on map "+e.getMessage());
                                }
                            }
                        });
                }
            }

        }
    }

    //////////////////////////////////////////////////
    //                                              //
    //          Background Process Functions        //
    //                                              //
    //////////////////////////////////////////////////


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * it starts the process in foreground. Normally this is done when screen goes off
     * THIS IS REQUIRED IN ANDROID 8 :
     * "The system allows apps to call Context.startForegroundService()
     * even while the app is in the background.
     * However, the app must call that service's startForeground() method within five seconds
     * after the service is created."
     */
    public void restartForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "restarting foreground");
            try {
                Notification notification = new Notification();
                startForeground(NOTIFICATION_ID, notification.setNotification(this, "My Routes", "Currently Tracking.", R.drawable.ic_sleep));
                Log.i(TAG, "restarting foreground successful");
            } catch (Exception e) {
                Log.e(TAG, "Error in notification " + e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy called");
//        if (MapsActivity.buttonsExist()) {
//            if (!MapsActivity.isPausedOrStopped()) {
        // restart the never ending service
//        new	java.util.Timer().schedule(
//                new	java.util.TimerTask()	{
//                    @Override
//                    public void	run()	{
//                        //	your	code	here
//                    }
//                },
//                2000
//        );
//        Intent broadcastIntent = new Intent(Globals.RESTART_INTENT);
//        sendBroadcast(broadcastIntent);
        // adds to the shared preferences that we are currently tracking the location
//        try {
//            SharedPreferences prefs = getSharedPreferences("uk.ac.shef.oak.ServiceRunning", MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("tracking", true);
//            editor.apply();
//            Log.i("Shared Preferences", "Working");
//        } catch (NullPointerException e) {
//            Log.e(TAG, "error saving: are you testing?" + e.getMessage());
//        }
//            }
//        }
    }


    /**
     * this is called when the process is killed by Android
     *
     * @param rootIntent
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "onTaskRemoved called");
        // restart the never ending service
        Intent broadcastIntent = new Intent(Globals.RESTART_INTENT);
        sendBroadcast(broadcastIntent);
    }
}