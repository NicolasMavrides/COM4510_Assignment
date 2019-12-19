package uk.ac.shef.oak.com4510.ui.newtrip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import uk.ac.shef.oak.com451.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import uk.ac.shef.oak.com4510.MapsActivity;

/**
 * New Trip fragment code for the New Trip activity
 */

public class NewtripFragment extends Fragment {
    private Fragment fragment;
    private NewtripViewModel newtripViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newtripViewModel =
                ViewModelProviders.of(this).get(NewtripViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_newtrip, container, false);
        final TextView textView = root.findViewById(R.id.date);
        // Get current date and time for trip commencement
        final Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        textView.setText(formattedDate);

        Button button =  root.findViewById(R.id.create_trip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AutoCompleteTextView titleView = root.findViewById(R.id.route_name);
                String title = titleView.getText().toString();

                // hiding the keyboard after button click
                if (title.replaceAll("\\s+","").length() != 0) { // makes sure that title is not empty
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(titleView.getWindowToken(), 0);
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("name", title);
                    intent.putExtra("dateTime", String.valueOf(c));

                    // Start the trip
                    getActivity().startActivity(intent);
                }
                else {
                    // turn into public func related to activity
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Please enter a trip name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return root;
    }
}