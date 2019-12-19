/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.ac.shef.oak.com451.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import uk.ac.shef.oak.com4510.database.Trip;

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
            holder.time.setText(items.get(position).getTime());
            holder.date.setText(items.get(position).getDate());
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
