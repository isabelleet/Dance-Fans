package com.mygdx.game.model;

import java.io.Serializable;

public abstract class Dancer implements Serializable {

    private String name;
    //TODO: only update this index when previewDanceFloor becomes danceFloor?
    private int index;

    /**
     * Creates a dancer that starts on a specified tile.
     * @param name the name of the dancer.
     * @param index which tile the dancer should be on.
     */
    public Dancer(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Creates a dancer on tile 0.
     * @param name the name of the dancer.
     */
    public Dancer(String name) {
        this.name = name;
        //Risk for bugs
        this.index = 0;
    }

    /**
     * Sets the index of the dancer.
     * @param index which index the dancer should be set to.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter for a dancer's index.
     * @return the index of the dancer.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Getter for the dancer's name.
     * @return the name of the dancer.
     */
    public String getSpriteName() {
        return this.name;
    }
}
