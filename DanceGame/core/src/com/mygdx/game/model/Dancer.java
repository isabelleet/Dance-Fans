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
    private Coordinates coordinates;

    /**
     * Creates a dancer that starts on a specified tile.
     * @param type the name of the dancer.
     * @param coordinates which tile the dancer should be on.
     */
    public Dancer(Color color, Type type, Coordinates coordinates) {
        this.color = color;
        this.type = type;
        this.coordinates = coordinates;
    }

    /**
     * Creates a dancer on tile 0.
     * @param type the name of the dancer.
     */
    public Dancer(Color color, Type type) {
        this.color = color;
        this.type = type;
        //Risk for bugs
        this.coordinates = new Coordinates(0,0);
    }

    /**
     * Sets the index of the dancer.
     * @param coordinates which index the dancer should be set to.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates);
    }

    /**
     * Getter for a dancer's index.
     * @return the index of the dancer.
     */
    public Coordinates getCoordinates() {
        return new Coordinates(coordinates);
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
