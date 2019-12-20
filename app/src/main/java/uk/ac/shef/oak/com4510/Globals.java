package uk.ac.shef.oak.com4510;

public class Globals {
    public static final String RESTART_INTENT = "uk.ac.shef.oak.com4510.restarter";
    public static String currentTripId;

    public static void setTripId(String id){
        currentTripId = id;
    }
}
