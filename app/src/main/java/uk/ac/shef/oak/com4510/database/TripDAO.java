/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface TripDAO {
    @Insert
    void insertAll(Trip... tripData);

    @Insert
    void insertTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

    @Query("SELECT * FROM Trip ORDER BY name ASC")
    LiveData<List<Trip>> retrieveAllTrips();

    @Query("SELECT * FROM Trip WHERE name = :title")
    List<Trip> retrieveTripByTitle(String title);

    //TODO Other queries

    @Delete
    void deleteAll(Trip... tripData);

}
