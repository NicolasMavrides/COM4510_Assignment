/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.database;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices={@Index(value={"title"})})
public class Photo {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int photo_id=0;
    private String title;
    private String description;
    private String file;
    private int trip_id;

    public Photo(String title, String description, String file, int trip_id) {
        this.title= title;
        this.description= description;
        this.file= file;
        this.trip_id= trip_id;
    }

    @androidx.annotation.NonNull
    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

}