package uk.ac.shef.oak.com4510.database;
import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LatLngConverter {
    @TypeConverter
    public List<Float> storedStringToFloat(String value) {
        List<String> stringLat = Arrays.asList(value.split("\\s*,\\s*"));
        List<Float> lats = new ArrayList<>();
        for (String lat : stringLat){
            lats.add(Float.parseFloat(lat));
        }
        return lats;
    }

    @TypeConverter
    public String floatToStoredString(List<Float> latitudes) {
        String value = "";

        for (Float lat : latitudes)
            value += lat + ",";

        return value;
    }
}