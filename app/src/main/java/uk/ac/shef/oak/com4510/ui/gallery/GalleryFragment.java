package uk.ac.shef.oak.com4510.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.shef.oak.com451.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class GalleryFragment extends Fragment {
    private List<ImageElement> myPictureList = new ArrayList<>();
    private RecyclerView.Adapter  mAdapter;
    private RecyclerView mRecyclerView;
    private GalleryViewModel galleryViewModel;
    private Fragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragment = this;

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mRecyclerView = root.findViewById(R.id.grid_recycler_view);
        // set up the RecyclerView
        int numberOfColumns = 4;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        mAdapter= new GalleryAdapter(myPictureList);
        mRecyclerView.setAdapter(mAdapter);

        myPictureList.add(new ImageElement(R.drawable.joe1));

        // the floating button that will allow us to get the images from the Gallery
        /*FloatingActionButton fabGallery = root.findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(fragment, 0);
            }
        });

        // the floating button that will allow us to takes pictures
        FloatingActionButton fabCamera = root.findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(fragment, 0);
            }
        });
        /*
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
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
        myPictureList.addAll(getImageElements(returnedPhotos));
        // we tell the adapter that the data is changed and hence the grid needs
        // refreshing
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(returnedPhotos.size() - 1);
    }

    /**
     * given a list of photos, it creates a list of myElements
     * @param returnedPhotos
     * @return
     */
    private List<ImageElement> getImageElements(List<File> returnedPhotos) {
        List<ImageElement> imageElementList= new ArrayList<>();
        for (File file: returnedPhotos){
            Log.i("File:", file.getAbsolutePath());
            ImageElement element= new ImageElement(file);
            imageElementList.add(element);
        }
        return imageElementList;
    }
}