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

    private Coordinates preCoords;

    /**
     * Creates a new MainDancer.
     * @param type which MainDancer.
     * @param coords which coordinates it should start on.
     */
    public MainDancer(Color color, Type type, Coordinates coords) {
        super(color, type, coords);
    }


    public Coordinates getPreviewCoords() {
        return new Coordinates(preCoords);
    }

    /**
     * Setter for the index used to preview the Dancers position before the player confirms their move.
     * @param coords which coordinates to move the MainDancer to.
     */
    public void setPreviewCoords(Coordinates coords) {
        this.preCoords = new Coordinates(coords);
    }
}
