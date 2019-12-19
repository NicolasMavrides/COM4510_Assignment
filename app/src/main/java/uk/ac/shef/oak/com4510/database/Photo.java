/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


/*,
        foreignKeys = @ForeignKey(entity = Trip.class,
        parentColumns = "photo_id",
        childColumns = "trip_id",
        onDelete = CASCADE))
*/

@Entity(indices={@Index(value={"title"})})
public class Photo {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int photo_id=0;
    private String title;
    private String description;
    private String file_path;
    private float latitude;
    private float longitude;
    private int temperature;
    private int pressure;

   /* @ColumnInfo(name = "trip_fk")
    private int trip_id; */

    public Photo(String title, String description, String file_path, float latitude, float longitude, int temperature, int pressure) {
        this.title= title;
        this.description= description;
        this.file_path= file_path;
        this.latitude= latitude;
        this.longitude= longitude;
        this.temperature= temperature;
        this.pressure= pressure;
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

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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
}