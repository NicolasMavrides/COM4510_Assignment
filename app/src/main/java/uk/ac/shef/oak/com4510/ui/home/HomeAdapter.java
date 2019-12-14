/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.ui.home;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.shef.oak.com451.R;
import uk.ac.shef.oak.com4510.ShowImageActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.View_Holder> {
    static private Context context;
    private static List<ListElement> items;

    public HomeAdapter(List<ListElement> items) {
        this.items = items;
    }

    public HomeAdapter(Context cont, List<ListElement> items) {
        super();
        this.items = items;
        context = cont;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image,
                parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, final int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView
        if (holder!=null && items.get(position)!=null) {
            holder.title.setText(items.get(position).title);
            holder.preview.setText(items.get(position).preview);
            holder.imageView.setImageResource(items.get(position).image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        Image image;
        TextView title;
        TextView preview;
        ImageView imageView;


        View_Holder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            preview = itemView.findViewById(R.id.preview_text);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }
}