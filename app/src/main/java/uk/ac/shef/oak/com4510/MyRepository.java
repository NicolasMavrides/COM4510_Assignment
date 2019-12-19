package uk.ac.shef.oak.com4510;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import uk.ac.shef.oak.com4510.database.AppDatabase;
import uk.ac.shef.oak.com4510.database.Photo;
import uk.ac.shef.oak.com4510.database.PhotoDAO;
import uk.ac.shef.oak.com4510.database.Trip;
import uk.ac.shef.oak.com4510.database.TripDAO;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MyRepository {

    private MutableLiveData<List<Trip>> tripsList = new MutableLiveData<>();
    private LiveData<List<Trip>> allTrips;
    private TripDAO tripDao;

    private MutableLiveData<List<Photo>> photosList = new MutableLiveData<>();
    private LiveData<List<Photo>> allPhotos;
    private PhotoDAO photoDao;

    public MyRepository(Application application) {
        AppDatabase db;
        db = AppDatabase.getDatabase(application);
        tripDao = db.tripDao();
        allTrips = tripDao.retrieveAllTrips();
        photoDao = db.photoDao();
        allPhotos = photoDao.retrieveAllPhotos();
    }

    public void insertTrip(Trip newTrip) {
        InsertAsyncTripTask task = new InsertAsyncTripTask(tripDao);
        Log.i("Repositry: ", "data submitted");
        task.execute(newTrip);
    }

    public void findTrip(String tripName) {
        QueryAsyncTripTask task = new QueryAsyncTripTask(tripDao);
        task.repository = this;
        task.execute(tripName);
    }

    public void deleteTrip(Trip trip) {
        DeleteAsyncTripTask task = new DeleteAsyncTripTask(tripDao);
        task.execute(trip);
    }

    public void insertPhoto(Photo newPhoto) {
        InsertAsyncPhotoTask task = new InsertAsyncPhotoTask(photoDao);
        task.execute(newPhoto);
    }

    public void findPhoto(String photoName) {
        QueryAsyncPhotoTask task = new QueryAsyncPhotoTask(photoDao);
        task.repository = this;
        task.execute(photoName);
    }

    public void deletePhoto(Photo photo) {
        DeleteAsyncPhotoTask task = new DeleteAsyncPhotoTask(photoDao);
        task.execute(photo);
    }


    private void asyncTripFinished(List<Trip> tripsResults) {
        tripsList.setValue(tripsResults);
    }

    private void asyncPhotoFinished(List<Photo> photosResults) {
        photosList.setValue(photosResults);
    }


    private static class QueryAsyncTripTask extends AsyncTask<String, Void, List<Trip>> {

        private TripDAO asyncTaskTripDao;
        private MyRepository repository = null;

        QueryAsyncTripTask(TripDAO tripdao) {
            asyncTaskTripDao = tripdao;
        }

        @Override
        protected List<Trip> doInBackground(final String... params) {
            return asyncTaskTripDao.retrieveTripByTitle(params[0]);
        }

        @Override
        protected void onPostExecute(List<Trip> tripResult) {
            repository.asyncTripFinished(tripResult);
        }
    }

    private static class QueryAsyncPhotoTask extends AsyncTask<String, Void, List<Photo>> {

        private PhotoDAO asyncTaskPhotoDao;
        private MyRepository repository = null;

        QueryAsyncPhotoTask(PhotoDAO photodao) {
            asyncTaskPhotoDao = photodao;
        }

        @Override
        protected List<Photo> doInBackground(final String... params) {
            return asyncTaskPhotoDao.retrievePhotoByTitle(params[0]);
        }

        @Override
        protected void onPostExecute(List<Photo> photoResult) {
            repository.asyncPhotoFinished(photoResult);
        }
    }

    private static class InsertAsyncPhotoTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDAO asyncTaskPhotoDao;

        InsertAsyncPhotoTask(PhotoDAO photodao) {
            asyncTaskPhotoDao = photodao;
        }

        @Override
        protected Void doInBackground(final Photo... params) {
            asyncTaskPhotoDao.insertPhoto(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncPhotoTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDAO asyncTaskPhotoDao;

        DeleteAsyncPhotoTask(PhotoDAO photodao) {
            asyncTaskPhotoDao = photodao;
        }

        @Override
        protected Void doInBackground(final Photo... params) {
            asyncTaskPhotoDao.deletePhoto(params[0]);
            return null;
        }
    }


    private static class InsertAsyncTripTask extends AsyncTask<Trip, Void, Void> {

        private TripDAO asyncTaskTripDao;

        InsertAsyncTripTask(TripDAO tripdao) {
            asyncTaskTripDao = tripdao;
        }

        @Override
        protected Void doInBackground(final Trip... params) {
            asyncTaskTripDao.insertTrip(params[0]);
            Log.i("AysncTask: ", "data submitted");
            Log.i("Trip: ", params[0].getName());
            return null;
        }
    }

    private static class DeleteAsyncTripTask extends AsyncTask<Trip, Void, Void> {

        private TripDAO asyncTaskTripDao;

        DeleteAsyncTripTask(TripDAO tripdao) {
            asyncTaskTripDao = tripdao;
        }

        @Override
        protected Void doInBackground(final Trip... params) {
            asyncTaskTripDao.deleteTrip(params[0]);
            return null;
        }
    }


    public LiveData<List<Trip>> retrieveAllTrips() {
        return allTrips;
    }

    public MutableLiveData<List<Trip>> searchTrips() {
        return tripsList;
    }


    public LiveData<List<Photo>> retrieveAllPhotos() {
        return allPhotos;
    }

    public MutableLiveData<List<Photo>> searchPhotos() {
        return photosList;
    }

}