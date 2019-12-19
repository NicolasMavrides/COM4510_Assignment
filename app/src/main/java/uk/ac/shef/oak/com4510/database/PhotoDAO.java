/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PhotoDAO {
    @Insert
    void insertAll(Photo... photodata);

    @Insert
    long insertPhoto(Photo photodata);

    @Delete
    void deletePhoto(Photo photo);

    @Query("SELECT * FROM Photo ORDER BY date ASC")
    LiveData<List<Photo>> retrieveAllPhotos();

    @Query("SELECT * FROM Photo WHERE title = :title")
    List<Photo> retrievePhotoByTitle(String title);

    @Query("SELECT * FROM Photo WHERE photo_id = :photo_id")
    List<Photo> retrievePhotoById(int photo_id);

    //TODO Other queries

    @Delete
    void deleteAll(Photo... photoData);
}
