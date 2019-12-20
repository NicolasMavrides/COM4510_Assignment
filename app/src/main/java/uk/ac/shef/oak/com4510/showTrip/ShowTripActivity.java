package uk.ac.shef.oak.com4510.showTrip;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.database.Trip;
import uk.ac.shef.oak.com4510.ui.home.HomeAdapter;

public class ShowTripActivity extends AppCompatActivity {

    // Activity Related Variables
    private static AppCompatActivity activity;
    public static AppCompatActivity getActivity() {
        return activity;
    }
    private static String trip_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        activity = this;

        Bundle b = getIntent().getExtras();
        int position=-1;
        if(b != null) {
            // this is the image position in the itemList
            position = b.getInt("position");
            Log.i("position: ", String.valueOf(position));
            if (position!=-1){
                Trip element= HomeAdapter.getItems().get(position);

                getSupportActionBar().setTitle(element.getName());
                trip_name = element.getName();

                //Date
                TextView dateText = findViewById(R.id.view_date);
                Date date = new Date(element.getDate());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(date);
                dateText.setText(formattedDate);

                //Time
                TextView time = findViewById(R.id.view_time_taken);
                time.setText(element.getTimeTaken());

                //Distance
                DecimalFormat decf = new DecimalFormat("##.###");
                TextView distance = findViewById(R.id.view_distance_traveled);
                distance.setText(decf.format(element.getDistance())+"km");

                //Average Temp
                float defaultValue = 100000f;
                TextView avg_temp = findViewById(R.id.view_average_temp);
                if (element.getAv_temperature() == defaultValue){
                    avg_temp.setText("N/A");
                }
                else {
                    avg_temp.setText(String.valueOf(element.getAv_temperature()));
                }

                //Average Pressure
                TextView avg_press = findViewById(R.id.view_average_pressure);
                if (element.getAv_pressure() == defaultValue){
                    avg_press.setText("N/A");
                }
                else {
                    avg_press.setText(String.valueOf(element.getAv_pressure()));
                }
            }
        }
    }

    public static String getTripName(){
        return trip_name;
    }
}