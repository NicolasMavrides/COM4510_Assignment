/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.ui.home;

/**
 * this class represents the data supporting the adapter
 */

class ListElement {
    int image;
    String title;
    String preview;

    public ListElement(int image, String title, String preview) {
        this.image = image;
        this.title = title;
        this.preview = preview;
    }
}
