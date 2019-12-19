/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.ui.home;

/**
 * this class represents the data supporting the adapter
 */

class ListElement {
    String title;
    String date;
    String time;

    public ListElement(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }
}
