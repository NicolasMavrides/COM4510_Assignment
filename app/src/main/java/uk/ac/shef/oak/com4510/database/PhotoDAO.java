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
    void insertAll(PhotoData... photodata);

    @Insert
    void insert(PhotoData photodata);

    @Delete
    void delete(PhotoData photoData);

    @Query("SELECT * FROM PhotoData ORDER BY title ASC")
    LiveData<List<PhotoData>> retrieveAllData();

    @Query("SELECT * FROM PhotoData WHERE title = :title")
    PhotoData retrieveByTitle(String title);

    //TODO Other queries

    @Delete
    void deleteAll(PhotoData... photoData);
}
