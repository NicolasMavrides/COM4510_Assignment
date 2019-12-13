/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.database;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices={@Index(value={"title"})})
public class PhotoData {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int id=0;
    private String title;
    private String description;
    private String file;
    //TODO Date and Time

    @Ignore
    public Bitmap picture;

    public PhotoData(String title, String description, String file) {
        this.title= title;
        this.description= description;
    }

    @androidx.annotation.NonNull
    public int getId() {
        return id;
    }
    public void setId(@androidx.annotation.NonNull int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Bitmap getPicture() {
        return picture;
    }
    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }
}
