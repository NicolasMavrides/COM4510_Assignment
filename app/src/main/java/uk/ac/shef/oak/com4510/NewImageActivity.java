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

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import uk.ac.shef.oak.com4510.ui.gallery.ImageElement;

public class NewImageActivity extends AppCompatActivity {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;
    private static final int REQUEST_CAMERA = 1889;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 100;
    private String mdate;
    private String mtrip;
    private Activity activity;
    private ImageView imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image);
        activity = this;

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
                EasyImage.openGallery(getActivity(), 0);
            }
        });

        // the floating button that will allow us to takes pictures
        Button fabCamera = findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(getActivity(), 0);
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
                    //TODO Save button - add photo to DB
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
