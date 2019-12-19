package uk.ac.shef.oak.com4510.database;

import java.util.List;

/**
 * Class for defining Photo_ids field for Trips table. Each trip stores a list of 0 or more IDs of
 * the photos taken on that trip. IDs are stored as a single string and converted to a list by the
 * Photo_IdConverter for application functionalities.
 */

public class Photo_ids {
    private List<String> photo_ids;

    public Photo_ids(List<String> photo_ids) {
        this.photo_ids = photo_ids;
    }

    public List<String> getPhoto_ids() {
        return photo_ids;
    }

    public void setPhoto_ids(List<String> photo_ids) {
        this.photo_ids = photo_ids;
    }

}