package uk.ac.shef.oak.com4510.database;

import java.util.List;

public class Longitudes {
    private List<String> longitudes;

    public Longitudes(List<String> longitudes) {
        this.longitudes = longitudes;
    }

    public List<String> getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(List<String> longitudes) {
        this.longitudes = longitudes;
    }

}