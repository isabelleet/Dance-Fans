package com.mygdx.game.model;

/**
 * Dancer is an abstract class which can occupy a tile. This might be changed later if other types of objects can
 * occupy a tile. It keeps track of what index it is standing on, and its own name.
 *
 * Is used in DanceFloorTile.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 */

public abstract class Dancer {
    private final Color color;
    private final Type type;
    //TODO: only update this index when previewDanceFloor becomes danceFloor?
    private int index;

    /**
     * Creates a dancer that starts on a specified tile.
     * @param type the name of the dancer.
     * @param index which tile the dancer should be on.
     */
    public Dancer(Color color, Type type, int index) {
        this.color = color;
        this.type = type;
        this.index = index;
    }

    /**
     * Creates a dancer on tile 0.
     * @param type the name of the dancer.
     */
    public Dancer(Color color, Type type) {
        this.color = color;
        this.type = type;
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
    public Type getType() {
        return this.type;
    }

    public Color getColor() {
        return this.color;
    }
}
