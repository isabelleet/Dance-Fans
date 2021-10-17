package com.mygdx.game.model;

/**
 * DanceFloorTile keeps track of what is on that specific tile. Can change and tell other classes what is
 * currently on it. The thing on the tile kept track of using Color and Type.
 *
 * Is used by DanceFloor.
 *
 * @author Jakob Persson
 * @author Joar Granström
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class DanceFloorTile {
    private Color color;
    private Type type;

    /**
     * Creates a new DanceFloorTile with an occupant.
     * @param type the name of the occupant.
     */
    public DanceFloorTile(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Color getColor(){
        return color;
    }

    /**
     * Gives a Dancer to the tile.
     * @param type the Dancer that is occupying the tile.
     */
    public void setOccupant(Color color, Type type) {
        this.color = color;
        this.type = type;
    }



}
