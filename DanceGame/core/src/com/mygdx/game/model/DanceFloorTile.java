package com.mygdx.game.model;

/**
 * DanceFloorTile keeps track of what is on that specific tile. Can change and tell other classes what is
 * currently on it.
 *
 * Is used by DanceFloor.
 *
 * @author Jakob Persson
 * @author Joar Granström
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class DanceFloorTile {
    private Type type;

    /**
     * Creates a new DanceFloorTile with an occupant.
     * @param occupant the name of the occupant.
     */
    public DanceFloorTile(Type type) {
        this.type = type;
    }

    /**
     * Getter for the name of the sprite on the tile.
     * @return A string with the name of the occupant.
     */
    public Type getType() {
        return type;
    }

    /**
     * Gives a Dancer to the tile.
     * @param type the Dancer that is occupying the tile.
     */
    public void setOccupant(Type type) {
        this.type = type;

    }

    // Constructor


}
