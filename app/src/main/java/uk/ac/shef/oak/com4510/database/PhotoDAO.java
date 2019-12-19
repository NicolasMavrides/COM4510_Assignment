package uk.ac.shef.oak.com4510.database;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * DAO for Photos
 */

@Dao
public interface PhotoDAO {
    @Insert
    void insertAll(Photo... photodata);

    @Insert
    long insertPhoto(Photo photodata);

    @Delete
    void deletePhoto(Photo photo);

    @Query("SELECT * FROM Photo ORDER BY date DESC")
    LiveData<List<Photo>> retrieveAllPhotos();

    @Query("SELECT * FROM Photo WHERE title = :title")
    List<Photo> retrievePhotoByTitle(String title);

    @Query("SELECT * FROM Photo WHERE photo_id = :photo_id")
    List<Photo> retrievePhotoById(int photo_id);

    @Query("SELECT * FROM Photo WHERE title = :date")
    List<Photo> retrievePhotoByDate(String date);

    @Query("SELECT * FROM Photo WHERE title = :time")
    List<Photo> retrievePhotoByTime(String time);

    @Delete
    void deleteAll(Photo... photoData);
}
