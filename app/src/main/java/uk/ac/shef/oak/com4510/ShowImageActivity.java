package uk.ac.shef.oak.com4510;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.database.Photo;
import uk.ac.shef.oak.com4510.ui.gallery.GalleryAdapter;
import uk.ac.shef.oak.com4510.showTrip.TripGalleryAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class ShowImageActivity extends AppCompatActivity {

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
            }

        }
    }

}
