package com.mygdx.game.model;

import java.io.Serializable;

/**
 * MainDancer is the representation of the player on the DanceFloor and extends Dancer. It also keeps track of
 * where it exists in the preview.
 *
 * Is used in Player.
 *
 * @author Jakob Persson
 * @author Joar Granström
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class MainDancer extends Dancer {

    private int previewIndex;

    /**
     * Creates a new MainDancer.
     * @param type which MainDancer.
     * @param index which index it should start on.
     */
    public MainDancer(Color color, Type type, int index) {
        super(color, type, index);
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
