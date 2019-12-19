package uk.ac.shef.oak.com4510.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.shef.oak.com451.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryFragment extends Fragment {
    private List<ImageElement> myPictureList = new ArrayList<>();
    private GalleryAdapter  mAdapter;
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

        return root;
    }
}