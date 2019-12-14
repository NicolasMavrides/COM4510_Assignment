/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.database;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.Polyline;

import java.util.List;

@Entity(indices={@Index(value={"title"})})
public class Trip {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int trip_id=0;
    private String date;
    private String name;
    private String description;
    private int temperature;
    private int pressure;
    private List<Photo> file;
    private Polyline polyline;

    //TODO Date and Time

    @Ignore
    public Bitmap picture;

    public Trip(String date, String name, String description, int temperature, int pressure, List<Photo> file, Polyline polyline) {
        this.date= date;
        this.name= name;
        this.temperature= temperature;
        this.pressure= pressure;
        this.file= file;
        this.polyline= polyline;
    }

    @androidx.annotation.NonNull
    public int getId() {
        return trip_id;
    }

    public void setId(int id) {
        this.trip_id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public List<Photo> getFile() {
        return file;
    }

    public void setFile(List<Photo> file) {
        this.file = file;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}