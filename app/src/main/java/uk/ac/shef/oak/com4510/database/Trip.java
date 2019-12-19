package uk.ac.shef.oak.com4510.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Trip class for photo database entity in Rooms Stored seperately from Photos in Trip table.
 * Photos for a specific trip are accessible in photo_ids by their unique ID number.
 */

@Entity(indices=@Index(value={"name"}))
public class Trip {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int trip_id=0;
    private String date;
    private String name;
    private String description;
    private float av_temperature;
    private float av_pressure;
    private String latitudes;
    private String longitudes;
    private String photo_ids;

    /** Trip Constructor
     * @param date date of the trip
     * @param name name of the trip
     * @param description description of the trip
     * @param av_temperature average temperature sensed during the trip
     * @param av_pressure average pressure sensed during the trip
     * @param latitudes list of latitudes from duration of the trip
     * @param longitudes list of longitudes from duration of the trip
     * @param photo_ids list of IDs of photos that were taken during the trip (links the photos to
     *                  their respective trip)
     */
    public Trip(String date, String name, String description, float av_temperature, float av_pressure, String latitudes, String longitudes, String photo_ids) {
        this.trip_id= trip_id;
        this.date= date;
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

    public float getAv_temperature() {
        return av_temperature;
    }

    public void setAv_temperature(int av_temperature) {
        this.av_temperature = av_temperature;
    }

    public float getAv_pressure() {
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