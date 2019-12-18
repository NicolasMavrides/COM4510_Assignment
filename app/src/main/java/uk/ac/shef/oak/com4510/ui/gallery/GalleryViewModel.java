package uk.ac.shef.oak.com4510.ui.gallery;

import android.app.Application;

import uk.ac.shef.oak.com4510.MyRepository;

import androidx.lifecycle.AndroidViewModel;

public class GalleryViewModel extends AndroidViewModel {
    private final MyRepository mRepository;

    //private MutableLiveData<String> mText;

    public GalleryViewModel(Application application) {
        super(application);
        // creation and connection to the Repository
        mRepository = new MyRepository(application);
    }
    //mText = new MutableLiveData<>();
    //mText.setValue("This is home fragment");

    /*public LiveData<String> getText() {
        return mText;
    }*/

    /**
     * this is the presenter's interface method that enables the UI to call the presenter
     * it sends the data to the model
     * @param title
     * @param description
     */
    /*public void insertTitleDescrition(String title, String description) {
        // send it to the model
        mRepository.insertTitleDescription(title, description);
    }*/


    /**
     * it receives confirmation of correct insertion of title and description. It sends them back to the UI
     * @param title
     * @param description
     */
    /*public void titleDescriptionInserted(String title, String description){
        // send it back to the UI
        userinterface.titleDescritpionInsertedFeedback(title, description);
    }*/

    /**
     * it receives confirmation of correct insertion of title and description. It sends them back to the UI
     * @param title
     * @param description
     * @param s
     */
    /*public void errorInsertingTitleDescription(String title, String description, String errorString){
        // send it back to the UI
        userinterface.titleDescritpionError(title, description, errorString);
    }*/
}