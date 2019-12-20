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
    private long trip_id=0;
    private String date;
    private String name;
    private String timeTaken;
    private Double distance;
    private float av_temperature;
    private float av_pressure;
    private String latitudes;
    private String longitudes;

    /** Trip Constructor
     * @param date date of the trip
     * @param name name of the trip
     * @param timeTaken time taken for the trip
     * @param distance distance travelled on trip
     * @param av_temperature average temperature sensed during the trip
     * @param av_pressure average pressure sensed during the trip
     * @param latitudes list of latitudes from duration of the trip
     * @param longitudes list of longitudes from duration of the trip
     */
    public Trip(String date, String name, String timeTaken, Double distance, float av_temperature, float av_pressure, String latitudes, String longitudes) {
        this.trip_id= trip_id;
        this.date= date;
        this.name= name;
        this.timeTaken = timeTaken;
        this.distance = distance;
        this.av_temperature= av_temperature;
        this.av_pressure= av_pressure;
        this.latitudes= latitudes;
        this.longitudes= longitudes;
    }

    @androidx.annotation.NonNull
    public long getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(long trip_id) {
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

    public float getAv_temperature() {
        return av_temperature;
    }

    public void setAv_temperature(float av_temperature) {
        this.av_temperature = av_temperature;
    }

    public float getAv_pressure() {
        return av_pressure;
    }

    public void setAv_pressure(float av_pressure) {
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

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}