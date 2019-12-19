package uk.ac.shef.oak.com4510.database;
import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Converter class for latitude and longitude values. Converts between types String and List<Float>
 * so that values can be stored in single Room database entry and likewise be accessed as a list
 * of floats for the application functionalities
 */

public class LatLngConverter {
    @TypeConverter
    public List<Float> storedStringToFloat(String value) {
        List<String> string = Arrays.asList(value.split("\\s*,\\s*"));
        List<Float> floats = new ArrayList<>();
        for (String val : string){
            floats.add(Float.parseFloat(val));
        }
        return floats;
    }

    @TypeConverter
    public String floatToStoredString(List<Float> values) {
        String strValue = "";

        for (Float val : values)
            strValue += val + ",";

        return strValue;
    }
}