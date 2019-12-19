package uk.ac.shef.oak.com4510;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.shef.oak.com451.R;

public class EndTripActivity extends AppCompatActivity {

    private static AppCompatActivity activity;
    public static AppCompatActivity getActivity() {
        return activity;
    }
    public static void setActivity(AppCompatActivity activity) {
        EndTripActivity.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setActivity(this);


    }
}
