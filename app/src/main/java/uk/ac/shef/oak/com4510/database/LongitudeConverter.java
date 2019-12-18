package uk.ac.shef.oak.com4510.database;
import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;

public class LongitudeConverter {
    @TypeConverter
    public Longitudes storedStringToLongitudes(String value) {
        List<String> longitudes = Arrays.asList(value.split("\\s*,\\s*"));
        return new Longitudes(longitudes);
    }

    @TypeConverter
    public String longitudesToStoredString(Longitudes longitudes) {
        String value = "";

        for (String lo :longitudes.getLongitudes())
            value += lo + ",";

        return value;
    }
}