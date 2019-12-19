package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import uk.ac.shef.oak.com451.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    // Activity Related Variables
    private static AppCompatActivity activity;
    public static AppCompatActivity getActivity() {
        return activity;
    }
    public static void setActivity(AppCompatActivity activity) {
        MainActivity.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivity(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_gallery, R.id.navigation_newtrip)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        SharedPreferences prefs= getSharedPreferences("uk.ac.shef.oak.ServiceRunning", MODE_PRIVATE);
        String tracking_mode = prefs.getString("tracking", "stopped");
//        Log.i("Shared Preferences", tracking_mode);
        if (!tracking_mode.equals("stopped")) {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
