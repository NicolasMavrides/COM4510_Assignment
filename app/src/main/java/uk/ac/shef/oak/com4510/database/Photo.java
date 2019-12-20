package uk.ac.shef.oak.com4510.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Photo class for photo database entity in Rooms
 * Stored seperately from Trips in Photo table and are accessible from trips by
 * their unique ID number.
 */

@Entity(indices={@Index(value={"title"})})
public class Photo {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private long photo_id=0;
    private String title;
    private String description;
    private String file_path;
    private String date;
    private float latitude;
    private float longitude;
    private float temperature;
    private float pressure;
    private long trip_id;

    /** Photo Constructor
     * @param title photo title
     * @param description photo description
     * @param file_path file path to photo on device storage
     * @param latitude latitude where photo was taken
     * @param longitude longitude where photo was taken
     * @param temperature temperature when photo was taken
     * @param pressure pressure when photo was taken
     * @param date date when photo was taken
     */

    public Photo(String title, String date, String description, String file_path, float latitude, float longitude, float temperature, float pressure, long trip_id) {
        this.title= title;
        this.description= description;
        this.file_path = file_path;
        this.latitude= latitude;
        this.longitude= longitude;
        this.temperature= temperature;
        this.pressure= pressure;
        this.date= date;
        this.trip_id = trip_id;
    }

    // Getter and Setter methods
    @androidx.annotation.NonNull
    public long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(long photo_id) {
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

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(long trip_id) {
        this.trip_id = trip_id;
    }
}