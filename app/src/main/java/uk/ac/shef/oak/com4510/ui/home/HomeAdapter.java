package uk.ac.shef.oak.com4510.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import uk.ac.shef.oak.com451.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import uk.ac.shef.oak.com4510.database.Trip;

/**
 * Home adapter class for the User Interface and Data Sources
 * related to the app's Home Page functions
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private static List<Trip> items;

    public HomeAdapter(List<Trip> items) {
        this.items = items;
    }

    public void updateData(List<Trip> trips){
        items.clear();
        items.addAll(trips);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trips, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder!=null && items.get(position)!=null) {
            holder.title.setText(items.get(position).getName());

            Date date = new Date(items.get(position).getName());
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(date);

            SimpleDateFormat tf = new SimpleDateFormat("h:mm a");
            final String formattedTime = tf.format(date);

            holder.time.setText(formattedTime);
            holder.date.setText(formattedDate);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trip_title);
            date = itemView.findViewById(R.id.trip_date);
            time = itemView.findViewById(R.id.trip_time);
        }
    }
}
