package uk.ac.shef.oak.com4510;

import android.app.Application;
import android.content.SharedPreferences;
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


/**
 *  Repository class for handling data operations of the application, between the data sources and
 *  ViewModels.
 */

public class MyRepository {

    // Live data lists for trips and photos
    private MutableLiveData<List<Trip>> tripsList = new MutableLiveData<>();
    private LiveData<List<Trip>> allTrips;
    private TripDAO tripDao;
    private static long photoId;
    private static long tripID;

    private MutableLiveData<List<Photo>> photosList = new MutableLiveData<>();
    private LiveData<List<Photo>> allPhotos;
    private PhotoDAO photoDao;

    // Initialise the repository
    public MyRepository(Application application) {
        AppDatabase db;
        db = AppDatabase.getDatabase(application);
        tripDao = db.tripDao();
        allTrips = tripDao.retrieveAllTrips();
        photoDao = db.photoDao();
        allPhotos = photoDao.retrieveAllPhotos();
    }

    public long getCurrentTripID() {
        return tripID;
    }

    public PhotoDAO getPhotoDao() {
        return photoDao;
    }

    /* Data handling methods for the trip object */
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



    /* Data handling methods for the photo object */

    public void insertPhoto(Photo newPhoto, SharedPreferences prefs) {
        InsertAsyncPhotoTask task = new InsertAsyncPhotoTask(photoDao, prefs);
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

    public void setPhotoID(long id){
        photoId = id;
    }

    private void asyncTripFinished(LiveData<List<Trip>> tripsResults) {
        tripsList.setValue(tripsResults.getValue());
    }

    private void asyncPhotoFinished(List<Photo> photosResults) {
        photosList.setValue(photosResults);
    }

    private static class QueryAsyncTripTask extends AsyncTask<String, Void, LiveData<List<Trip>> > {

        private TripDAO asyncTaskTripDao;
        private MyRepository repository = null;

        QueryAsyncTripTask(TripDAO tripdao) {
            asyncTaskTripDao = tripdao;
        }

        @Override
        protected LiveData<List<Trip>> doInBackground(final String... params) {
            return asyncTaskTripDao.retrieveTripByTitle(params[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<Trip>> tripResult) {
            repository.asyncTripFinished(tripResult);
        }
    }


    /* Asynchronous query task for Photo */

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


    /* Asynchronous insert task for Photo */

    private static class InsertAsyncPhotoTask extends AsyncTask<Photo, Void, Void> {
        private PhotoDAO asyncTaskPhotoDao;
        private SharedPreferences prefs;

        InsertAsyncPhotoTask(PhotoDAO photodao, SharedPreferences prefs) {
            asyncTaskPhotoDao = photodao;
            this.prefs = prefs;
        }

        @Override
        protected Void doInBackground(final Photo... params) {
            photoId = asyncTaskPhotoDao.insertPhoto(params[0]);
            String currentPhotoIds = prefs.getString("photo_ids", "");
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("photo_ids", currentPhotoIds+photoId+";");
            editor.apply();
            return null;
        }
    }


    /* Asynchronous delete task for Trip */

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


    /* Asynchronous insert task for Trip */

    private static class InsertAsyncTripTask extends AsyncTask<Trip, Void, Void> {

        private TripDAO asyncTaskTripDao;

        InsertAsyncTripTask(TripDAO tripdao) {
            asyncTaskTripDao = tripdao;
        }

        @Override
        protected Void doInBackground(final Trip... params) {
            tripID = asyncTaskTripDao.insertTrip(params[0]);
            Log.i("AysncTask: ", "data submitted");
            Log.i("Trip: ", params[0].getName());
            return null;
        }
    }


    /* Asynchronous delete task for Trip */

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


    /* Return all or refined trip lists */

    public LiveData<List<Trip>> retrieveAllTrips() {
        return allTrips;
    }

    public MutableLiveData<List<Trip>> searchTrips() {
        return tripsList;
    }


    /* Return all or refined photo lists */

    public LiveData<List<Photo>> retrieveAllPhotos() {
        return allPhotos;
    }

    public MutableLiveData<List<Photo>> searchPhotos() {
        return photosList;
    }

}