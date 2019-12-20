package uk.ac.shef.oak.com4510.showTrip;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.database.Photo;
import static uk.ac.shef.oak.com4510.showTrip.ShowTripActivity.getTripID;


/**
 * Gallery fragment code for the Gallery activity using the RecyclerView
 */

public class TripGalleryFragment extends Fragment {
    private List<Photo> myPictureList = new ArrayList<>();
    private TripGalleryAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TripGalleryViewModel tripGalleryViewModel;
    private static Fragment f;

    public static Fragment getFragment() {
        return f;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        f = this;
        tripGalleryViewModel =
                ViewModelProviders.of(this).get(TripGalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mRecyclerView = root.findViewById(R.id.grid_recycler_view);

        // set up the RecyclerView
        int numberOfColumns = 4;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        mAdapter = new TripGalleryAdapter(myPictureList);
        mRecyclerView.setAdapter(mAdapter);

        long trip_id = getTripID();
        tripGalleryViewModel.getPhotosByTripID(trip_id).observe(getFragment(), new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photosByTrip) {
                Log.i("Photo: ", "changed");
                mAdapter.updateData(photosByTrip);
            }
        });
        return root;
    }
}