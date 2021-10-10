package com.mygdx.game.model;


import java.io.Serializable;

public class MainDancer extends Dancer implements Serializable {

    private int previewIndex;

    /**
     * Creates a new MainDancer.
     * @param name name of the MainDancer.
     * @param index which index it should start on.
     */
    public MainDancer(String name, int index) {
        super(name, index);
    }

    /**
     * Getter for the index used to preview the Dancers position before the player confirms their move.
     * @return an int describing where the MainDancer is.
     */
    public int getPreviewIndex() {
        return previewIndex;
    }

    /**
     * Setter for the index used to preview the Dancers position before the player confirms their move.
     * @param index which index to move the MainDancer to.
     */
    public void setPreviewIndex(int index) {
        this.previewIndex = index;
    }
}
