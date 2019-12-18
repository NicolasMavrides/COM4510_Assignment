package uk.ac.shef.oak.com4510.database;

import java.util.List;

public class Latitudes {
    private List<String> latitudes;

    public Latitudes(List<String> latitudes) {
        this.latitudes = latitudes;
    }

    public List<String> getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(List<String> latitudes) {
        this.latitudes = latitudes;
    }
}