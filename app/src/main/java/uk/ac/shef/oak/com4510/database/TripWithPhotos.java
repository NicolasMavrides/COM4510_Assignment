package uk.ac.shef.oak.com4510.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;


public class TripWithPhotos {
    @Embedded public Trip trip;
    @Relation(
            parentColumn = "trip_id",
            entityColumn = "photo_id"
    )
    public List<Photo> photos;
}
