/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.database;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices=@Index(value={"name"}))
public class Trip {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int trip_id=0;
    private String date;
    private String time;
    private String name;
    private String description;
    private int av_temperature;
    private int av_pressure;
    private String latitudes;
    private String longitudes;
    private String photo_ids;


    public Trip(String date, String time, String name, String description, int av_temperature, int av_pressure, String latitudes, String longitudes, String photo_ids) {
        this.date= date;
        this.time= time;
        this.name= name;
        this.description= description;
        this.av_temperature= av_temperature;
        this.av_pressure= av_pressure;
        this.latitudes= latitudes;
        this.longitudes= longitudes;
        this.photo_ids= photo_ids;
    }

    @androidx.annotation.NonNull
    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getAv_temperature() {
        return av_temperature;
    }

    public void setAv_temperature(int av_temperature) {
        this.av_temperature = av_temperature;
    }

    public int getAv_pressure() {
        return av_pressure;
    }

    public void setAv_pressure(int av_pressure) {
        this.av_pressure = av_pressure;
    }

    public String getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(String latitudes) {
        this.latitudes = latitudes;
    }

    public String getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(String longitudes) {
        this.longitudes = longitudes;
    }

    public String getPhoto_ids() {
        return photo_ids;
    }

    public void setPhoto_ids(String photo_ids) {
        this.photo_ids = photo_ids;
    }
}