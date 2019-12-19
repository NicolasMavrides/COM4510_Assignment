package uk.ac.shef.oak.com4510.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import uk.ac.shef.oak.com451.R;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.ac.shef.oak.com4510.database.Photo;


/**
 * Gallery fragment code for the Gallery activity using the RecyclerView
 */

public class GalleryFragment extends Fragment {
    private List<Photo> myPictureList = new ArrayList<>();
    private GalleryAdapter  mAdapter;
    private RecyclerView mRecyclerView;
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mRecyclerView = root.findViewById(R.id.grid_recycler_view);
        // set up the RecyclerView
        int numberOfColumns = 4;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        mAdapter= new GalleryAdapter(myPictureList);
        mRecyclerView.setAdapter(mAdapter);

        galleryViewModel.getAllPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photoList) {
                Log.i("Photo: ", "changed");
                mAdapter.updateData(photoList);
            }
        });
        return root;
    }
}