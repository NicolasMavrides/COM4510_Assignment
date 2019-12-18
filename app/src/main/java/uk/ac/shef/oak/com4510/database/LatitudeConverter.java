package uk.ac.shef.oak.com4510.database;
import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;

public class LatitudeConverter {
    @TypeConverter
    public Latitudes storedStringToLatitudes(String value) {
        List<String> latitudes = Arrays.asList(value.split("\\s*,\\s*"));
        return new Latitudes(latitudes);
    }

    @TypeConverter
    public String latitudesToStoredString(Latitudes latitudes) {
        String value = "";

        for (String lat :latitudes.getLatitudes())
            value += lat + ",";

        return value;
    }
}