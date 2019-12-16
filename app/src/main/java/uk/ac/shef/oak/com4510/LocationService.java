package uk.ac.shef.oak.com4510;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import uk.ac.shef.oak.com4510.sensors.Accelerometer;
import uk.ac.shef.oak.com4510.sensors.Barometer;
import uk.ac.shef.oak.com4510.sensors.Thermometer;

public class LocationService extends IntentService {
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    private Accelerometer accelerometer;
    private Barometer barometer;
    private Thermometer thermometer;
    private Float mCurrentTemp, mCurrentPress;


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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        accelerometer.startAccelerometerRecording();
        barometer.startSensingPressure(accelerometer);
        thermometer.startSensingTemperature(accelerometer);

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

}