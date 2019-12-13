package uk.ac.shef.oak.com4510;

import android.app.Application;
import android.os.AsyncTask;

import uk.ac.shef.oak.com4510.database.PhotoDAO;
import uk.ac.shef.oak.com4510.database.PhotoData;
import uk.ac.shef.oak.com4510.database.PhotoDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyRepository extends ViewModel {
    private final PhotoDAO mPhotoDao;
    ViewModel viewModel;

    public MyRepository(Application application, ViewModel viewModel) {
        PhotoDatabase db = PhotoDatabase.getDatabase(application);
        mPhotoDao = db.photoDao();

        this.viewModel = viewModel;
    }

    /*public void insertTitleDescription(String title, String description) {
        if (!title.isEmpty() && (!description.isEmpty())) {
            // data insertion cannot be done on the UI thread. Use an ASync process!!
            new InsertIntoDbAsync(mPhotoDao, new PhotoData(title, description), viewModel).execute();
        } else viewModel.errorInsertingTitleDescription(title, description, "Tile or Description should not be empty");
    }*/


    /**
     * it gets the data when changed in the db and returns it to the ViewModel
     * @return
     */
    public LiveData<List<PhotoData>> getPhotoData() {
        return mPhotoDao.retrieveAllData();
    }

    /*public void getTitleDescription(String title, String description)
    {
        new GetFromDbAsync(mPhotoDao, new PhotoData(title, description),viewModel).execute();

    }*/

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    /*private static class GetFromDbAsync extends AsyncTask<Void, Void, Void> {
        private final PhotoDAO mPhotoDao;
        private final PhotoData mPhotoData;
        private final ViewModel mviewModel;

        private final ArrayList<PhotoData> m_list_PhotoData = new ArrayList<>();

        GetFromDbAsync(PhotoDAO dao, PhotoData photoData, ViewModel viewModel) {
            mPhotoDao = dao;
            mPhotoData= photoData;
            mviewModel= viewModel;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mPhotoDao.insert(mPhotoData);
            if(!mPhotoDao.retrieveAllData().isEmpty()) {

                for (int i = 0; i < mPhotoDao.retrieveAllData().size(); i++) {
                    m_list_PhotoData.add(mPhotoDao.retrieveAllData().get(i));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(!m_list_PhotoData.isEmpty()) {
                int i = m_list_PhotoData.size();
                mviewModel.titleDescriptionRetrieved(m_list_PhotoData.get(0).getTitle(), m_list_PhotoData.get(0).getDescription());
                mviewModel.ListDataRetreived(m_list_PhotoData);
            }
        }

    }*/

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class InsertIntoDbAsync extends AsyncTask<Void, Void, Void> {
        private final PhotoDAO mPhotoDao;
        private final PhotoData mPhotoData;
        private final ViewModel mviewModel;

        InsertIntoDbAsync(PhotoDAO dao, PhotoData photoData, ViewModel viewModel) {
            mPhotoDao = dao;
            mPhotoData= photoData;
            mviewModel= viewModel;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mPhotoDao.insert(mPhotoData);
            //TODO log
            return null;
        }

        /*@Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mviewModel.titleDescriptionInserted(mPhotoData.getTitle(), mPhotoData.getDescription());
        }*/
    }
}
