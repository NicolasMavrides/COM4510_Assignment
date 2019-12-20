package uk.ac.shef.oak.com4510.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

/**
 * DAO for Trips
 */
@Dao
public interface TripDAO {
    @Insert
    void insertAll(Trip... tripData);

    @Insert
    long insertTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

    @Query("SELECT * FROM Trip ORDER BY date DESC")
    LiveData<List<Trip>> retrieveAllTrips();

    @Query("SELECT * FROM Trip WHERE name = :title")
    LiveData<List<Trip>> retrieveTripByTitle(String title);

    @Delete
    void deleteAll(Trip... tripData);

}
