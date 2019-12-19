package uk.ac.shef.oak.com4510.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.database.Trip;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    private List<ListElement> myTrips = new ArrayList<>();
    private RecyclerView.Adapter  mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeViewModel homeViewModel;
    private Fragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragment = this;

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.list_recycler_view);
        // set up the RecyclerView
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);






       /* myList.add(new ListElement(R.drawable.joe1, "Good Morning",
                "Just wanted to say hello 1"));

        myList.add(new ListElement(R.drawable.joe1, "Good Morning 2",
                "Just wanted to say hello again!"));

*/

        Trip newtrip = new Trip("test", "date", "time", "falls", 20, 45, "5", "6", "1, 2, 3, 4" );
        homeViewModel.insertTrip(newtrip);

        homeViewModel.getAllTrips().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(@Nullable List<Trip> tripList) {
                for (int i = 0; i < tripList.size(); i++) {
                    myTrips.add(new ListElement("title", "date", "time"));
                }
            }
        });

        mAdapter= new HomeAdapter(2); // List.size()?
        mRecyclerView.setAdapter(mAdapter);


        return root;
    }

}
