package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    private String trip_name, trip_date;
    private String time_taken;
    private Double distance_travelled;
    private Float average_temperature, average_pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endtrip);
        setActivity(this);

        Bundle b = getIntent().getExtras();
        if (b!= null){
            trip_name = b.getString("trip_name", "Default Trip");
            time_taken = b.getString("time_taken", "00:00:00");
            trip_date = b.getString("date", "Default Date");
            distance_travelled = b.getDouble("distance", 0d);
            average_temperature = b.getFloat("avg_temp", 100000f);
            average_pressure = b.getFloat("avg_press", 100000f);
        }

        getSupportActionBar().setTitle("Trip Summary of " + trip_name);

        // Done Button Initialization
        Button mButtonDone = (Button) findViewById(R.id.button_done);
        // if Done is clicked, go back to the main activity
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
