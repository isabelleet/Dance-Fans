package com.mygdx.game.model;

import com.mygdx.game.model.Enums.Color;
import com.mygdx.game.model.Enums.Type;

/**
 * Dancer is an abstract class which extends FloorObject. There might be functionality added in the future which is
 * dancer specific.
 *
 * Is used by DanceFan and MainDancer.
 *
 * Uses Color, Type and Coordinates.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 */

public abstract class Dancer extends FloorObject {

    /**
     * Creates a dancer that which has a specific color, type and coordinates for a specified tile.
     * @param type the name of the dancer.
     * @param coordinates which tile the dancer should be on.
     */
    public Dancer(Color color, Type type, Coordinates coordinates) {
        super(color, type, coordinates);
    }

}
