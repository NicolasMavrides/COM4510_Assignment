package uk.ac.shef.oak.com4510.ui.newtrip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class NewtripFragment extends Fragment {
    private Fragment fragment;
    private NewtripViewModel newtripViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newtripViewModel =
                ViewModelProviders.of(this).get(NewtripViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_newtrip, container, false);
        final TextView textView = root.findViewById(R.id.date);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        Log.i("date: ", formattedDate);
        textView.setText(formattedDate);

        fragment = this;

        Button button =  root.findViewById(R.id.create_trip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AutoCompleteTextView titleView = root.findViewById(R.id.route_name);
                String title = titleView.getText().toString();
                /*AutoCompleteTextView descriptionView = view.findViewById(R.id.description);
                String description = descriptionView.getText().toString();*/

                // hiding the keyboard after button click
                // TODO - when no trip name is entered
                if (title != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(titleView.getWindowToken(), 0);
                    Log.i("title: ", title);
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("name", title);
                    intent.putExtra("date", formattedDate);
                    getActivity().startActivity(intent);
                }
                else {
                    Snackbar.make(titleView, "Please enter a trip name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        return root;
    }
}