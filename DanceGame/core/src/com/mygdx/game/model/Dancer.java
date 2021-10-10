package com.mygdx.game.model;

import java.io.Serializable;

public class Dancer implements Serializable {

    private int id;
    private String name;
    //TODO: only update this index when previewDanceFloor becomes danceFloor?
    private int index;

    public Dancer(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public Dancer(String name) {
        this.name = name;
        //Risk for bugs
        this.index = 0;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }


    public String getSpriteName() {
        return this.name;
    }
}
