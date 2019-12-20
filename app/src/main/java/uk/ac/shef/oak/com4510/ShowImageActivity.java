package uk.ac.shef.oak.com4510;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.database.Photo;
import uk.ac.shef.oak.com4510.ui.gallery.GalleryAdapter;
import uk.ac.shef.oak.com4510.showTrip.TripGalleryAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class ShowImageActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static GoogleMap mMap;
    private static Polyline polyline;
    private static PolylineOptions polylineOptions;
    private LatLng marker;
    private String title;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);

        Bundle b = getIntent().getExtras();
        int position=-1;
        if(b != null) {
            // this is the image position in the itemList
            position = b.getInt("position");
            if (position!=-1){
                ImageView imageView = findViewById(R.id.image);
                Photo element;
                if (GalleryAdapter.getItems() == null){
                    element= TripGalleryAdapter.getItems().get(position);
                }
                else {
                    element= GalleryAdapter.getItems().get(position);
                }
                if (element.getFile_path()!=null) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(element.getFile_path());
                    imageView.setImageBitmap(myBitmap);
                    getSupportActionBar().setTitle(element.getTitle());
                }

                title = element.getTitle();

                //Temperature
                TextView temp = findViewById(R.id.view_photo_temp);
                String temp_string = element.getTemperature() + " C";
                temp.setText(temp_string);

                //Pressure
                TextView pressure = findViewById(R.id.view_photo_press);
                String press_string = element.getPressure() + " mbar";
                pressure.setText(press_string);

                //Description
                TextView descriptionView = findViewById(R.id.view_photo_description);
                description = element.getDescription();
                descriptionView.setText(element.getDescription());

                //Marker
                marker = new LatLng(element.getLatitude(), element.getLongitude());
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*polylineOptions = new PolylineOptions().clickable(true)
                .color(Color.BLUE)
                .width(10)
                .geodesic(true);
        List<LatLng> points = polyline.getPoints();
        polyline = mMap.addPolyline(polylineOptions);*/

        mMap.getUiSettings().setZoomControlsEnabled(true);
        setMarker(marker, mMap, title, true, 12, description);
    }

    /**
     * Sets Marker on Map
     * @param pos  - LatLong Position on where to put the marker
     * @param map - map on where to put the marker
     * @param title - title to add to marker
     * @param move_camera - if true sets camera on marker
     * @param zoom - zoom of google maps view if move camera is set to true
     * @param snippet - description of marker
     */
    static void setMarker(LatLng pos, GoogleMap map, String title, boolean move_camera, float zoom, String snippet){
        // TODO if possible - not sure it is - add code to resize marker details based on snippet and title sizes
        if (snippet.replaceAll("\\s+","").length() != 0) {
            map.addMarker(new MarkerOptions().position(pos).title(title).snippet(snippet));
        }
        else{
            map.addMarker(new MarkerOptions().position(pos).title(title));
        }
        if (move_camera) {
            if (zoom >= 0) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            }
        }
    }
}
