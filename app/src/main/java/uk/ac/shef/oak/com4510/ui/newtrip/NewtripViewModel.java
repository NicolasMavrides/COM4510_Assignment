package uk.ac.shef.oak.com4510.ui.newtrip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewtripViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewtripViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}