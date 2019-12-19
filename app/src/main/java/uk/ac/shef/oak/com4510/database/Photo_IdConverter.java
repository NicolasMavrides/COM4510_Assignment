package uk.ac.shef.oak.com4510.database;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Converter class for Photo ID values in Trips table. Converts between types String and List<String>
 * so that values can be stored in single Room database entry and likewise be accessed as a list
 * of strings which are then parsed as integers for the application functionalities.
 */

public class Photo_IdConverter {
    @TypeConverter
    public Photo_ids storedStringToPhoto_id(String value) {
        List<String> photo_ids = Arrays.asList(value.split("\\s*,\\s*"));
        return new Photo_ids(photo_ids);
    }

    @TypeConverter
    public String photo_idToStoredString(Photo_ids photo_ids) {
        String value = "";

        for (String ids :photo_ids.getPhoto_ids())
            value += ids + ",";

        return value;
    }
}