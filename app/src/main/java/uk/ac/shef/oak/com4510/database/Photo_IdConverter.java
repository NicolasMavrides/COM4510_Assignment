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

    /**
     * storedStringToPhoto_id: convert from string to List of photo ID Strings
     * @param value the string to be converted
     */

    @TypeConverter
    public Photo_ids storedStringToPhoto_id(String value) {
        List<String> photo_ids = Arrays.asList(value.split("\\s*,\\s*"));
        return new Photo_ids(photo_ids);
    }


    /**
     * photo_idToStoredString: convert from list of photo ID Strings to a continuous string
     * @param photo_ids the string list to be converted
     */

    @TypeConverter
    public String photo_idToStoredString(Photo_ids photo_ids) {
        String value = "";

        for (String ids :photo_ids.getPhoto_ids())
            value += ids + ",";

        return value;
    }
}