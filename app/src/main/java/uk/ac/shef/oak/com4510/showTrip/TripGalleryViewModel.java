package uk.ac.shef.oak.com4510.showTrip;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import uk.ac.shef.oak.com4510.MyRepository;
import uk.ac.shef.oak.com4510.database.Photo;
import uk.ac.shef.oak.com4510.database.Trip;

public class TripGalleryViewModel extends AndroidViewModel {
    private final MyRepository mRepository;
    private LiveData<List<Trip>> allTrips;
    private MutableLiveData<List<Trip>> tripsList;
    private LiveData<List<Photo>> allPhotos;
    private MutableLiveData<List<Photo>> photosList;

    public TripGalleryViewModel(Application application) {
        super(application);
        // creation and connection to the Repository
        mRepository = new MyRepository(application);
        allTrips = mRepository.retrieveAllTrips();
        tripsList = mRepository.searchTrips();
        allPhotos = mRepository.retrieveAllPhotos();
        photosList = mRepository.searchPhotos();
    }

    MutableLiveData<List<Trip>> getTripSearchResults() {
        return tripsList;
    }

    LiveData<List<Trip>> getAllTrips() {
        return allTrips;
    }

    public void insertTrip(Trip tripName) {
        Log.i("HomeViewModel: ", "data submitted");
        mRepository.insertTrip(tripName);
    }

    public void findTrip(String tripName) {
        mRepository.findTrip(tripName);
    }

    public void deleteTrip(Trip tripName) {
        mRepository.deleteTrip(tripName);
    }

    MutableLiveData<List<Photo>> getPhotoSearchResults() {
        return photosList;
    }

    LiveData<List<Photo>> getAllPhotos() {
        return allPhotos;
    }

    public void insertPhoto(Photo photoName, SharedPreferences prefs) {
        mRepository.insertPhoto(photoName, prefs);
    }

    public void findPhoto(String photoName) {
        mRepository.findPhoto(photoName);
    }


    LiveData<List<Photo>> getPhotosByTripName(String tname) {
        LiveData<List<Photo>> t = (mRepository.getPhotoDao()).retrievePhotoByTripName(tname);
        return t;
    }

    LiveData<List<Photo>> getPhotoById(long id){
        LiveData<List<Photo>> p = (mRepository.getPhotoDao()).retrievePhotoById(id);
        return p;
    }
}