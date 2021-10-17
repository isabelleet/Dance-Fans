package com.mygdx.game.model;

/**
 * Dancer is an abstract class which extends FloorObject. There might be functionality added in the future which is
 * dancer specific.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 */

public abstract class Dancer extends FloorObject {

    /**
     * Creates a dancer that starts on a specified tile.
     * @param type the name of the dancer.
     * @param coordinates which tile the dancer should be on.
     */
    public Dancer(Color color, Type type, Coordinates coordinates) {
        super(color, type, coordinates);
    }

}
