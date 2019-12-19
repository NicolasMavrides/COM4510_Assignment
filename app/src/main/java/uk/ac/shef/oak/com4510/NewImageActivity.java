package uk.ac.shef.oak.com4510;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import pl.aprilapps.easyphotopicker.EasyImage;
import uk.ac.shef.oak.com451.R;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import uk.ac.shef.oak.com4510.database.Photo;
import uk.ac.shef.oak.com4510.database.PhotoDAO;
import uk.ac.shef.oak.com4510.ui.gallery.ImageElement;

public class NewImageActivity extends AppCompatActivity {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;
    private static final int REQUEST_CAMERA = 1889;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 100;
    private String mdate;
    private String mtrip;
    private float mcTemp, mcPress;
    private String titleStr;
    private String snippStr;
    private Activity activity;
    private ImageView imagePreview;
    private List<LatLng> polyline_points;
    private SharedPreferences prefs;
    private PhotoDAO newPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image);
        activity = this;
        prefs= getSharedPreferences("uk.ac.shef.oak.ServiceRunning", MODE_PRIVATE);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            mtrip = b.getString("name");
            mdate = b.getString("date");
            getSupportActionBar().setTitle("Add a photo");
        }

        // required by Android 6.0 +
        checkPermissions(getApplicationContext());
        initEasyImage();

        imagePreview = findViewById(R.id.preview);

        // the button that will allow us to get the images from the Gallery
        Button fabGallery = findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    EasyImage.openGallery(getActivity(), 0);
                } else {
                    Toast.makeText(getApplicationContext(),"You need to enable file storage access permission to do that.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // the floating button that will allow us to takes pictures
        Button fabCamera = findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    EasyImage.openCamera(getActivity(), 0);
                } else {
                    Toast.makeText(getApplicationContext(),"You need to enable camera access permission to do that.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView photoName = findViewById(R.id.photo_name);
                AutoCompleteTextView photoDescription = findViewById(R.id.photo_description);
                String name = photoName.getText().toString();
                String description = photoDescription.getText().toString();
                Log.i("photo name: ", name);
                Log.i("photo description: ", description);

                // TODO - when no trip name is entered
                if ((name.replaceAll("\\s+","").length() != 0) || (description.replaceAll("\\s+","").length() != 0)) {
                    // Adds Marker to Map on saved spot
                    if (MapsActivity.getMap() != null) {
                        polyline_points = MapsActivity.getPolyline().getPoints();
                        //TODO add to photo obj
                        titleStr = "No Title";
                        if (name.replaceAll("\\s+","").length() != 0){
                            titleStr = name;
                        }
                        // could refactor to add stuff like temperature/ barometric press at location
                        snippStr = "No Description";
                        if (description.replaceAll("\\s+","").length() != 0){
                            snippStr = description;
                        }
                        mcTemp = prefs.getFloat("current_temperature", 100000f);
                        Log.i("temp_recorded",mcTemp+"");
                        if ( mcTemp != 100000f){
                            snippStr += "\nTemperature: " + mcTemp + '\n';
                        }
                        else{
                            snippStr += "\nTemperature: N/A \n";
                        }
                        mcPress = prefs.getFloat("current_pressure", 100000f);
                        Log.i("press_recorded","" + mcPress);
                        if (mcPress != 100000f){
                            snippStr += "Barometric Pressure: " + mcPress;
                        }
                        else{
                            snippStr += "Barometric Pressure: N/A \n";
                        }


                        // instead of saving the temperature and pressure in separate columns we could just add them
                        // to the description, but in case we would want to compare points in the future for example
                        // by temperature/pressure
                        //TODO generate photoid and filepath then uncomment below
//                        int photoId = newPhoto.generatePhotoId();
//                        newPhoto.insertPhoto(new Photo(photoId, // id
//                                                       titleStr, // title
//                                                       snippStr, // description
//                                                       filepath, // photo file path
//                                                       (float) polyline_points.get(polyline_points.size()-1).latitude, // latitude
//                                                       (float) polyline_points.get(polyline_points.size()-1).longitude, // longitude
//                                                        mcTemp, // temperature
//                                                        mcPress // pressure
//                        ));

                        //TODO add generated photoid into shared preferences (uncomment below)
//                        String currentPhotoIds = prefs.getString("photo_ids", "");
//                        SharedPreferences.Editor editor = prefs.edit();
//                        editor.putString("photo_ids", currentPhotoIds+photoId+";");
//                        editor.apply();

                        MapsActivity.setMarker(polyline_points.get(polyline_points.size()-1),
                                MapsActivity.getMap(), titleStr, true, 14.0f, snippStr);
                    }
                }
                else {
                    // turn into public func related to activity
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Please enter a photo name or description", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    /**
     * it initialises EasyImage
     */
    private void initEasyImage() {
        EasyImage.configuration(this)
                .setImagesFolderName("EasyImage sample")
                // it adds new pictures to the gallery
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                // it allows to select multiple pictures in the gallery
                .setAllowMultiplePickInGallery(false);
    }

    /**
     * check permissions are necessary starting from Android 6
     * if you do not set the permissions, the activity will simply not work and you will be probably baffled for some hours
     * until you find a note on StackOverflow
     * @param context the calling context
     */
    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    /**
     * add the selected images to the grid
     * @param returnedPhotos
     */
    private void onPhotosReturned(List<File> returnedPhotos) {
        File returnedPhoto = returnedPhotos.get(0);
        Bitmap myBitmap = BitmapFactory.decodeFile(returnedPhoto.getAbsolutePath());
        imagePreview.setImageBitmap(myBitmap);
    }

    public Activity getActivity() {
        return activity;
    }
}
