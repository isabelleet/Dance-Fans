package com.mygdx.game.model;

import com.mygdx.game.Enums.Color;
import com.mygdx.game.Enums.Type;

/**
 * MainDancer is the representation of the player on the DanceFloor and extends Dancer. It also keeps track of
 * where it exists in the preview.
 *
 * Is used by Player, DanceFloor.
 *
 * Uses Color, Type, Coordinates, Dancer, FloorObject.
 *
 *
 * @author Jakob Persson
 * @author Joar Granström
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class MainDancer extends Dancer {
    // used to preview its position before the player confirms their move.
    private Coordinates preCoords;

    /**
     * Constructor, creates a new MainDancer with the given color and coordinates for a specific tile.
     * @
     * @param coords which coordinates it should start on.
     */
    protected MainDancer(Color color, Coordinates coords) {
        super(color, Type.MD, coords);
    }
    protected MainDancer(Color color, Type type, Coordinates coords) {
        super(color, type, coords);
    }

    /**
     * Getter for the coordinates in the preview.
     * @return the coordinates in the preview.
     */
    protected Coordinates getPreviewCoordinates() {
        return new Coordinates(preCoords);
    }

    /**
     * Setter for the coordinates in the preview.
     * @param coords is the coordinates this has moved to in the preview.
     */
    protected void setPreviewCoordinates(Coordinates coords) {
        this.preCoords = new Coordinates(coords);
    }
}
